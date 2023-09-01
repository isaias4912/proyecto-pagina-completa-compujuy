import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, Input, Output, EventEmitter } from '@angular/core';
import { ComponentPage, TypeComponent } from '../../../../core/component-page';
import { EntidadHTTPService } from '../../../../core/services/http/entidad-http.service';
import { Observable, BehaviorSubject, iif, of } from 'rxjs';
import { RespPagination } from '../../../../core/models/resp-pagination';
import { switchMap, tap, pluck } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../shared/services/confirmation-service.service';
import { List } from '../../../../core/list';
import { ActivatedRoute } from '@angular/router';
import { Entidad } from '../../../../core/models/entidad';
import { DialogGenComponent } from 'src/app/modules/shared/components/dialog/gen/dialog-gen.component';
import { DetailPersonaComponent } from 'src/app/modules/shared/components/persona/detail/detail-per-modal.component';
import { AdItem } from 'src/app/modules/shared/models/ad-item';
import { DetailEmpComponent } from 'src/app/modules/empresa/modules/shared-prov/components/detail/detail-emp.component';
declare var $: any;

@Component({
  selector: 'list-ent',
  templateUrl: './list-ent.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListEntComponent extends ComponentPage implements OnInit, AfterViewInit, List {

  @Input()
  public data: RespPagination<any>;
  public entidades$: Observable<any> = null;
  private refresh = new BehaviorSubject<void>(null);
  @Input()
  public typeView = 'LIST'; // puede ser list o list_select
  @Output()
  selectEnt = new EventEmitter<any>();
  public filterEntidad = {
    dni: "", apellido: "", nombre: "", cuit: "", tipoEntidad: null, tipoEmpresa: null
  };
  public dataOptions = {
    filterTipo: [
      { value: null, name: "Todos" },
      { value: 'PERSONA', name: "Persona" },
      { value: 'EMPRESA', name: "Empresa" },
    ],
    filterTipoEmpresa: [
      { value: null, name: "Todos" },
      { value: 'MONOTRIBUTO', name: "Monotributo" },
      { value: 'RESPONSABLE_INSCRIPTO', name: "Responsable Inscripto" },
      { value: 'EXCENTO', name: "Excento" },
      { value: 'CONSUMIDOR_FINAL', name: "Consumidor Final" }
    ]
  };
  constructor(
    private entidadHTTPService: EntidadHTTPService,
    private modalService: NgbModal,
    protected confirmationService: ConfirmationServiceService,
    private activatedRoute: ActivatedRoute
  ) {
    super();
  }

  ngOnInit() {
    this.entidades$ = this.refresh.asObservable().pipe(
      switchMap(() =>
        this.activatedRoute.queryParams.pipe(tap(params => {
          if (this.firstExecute) {
            this.firstExecute = false;
            this.resetFilter();
            this.filterEntidad = this.getFilterFromUrl(this.filterEntidad, params, ['id']);
          }
        }),
          switchMap(() =>
            iif(() => this.data == null || this.data == undefined
              , this.entidadHTTPService.getAllPagination(this.paramPagination, this.filterEntidad)
              , of(this.data).pipe(tap(() => {
                this.data = null; // la primera  vez no mas deberia completar los datos
              }))
            )
          ),
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
    this.setParamQuery(this.filterEntidad);
  }
  reset() {
    this.resetFilter();
    this.initFocus();
    this.setParamQuery(this.filterEntidad);
  }
  resetFilter() {
    this.filterEntidad = {
      dni: "", apellido: "", nombre: "", cuit: "", tipoEntidad: null, tipoEmpresa: null
    };
  }
  initFocus() {
    setTimeout(() => {
      $("#txtFiltroDni").focus();
    });
  }
  remove(entity: any) {
  }
  detail(entity: Entidad) {
    if (entity.tipo == 'EMPRESA') {
      let modalDetail = this.modalService.open(DialogGenComponent, { size: 'lg', backdrop: 'static' });
      modalDetail.componentInstance.item = new AdItem(DetailEmpComponent, { idEntity: entity.id });
      modalDetail.componentInstance.title = 'Detalle de datos de la empresa ' + entity.empresa.razonSocial;

    }
    if (entity.tipo == 'PERSONA') {
      let modalDetail = this.modalService.open(DialogGenComponent, { size: 'lg', backdrop: 'static' });
      modalDetail.componentInstance.item = new AdItem(DetailPersonaComponent, { idEntity: entity.id });
      modalDetail.componentInstance.title = 'Detalle de datos de la persona ' + entity.persona.nombre + ' ' + entity.persona.apellido;

    }
  }
  select(entidad: any) {
    this.selectEnt.emit(entidad);
  }
  setParamQuery(data: any) {
    if (this.typeComponent == TypeComponent.page) {
      let tempParam: any = {};
      tempParam.dni = encodeURI(data.dni);
      tempParam.nombre = encodeURI(data.nombre);
      tempParam.apellido = encodeURI(data.apellido);
      tempParam.cuit = encodeURI(data.cuit);
      tempParam.tipoEntidad = encodeURI(data.tipoEntidad);
      tempParam.tipoEmpresa = encodeURI(data.tipoEmpresa);
      this.setParamURL(tempParam).then(resp => {
        if (!resp) {
          this.refresh.next();
        }
      });
    } else {
      this.refresh.next();
    }
  }
}

