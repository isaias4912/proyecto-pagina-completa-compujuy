import { Injectable } from '@angular/core';
import { Entidad } from '../../models/entidad'
import { Observable } from 'rxjs';
import { ServiceService } from '../service.service';
import { RespPagination } from '../../models/resp-pagination';
import { Response } from '../utils/response';

@Injectable()
export class EntidadHTTPService extends ServiceService {

  constructor() {
    super();
  }

  public getAllPagination(params: any, data: any): Observable<RespPagination<Entidad>> {
    return this.http.post<RespPagination<Entidad>>(this.BASE_URL_API + 'entidad/all/pagination?' + $.param(params), data);
  }
  public getEntidadByMultipleFilter(q, orden, pagina, paginaTamanio, reverse): Observable<RespPagination<Entidad>> {
    return this.http.get<RespPagination<Entidad>>(this.BASE_URL_API + 'entidad/pagination/multiple?q=' + q + "&order=" + orden + "&page=" + pagina + "&pageSize=" + paginaTamanio + "&reverse=" + reverse);
  }
  
}
