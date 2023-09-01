import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ServiceService } from '../service.service';
import { RespPagination } from '../../models/resp-pagination';
import { Response } from '../utils/response';

@Injectable()
export class CtaCteProvHTTPService extends ServiceService {

  constructor() {
    super();
  }

  public getAllMtoCtaCtePagination(params: any, data: any): Observable<RespPagination<any>> {
    return this.http.post<RespPagination<any>>(this.BASE_URL_API + 'proveedores/ctacte/mto/all/pagination?' + $.param(params), data);
  }
  public getAllMtoCtaCte(params: any, data: any): Observable<any> {
    return this.http.post<any>(this.BASE_URL_API + 'proveedores/ctacte/mto/all?' + $.param(params), data);
  }
  public getCtaCte(id: number): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'proveedores/ctacte/' + id);
  }
  public getCtaCteDataPagos(): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'proveedores/ctacte/dataPagos');
  }
  public getPreviewPagos(data: any): Observable<Response<any>> {
    return this.http.post<Response<any>>(this.BASE_URL_API + 'proveedores/ctacte/pagarPreview', data);
  }
  public pagarCtaCte(data: any): Observable<Response<any>> {
    return this.http.post<Response<any>>(this.BASE_URL_API + 'proveedores/ctacte/pagar', data);
  }
  public getPagosCtaCte(id: number): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'proveedores/ctacte/mto/' + id+'/pagos');
  }
  public getPagosFromIdPagoCtaCte(id: number): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'proveedores/ctacte/pagos/' + id+'/pagos');
  }
  public getDetailCtaCte(id: number): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'proveedores/ctacte/mto/' + id+'/detail');
  }

}
