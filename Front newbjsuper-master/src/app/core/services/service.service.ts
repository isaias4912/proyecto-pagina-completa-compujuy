import { Injectable } from '@angular/core';
import { throwError } from 'rxjs';
import { AppInjector } from './utils/app-injector';
import { HttpClient } from '@angular/common/http';
import { UtilService } from './utils/util.service';
import { environment } from 'src/environments/environment';

interface header {
  headers: any;
}

@Injectable()
export class ServiceService {
  protected BASE_URL = environment.baseURL;
  protected BASE_URL_API = environment.baseAPIURL;
  protected http: HttpClient;
  protected utilHTTPService: UtilService;
  constructor() {
    const injector = AppInjector.getInjector();
    this.http = injector.get(HttpClient);
    this.utilHTTPService = injector.get(UtilService);
  }
  // Error handling 
  public handleErrors(error) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(errorMessage);
  }

  getHeader(ignoreLoadingBar: boolean = false) {
    let header: header = { headers: {} };
    if (ignoreLoadingBar) {
      header.headers.ignoreLoadingBar = '';
    }
    return header;
  }
  getHeaderArrayBuffer() {
    let header: any = { responseType: 'arraybuffer' };
    return header;
  }
  getHeaderArrayBufferContenType() {
    let header: any = { contentType: 'arraybuffer' };
    return header;
  }
}
