import { Injectable } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { ParamPagination } from '../../models/param-pagination'
import { Observable } from 'rxjs';
import { Response } from './response';
import { ServiceService } from '../service.service';
import { retry, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UtilService {

  constructor() {
  }
  public toParamURL(paramPagination: ParamPagination) {
    let params = new HttpParams();
    params = params.append('_format', paramPagination._format);
    params = params.append('page', paramPagination.page.toString());
    params = params.append('pageSize', paramPagination.pageSize.toString());
    params = params.append('order', paramPagination.order);
    params = params.append('reverse', paramPagination.reverse ? "true" : "false");
    params = params.append('type', paramPagination.type);
    return params;
  }
}
