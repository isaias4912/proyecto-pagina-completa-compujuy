import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';



@Injectable()
export class TributoEventService {

  private _data = new Subject<any>();
  public data$ = this._data.asObservable();

  constructor() { }
  changeData(data: any) {
    this._data.next(data);
  }
}
