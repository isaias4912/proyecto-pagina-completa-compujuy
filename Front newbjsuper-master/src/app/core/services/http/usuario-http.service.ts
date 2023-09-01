import { Injectable } from '@angular/core';
import { Usuario } from '../../models/usuario'
import { Observable } from 'rxjs';
import { ServiceService } from '../service.service';
import { RespPagination } from '../../models/resp-pagination';
import { Response } from '../utils/response';

@Injectable()
export class UsuarioHTTPService extends ServiceService {

  constructor() {
    super();
  }

  public getAllPagination(params: any, data: any): Observable<RespPagination<Usuario>> {
    return this.http.post<RespPagination<Usuario>>(this.BASE_URL_API + 'usuarios/all/pagination?' + $.param(params), data);
  }
  public getRoles(): Observable<Response<Usuario>> {
    return this.http.get<Response<Usuario>>(this.BASE_URL_API + 'usuarios/roles');
  }
  public getMy(): Observable<Response<Usuario>> {
    return this.http.get<Response<Usuario>>(this.BASE_URL_API + 'usuarios/my');
  }
  public getUsuario(id: number): Observable<Response<Usuario>> {
    return this.http.get<Response<Usuario>>(this.BASE_URL_API + 'usuarios/' + id);
  }
  public getUsuarioMin(id: number, ignoreLoadingBar: boolean = false): Observable<Response<Usuario>> {
    return this.http.get<Response<Usuario>>(this.BASE_URL_API + 'usuarios/min/' + id, this.getHeader(ignoreLoadingBar));
  }
  public insertUsuario(data: any): Observable<Response<Usuario>> {
    return this.http.post<Response<Usuario>>(this.BASE_URL_API + 'usuarios/', data);
  }
  public updateUsuario(id: number, data: any): Observable<Response<Usuario>> {
    return this.http.put<Response<Usuario>>(this.BASE_URL_API + 'usuarios/' + id, data);
  }
  public deleteUsuario(id: number): Observable<Response<any>> {
    return this.http.delete<Response<any>>(this.BASE_URL_API + 'usuarios/' + id);
  }
  public enableOrDisableUsuario(data: any, value: boolean): Observable<Response<any>> {
    let url;
    if (value) {
      url = this.BASE_URL_API + "usuarios/enable";
    } else {
      url = this.BASE_URL_API + "usuarios/disable";
    }
    return this.http.put<any>(url, data);
  }
}
