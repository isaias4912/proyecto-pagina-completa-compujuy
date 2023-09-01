import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input, OnDestroy, Output, EventEmitter } from '@angular/core';
import { ComponentPage } from '../../../../core/component-page';
import { Observable, BehaviorSubject } from 'rxjs';
import { switchMap, pluck, tap, map } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../shared/services/confirmation-service.service';
import { CtaCteHTTPService } from '../../../../core/services/http/cta-cte-http.service';
import { DetailMtoModal } from '../../modal/detail-mto/detail-mto.modal';
import { DialogGenComponent } from '../../../shared/components/dialog/gen/dialog-gen.component';
import { DetailVenComponent } from '../../../venta/modules/shared-ven/components/detail/detail-ven.component';
import { AdItem } from '../../../shared/models/ad-item';
import { CtaCteProvHTTPService } from 'src/app/core/services/http/cta-cte-prov-http.service';
import { DetailFactCompModalComponent } from 'src/app/modules/compra/modules/factura-comp/modules/shared-fact-comp/components/detail/detail-fact-comp-modal.component';

declare var $: any;

@Component({
  selector: 'detail-mto',
  templateUrl: './detail-mto.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DetailMtoComponent extends ComponentPage implements OnInit, AfterViewInit, OnDestroy {

  public listDetail$: Observable<any> = null;
  public payments: any;
  private refresh = new BehaviorSubject<void>(null);
  public dataPago: any = {};
  public seed = Math.random().toString(36).substring(5);
  @Input()
  public type: 'CTA_CTE_CLI' | 'CTA_CTE_PROV';
  @Input()
  public mto: any = {};
  public showFormasPago: boolean = false;
  constructor(
    private ctaCteHTTPService: CtaCteHTTPService,
    private ctaCteProvHTTPService: CtaCteProvHTTPService,
    private modalService: NgbModal,
    protected confirmationService: ConfirmationServiceService,
  ) {
    super();
  }


  ngOnInit() {
    if (this.type == 'CTA_CTE_CLI') {
      this.listDetail$ = this.refresh.asObservable().pipe(
        switchMap(() => this.ctaCteHTTPService.getDetailCtaCte(this.mto.id))
        , pluck('data'), map(item => {
          return item.map(value => {
            value.$$activeRow = this.isSelected(value.movimientos);
            value.$$showFormaPago = false;
            return value;
          })
        }), tap(resp => {
        })
      );
    }
    if (this.type == 'CTA_CTE_PROV') {
      this.listDetail$ = this.refresh.asObservable().pipe(
        switchMap(() => this.ctaCteProvHTTPService.getDetailCtaCte(this.mto.id))
        , pluck('data'), map(item => {
          return item.map(value => {
            value.$$activeRow = this.isSelected(value.movimientos);
            value.$$showFormaPago = false;
            return value;
          })
        }), tap(resp => {
        })
      );
    }
  }
  ngAfterViewInit(): void {
  }
  pageChanged() {
    this.refresh.next();
  }
  ngOnDestroy(): void {
  }
  detail(entity: any) {
    const modalDetailCtaCte = this.modalService.open(DetailMtoModal, { size: 'xl', backdrop: 'static' });
    modalDetailCtaCte.componentInstance.mto = { ...entity };
    modalDetailCtaCte.componentInstance.type = this.type;
  }
  detailVenta(id: number) {
    let modalDetail = this.modalService.open(DialogGenComponent, { size: 'xl', backdrop: 'static' });
    modalDetail.componentInstance.item = new AdItem(DetailVenComponent, { idEntity: id });
    modalDetail.componentInstance.title = 'Detalle de la venta | ' + id;
  }
  detailCbteComp(id: number) {
    const modalInvoice = this.modalService.open(DetailFactCompModalComponent, { size: 'xl', backdrop: 'static' });
    modalInvoice.componentInstance.idInvoice = id;
  }
  isSelected(movimientos: []): boolean {
    if (!movimientos) {
      return false;
    }
    let resp = false;
    movimientos.forEach((item: any) => {
      if (item.id == this.mto.id) {
        resp = true;
      }
    })
    return resp;
  }
}

