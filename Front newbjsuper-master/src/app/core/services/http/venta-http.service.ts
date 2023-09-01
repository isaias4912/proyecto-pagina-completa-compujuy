import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ServiceService } from '../service.service';
import { RespPagination } from '../../models/resp-pagination';
import { Response } from '../utils/response';
import { AuthService } from '../auth/auth.service';

@Injectable()
export class VentaHTTPService extends ServiceService {

  public configuration: any = null;
  constructor(
    private authService: AuthService
  ) {
    super();
    this.configuration = this.authService.getConfiguracion();
    console.log('this.configuration', this.configuration)
  }

  public getAllPagination(params: any, data: any): Observable<RespPagination<any>> {
    return this.http.post<RespPagination<any>>(this.BASE_URL_API + 'ventas/all/pagination?' + $.param(params), data);
  }
  public getLibroIvaAllPagination(params: any, data: any): Observable<RespPagination<any>> {
    return this.http.post<RespPagination<any>>(this.BASE_URL_API + 'ventas/libro/all/pagination?' + $.param(params), data);
  }
  printList(dataFilter: any, paramURL: any, typePrint: string) {
    return this.http.post<any>(this.BASE_URL_API + 'reportes/ventas/' + typePrint + '/list?' + $.param(paramURL), dataFilter, this.getHeaderArrayBuffer());
  }
  printDetail(id: string) {
    return this.http.get<any>(this.BASE_URL_API + 'reportes/ventas/pdf/' + id, this.getHeaderArrayBuffer());
  }
  printTicket(id: string) {
    return this.http.get<any>(this.BASE_URL_API + 'reportes/ventas/ticket/pdf/' + id + '?tipo=80', this.getHeaderArrayBuffer());
  }
  downloadCbte(id: number, type: string = "A4", typeResponse: string = "byte") {
    return this.http.get<any>(this.BASE_URL_API + 'reportes/ventas/cbte/pdf/' + id + "?tipo=" + type, this.getHeaderArrayBuffer());
  }
  printCbte(data: any, typePrint: string, host: string, port: string) {
    return this.http.post<any>(host + ':' + port + '/newstock/api/print?typePrint=' + typePrint, data, this.getHeaderArrayBufferContenType());
    // return this.http.post<any>('http://192.168.0.106:8082/newstock/api/print', data, this.getHeaderArrayBufferContenType());
  }
  sendEmailCbte(id: number, data) {
    return this.http.post<any>(this.BASE_URL_API + 'mailing/ventas/cbte/pdf/' + id, data);
  }
  getEmailsCliente(id: number) {
    return this.http.get<any>(this.BASE_URL_API + 'clientes/' + id + '/mails');
  }
  public getTransaccionesAllPagination(params: any, data: any): Observable<RespPagination<any>> {
    return this.http.post<RespPagination<any>>(this.BASE_URL_API + 'ventas/transacciones/all/pagination?' + $.param(params), data);
  }
  public getDetTransaccionesAllPagination(params: any, data: any): Observable<RespPagination<any>> {
    return this.http.post<RespPagination<any>>(this.BASE_URL_API + 'ventas/detalletransacciones/all/pagination?' + $.param(params), data);
  }
  public getAllDetalleTransaccion(data: any): Observable<Response<any>> {
    return this.http.post<Response<any>>(this.BASE_URL_API + 'ventas/detalletransaccioncaja/all/', data);
  }
  public getDetalleTransaccion(id: any): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'ventas/detalletransaccioncaja/' + id);
  }
  public getVentaDetalle(id: number): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'ventas/' + id);
  }
  public getDataFilter(): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'ventas/dataFilter');
  }
  public saveVenta(data: any): Observable<Response<any>> {
    return this.http.post<Response<any>>(this.BASE_URL_API + 'ventas', data);
  }
  public getDataVentasWeb(): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'ventas/dataventasweb');
  }
  public getDataVentas(params:{}): Observable<Response<any>> {
    console.log('params', Object.keys(params).length)
    if (Object.keys(params) && Object.keys(params).length > 0 ){
      return this.http.get<Response<any>>(this.BASE_URL_API + 'ventas/data/dataVentas?' + $.param(params));
    }else{
      return this.http.get<Response<any>>(this.BASE_URL_API + 'ventas/data/dataVentas');
    }
  }
  public getDataAfip(): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'fact/fe/datafe');
  }
  public solicitarCAE(data: any): Observable<Response<any>> {
    return this.http.post<Response<any>>(this.BASE_URL_API + 'fact/fe/solicitarCAE', data);
  }
  public previewCAE(data: any): Observable<Response<any>> {
    return this.http.post<Response<any>>(this.BASE_URL_API + 'fact/fe/previewCAE', data);
  }
  public refreshDataAfip(): Observable<Response<any>> {
    return this.http.post<Response<any>>(this.BASE_URL_API + 'fact/fe/datafe', {});
  }

}
