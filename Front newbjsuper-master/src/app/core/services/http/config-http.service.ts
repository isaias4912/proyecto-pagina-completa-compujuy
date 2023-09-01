import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ServiceService } from '../service.service';
import { Response } from '../utils/response';

@Injectable()
export class ConfigHTTPService extends ServiceService {

  constructor() {
    super();
  }

  public getAppData(): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'app');
  }
  public update(data: any): Observable<Response<any>> {
    return this.http.put<Response<any>>(this.BASE_URL_API + 'app', data);
  }
  public updateConfiguracion(data: any): Observable<Response<any>> {
    return this.http.put<Response<any>>(this.BASE_URL_API + 'util/configuracion', data);
  }
  public enableProduccionAfip(value: any): Observable<Response<any>> {
    return this.http.put<Response<any>>(this.BASE_URL_API + 'util/configuracion/enableprodafip?value=' + value, {});
  }
  public getConfiguracion(): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'util/configuracion');
  }
  public getConfiguracionGral(): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'util/configuraciongral');
  }
  public generateCSR(data: any): Observable<Response<any>> {
    return this.http.put<Response<any>>(this.BASE_URL_API + 'util/generate/csr', data);
  }
  public generatePrivateKey(): Observable<Response<any>> {
    return this.http.put<Response<any>>(this.BASE_URL_API + 'util/generate/private', null);
  }
  public downloadCSR() {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'util/download/csr', this.getHeaderArrayBuffer());
  }
  public downloadCRT() {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'util/download/crt', this.getHeaderArrayBuffer());
  }
  public testAfip(): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL_API + 'fact/fe/tipocomprobantes');
  }
  public saveBackup(data: any): Observable<Response<any>> {
    return this.http.put<Response<any>>(this.BASE_URL_API + 'util/backup', data);
  }
  public generateBackup(): Observable<Response<any>> {
    return this.http.post<Response<any>>(this.BASE_URL_API + 'util/backup/generate', {});
  }


}
