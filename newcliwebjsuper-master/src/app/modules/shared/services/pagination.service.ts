import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Pagination } from '../models/pagination';

@Injectable()
export class PaginationService {
  private pagination = new BehaviorSubject<Pagination>(null);
  public pagination$ = this.pagination.asObservable();
  constructor() { }
}
