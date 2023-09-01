import { Injectable } from '@angular/core';
import { Familia } from '../../models/familia'
import { Observable } from 'rxjs';
import { ServiceService } from '../service.service';
import { RespPagination } from '../../models/resp-pagination';
import { ProductoPadre } from '../../models/producto-padre';
import { Response } from '../utils/response';

@Injectable()
export class ProductoPadreHTTPService extends ServiceService {

  constructor() {
    super();
  }

  public getProductoPadreMinById(idPadre: number, ignoreLoadingBar: boolean = false): Observable<Familia> {
    return this.http.get<Familia>(this.BASE_URL_API + 'productosPadre/min/' + idPadre, this.getHeader(ignoreLoadingBar));
  }
  
  public getProductoPadreById(idPadre: number): Observable<Familia> {
    return this.http.get<Familia>(this.BASE_URL_API + 'productosPadre/' + idPadre);
  }

  public getProductosPadresByPagination(params: any, data: any): Observable<RespPagination<ProductoPadre>> {
    return this.http.post<RespPagination<ProductoPadre>>(this.BASE_URL_API + 'productosPadre/?' + $.param(params), data);
  }
  public insertProductoPadre(data: any): Observable<Response<ProductoPadre>> {
    return this.http.post<Response<ProductoPadre>>(this.BASE_URL_API + 'productosPadre/', data);
  }
  public updateProductoPadre(data: any): Observable<Response<ProductoPadre>> {
    return this.http.put<Response<ProductoPadre>>(this.BASE_URL_API + 'productosPadre/', data);
  }
  public deleteProductoPadre(id: string): Observable<Response<any>> {
    return this.http.delete<Response<any>>(this.BASE_URL_API + 'productosPadre/' + id);
  }
}
