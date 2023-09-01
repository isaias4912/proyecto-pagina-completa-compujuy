import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input } from '@angular/core';
import { Observable } from 'rxjs';
import { pluck } from 'rxjs/operators';
import { VentaHTTPService } from '../../../../../../core/services/http/venta-http.service';

@Component({
  selector: 'detail-trans',
  templateUrl: './detail-trans.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DetailTransComponent implements OnInit, AfterViewInit {

  @Input()
  public idEntity: number;
  detTransacccion$: Observable<any>;

  constructor(
    private ventaHTTPService: VentaHTTPService
  ) {
  }

  ngOnInit() {
    this.detTransacccion$ = this.ventaHTTPService.getDetalleTransaccion(this.idEntity).pipe(pluck('data'));
  }

  ngAfterViewInit(): void {
  }

}

