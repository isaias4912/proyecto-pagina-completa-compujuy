import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ServiceService } from '../service.service';
import { RespPagination } from '../../models/resp-pagination';
import { Response } from '../utils/response';

@Injectable()
export class CtaCteHTTPService extends ServiceService {

  constructor() {
    super();
  }

  public getAllMtoCtaCtePagination(params: any, data: any): Observable<RespPagination<any>> {
    return this.http.post<RespPagination<any>>(this.BASE_URL_API + 'clientes/ctacte/mto/all/pagination?' + $.param(params), data);
  }
  public getAllMtoCtaCte(params: any, data: any): Observable<any> {
    return this.http.post<any>(this.BASE_URL_API + 'clientes/ctacte/mto/all?' + $.param(params), data);
  }
  printlMtoCtaCte(paramURL: any, dataFilter: any, typePrint: string) {
    return this.http.post<any>(this.BASE_URL_API + 'reportes/clientes/' + typePrint + '/ctacte/mto/all?' + $.param(paramURL), dataFilter, this.getHeaderArrayBuffer());
  }
  public getCtaCte(id: number): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'clientes/ctacte/' + id);
  }
  public getCtaCteDataPagos(): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'clientes/ctacte/dataPagos');
  }
  public getPreviewPagos(data: any): Observable<Response<any>> {
    return this.http.post<Response<any>>(this.BASE_URL_API + 'clientes/ctacte/pagarPreview', data);
  }
  public pagarCtaCte(data: any): Observable<Response<any>> {
    return this.http.post<Response<any>>(this.BASE_URL_API + 'clientes/ctacte/pagar', data);
  }
  public getPagosCtaCte(id: number): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'clientes/ctacte/mto/' + id + '/pagos');
  }
  public getPagosFromIdPagoCtaCte(id: number): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'clientes/ctacte/pagos/' + id + '/pagos');
  }
  public getDetailCtaCte(id: number): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'clientes/ctacte/mto/' + id + '/detail');
  }

}
