import { Injectable } from '@angular/core';
import { Cliente } from '../../models/cliente'
import { Observable } from 'rxjs';
import { ServiceService } from '../service.service';
import { RespPagination } from '../../models/resp-pagination';
import { Response } from '../utils/response';

@Injectable()
export class ClienteHTTPService extends ServiceService {

  constructor() {
    super();
  }

  public getAllPagination(params: any, data: any): Observable<RespPagination<Cliente>> {
    return this.http.post<RespPagination<Cliente>>(this.BASE_URL_API + 'clientes/all/pagination?' + $.param(params), data);
  }
  public getCliente(id: number): Observable<Response<Cliente>> {
    return this.http.get<Response<Cliente>>(this.BASE_URL_API + 'clientes/' + id);
  }
  public getCtaCte(id: number): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'clientes/ctacte/' + id);
  }
  public getClienteByMultipleFilter(q, active, orden, pagina, paginaTamanio, reverse): Observable<RespPagination<any>> {
    return this.http.get<RespPagination<any>>(this.BASE_URL_API + 'clientes/pagination/multiple?q=' + q + "&active=" + active + "&order=" + orden + "&page=" + pagina + "&pageSize=" + paginaTamanio + "&reverse=" + reverse);
  }
  public getClienteMin(id: number | string): Observable<Response<Cliente>> {
    return this.http.get<Response<Cliente>>(this.BASE_URL_API + 'clientes/' + id + '/min');
  }
  public insertCliente(data: any): Observable<Response<Cliente>> {
    return this.http.post<Response<Cliente>>(this.BASE_URL_API + 'clientes/', data);
  }
  public createCtaCte(id: number, data: any): Observable<Response<Cliente>> {
    return this.http.post<Response<Cliente>>(this.BASE_URL_API + 'clientes/' + id + '/ctacte', data);
  }
  public updateCtaCte(id: number, data: any): Observable<Response<Cliente>> {
    return this.http.put<Response<Cliente>>(this.BASE_URL_API + 'clientes/' + id + '/ctacte', data);
  }
  public updateCliente(id: number, data: any): Observable<Response<Cliente>> {
    return this.http.put<Response<Cliente>>(this.BASE_URL_API + 'clientes/' + id, data);
  }
  public deleteCliente(id: number): Observable<Response<any>> {
    return this.http.delete<Response<any>>(this.BASE_URL_API + 'clientes/' + id);
  }
  public enableOrDisableCliente(data: any, value: boolean): Observable<Response<any>> {
    let url;
    if (value) {
      url = this.BASE_URL_API + "clientes/enable";
    } else {
      url = this.BASE_URL_API + "clientes/disable";
    }
    return this.http.put<any>(url, data);
  }
}
