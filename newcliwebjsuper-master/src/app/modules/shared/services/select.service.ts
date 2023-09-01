import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';
import { DataSelect } from '../models/data-select';



@Injectable()
export class SelectService {

  private _select = new Subject<DataSelect>();
  public select$ = this._select.asObservable();

  constructor() { }
  select(data: any) {
    this._select.next(data);
  }
}
