import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ComponentPage } from '../../../../core/component-page';
import { Observable, BehaviorSubject } from 'rxjs';
import { RespPagination } from '../../../../core/models/resp-pagination';
import { switchMap, tap, pluck } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../shared/services/confirmation-service.service';
import { List } from '../../../../core/list';
import { OfertaHTTPService } from '../../../../core/services/http/oferta-http.service';
declare var $: any;

@Component({
  selector: 'list-ofer',
  templateUrl: './list-ofer.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListOferComponent extends ComponentPage implements OnInit, AfterViewInit, List {

  public ofertas$: Observable<any> = null;
  private refresh = new BehaviorSubject<void>(null);
  public filterOferta = {
    nombre: "", activo: 1, vigente: 1
  };
  public dataOptions = {
    filterActivo: [{ value: 0, name: "Inactivo" }, { value: 1, name: "Activo" }, { value: 2, name: "Todos" }],
    filterVigente: [{ value: 1, name: "Vigentes" }, { value: 0, name: "No vigentes" }, { value: 2, name: "Todas" }]
  };
  constructor(
    private ofertaHTTPService: OfertaHTTPService,
    private modalService: NgbModal,
    protected confirmationService: ConfirmationServiceService,
    private activatedRoute: ActivatedRoute

  ) {
    super();
  }

  ngOnInit() {
    this.ofertas$ = this.refresh.asObservable().pipe(
      switchMap(() =>
        this.activatedRoute.queryParams.pipe(tap(params => {
          if (this.firstExecute) {
            this.firstExecute = false;
            this.resetFilter();
            this.filterOferta = this.getFilterFromUrl(this.filterOferta, params, [], ['activo', 'vigente']);
          }
        }),
          switchMap(() => this.ofertaHTTPService.getAllPagination(this.paramPagination, this.filterOferta)),
          tap((resp: RespPagination<any>) => {
            this.setValuePagination(resp);
          }), pluck('data')
        )));

  }
  ngAfterViewInit(): void {
    this.initFocus();
  }

  pageChanged() {
    this.refresh.next();
  }
  query() {
    this.setParamQuery(this.filterOferta);
  }
  reset() {
    this.resetFilter();
    this.initFocus();
    this.setParamQuery(this.filterOferta);
  }
  resetFilter() {
    this.filterOferta = {
      nombre: "", activo: 1, vigente: 1
    };
  }
  initFocus() {
    setTimeout(() => {
      $("#txtFiltroNombre").focus();
    });
  }
  remove(entity: any) {

  }
  replaceGuionBajo(value) {
    if (value) {
      return value.replace(/_/g, " ");
    }
    return '';
  };
  checkTipoOferta(value, cantidad) {
    if (value == "CADA_N_PRODUCTO") {
      return value.replace(/_N_/g, " " + cantidad + " ");
    } else {
      return value;
    }
  };
  updatePrioridad(data: any) {
    this.ofertaHTTPService.updateOfertaPrioridad(data.data.id, { prioridad: data.newValue }, true).subscribe({
      next: resp => { this.messageToast.success('Se modificó correctamente la prioridad de la oferta ' + data.data.nombre); data.component.confirm(); },
      error: resp => { data.component.cancel(); },
    })
  }
  updateFechaHasta(data: any) {
    this.ofertaHTTPService.updateOfertaFechaHasta(data.data.id, { fecha: data.newValue }, true).subscribe({
      next: resp => { this.messageToast.success('Se modificó la prioridad la fecha de fin de la oferta ' + data.data.nombre); data.component.confirm(); },
      error: resp => { data.component.cancel(); },
    })
  }
  setParamQuery(data: any) {
    let tempParam: any = {};
    tempParam.nombre = encodeURI(data.nombre);
    tempParam.activo = encodeURI(data.activo);
    tempParam.vigente = encodeURI(data.vigente);
    this.setParamURL(tempParam).then(resp => {
      if (!resp) {
        this.refresh.next();
      }
    });
  }
}

