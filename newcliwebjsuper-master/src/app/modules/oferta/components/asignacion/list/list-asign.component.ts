import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, Input } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { ComponentPage } from '../../../../../core/component-page';
import { Observable, BehaviorSubject } from 'rxjs';
import { RespPagination } from '../../../../../core/models/resp-pagination';
import { switchMap, tap, pluck } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../../shared/services/confirmation-service.service';
import { List } from '../../../../../core/list';
import { OfertaHTTPService } from '../../../../../core/services/http/oferta-http.service';

declare var $: any;

@Component({
  selector: 'list-asign',
  templateUrl: './list-asign.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListAsignComponent extends ComponentPage implements OnInit, AfterViewInit, List {

  public asignaciones$: Observable<any> = null;
  private refresh = new BehaviorSubject<void>(null);
  public filterOferta = {
    idOferta: 0, nombre: "", activo: 1
  };
  public dataOptions = {
    filterActivo: [{ value: 0, name: "Inactivo" }, { value: 1, name: "Activo" }, { value: 2, name: "Todos" }]
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
    this.activatedRoute.paramMap.subscribe(
      (params: ParamMap) => {
        if (this.firstExecute) {
          this.firstExecute=false;
          this.id = this.decrypt(params.get("idOferta"));
          if (this.id) {
            this.filterOferta.idOferta = this.id;
          } else {
            throw new Error("Attribute 'id of oferta' is required");
          }
        }
      });
    this.asignaciones$ = this.refresh.asObservable().pipe(
      switchMap(() =>
        this.activatedRoute.queryParams.pipe(tap(params => {
          this.resetFilter();
          this.filterOferta = this.getFilterFromUrl(this.filterOferta, params, [], ['activo', 'idOferta']);
        }),
          switchMap(() => this.ofertaHTTPService.getOfertasProductosPagination(this.paramPagination, this.filterOferta)),
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
      idOferta: this.filterOferta.idOferta, nombre: "", activo: 1
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
    tempParam.nombre = encodeURI(data.nombre);
    tempParam.activo = encodeURI(data.activo);
    this.setParamURL(tempParam).then(resp => {
      if (!resp) {
        this.refresh.next();
      }
    });
  }
  enabledOrDisabled(value) {
    this.ofertaHTTPService.enableOrDisableOfertasProductos(this.listChecked, value).subscribe(resp => {
      if (value) {
        this.messageToast.success("Se activo correctamente la/las asignación/es");
      } else {
        this.messageToast.success("Se desactivo correctamente la/las asignación/es");
      }
      this.pageChanged();
    });
  };
}

