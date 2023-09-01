import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ServiceService } from '../service.service';
import { Response } from '../utils/response';

@Injectable()
export class UsuarioPublicHTTPService extends ServiceService {

  constructor() {
    super();
  }

  public existUser(user: string): Observable<Response<any>> {
    return this.http.get<Response<any>>(this.BASE_URL + 'public/user/exist/' + user);
  }
  public existMailUser(mail: string): Observable<Response<any>> {
    return this.http.post<Response<any>>(this.BASE_URL + 'public/user/existmail/', { data: mail });
  }
  public saveUser(data: any): Observable<Response<any>> {
    return this.http.post<Response<any>>(this.BASE_URL + 'public/user/', data);
  }
}
