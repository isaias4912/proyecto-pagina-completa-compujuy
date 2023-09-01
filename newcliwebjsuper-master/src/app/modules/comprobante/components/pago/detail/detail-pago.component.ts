import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, Input, Output, EventEmitter } from '@angular/core';
import { UtilPage } from '../../../../../core/util-page';
import { CtaCteHTTPService } from '../../../../../core/services/http/cta-cte-http.service';
import { ComprobanteService } from '../../../services/comprobante.service';
import { BehaviorSubject, Observable } from 'rxjs';
import { switchMap, pluck } from 'rxjs/operators';
import { CtaCteProvHTTPService } from 'src/app/core/services/http/cta-cte-prov-http.service';

declare var $: any;

@Component({
  selector: 'detail-pago',
  templateUrl: './detail-pago.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DetailPagoComponent extends UtilPage implements OnInit, AfterViewInit {

  private refresh = new BehaviorSubject<void>(null);
  public pagos$: Observable<any> = null;
  @Input()
  id: number;
  @Input()
  public type: 'CTA_CTE_CLI' | 'CTA_CTE_PROV';
  constructor(
    private ctaCteHTTPService: CtaCteHTTPService,
    private ctaCteProvHTTPService: CtaCteProvHTTPService,
  ) {
    super();
  }

  ngOnInit() {
    if (this.type == 'CTA_CTE_CLI') {
      this.pagos$ = this.refresh.asObservable().pipe(
        switchMap(() => this.ctaCteHTTPService.getPagosFromIdPagoCtaCte(this.id).pipe(pluck('data'))));
    }
    if (this.type == 'CTA_CTE_PROV') {
      this.pagos$ = this.refresh.asObservable().pipe(
        switchMap(() => this.ctaCteProvHTTPService.getPagosFromIdPagoCtaCte(this.id).pipe(pluck('data'))));
    }
  }
  ngAfterViewInit(): void {
  }

}

