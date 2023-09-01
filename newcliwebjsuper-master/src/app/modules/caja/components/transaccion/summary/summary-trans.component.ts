import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input, ViewChildren, Directive, Output, EventEmitter, QueryList } from '@angular/core';
import { Observable } from 'rxjs';
import { pluck, tap } from 'rxjs/operators';
import { CajaHTTPService } from '../../../../../core/services/http/caja-http.service';
import { UtilPage } from 'src/app/core/util-page';
export type SortColumn = '';
export type SortDirection = 'asc' | 'desc' | '';
const rotate: { [key: string]: SortDirection } = { 'asc': 'desc', 'desc': '', '': 'asc' };
const compare = (v1: string, v2: string) => v1 < v2 ? -1 : v1 > v2 ? 1 : 0;
export interface SortEvent {
  column: SortColumn;
  direction: SortDirection;
}

@Component({
  selector: 'summary-trans',
  templateUrl: './summary-trans.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SummaryTransComponent extends UtilPage implements OnInit, AfterViewInit {
  // @ViewChildren(NgbdSortableHeader) headers: QueryList<NgbdSortableHeader>;

  @Input()
  public idEntity: number;
  summary$: Observable<any>;
  public detallesTransaccionCaja: any;
  constructor(
    private cajaHTTPService: CajaHTTPService,
  ) {
    super();
  }

  ngOnInit() {
    this.summary$ = this.cajaHTTPService.resumenCaja(this.idEntity).pipe(pluck('data'), tap(resp => {
      this.detallesTransaccionCaja = resp.transaccionCaja.detallesTransaccionCaja;
    }));
  }

  ngAfterViewInit(): void {
  }
  onSort({ column, direction }: SortEvent, list: any) {
    if (direction === '' || column === '') {
    } else {
      this.detallesTransaccionCaja = this.detallesTransaccionCaja.sort((a, b) => {
        const res = compare(`${a[column]}`, `${b[column]}`);
        return direction === 'asc' ? res : -res;
      });
    }
  }
}

