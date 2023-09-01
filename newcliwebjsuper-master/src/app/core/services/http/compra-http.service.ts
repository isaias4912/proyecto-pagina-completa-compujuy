import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ServiceService } from '../service.service';
import { RespPagination } from '../../models/resp-pagination';
import { Response } from '../utils/response';
import { EncMovimiento } from '../../models/acc-enc-mov';

@Injectable()
export class CompraHTTPService extends ServiceService {

  constructor() {
    super();
  }
  public getImpuestosActivos(): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'cbteComp/impuestos');
  }
  public getDataCompras(): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'cbteComp/dataCompras');
  }
  public getFactura(id: any): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'cbteComp/' + id);
  }
  public getFacturaByPago(id: number): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'cbteComp/pago/' + id);
  }
  public getDataForPagoFacturaCompra(): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'cbteComp/dataPagos/proveedores');
  }
  public saveFacturaCompra(data: any): Observable<Response<any>> {
    return this.http.post<Response<any>>(this.BASE_URL_API + 'cbteComp/', data);
  }
  public getAllFacturasComprasPagination(params: any, data: any): Observable<RespPagination<any>> {
    return this.http.post<RespPagination<any>>(this.BASE_URL_API + 'cbteComp/all/pagination?' + $.param(params), data);
  }
  public getFacturaByNum(q: string): Observable<Response<EncMovimiento>> {
    return this.http.get<Response<EncMovimiento>>(this.BASE_URL_API + 'cbteComp/numero?q=' + q);
  }
  public getPreviewAddStock(id: string): Observable<Response<EncMovimiento>> {
    return this.http.get<Response<EncMovimiento>>(this.BASE_URL_API + 'existencias/add/factura/preview/' + id);
  }
  public insertAddStock(data: any): Observable<Response<EncMovimiento>> {
    return this.http.post<Response<EncMovimiento>>(this.BASE_URL_API + 'existencias/add/factura', data);
  }
  public insertCbteComp(data: any): Observable<Response<EncMovimiento>> {
    return this.http.post<Response<EncMovimiento>>(this.BASE_URL_API + 'cbteComp', data);
  }
  public getLibroIvaAllPagination(params: any, data: any): Observable<RespPagination<any>> {
    return this.http.post<RespPagination<any>>(this.BASE_URL_API + 'cbteComp/libro/all/pagination?' + $.param(params), data);
  }
}
