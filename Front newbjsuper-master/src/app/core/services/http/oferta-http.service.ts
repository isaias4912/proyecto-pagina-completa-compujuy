import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ServiceService } from '../service.service';
import { RespPagination } from '../../models/resp-pagination';
import { Response } from '../utils/response';
import { Oferta } from '../../models/oferta';

@Injectable()
export class OfertaHTTPService extends ServiceService {

  constructor() {
    super();
  }

  public getAllPagination(params: any, data: any): Observable<RespPagination<Oferta>> {
    return this.http.post<RespPagination<Oferta>>(this.BASE_URL_API + 'ofertas/all/pagination?' + $.param(params), data);
  }
  public getOfertasProductosPagination(params: any, data: any): Observable<RespPagination<any>> {
    return this.http.post<RespPagination<any>>(this.BASE_URL_API + 'ofertas/productos/all/pagination?' + $.param(params), data);
  }
  public getOferta(id: number): Observable<Response<Oferta>> {
    return this.http.get<Response<Oferta>>(this.BASE_URL_API + 'ofertas/' + id);
  }
  public getOfertaAndProductos(id: number): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'ofertas/ofertasproductos/' + id);
  }
  public insertOferta(data: any): Observable<Response<Oferta>> {
    return this.http.post<Response<Oferta>>(this.BASE_URL_API + 'ofertas/', data);
  }
  public insertOfertaProductos(data: any): Observable<Response<any>> {
    return this.http.post<Response<Oferta>>(this.BASE_URL_API + 'ofertas/ofertasproductos/', data);
  }

  public enableOrDisableOferta(data: any, value: boolean): Observable<Response<any>> {
    let url;
    if (value) {
      url = this.BASE_URL_API + "ofertas/enable";
    } else {
      url = this.BASE_URL_API + "ofertas/disable";
    }
    return this.http.put<any>(url, data);
  }
  public enableOrDisableOfertasProductos(data: any, value: boolean): Observable<Response<any>> {
    let url;
    if (value) {
      url = this.BASE_URL_API + "ofertas/productos/enable";
    } else {
      url = this.BASE_URL_API + "ofertas/productos/disable";
    }
    return this.http.put<any>(url, data);
  }
  public updateOfertaPrioridad(id: number, data: any, ignoreLoadingBar: boolean = false): Observable<Response<any>> {
    return this.http.put<Response<any>>(this.BASE_URL_API + 'ofertas/' + id + '/prioridad', data, this.getHeader(ignoreLoadingBar));
  }
  public updateOfertaFechaHasta(id: number, data: any, ignoreLoadingBar: boolean = false): Observable<Response<any>> {
    return this.http.put<Response<any>>(this.BASE_URL_API + 'ofertas/' + id + '/fechaHasta', data, this.getHeader(ignoreLoadingBar));
  }

}
