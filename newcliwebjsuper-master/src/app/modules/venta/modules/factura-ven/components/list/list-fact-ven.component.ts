import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, Input, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ComponentPage } from '../../../../../../core/component-page';
import { Observable, BehaviorSubject } from 'rxjs';
import { VentaHTTPService } from '../../../../../../core/services/http/venta-http.service';
import { switchMap, tap, pluck, finalize } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../../../shared/services/confirmation-service.service';
import { List } from '../../../../../../core/list';
import { DateComponent } from 'src/app/modules/shared/components/date/date.component';
import { Chart } from 'chart.js';
declare var $: any;
import * as moment from 'moment';
import * as download from 'downloadjs';
import { DetailVenModal } from '../../../shared-ven/modals/detail/detail-ven.modal';
import { SendMailModalComponent } from 'src/app/modules/mail/components/send-mail-modal/send-mail-modal.component';

@Component({
  selector: 'list-fact-ven',
  templateUrl: './list-fact-ven.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListFactVenComponent extends ComponentPage implements OnInit, AfterViewInit, List {

  public ventas$: Observable<any> = null;
  public dataFilter$: Observable<any> = null;
  public refresh = new BehaviorSubject<void>(null);
  @ViewChild('fechaInicialForm', { static: false }) fechaInicial: DateComponent;
  @ViewChild('fechaFinalForm', { static: false }) fechaFinal: DateComponent;
  isCollapsed = true;
  public summary: any = {};
  public graficos = { graficoDiario: [] };
  public dataFilter: any = {
  }
  public bandera: any;
  public filterExtra: boolean = false;
  public filterVentas = {
    fechaInicial: "",
    fechaFinal: "",
    totalMinimo: null,
    totalMaximo: null,
    caja: null,
    pagada: 2,
    $sucursal: null,
    sucursales: [],
    productos: [],
    usuario: null,
    graficoDiario: 0,
    summary: 0,
    extraFilter: 0
  };
  constructor(
    public ventaHTTPService: VentaHTTPService,
    private modalService: NgbModal,
    protected confirmationService: ConfirmationServiceService,
    private activatedRoute: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {
    super();
  }

  ngOnInit() {
    this.dataFilter$ = this.ventaHTTPService.getDataFilter().pipe(pluck('data'), tap(resp => {
      this.dataFilter = resp;
      // this.cdr.markForCheck();
    }), finalize(() => {
      this.loadVenta();
    }))
  }

  loadVenta() {
    this.ventas$ = this.refresh.asObservable().pipe(
      switchMap(() =>
        this.activatedRoute.queryParams.pipe(tap(params => {
          if (this.firstExecute) {
            this.firstExecute = false;
            this.resetFilter();
            this.filterVentas = this.getFilterFromUrl(this.filterVentas, params, [], ['pagada', 'summary', 'graficoDiario'], [], ['sucursales', 'productos'], ['usuario', 'caja']);
            if (this.filterVentas.caja) {
              this.filterVentas.caja = this.dataFilter.cajas.find(i => i.id === this.filterVentas.caja.id);
            }
          }
        }),
          switchMap(() => {
            let paramVenta: any = { ...this.paramPagination }
            paramVenta.summary = this.filterVentas.summary;
            paramVenta.graficoDiario = this.filterVentas.graficoDiario;
            return this.ventaHTTPService.getAllPagination(paramVenta, this.filterVentas)
          }),
          tap((resp: any) => {
            this.setValuePagination(resp.data);
            if (resp.summary) {
              this.summary = resp.summary;
            };
            if (resp.graficos) {
              this.graficos = resp.graficos;
              this.chartLine();
            };
          }), pluck('data')
        )));
  }
  ngAfterViewInit(): void {
    this.initFocus();
    setTimeout(() => {
      this.loadHelp();
    }, 250);
  }
  showOrHideExtraFilter() {
    $("#divFilterExtra").collapse('hide');
    if (this.filterVentas.extraFilter) {
      $("#divFilterExtra").collapse('show');
    }
  }
  finishRenderSel2Prod() {
    this.filterVentas.extraFilter = 1;
    if ((!this.filterVentas.productos || this.filterVentas.productos.length == 0) &&
      (this.filterVentas.totalMaximo <= 0) && (this.filterVentas.totalMinimo <= 0)
    ) {
      $("#divFilterExtra").collapse('hide');
      this.filterVentas.extraFilter = 0;
    }
  }

  pageChanged() {
    this.refresh.next();
  }
  query() {
    this.setParamQuery(this.filterVentas);
  }
  reset() {
    this.resetFilter();
    this.initFocus();
    this.setParamQuery(this.filterVentas);
  }
  // resetComponents() {
  //   this.fechaInicial.setDate(this.filterVentas.fechaInicial);
  //   this.fechaFinal.setDate(this.filterVentas.fechaInicial);
  // }
  resetFilter() {
    this.filterVentas = {
      fechaInicial: "",
      fechaFinal: "",
      totalMinimo: 0,
      totalMaximo: 0,
      caja: null,
      pagada: 2,
      $sucursal: null,
      sucursales: [],
      usuario: null,
      productos: [],
      graficoDiario: 0,
      summary: 0,
      extraFilter: 0
    };
  }

  initFocus() {
    setTimeout(() => {
      $("#txtFiltroDNI").focus();
    });
  }
  remove(entity: any) {
  }
  setParamQuery(data: any) {
    let tempParam: any = {};
    tempParam.fechaInicial = encodeURI(data.fechaInicial);
    tempParam.fechaFinal = encodeURI(data.fechaFinal);
    tempParam.pagada = encodeURI(data.pagada);
    tempParam.usuario = encodeURI(data.usuario ? data.usuario.id : null);
    tempParam.totalMinimo = encodeURI(data.totalMinimo);
    tempParam.totalMaximo = encodeURI(data.totalMaximo);
    tempParam.sucursales = this.getParamURLFromArrayObj(data.sucursales);
    tempParam.productos = this.getParamURLFromArrayObj(data.productos);
    tempParam.caja = encodeURI(data.caja ? data.caja.id : null);
    tempParam.summary = encodeURI(data.summary ? '1' : '0');
    tempParam.graficoDiario = encodeURI(data.graficoDiario ? '1' : '0');
    this.setParamURL(tempParam).then(resp => {
      if (!resp) {
        this.refresh.next();
      }
    });
  }
  formatNumberFact(ptoVta, cbte) {
    let resp = "";
    ptoVta = ptoVta.toString();
    cbte = cbte.toString();
    let lengthPtoVta = 5 - ptoVta.length;
    for (var i = 0; i < lengthPtoVta; i++) {
      ptoVta = "0" + ptoVta;
    }
    let lengthCbte = 8 - cbte.length;
    for (let i = 0; i < lengthCbte; i++) {
      cbte = "0" + cbte;
    }
    return ptoVta + "-" + cbte;
  }
  isCuentaCorriente(pagos) {
    let rta = false;
    pagos.forEach((pago) => {
      if (pago.formaPago.id == 4) { // por ahora esta hardcode el id de la cta cte
        rta = true;
      }
    });
    return rta;
  }
  detailVenta(entity: any) {
    let modalDetail = this.modalService.open(DetailVenModal, { size: 'xl', backdrop: 'static' });
    modalDetail.componentInstance.idEntity = entity.id;
    modalDetail.componentInstance.title = 'Detalle de la venta | ' + entity.id;
  }

  printList(typePrint) {
    this.ventaHTTPService.printList(this.filterVentas, this.paramPagination, typePrint).subscribe((response: any) => {
      let nameFile = "ListaVentas-" + moment().format('DMMYYYYhmmss');
      let blob = new Blob([response], { type: "application/" + typePrint });
      download(blob, nameFile + "." + typePrint, "file/" + typePrint);
    });
  };

  sendEmailCbte(entity: any) {
    if (entity.idCliente && this.isNumber(entity.idCliente)) {
      this.ventaHTTPService.getEmailsCliente(entity.idCliente).subscribe((response: any) => {
        this.openModalEmail(entity, response.data);
      });
    } else {
      this.openModalEmail(entity, []);
    }
  };
  openModalEmail(entity, mails) {
    let modalSendMail = this.modalService.open(SendMailModalComponent, { size: 'lg', backdrop: 'static' });
    modalSendMail.componentInstance.title = "Enviar comprobante de venta por email | " + entity.id;
    modalSendMail.componentInstance.mails = mails;
    this.confirmationService.confirm({
      accept: (data) => {
        this.ventaHTTPService.sendEmailCbte(entity.id, data).subscribe(resp => {
          modalSendMail.close();
          this.messageToast.success("El/Los mail/s fueron enviados correctamente.");
        });
      }
    });
  }
  chartLine() {
    let horas = [];
    let values = [];
    this.graficos.graficoDiario.forEach((item) => {
      horas.push(item.hora ? item.hora : '');
      values.push(item.value ? item.value : 0);
    });
    setTimeout(() => {
      new Chart(document.getElementById("bar-chart-ventas-dia"), {
        type: 'line',
        data: {
          labels: horas,
          datasets: [
            {
              label: "Total ($)",
              backgroundColor: 'rgb(255, 99, 132)',
              borderColor: 'rgb(255, 99, 132)',
              fill: false,
              data: values,
            }
          ]
        },
        options: {
          legend: { display: false },
          title: {
            display: true,
            text: 'Ventas del día de hoy'
          },
          scales: {
            xAxes: [{
              display: true,
              scaleLabel: {
                display: true,
                labelString: 'Horas'
              }
            }],
            yAxes: [{
              display: true,
              scaleLabel: {
                display: true,
                labelString: 'Pesos'
              }
            }]
          }
        }
      });
    });
  }
}

