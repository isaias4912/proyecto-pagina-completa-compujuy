import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ParamPagination } from 'src/app/core/models/param-pagination';

@Component({
  selector: 'app-sort',
  templateUrl: './sort.component.html',
})
export class SortComponent implements OnInit {

  @Input()
  text: string;
  @Input()
  paramPagination: ParamPagination;
  @Input()
  order: string;
  @Output()
  sortChanged = new EventEmitter<ParamPagination>();

  constructor() {

  }

  ngOnInit() {
  }

  sort() {
    this.paramPagination.order= this.order;
    this.paramPagination.reverse = !this.paramPagination.reverse;
    this.sortChanged.emit(this.paramPagination);
  }
}
