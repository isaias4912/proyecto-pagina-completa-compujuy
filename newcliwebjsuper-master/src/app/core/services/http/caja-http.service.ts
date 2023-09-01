import { Injectable } from '@angular/core';
import { Caja } from '../../models/caja'
import { Observable } from 'rxjs';
import { ServiceService } from '../service.service';
import { RespPagination } from '../../models/resp-pagination';
import { Response } from '../utils/response';

@Injectable()
export class CajaHTTPService extends ServiceService {

  constructor() {
    super();
  }

  public getAllPagination(params: any, data: any): Observable<RespPagination<Caja>> {
    return this.http.post<RespPagination<Caja>>(this.BASE_URL_API + 'cajas/all/pagination?' + $.param(params), data);
  }
  public getCaja(id: number): Observable<Response<Caja>> {
    return this.http.get<Response<Caja>>(this.BASE_URL_API + 'cajas/' + id);
  }
  public getDataCaja(): Observable<Response<Caja>> {
    return this.http.get<Response<Caja>>(this.BASE_URL_API + 'cajas/data/caja');
  }
  public resumenCaja(idTransaccion: number): Observable<Response<Caja>> {
    return this.http.get<Response<Caja>>(this.BASE_URL_API + 'cajas/resumencaja/'+ idTransaccion);
  }
  public insertCaja(data: any): Observable<Response<Caja>> {
    return this.http.post<Response<Caja>>(this.BASE_URL_API + 'cajas/', data);
  }
  public updateCaja(id: number, data: any): Observable<Response<Caja>> {
    return this.http.put<Response<Caja>>(this.BASE_URL_API + 'cajas/' + id, data);
  }
  public deleteCaja(id: number): Observable<Response<any>> {
    return this.http.delete<Response<any>>(this.BASE_URL_API + 'cajas/' + id);
  }
  public enableOrDisableCaja(data: any, value: boolean): Observable<Response<any>> {
    let url;
    if (value) {
      url = this.BASE_URL_API + "cajas/enable";
    } else {
      url = this.BASE_URL_API + "cajas/disable";
    }
    return this.http.put<any>(url, data);
  }
}
