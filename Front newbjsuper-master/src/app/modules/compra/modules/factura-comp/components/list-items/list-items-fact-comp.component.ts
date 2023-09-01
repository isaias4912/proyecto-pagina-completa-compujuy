import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input, Output, EventEmitter } from '@angular/core';
import { Observable } from 'rxjs';
import { pluck } from 'rxjs/operators';
import { CompraHTTPService } from 'src/app/core/services/http/compra-http.service';
declare var $: any;
@Component({
  selector: 'list-items-fact-comp',
  templateUrl: './list-items-fact-comp.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListItemsFactCompComponent implements OnInit, AfterViewInit {

  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Input()
  public idFactura: number;
  @Output()
  selectItem = new EventEmitter<any>();
  public factura$: Observable<any>;

  constructor(
    private compraHTTPService: CompraHTTPService,
  ) {
  }

  ngOnInit() {
    this.factura$ = this.compraHTTPService.getFactura(this.idFactura.toString()).pipe(pluck('data'));
  }
  ngAfterViewInit(): void {
  }

  selectItemInvoice(item) {
    this.selectItem.emit(item);
  }
}

