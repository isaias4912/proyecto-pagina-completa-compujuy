import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';



@Injectable()
export class NavigationTableService {

  private _finishLoad = new Subject<any | null>();
  public finishLoad$ = this._finishLoad.asObservable();

  constructor() { }
  public finishLoad(data: any) {
    this._finishLoad.next(data);
  }
}
