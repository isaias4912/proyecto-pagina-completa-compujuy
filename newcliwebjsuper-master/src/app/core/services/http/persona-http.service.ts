import { Injectable } from '@angular/core';
import { Persona } from '../../models/persona'
import { Observable } from 'rxjs';
import { ServiceService } from '../service.service';
import { RespPagination } from '../../models/resp-pagination';
import { Response } from '../utils/response';

@Injectable()
export class PersonaHTTPService extends ServiceService {

  constructor() {
    super();
  }

  public getIsExistClienteOrPersona(dni: string): Observable<any> {
    return this.http.get<any>(this.BASE_URL_API + 'clientes/existclienteorpersona/' + dni);
  }
  public getPersona(id: number, ignoreLoadingBar: boolean = false): Observable<Response<Persona>> {
    return this.http.get<Response<Persona>>(this.BASE_URL_API + 'personas/' + id, this.getHeader(ignoreLoadingBar));
  }
  public getAllPagination(params: any, data: any): Observable<RespPagination<Persona>> {
    return this.http.post<RespPagination<Persona>>(this.BASE_URL_API + 'personas/pagination?' + $.param(params), data);
  }
  public getPersonaByMultipleFilter(q, orden, pagina, paginaTamanio, reverse): Observable<RespPagination<Persona>> {
    return this.http.get<RespPagination<Persona>>(this.BASE_URL_API + 'personas/pagination/multiple?q=' + q + "&order=" + orden + "&page=" + pagina + "&pageSize=" + paginaTamanio + "&reverse=" + reverse);
  }
  public getPersonaByDni(dni: string, ignoreLoadingBar: boolean = false): Observable<Response<Persona>> {
    return this.http.get<Response<Persona>>(this.BASE_URL_API + 'personas/dni/' + dni, this.getHeader(ignoreLoadingBar));
  }
  public insertPersona(data: any): Observable<Response<Persona>> {
    return this.http.post<Response<Persona>>(this.BASE_URL_API + 'personas/', data);
  }
  public updatePersona(id: number, data: any): Observable<Response<Persona>> {
    return this.http.put<Response<Persona>>(this.BASE_URL_API + 'personas/' + id, data);
  }
  public deletePersona(id: number): Observable<Response<any>> {
    return this.http.delete<Response<any>>(this.BASE_URL_API + 'personas/' + id);
  }
}
