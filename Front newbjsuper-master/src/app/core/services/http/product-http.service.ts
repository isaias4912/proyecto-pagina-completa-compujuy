import { Injectable } from '@angular/core';
import { retry, catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { ServiceService } from '../service.service';
import { Producto } from '../../models/producto';
import { Response } from '../utils/response';
import { RespPagination } from '../../models/resp-pagination';
import { ResponsePagination } from '../utils/responsePagination';
declare var $: any;

@Injectable()
export class ProductosHTTPService extends ServiceService {

  constructor() {
    super();
  }

  public list(dataFilter: any, paramURL: any): Observable<RespPagination<any>> {
    paramURL = this.utilHTTPService.toParamURL(paramURL);
    return this.http.post<RespPagination<any>>(this.BASE_URL + 'api/v1/productos/detalleProductos?' + paramURL.toString(), dataFilter);
  }

  public saveProducto(producto: Producto): Observable<Response<Producto>> {
    return this.http.post<Response<Producto>>(this.BASE_URL_API + 'productos', producto)
  }
  public updateProducto(producto: Producto): Observable<Response<Producto>> {
    return this.http.put<Response<Producto>>(this.BASE_URL_API + 'productos/' + producto.id, producto)
  }
  public uploadImage(formData: FormData): Observable<any> {
    return this.http.post(this.BASE_URL_API + 'productos/upload/image', formData)
  }

  public getProductoById(id, precios = null): Observable<Response<Producto>> {
    let tempPrecios = 0;
    if (precios != undefined && precios != null) {
      tempPrecios = precios;
    }
    return this.http.get<Response<Producto>>(this.BASE_URL_API + 'productos/' + id + "?precios=" + tempPrecios)
      .pipe(
        retry(1),
      );
  }
  public getProductoPreviewById(id, ignoreLoadingBar: boolean = false): Observable<Response<Producto>> {
    return this.http.get<Response<Producto>>(this.BASE_URL_API + 'productos/preview/' + id, this.getHeader(ignoreLoadingBar))
      .pipe(
        retry(1),
      );
  }

  public updatePrecioPorProducto(id: string, data: any) {
    return this.http.put<any>(this.BASE_URL_API + 'productos/' + id + '/updateprecio/', data)
      .pipe(
        retry(1),
      );
  }
  public getProductoSubMinById(id: string) {
    return this.http.get<any>(this.BASE_URL_API + 'productos/submin/' + id)
      .pipe(
        retry(1),
      );
  }
  public getProductoMinById(id: string) {
    return this.http.get<any>(this.BASE_URL_API + 'productos/min/' + id)
      .pipe(
        retry(1),
      );
  }
  public getProductosMin(data): Observable<Response<any>> {
    return this.http.post<Response<any>>(this.BASE_URL_API + 'productos/min/ids', data);
  }

  public getProductosMinObjs(data): Observable<Response<any>> {
    return this.http.post<Response<any>>(this.BASE_URL_API + 'productos/min/objs', data);
  }

  public getHistorialProducto(id: string, data: any) {
    return this.http.get<any>(this.BASE_URL_API + 'productos/' + id + "/historial/stock?page=" + data.page + "&pageSize=" + data.pagesize)
      .pipe(
        retry(1),
      );
  }
  public deleteProducto(id: string): Observable<Response<any>> {
    return this.http.delete<Response<any>>(this.BASE_URL_API + 'productos/' + id);
  }


  public enableOrDisableProducto(data, value) {
    let url;
    if (value) {
      url = this.BASE_URL_API + "productos/enable";
    } else {
      url = this.BASE_URL_API + "productos/disable";
    }
    return this.http.put<any>(url, data)
      .pipe(
        retry(1),
      );
  }

  getCodigoEspecial(id: number) {
    return this.http.get<any>(this.BASE_URL_API + 'productos/familia/' + id + '/codigoespecial', this.getHeader(true))
      .pipe(
        retry(1),
      );
  }
  printDetail(id: string) {
    return this.http.get<any>(this.BASE_URL_API + 'reportes/productos/pdf/' + id, this.getHeaderArrayBuffer());
  }

  printList(dataFilter: any, paramURL: any, typePrint: string) {
    return this.http.post<any>(this.BASE_URL_API + 'reportes/productos/' + typePrint + '/list?' + $.param(paramURL), dataFilter, this.getHeaderArrayBuffer());
  }

  getDataNewUpdate() {
    return this.http.get<any>(this.BASE_URL_API + 'productos/dataNewUpdate')
      .pipe(
        retry(1),
      );
  };
  isExistBarCode(barCode: string, loading = false) {
    return this.http.get<any>(this.BASE_URL_API + 'productos/existebarcode/' + barCode, this.getHeader(loading));
  };
  generateBarCodePDF(data : any, cols:string, style:string, barcode:string, typePrint:string) {
    return this.http.post<any>(this.BASE_URL_API + "reportes/productos/barcodes/"+typePrint+"?cols=" + cols+"&style="+style+"&barcode="+barcode, data, this.getHeaderArrayBuffer());
  };
  public getParentsAndChilds(idProducto): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'productos/' + idProducto + "/parentsandchilds");
  }
  public getProductoByMultipleFilter(q, orden, pagina, paginaTamanio, reverse, precios): Observable<RespPagination<Producto>> {
    return this.http.get<RespPagination<Producto>>(this.BASE_URL_API + 'productos/pagination/?q=' + q + "&order=" + orden + "&page=" + pagina + "&pageSize=" + paginaTamanio + "&reverse=" + reverse + "&precios=" + precios);
  }
  public addExistenciasStock(data: any): Observable<any> {
    return this.http.post(this.BASE_URL_API + 'existencias/add', data);
  }
  public addExistenciasStockPase(data: any): Observable<any> {
    return this.http.post(this.BASE_URL_API + 'existencias/add/pase', data);
  }
  public removeExistenciasStock(data: any): Observable<any> {
    return this.http.post(this.BASE_URL_API + 'existencias/remove', data);
  }
  public getStockByProducto(id: string): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'productos/' + id + '/stocks');
  }
  public getMovimientosByProducto(params: any, id: string, data: any): Observable<ResponsePagination<Array<any>>> {
    return this.http.post<ResponsePagination<any>>(this.BASE_URL_API + 'productos/' + id + '/movimientos/stock?' + $.param(params), data);
  }
  public getPasesByPage(params: any, data: any): Observable<RespPagination<any>> {
    return this.http.post<RespPagination<any>>(this.BASE_URL_API + 'existencias/pases/all/pagination?' + $.param(params), data);
  }
  public confirmPase(data: any): Observable<Response<any>> {
    return this.http.put<Response<any>>(this.BASE_URL_API + 'existencias/pases/confirm', data);
  }
  public deletePase(id: string): Observable<Response<any>> {
    return this.http.delete<Response<any>>(this.BASE_URL_API + 'existencias/remove/pase/' + id);
  }
  public getMovimientosPrecioByProducto(params: any, id: string): Observable<Response<Array<any>>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'productos/' + id + '/movimientos/precio?' + $.param(params));
  }
  public getLista(id: string): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'listaprecios/' + id);
  }
  public getListasAllPagination(params: any, filter: any): Observable<RespPagination<any>> {
    return this.http.post<RespPagination<any>>(this.BASE_URL_API + 'listaprecios/all/pagination?' + $.param(params), filter);
  }
  public saveListaPrecio(data: any): Observable<Response<any>> {
    return this.http.post<Response<any>>(this.BASE_URL_API + 'listaprecios/', data);
  }
  public updateListaPrecio(id: string, data: any): Observable<Response<any>> {
    return this.http.put<Response<any>>(this.BASE_URL_API + 'listaprecios/' + id, data);
  }
  public deleteListaPrecio(id: string): Observable<Response<any>> {
    return this.http.delete<Response<any>>(this.BASE_URL_API + 'listaprecios/' + id);
  }
  public enableOrDisableListas(data: any, value: boolean): Observable<RespPagination<any>> {
    let url;
    if (value) {
      url = this.BASE_URL_API + "listaprecios/enable";
    } else {
      url = this.BASE_URL_API + "listaprecios/disable";
    }
    return this.http.put<any>(url, data)
      .pipe(
        retry(1),
      );
  }
  public getVencimientosAllPagination(params: any, filter: any): Observable<RespPagination<any>> {
    return this.http.post<RespPagination<any>>(this.BASE_URL_API + 'util/vencimiento/all/pagination?' + $.param(params), filter);
  }
  public enableOrDisableVencimientos(data: any, value: boolean): Observable<Response<any>> {
    let url;
    if (value) {
      url = this.BASE_URL_API + "util/vencimiento/enable";
    } else {
      url = this.BASE_URL_API + "util/vencimiento/disable";
    }
    return this.http.put<any>(url, data)
      .pipe(
        retry(1),
      );
  }
  public deleteVencimiento(id: string): Observable<Response<any>> {
    return this.http.delete<Response<any>>(this.BASE_URL_API + 'util/vencimiento/' + id);
  }
  public insertVencimiento(data: any): Observable<Response<any>> {
    return this.http.post<Response<any>>(this.BASE_URL_API + 'util/vencimiento/', data);
  }
}
