import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ServiceService } from '../service.service';
import { catchError } from 'rxjs/operators';

@Injectable()
export class MainHTTPService extends ServiceService {

  constructor() {
    super();
  }

  public post(url: string, data: any = null): Observable<any> {
    return this.http.post(url, data);
  }
  public postMultipart(url: string, data: any = null): Observable<any> {
    return this.http.post(url, data, { reportProgress: true, observe: "events"});
  }
}
