import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { ComponentPage } from '../../../../core/component-page';
import { Observable, concat, BehaviorSubject } from 'rxjs';
import { switchMap, tap, pluck } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../shared/services/confirmation-service.service';
import { ActivatedRoute } from '@angular/router';
import { CtaCteProvHTTPService } from 'src/app/core/services/http/cta-cte-prov-http.service';
import { SelectProvService } from 'src/app/modules/proveedor/modules/shared-prov/components/select/select-prov.service';
import { PayModal } from 'src/app/modules/cta-cte/modal/pay/pay.modal';
import { DetailMtoModal } from 'src/app/modules/cta-cte/modal/detail-mto/detail-mto.modal';
import { DetailFactCompModalComponent } from 'src/app/modules/compra/modules/factura-comp/modules/shared-fact-comp/components/detail/detail-fact-comp-modal.component';
declare var $: any;

@Component({
  selector: 'list-cta-cte-prov',
  templateUrl: './list-cta-cte-prov.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListCtaCteProvComponent extends ComponentPage implements OnInit, AfterViewInit {
  public actions$: Observable<any> = null;
  private refreshCtasCte = new BehaviorSubject<void>(null);
  public filterMto = {
    proveedor: null, estado: 0, idFacturaCompra: null
  };
  public dataOptions = {
    filterEstados: [{ value: 0, name: "Inpagos" }, { value: 1, name: "Pagados" }, { value: 2, name: "Todos" }]
  };
  public dataPago: any = {};
  public dataPagosPreview: any = {};
  public movimientos: any;
  public infoCtaCte: any = { cliente: null };
  constructor(
    private ctaCteProvHTTPService: CtaCteProvHTTPService,
    private modalService: NgbModal,
    protected confirmationService: ConfirmationServiceService,
    private cdr: ChangeDetectorRef,
    private activatedRoute: ActivatedRoute,
    private selectProvService: SelectProvService
  ) {
    super();
  }
  ngOnInit() {
    this.actions$ = this.refreshCtasCte.asObservable().pipe(
      switchMap(() => this.activatedRoute.queryParams.pipe(tap(params => {
        if (this.firstExecute) {
          this.firstExecute = false;
          this.resetFilter();
          this.filterMto = this.getFilterFromUrl(this.filterMto, params, [], ['estado'], [], []);
          this.id = null;
          if (params.proveedor) {
            this.id = this.decrypt(params.proveedor);
            this.filterMto.proveedor = { id: this.id }
          }
        }
      }
      ),
        switchMap(() => this.ctaCteProvHTTPService.getAllMtoCtaCte(this.paramPagination, this.filterMto).pipe(
          tap(resp => {
            this.resetDataPago();
            this.movimientos = resp.data;
            this.infoCtaCte = resp.dataAditional;
            this.cdr.markForCheck();
          })))
      )
      )
    );
  }

  ngAfterViewInit(): void {
    this.getDataPago();
  }

  getDataPago() {
    if (!this.dataPago.dataPago) {
      this.ctaCteProvHTTPService.getCtaCteDataPagos().pipe(pluck('data')).subscribe(resp => {
        this.dataPago.dataPago = resp;
      });
    }
  }
  checkRowCtaCte(items: any) {
    this.checkRow(items);
    this.calc();
    setTimeout(() => {
      $("#numInteres").select();
    }, 100);
  }
  checkAllRowCtaCte(value: any, items: any) {
    this.checkAllRow(value, items);
    this.calc();
    setTimeout(() => {
      $("#numInteres").select();
    }, 100);
  }
  calc() {
    this.dataPago.totalSaldoSeleccion = 0.0;
    let ultimo = null;
    let i = 0;
    this.movimientos.forEach(mov => {
      if (mov.$$activo) {
        this.dataPago.totalSaldoSeleccion = this.dataPago.totalSaldoSeleccion + mov.saldo;
        i++;
        ultimo = mov.saldo;
      }
    });
    if (i > 1) {
      this.dataPago.pagoMinimo = this.round2((this.dataPago.totalSaldoSeleccion - ultimo) + 0.05);
    } else {
      if (i > 0) {
        this.dataPago.pagoMinimo = 0.05;
      } else {
        this.dataPago.pagoMinimo = 0.0;
      }
    }
    this.dataPago.totalSaldoSeleccion = this.round2(this.dataPago.totalSaldoSeleccion);
    this.dataPago.pagaCon = this.dataPago.totalSaldoSeleccion;
    this.dataPago.vuelto = 0.0;
  }
  pageChanged() {
    this.refreshCtasCte.next();
  }

  query() {
    this.setParamQuery(this.filterMto);
  }
  reset() {
    this.resetFilter();
    this.initFocus();
    this.setParamQuery(this.filterMto);
  }
  resetFilter() {
    this.filterMto = {
      proveedor: null, estado: 0, idFacturaCompra: null
    };
  }
  initFocus() {
    setTimeout(() => {
      $("#txtFiltroNombre").focus();
    });
  }
  onChangeProv(proveedor: any) {
    this.setParamQuery(this.filterMto);
  }
  showPago() {
    if (this.dataPago.pagaCon >= this.dataPago.pagoMinimo) {
      this.dataPagosPreview = {};
      let dataPagosPreview = {
        pagoCon: this.dataPago.pagaCon,
        interes: this.dataPago.interes,
        proveedor: this.filterMto.proveedor,
        movimientosCtaCte: []
      }
      this.movimientos.forEach(mov => {
        let temPagaCon = this.dataPago.pagaCon;
        if (mov.$$activo) {
          dataPagosPreview.movimientosCtaCte.push({
            id: mov.id
          });
        }
      });
      const modalNewCtaCte = this.modalService.open(PayModal, { size: 'xl', backdrop: 'static' });
      modalNewCtaCte.componentInstance.dataFilter = dataPagosPreview;
      modalNewCtaCte.componentInstance.dataPago = this.dataPago.dataPago;
      modalNewCtaCte.componentInstance.type = 'CTA_CTE_PROV';
      modalNewCtaCte.result.then((persona) => {
        this.refreshCtasCte.next();
      }, (reason) => {
      });
    } else {
    }
  };
  resetDataPago() {
    this.checkAll = false;
    this.disabledButtonsTable = true;
    this.dataPago.totalSaldoSeleccion = 0.0;
    this.dataPago.pagoMinimo = 0.0;
    this.dataPago.interes = 0.0;
  }
  detail(entity: any) {
    const modalDetailCtaCte = this.modalService.open(DetailMtoModal, { size: 'xl', backdrop: 'static' });
    modalDetailCtaCte.componentInstance.mto = entity;
    modalDetailCtaCte.componentInstance.type = 'CTA_CTE_PROV';
  }
  detailCbteComp(id: number) {
    const modalInvoice = this.modalService.open(DetailFactCompModalComponent, { size: 'xl', backdrop: 'static' });
    modalInvoice.componentInstance.idInvoice = id;
  }

  setParamQuery(data: any) {
    let tempParam: any = {};
    if (data.idFacturaCompra == null) {
      data.idFacturaCompra = "";
    }
    tempParam.idFacturaCompra = encodeURI(data.idFacturaCompra);
    tempParam.proveedor = encodeURI(data.proveedor ? this.encrypt(data.proveedor.id) : null);
    tempParam.estado = encodeURI(data.estado);
    this.setParamURL(tempParam).then(resp => {
      if (!resp) {
        this.refreshCtasCte.next();
      }
    });
  }
}

