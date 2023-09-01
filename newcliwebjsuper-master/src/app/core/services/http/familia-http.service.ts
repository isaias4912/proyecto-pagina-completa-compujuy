import { Injectable } from '@angular/core';
import { Familia } from '../../models/familia'
import { Observable } from 'rxjs';
import { ServiceService } from '../service.service';
import { RespPagination } from '../../models/resp-pagination';
import { Response } from '../utils/response';

@Injectable()
export class FamiliaHTTPService extends ServiceService {

  constructor() {
    super();
  }

  public getFamilia(idFamilia: number, ignoreLoadingBar: boolean = false): Observable<Familia> {
    return this.http.get<Familia>(this.BASE_URL_API + 'familias/' + idFamilia, this.getHeader(ignoreLoadingBar));
  }
  public getFamiliasMin(data): Observable<Response<any>> {
    return this.http.post<Response<any>>(this.BASE_URL_API + 'familias/min/ids', data);
  }
  public getFamiliasMinByObjs(data): Observable<Response<any>> {
    return this.http.post<Response<any>>(this.BASE_URL_API + 'familias/min/objs', data);
  }
  public getAllArbol(): Observable<any> {
    return this.http.get<any>(this.BASE_URL_API + 'familias/all/arbol');
  }
  public isExistNombreCorto(nombreCorto: string,ignoreLoadingBar: boolean = false): Observable<any> {
    return this.http.get<any>(this.BASE_URL_API + 'familias/nombreCorto/exist/' + nombreCorto, this.getHeader(ignoreLoadingBar));
  }
  public getAllPagination(params: any, data: any): Observable<RespPagination<Familia>> {
    return this.http.post<RespPagination<Familia>>(this.BASE_URL_API + 'familias/all/pagination/?' + $.param(params), data);
  }
  public insertFamilia(data: any): Observable<Response<Familia>> {
    return this.http.post<Response<Familia>>(this.BASE_URL_API + 'familias/', data);
  }
  public updateFamilia(id: number, data: any): Observable<Response<Familia>> {
    return this.http.put<Response<Familia>>(this.BASE_URL_API + 'familias/'+id, data);
  }
  public deleteFamilia(id: number): Observable<Response<any>> {
    return this.http.delete<Response<any>>(this.BASE_URL_API + 'familias/' + id);
  }
}
