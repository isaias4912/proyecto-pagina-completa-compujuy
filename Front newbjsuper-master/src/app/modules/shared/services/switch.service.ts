import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable()
export class SwitchService {
  private subject = new BehaviorSubject<boolean>(false);
  value$: Observable<boolean> = this.subject.asObservable();

  constructor() { }

  setValue(value: boolean) {
    this.subject.next(value);
  }
}
