import { Injectable } from '@angular/core';
import { retry } from 'rxjs/operators';
import { ServiceService } from '../service.service';
import { Observable } from 'rxjs';
import { Response } from '../utils/response';
import { RespPagination } from '../../models/resp-pagination';

@Injectable()
export class UtilHTTPService extends ServiceService {

  constructor() {
    super();
  }

  public enableOrDisableAlertaVencimientos(data: any, value: boolean): Observable<Response<any>> {
    let url;
    if (value) {
      url = this.BASE_URL_API + "util/vencimiento/alerta/enable";
    } else {
      url = this.BASE_URL_API + "util/vencimiento/alerta/disable";
    }
    return this.http.put<any>(url, data)
      .pipe(
        retry(1),
      );
  }
  public getDashBoard(): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL + "api/v1/util/dashboard");
  }
}
