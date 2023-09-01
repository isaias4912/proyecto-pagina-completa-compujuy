import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, Input, ChangeDetectorRef, HostListener } from '@angular/core';
import { ComponentPage } from '../../../../core/component-page';
import { Observable, of, Subject, concat, BehaviorSubject } from 'rxjs';
import { CtaCteHTTPService } from '../../../../core/services/http/cta-cte-http.service';
import { switchMap, tap, pluck, map, take } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../shared/services/confirmation-service.service';
import { ActivatedRoute } from '@angular/router';
import { ClienteHTTPService } from 'src/app/core/services/http/cliente-http.service';
import { DialogGenComponent } from 'src/app/modules/shared/components/dialog/gen/dialog-gen.component';
import { DetailVenComponent } from 'src/app/modules/venta/modules/shared-ven/components/detail/detail-ven.component';
import { AdItem } from 'src/app/modules/shared/models/ad-item';
import { SelectCliService } from 'src/app/modules/cliente/modules/shared-cli/components/select/select-cli.service';
import { PayModal } from 'src/app/modules/cta-cte/modal/pay/pay.modal';
import { DetailMtoModal } from 'src/app/modules/cta-cte/modal/detail-mto/detail-mto.modal';
import { DataCount } from 'src/app/core/interfaces/idataCount';
declare var $: any;

@Component({
  selector: 'list-cta-cte',
  templateUrl: './list-cta-cte.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListCtaCteComponent extends ComponentPage implements OnInit, AfterViewInit {
  public actions$: Observable<any> = null;
  private refreshCtasCte = new BehaviorSubject<void>(null);
  public filterMto = {
    cliente: null, estado: 0, idFacturaVenta: null
  };
  dataCount: DataCount = {
    getSize: () => { return 99 }
  };
  public dataOptions = {
    filterEstados: [{ value: 0, name: "Inpagos" }, { value: 1, name: "Pagados" }, { value: 2, name: "Todos" }]
  };
  public dataPago: any = {};
  public dataPagosPreview: any = {};
  public movimientos: any;
  public infoCtaCte: any = { cliente: null };
  constructor(
    public ctaCteHTTPService: CtaCteHTTPService,
    private modalService: NgbModal,
    protected confirmationService: ConfirmationServiceService,
    private cdr: ChangeDetectorRef,
    private activatedRoute: ActivatedRoute,
    private clienteHTTPService: ClienteHTTPService,
    private selectCliService: SelectCliService
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
          if (params.cliente) {
            this.id = this.decrypt(params.cliente);
            this.filterMto.cliente = { id: this.id }
          }
        }
      }
      ),
        switchMap(() => this.ctaCteHTTPService.getAllMtoCtaCte(this.paramPagination, this.filterMto).pipe(
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
      this.ctaCteHTTPService.getCtaCteDataPagos().pipe(pluck('data')).subscribe(resp => {
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
      cliente: null, estado: 0, idFacturaVenta: null
    };
  }
  initFocus() {
    setTimeout(() => {
      $("#txtFiltroNombre").focus();
    });
  }
  onChangeCli(cliente: any) {
    console.log('cliente', cliente)
    if (cliente && (!cliente.cuentaCorriente || cliente.cuentaCorriente == null)) {
      this.messageToast.warning('El cliente ' + cliente.descripcion + ' no tiene asociado una cuenta corriente.');
    }
    this.setParamQuery(this.filterMto);
  }
  showPago() {
    if (this.dataPago.pagaCon >= this.dataPago.pagoMinimo) {
      this.dataPagosPreview = {};
      let dataPagosPreview = {
        pagoCon: this.dataPago.pagaCon,
        interes: this.dataPago.interes,
        cliente: this.filterMto.cliente,
        movimientosCtaCte: []
      }
      console.log('dataPagosPreview', dataPagosPreview)
      this.movimientos.forEach(mov => {
        let temPagaCon = this.dataPago.pagaCon;
        if (mov.$$activo) {
          dataPagosPreview.movimientosCtaCte.push({
            id: mov.id
          });
        }
      });
      const modalNewCtaCte = this.modalService.open(PayModal, { size: 'xl', backdrop: 'static', windowClass: 'payModal' });
      modalNewCtaCte.componentInstance.title = this.getTitle();
      modalNewCtaCte.componentInstance.dataFilter = dataPagosPreview;
      modalNewCtaCte.componentInstance.dataPago = this.dataPago.dataPago;
      modalNewCtaCte.componentInstance.type = 'CTA_CTE_CLI';
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
    modalDetailCtaCte.componentInstance.type = 'CTA_CTE_CLI';

  }
  detailVenta(id: number) {
    let modalDetail = this.modalService.open(DialogGenComponent, { size: 'xl', backdrop: 'static' });
    modalDetail.componentInstance.item = new AdItem(DetailVenComponent, { idEntity: id });
    modalDetail.componentInstance.title = 'Detalle de la venta | ' + id;
  }
  setParamQuery(data: any) {
    let tempParam: any = {};
    if (data.idFacturaVenta == null) {
      data.idFacturaVenta = "";
    }
    tempParam.idFacturaVenta = encodeURI(data.idFacturaVenta);
    tempParam.cliente = encodeURI(data.cliente ? this.encrypt(data.cliente.id) : null);
    tempParam.estado = encodeURI(data.estado);
    this.setParamURL(tempParam).then(resp => {
      if (!resp) {
        this.refreshCtasCte.next();
      }
    });
  }
  private getTitle(): string {
    let resp = "";
    if (this.infoCtaCte) {
      if (this.infoCtaCte.cliente && this.infoCtaCte.cliente.nombreCompleto) {
        resp = "Pagos-" + this.infoCtaCte.cliente.nombreCompleto;
        if (this.infoCtaCte.cliente && this.infoCtaCte.cliente.tipoDocCliente && this.infoCtaCte.cliente.nroDocCliente) {
          resp = resp + "-" + this.infoCtaCte.cliente.tipoDocCliente + " " + this.infoCtaCte.cliente.nroDocCliente;
        }
        return resp;
      }
    }
    return "pagos";
  }
}

