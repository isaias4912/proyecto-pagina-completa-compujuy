import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, Input, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ComponentPage } from '../../../../core/component-page';
import { Comprobante } from '../../../../core/enums';
import { Observable, BehaviorSubject } from 'rxjs';
import { VentaHTTPService } from '../../../../core/services/http/venta-http.service';
import { switchMap, tap, pluck, finalize } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { List } from '../../../../core/list';
declare var $: any;
import * as moment from 'moment';
import * as download from 'downloadjs';
import { DetailVenModal } from 'src/app/modules/venta/modules/shared-ven/modals/detail/detail-ven.modal';

@Component({
  selector: 'libro-iva-ven',
  templateUrl: './libro-iva-ven.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LibroIvaVenComponent extends ComponentPage implements OnInit, AfterViewInit, List {

  public ventas$: Observable<any> = null;
  public refresh = new BehaviorSubject<void>(null);
  isCollapsed = true;
  public dataFilter: any = {
    comprobantes: [
      Comprobante.FACTURA_A,
      Comprobante.FACTURA_B,
      Comprobante.FACTURA_C,
      Comprobante.FACTURA_x,
    ]
  }
  public bandera: any;
  public filterExtra: boolean = false;
  public extraData: any = {};
  public filterVentas: any = {
    fechaInicial: "",
    fechaFinal: "",
    totalMinimo: null,
    totalMaximo: null,
    comprobantes: null,
    afipValida: 1,
    $sucursal: null,
    sucursales: [],
    productos: [],
    usuario: null,
  };
  constructor(
    private ventaHTTPService: VentaHTTPService,
    private modalService: NgbModal,
    private activatedRoute: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {
    super();
  }

  ngOnInit() {
    setTimeout(() => { // para que se cargue las fechas , con los datos iniciales
      this.loadVenta();
      this.cdr.markForCheck();
    });
  }

  loadVenta() {
    this.ventas$ = this.refresh.asObservable().pipe(
      switchMap(() =>
        this.activatedRoute.queryParams.pipe(tap(params => {
          if (this.firstExecute) {
            this.firstExecute = false;
            this.resetFilter();
            this.filterVentas = this.getFilterFromUrl(this.filterVentas, params, [], ['afipValida'], [], [], [], ['comprobantes']);
            this.loadComponents();
          }
        }),
          switchMap(() => this.ventaHTTPService.getLibroIvaAllPagination(this.paramPagination, this.filterVentas)),
          tap((resp: any) => {
            this.extraData = resp.extraData;
            this.setValuePagination(resp);
          }), pluck('data')
        )));
  }
  ngAfterViewInit(): void {
    this.initFocus();
  }
  loadComponents() {
    let dataTemp: any = [];
    if (this.filterVentas.comprobantes) {
      this.dataFilter.comprobantes.forEach((c2) => {
        let selected = false;
        this.filterVentas.comprobantes.forEach((c1) => {
          if (c1 == c2) {
            selected = true;
          }
        });
        dataTemp.push({ id: c2, text: c2, selected: selected });
      });
    } else {
      this.dataFilter.comprobantes.forEach((c2) => {
        dataTemp.push({ id: c2, text: c2, selected: false });
      });
    }
    setTimeout(() => {
      $("#cmbCbte").select2({
        data: dataTemp,
        theme: 'bootstrap4',
        width: "100%",
        placeholder: "Seleccione uno o mas cbte/s."
      });
      $('#cmbCbte').on("select2:select", (e) => {
        this.filterVentas.comprobantes = this.getComprobantes();
      });
      $('#cmbCbte').on("select2:unselect", (e) => {
        this.filterVentas.comprobantes = this.getComprobantes();
      });
      this.initFocus();
    });
  }
  getComprobantes() {
    let tempCbtes = $("#cmbCbte").select2("val");
    if (tempCbtes != null) {
      let tempRes = [];
      tempCbtes.forEach((id) => {
        tempRes.push(id);
      });
      return tempRes;
    } else {
      return null;
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
    $('#cmbCbte').val(null).trigger('change');
    this.setParamQuery(this.filterVentas);
  }

  resetFilter() {
    this.filterVentas = {
      fechaInicial: "",
      fechaFinal: "",
      totalMinimo: 0,
      totalMaximo: 0,
      comprobantes: null,
      afipValida: 1,
      $sucursal: null,
      sucursales: [],
      usuario: null,
      productos: []
    };
  }

  initFocus() {
    setTimeout(() => {
      $("#fechaDesdeFilter").focus();
    });
  }
  remove(entity: any) {
  }
  setParamQuery(data: any) {
    let tempParam: any = {};
    tempParam.fechaInicial = encodeURI(data.fechaInicial);
    tempParam.fechaFinal = encodeURI(data.fechaFinal);
    tempParam.sucursales = this.getParamURLFromArrayObj(data.sucursales);
    tempParam.afipValida = encodeURI(data.afipValida);
    tempParam.comprobantes = this.getParamURLFromArrayId(data.comprobantes);
    this.setParamURL(tempParam).then(resp => {
      if (!resp) {
        this.refresh.next();
      }
    });
  }

  detailVenta(entity: any) {
    let modalDetail = this.modalService.open(DetailVenModal, { size: 'xl', backdrop: 'static' });
    modalDetail.componentInstance.idEntity = entity.id;
    modalDetail.componentInstance.title = 'Detalle de la venta | ' + entity.id;
  }
  getIvaById(id: number) {
    return this.extraData.ivas.find(x => x.iva.id == id);
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
  printList(typePrint) {
    this.ventaHTTPService.printList(this.filterVentas, this.paramPagination, typePrint).subscribe((response: any) => {
      let nameFile = "ListaVentas-" + moment().format('DMMYYYYhmmss');
      let blob = new Blob([response], { type: "application/" + typePrint });
      download(blob, nameFile + "." + typePrint, "file/" + typePrint);
    });
  };


}

