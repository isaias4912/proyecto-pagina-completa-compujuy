import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, Input, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ComponentPage } from '../../../../../../core/component-page';
import { Observable, BehaviorSubject } from 'rxjs';
import { switchMap, tap, pluck, finalize } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../../../shared/services/confirmation-service.service';
import { List } from '../../../../../../core/list';
import { DateComponent } from 'src/app/modules/shared/components/date/date.component';
declare var $: any;
import { PresupuestoHTTPService } from 'src/app/core/services/http/presupuesto-http.service';
import { DetailPresupuestoModal } from '../../../shared-presupuesto/modals/detail/detail-presupuesto.modal';

@Component({
  selector: 'list-presupuesto-ven',
  templateUrl: './list-presupuesto-ven.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListPresupuestoVenComponent extends ComponentPage implements OnInit, AfterViewInit, List {

  public presupuestos$: Observable<any> = null;
  public dataFilter$: Observable<any> = null;
  public refresh = new BehaviorSubject<void>(null);
  @ViewChild('fechaInicialForm', { static: false }) fechaInicial: DateComponent;
  @ViewChild('fechaFinalForm', { static: false }) fechaFinal: DateComponent;
  isCollapsed = true;
  public dataFilter: any = {
  }
  public bandera: any;
  public filterExtra: boolean = false;
  public filterPres = {
    fechaInicial: "",
    fechaFinal: "",
    totalMinimo: null,
    totalMaximo: null,
    $sucursal: null,
    sucursales: [],
    productos: [],
    usuario: null,
    estadoCbte: 0,
    extraFilter: 0
  };
  constructor(
    public presupuestoHTTPService: PresupuestoHTTPService,
    private modalService: NgbModal,
    protected confirmationService: ConfirmationServiceService,
    private activatedRoute: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {
    super();
  }

  ngOnInit() {
    this.dataFilter$ = this.presupuestoHTTPService.getDataFilter().pipe(pluck('data'), tap(resp => {
      this.dataFilter = resp;
    }), finalize(() => {
      this.loadPresupuesto();
    }))
  }

  loadPresupuesto() {
    this.presupuestos$ = this.refresh.asObservable().pipe(
      switchMap(() =>
        this.activatedRoute.queryParams.pipe(tap(params => {
          if (this.firstExecute) {
            this.firstExecute = false;
            this.resetFilter();
            this.filterPres = this.getFilterFromUrl(this.filterPres, params, [],
              [], [], ['sucursales', 'productos'], ['usuario', 'caja']);
          }
        }),
          switchMap(() => {
            let paramPres: any = { ...this.paramPagination }
            return this.presupuestoHTTPService.getAllPagination(paramPres, this.filterPres)
          }),
          tap((resp: any) => {
            this.setValuePagination(resp.data);
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
    if (this.filterPres.extraFilter) {
      $("#divFilterExtra").collapse('show');
    }
  }
  finishRenderSel2Prod() {
    if ((!this.filterPres.productos || this.filterPres.productos.length == 0) &&
      (this.filterPres.totalMaximo <= 0) && (this.filterPres.totalMinimo <= 0)
    ) {
      $("#divFilterExtra").collapse('hide');
      this.filterPres.extraFilter = 0;
    }
  }

  pageChanged() {
    this.refresh.next();
  }
  query() {
    this.setParamQuery(this.filterPres);
  }
  reset() {
    this.resetFilter();
    this.initFocus();
    this.setParamQuery(this.filterPres);
  }
  // resetComponents() {
  //   this.fechaInicial.setDate(this.filterVentas.fechaInicial);
  //   this.fechaFinal.setDate(this.filterVentas.fechaInicial);
  // }
  resetFilter() {
    this.filterPres = {
      fechaInicial: "",
      fechaFinal: "",
      totalMinimo: 0,
      totalMaximo: 0,
      $sucursal: null,
      sucursales: [],
      usuario: null,
      productos: [],
      estadoCbte: 0,
      extraFilter:0
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
    tempParam.usuario = encodeURI(data.usuario ? data.usuario.id : null);
    tempParam.totalMinimo = encodeURI(data.totalMinimo);
    tempParam.totalMaximo = encodeURI(data.totalMaximo);
    tempParam.sucursales = this.getParamURLFromArrayObj(data.sucursales);
    tempParam.productos = this.getParamURLFromArrayObj(data.productos);
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

  detailPresupuesto(entity: any) {
    let modalDetail = this.modalService.open(DetailPresupuestoModal, { size: 'xl', backdrop: 'static' });
    modalDetail.componentInstance.idEntity = entity.id;
    modalDetail.componentInstance.title = 'Detalle del presupuesto | ' + entity.id;
  }
  updateEstadoCbtePresupuesto(presupuesto: any, value: number) {
    console.log('presupuesto', presupuesto)
    this.presupuestoHTTPService.updateEstadoCbtePresupuesto(presupuesto.id, { value: value }).subscribe(x => {
      // presupuesto.estadoCbte = x.data.value;
      // console.log('presupuesto', presupuesto)
      this.pageChanged();
      // console.log('value', x)
    })
    // let modalDetail = this.modalService.open(DetailVenModal, { size: 'xl', backdrop: 'static' });
    // modalDetail.componentInstance.idEntity = entity.id;
    // modalDetail.componentInstance.title = 'Detalle de la venta | ' + entity.id;
  }

  printList(typePrint) {
    // this.presupuestoHTTPService.printList(this.filterPres, this.paramPagination, typePrint).subscribe((response: any) => {
    //   let nameFile = "ListaVentas-" + moment().format('DMMYYYYhmmss');
    //   let blob = new Blob([response], { type: "application/" + typePrint });
    //   download(blob, nameFile + "." + typePrint, "file/" + typePrint);
    // });
  };

  sendEmailCbte(entity: any) {
    // if (entity.idCliente && this.isNumber(entity.idCliente)) {
    //   this.presupuestoHTTPService.getEmailsCliente(entity.idCliente).subscribe((response: any) => {
    //     this.openModalEmail(entity, response.data);
    //   });
    // } else {
    //   this.openModalEmail(entity, []);
    // }
  };
  openModalEmail(entity, mails) {
    // let modalSendMail = this.modalService.open(SendMailModalComponent, { size: 'lg', backdrop: 'static' });
    // modalSendMail.componentInstance.title = "Enviar comprobante de venta por email | " + entity.id;
    // modalSendMail.componentInstance.mails = mails;
    // this.confirmationService.confirm({
    //   accept: (data) => {
    //     this.presupuestoHTTPService.sendEmailCbte(entity.id, data).subscribe(resp => {
    //       modalSendMail.close();
    //       this.messageToast.success("El/Los mail/s fueron enviados correctamente.");
    //     });
    //   }
    // });
  }

}

