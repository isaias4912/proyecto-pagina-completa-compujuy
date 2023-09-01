import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ServiceService } from '../service.service';
import { Proveedor } from '../../models/proveedor';
import { RespPagination } from '../../models/resp-pagination';
import { Response } from '../utils/response';

@Injectable()
export class ProveedorHTTPService extends ServiceService {

  constructor() {
    super();
  }
  public getAllPagination(params: any, data: any): Observable<RespPagination<Proveedor>> {
    return this.http.post<RespPagination<Proveedor>>(this.BASE_URL_API + 'proveedores/all/pagination?' + $.param(params), data);
  }
  public getProveedoresByMultipleFilter(q, orden, pagina, paginaTamanio, reverse): Observable<RespPagination<Proveedor>> {
    return this.http.get<RespPagination<Proveedor>>(this.BASE_URL_API + 'proveedores/pagination/?q=' + q + "&order=" + orden + "&page=" + pagina + "&pageSize=" + paginaTamanio + "&reverse=" + reverse);
  }
  public getProveedor(id: number): Observable<Response<Proveedor>> {
    return this.http.get<Response<Proveedor>>(this.BASE_URL_API + 'proveedores/' + id);
  }
  public getProveedorMin(id: number | string): Observable<Response<Proveedor>> {
    return this.http.get<Response<Proveedor>>(this.BASE_URL_API + 'proveedores/' + id + '/min');
  }
  public insertProveedor(data: any): Observable<Response<Proveedor>> {
    return this.http.post<Response<Proveedor>>(this.BASE_URL_API + 'proveedores/', data);
  }
  public pagarCtaCte(data: any): Observable<Response<Proveedor>> {
    return this.http.post<Response<Proveedor>>(this.BASE_URL_API + 'proveedores/ctacte/pagar', data);
  }
  public updateProveedor(id: number, data: any): Observable<Response<Proveedor>> {
    return this.http.put<Response<Proveedor>>(this.BASE_URL_API + 'proveedores/' + id , data);
  }
  public deleteProveedor(id: number): Observable<Response<any>> {
    return this.http.delete<Response<any>>(this.BASE_URL_API + 'proveedores/' + id);
  }
  public enableOrDisableProveedor(data: any, value: boolean): Observable<Response<any>> {
    let url;
    if (value) {
      url = this.BASE_URL_API + "proveedores/enable";
    } else {
      url = this.BASE_URL_API + "proveedores/disable";
    }
    return this.http.put<any>(url, data);
  }

}
