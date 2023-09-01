import {
    HttpEvent,
    HttpHandler,
    HttpRequest,
    HttpErrorResponse,
    HttpInterceptor
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, finalize } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import { LoaderService } from '../utils/loader.service';
import { Router } from '@angular/router';
import { Injectable } from '@angular/core';

@Injectable()
export class HttpIntercept implements HttpInterceptor {
    constructor(
        private toastrService: ToastrService,
        private loaderService: LoaderService,
        public router: Router
    ) {
    }
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        this.loaderService.show();
        return next.handle(request)
            .pipe(
                catchError((error: HttpErrorResponse) => {
                    this.checkCodes(error);
                    let errorMessage = this.getMessage(error);
                    this.toastrService.error(errorMessage);
                    let e=error.error;
                    e.message=errorMessage;
                    return throwError(e);
                }), finalize(() => {
                    this.loaderService.hide();
                })
            )
    }
    checkCodes(error: HttpErrorResponse) {
        if (error.status == 401 || error.status == 403) {
            this.router.navigate(['/home']);
        }
    }
    getMessage(error: HttpErrorResponse) {
        let errorMessage = '';
        if (error.status == 401 || error.status == 403) {
            errorMessage = 'Usuario sin permisos, verifique sus datos o vuelva a ingresar';
        } else if (error.error && error.error.message) {
            errorMessage = error.error.message;
            if (error.error.data) {
                let errors = null;
                error.error.data.forEach(e => {
                    errors = errors ? errors : '' + '<div>- ' + e.message + '</div>';
                });
                errorMessage = errorMessage + errors;
            }
        } else if (error.url.includes('oauth/token')) {
            errorMessage = 'Usuario/Contraseña invalidos';
        } else {
            errorMessage = 'Hubo un problema, verifique su conexion, reintentelo o vuelva a intentarlo má/*  */s tarde.';
        }
        return errorMessage;
    }
}