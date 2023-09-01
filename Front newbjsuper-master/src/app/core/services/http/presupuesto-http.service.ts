import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ServiceService } from '../service.service';
import { RespPagination } from '../../models/resp-pagination';
import { AuthService } from '../auth/auth.service';
import { Response } from '../utils/response';

@Injectable()
export class PresupuestoHTTPService extends ServiceService {

  constructor(
    private authService: AuthService
  ) {
    super();
  }

  public getAllPagination(params: any, data: any): Observable<RespPagination<any>> {
    return this.http.post<RespPagination<any>>(this.BASE_URL_API + 'presupuesto/all/pagination?' + $.param(params), data);
  }
  printList(dataFilter: any, paramURL: any, typePrint: string) {
    return this.http.post<any>(this.BASE_URL_API + 'reportes/presupuesto/' + typePrint + '/list?' + $.param(paramURL), dataFilter, this.getHeaderArrayBuffer());
  }
  public getPresupuestoDetalle(id: number): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'presupuesto/' + id);
  }
  public getDataPresupuestos(): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'presupuesto/dataPresupuestos');
  }
  public getDataFilter(): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'presupuesto/dataFilter');
  }
  public savePresupuesto(data: any): Observable<Response<any>> {
    return this.http.post<Response<any>>(this.BASE_URL_API + 'presupuesto', data);
  }
  public updateEstadoCbtePresupuesto(id: number, data: any): Observable<Response<any>> {
    return this.http.post<Response<any>>(this.BASE_URL_API + 'presupuesto/' + id + '/estado-cbte', data);
  }
  public downloadCbte(id: number, type: string = "A4", typeResponse: string = "byte") {
    return this.http.get<any>(this.BASE_URL_API + 'reportes/presupuesto/cbte/pdf/' + id + "?tipo=" + type, this.getHeaderArrayBuffer());
  }
}
