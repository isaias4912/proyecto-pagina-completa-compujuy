import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, Input, OnDestroy, Output, EventEmitter } from '@angular/core';
import { ComponentPage } from '../../../../core/component-page';
import { Observable, BehaviorSubject, Subscription } from 'rxjs';
import { switchMap, pluck, tap } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../shared/services/confirmation-service.service';
import { CtaCteHTTPService } from '../../../../core/services/http/cta-cte-http.service';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { IDataForm } from '../../../../core/models/idata-form';
import { IPago } from '../../../../core/interfaces';
import { ItemsPagoComponent } from '../../../comprobante/components/pago/items/items-pago.component';
import { ComprobanteService } from '../../../comprobante/services/comprobante.service';
import { CtaCteProvHTTPService } from 'src/app/core/services/http/cta-cte-prov-http.service';

declare var $: any;

@Component({
  selector: 'pay',
  templateUrl: './pay.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PayComponent extends ComponentPage implements OnInit, AfterViewInit, OnDestroy {

  public payments$: Observable<any> = null;
  public payments: any;
  private refresh = new BehaviorSubject<void>(null);
  @Input()
  public dataPago: any = {};
  @Input()
  public type: 'CTA_CTE_CLI' | 'CTA_CTE_PROV';
  @Input()
  public dataForm: IDataForm = { submitted: false };
  @Input()
  public dataFilter: any = {};
  @Input()
  public buttonAction: boolean = true;
  @Output()
  eventSave = new EventEmitter<any>();
  public formPay: FormGroup;
  subPaga: Subscription;
  @ViewChild(ItemsPagoComponent, { static: false })
  itemsPagoComponent: ItemsPagoComponent;
  constructor(
    private formBuilder: FormBuilder,
    private ctaCteHTTPService: CtaCteHTTPService,
    private ctaCteProvHTTPService: CtaCteProvHTTPService,
    private modalService: NgbModal,
    protected confirmationService: ConfirmationServiceService,
    private comprobanteService: ComprobanteService
  ) {
    super();
  }


  ngOnInit() {
    this.setCantView(1);
    this.formPay = this.formBuilder.group({
      montoNeto: [null, [Validators.required]],
      interes: [0, [Validators.required]],
      montoTotal: [null, [Validators.required]],
      pagoCon: [null, [Validators.required, Validators.min(0.01)]],
      vuelto: [null, []],
      saldo: [null, []],
      descripcion: [null, []],
      movimientosCtaCte: [null, [Validators.required]],
    });
    if (this.type == 'CTA_CTE_CLI') {
      this.formPay.addControl('cliente', new FormControl(this.dataFilter.cliente, [Validators.required]));
      this.payments$ = this.refresh.asObservable().pipe(
        switchMap(() => this.ctaCteHTTPService.getPreviewPagos(this.dataFilter))
        , pluck('data'), tap(resp => {
          this.setData(resp);
        })
      );
    }
    if (this.type == 'CTA_CTE_PROV') {
      this.formPay.addControl('proveedor', new FormControl(this.dataFilter.proveedor, [Validators.required]));
      this.payments$ = this.refresh.asObservable().pipe(
        switchMap(() => this.ctaCteProvHTTPService.getPreviewPagos(this.dataFilter))
        , pluck('data'), tap(resp => {
          this.setData(resp);
        })
      );
    }
    this.subPaga = this.formPay.get('pagoCon').valueChanges.subscribe(resp => {
      this.comprobanteService.changeTotal(resp);
      if (resp) {
        if (resp > 0) {
          this.calc(resp);
        } else {
          this.calc(0);
        }
      }
    });
    this.finishViewEvent.subscribe(() => {
      setTimeout(() => {
        this.itemsPagoComponent.initValue();
      });
    });
  }
  ngAfterViewInit(): void {
    this.reset();
  }
  private setData(resp: any) {
    this.payments = resp;
    this.formPay.patchValue({
      montoNeto: resp.montoNeto,
      interes: resp.interes,
      montoTotal: resp.montoTotal,
      pagoCon: resp.pagoCon,
      movimientosCtaCte: resp.movimientosCtaCte,
      vuelto: null,
      descripcion: null,
    });
    this.calc(resp.pagoCon);
  }
  pageChanged() {
    this.refresh.next();
  }
  savePayCli(option = 1) {
    if (this.formPay.valid && this.itemsPagoComponent.formItems.valid) {
      let pago = this.formPay.value;
      let formasPagoCtaCte: Array<IPago> = this.itemsPagoComponent.getDataPagos();
      pago.pagos = formasPagoCtaCte;
      this.ctaCteHTTPService.pagarCtaCte(pago).subscribe(resp => {
        this.messageToast.success('Pago registrado correctamente');
        this.eventSave.emit(resp.data);
      });
    }
  }
  savePayProv(option = 1) {
    if (this.formPay.valid && this.itemsPagoComponent.formItems.valid) {
      let pago = this.formPay.value;
      let formasPagoCtaCte: Array<IPago> = this.itemsPagoComponent.getDataPagos();
      pago.pagos = formasPagoCtaCte;
      this.ctaCteProvHTTPService.pagarCtaCte(pago).subscribe(resp => {
        this.messageToast.success('Pago registrado correctamente');
        this.eventSave.emit(resp.data);
      });
    }
  }
  finishLoadComponent(evt: number) {
    this.loadingFinishView(evt);
  }
  calc(pago: number) {
    let saldo: number = 0;
    let vuelto: number = 0;
    let total: number = this.formPay.get('montoTotal').value;
    let paga: number = pago;
    if (paga >= total) {
      saldo = 0;
      vuelto = paga - total;
    } else {
      saldo = total - pago;
      vuelto = 0;
    }
    saldo = this.round2(saldo);
    vuelto = this.round2(vuelto);
    this.formPay.patchValue({
      saldo: saldo,
      vuelto: vuelto,
    });
  }
  reset() {
    this.dataFilter = {

    };
    setTimeout(() => {
      $("#txtFiltroNombre").focus();
    });
  }
  ngOnDestroy(): void {
    if (this.subPaga) {
      this.subPaga.unsubscribe();
    }
  }
  get f() {
    return this.formPay.controls;
  }
}

