import { Component, OnInit, Input, Output, EventEmitter, ChangeDetectionStrategy, ChangeDetectorRef, AfterViewInit } from '@angular/core';
import { ParamPagination } from 'src/app/core/models/param-pagination';

@Component({
  selector: 'app-resgister',
  templateUrl: './resgister.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ResgistertComponent implements OnInit, AfterViewInit {

  @Input()
  text: string;
  @Input()
  paramPagination: ParamPagination;
  @Input()
  order: string;
  @Output()
  registerChanged = new EventEmitter<ParamPagination>();
  public pagesize = 10;
  constructor() {
  }

  ngOnInit() {
  }
  ngAfterViewInit(): void {
    this.pagesize = this.paramPagination.pageSize;
  }

  changeRegister() {
    this.paramPagination.pageSize = this.pagesize;
    this.registerChanged.emit(this.paramPagination);
  }
}
