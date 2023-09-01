import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ServiceService } from '../service.service';
import { Empresa } from '../../models/empresa';
import { RespPagination } from '../../models/resp-pagination';
import { Response } from '../utils/response';

@Injectable()
export class EmpresaHTTPService extends ServiceService {

  constructor() {
    super();
  }
  public getAllPagination(params: any, data: any): Observable<RespPagination<Empresa>> {
    return this.http.post<RespPagination<Empresa>>(this.BASE_URL_API + 'empresas/all/pagination?' + $.param(params), data);
  }
  public getEmpresasByMultipleFilter(q, orden, pagina, paginaTamanio, reverse): Observable<RespPagination<Empresa>> {
    return this.http.get<RespPagination<Empresa>>(this.BASE_URL_API + 'empresas/pagination/?q=' + q + "&order=" + orden + "&page=" + pagina + "&pageSize=" + paginaTamanio + "&reverse=" + reverse);
  }
  public getEmpresa(id: number): Observable<Response<Empresa>> {
    return this.http.get<Response<Empresa>>(this.BASE_URL_API + 'empresas/' + id);
  }
  public getEmpresaMin(id: number | string): Observable<Response<Empresa>> {
    return this.http.get<Response<Empresa>>(this.BASE_URL_API + 'empresas/' + id + '/min');
  }
  public getEmpresaByCuit(cuit: string, ignoreLoadingBar: boolean = false): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'empresas/cuit/' + cuit, this.getHeader(ignoreLoadingBar));
  }
  public insertEmpresa(data: any): Observable<Response<Empresa>> {
    return this.http.post<Response<Empresa>>(this.BASE_URL_API + 'empresas/', data);
  }
  public updateEmpresa(id: number, data: any): Observable<Response<Empresa>> {
    return this.http.put<Response<Empresa>>(this.BASE_URL_API + 'empresas/' + id , data);
  }
  public deleteEmpresa(id: number): Observable<Response<any>> {
    return this.http.delete<Response<any>>(this.BASE_URL_API + 'empresas/' + id);
  }
}
