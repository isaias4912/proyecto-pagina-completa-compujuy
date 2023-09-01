import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, Input, Output, EventEmitter } from '@angular/core';
import { ComponentPage, TypeComponent } from '../../../../../../core/component-page';
import { Observable, BehaviorSubject, iif, of } from 'rxjs';
import { RespPagination } from '../../../../../../core/models/resp-pagination';
import { ProveedorHTTPService } from '../../../../../../core/services/http/proveedor-http.service';
import { switchMap, tap, pluck } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../../../shared/services/confirmation-service.service';
import { List } from '../../../../../../core/list';
import { ConfirmComponent } from 'src/app/modules/shared/components/confirm/confirm.component';
import { ActivatedRoute } from '@angular/router';
import { DialogGenComponent } from 'src/app/modules/shared/components/dialog/gen/dialog-gen.component';
import { AdItem } from 'src/app/modules/shared/models/ad-item';
import { DetailProvComponent } from '../../../../modules/shared-prov/components/detail/detail-prov.component';
import { Entidad } from 'src/app/core/models/entidad';
declare var $: any;

@Component({
  selector: 'list-prov',
  templateUrl: './list-prov.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListProvComponent extends ComponentPage implements OnInit, AfterViewInit, List {
  @Input()
  public data: RespPagination<any>;
  public proveedores$: Observable<any> = null;
  private refresh = new BehaviorSubject<void>(null);
  @Input()
  public typeView = 'LIST'; // puede ser list o list_select
  public filterProveedor = {
    dni: "", apellido: "", nombre: "", activo: 1, cuit: "", tipoProveedor: null
  };
  @Output()
  selectProv = new EventEmitter<any>();
  public dataOptions = {
    filterActivo: [{ value: 0, name: "Inactivo" }, { value: 1, name: "Activo" }, { value: 2, name: "Todos" }],
    filterTipo: [
      { value: null, name: "Todos" },
      { value: 'MONOTRIBUTO', name: "Monotributo" },
      { value: 'RESPONSABLE_INSCRIPTO', name: "Responsable Inscripto" },
      { value: 'EXCENTO', name: "Excento" }
    ]
  };
  constructor(
    private proveedorHTTPService: ProveedorHTTPService,
    private modalService: NgbModal,
    protected confirmationService: ConfirmationServiceService,
    private activatedRoute: ActivatedRoute
  ) {
    super();
  }

  ngOnInit() {
    this.proveedores$ = this.refresh.asObservable().pipe(
      switchMap(() =>
        this.activatedRoute.queryParams.pipe(tap(params => {
          this.buildFilter(params);
        }),
          switchMap(() =>
            iif(() => this.data == null || this.data == undefined
              , this.proveedorHTTPService.getAllPagination(this.paramPagination, this.filterProveedor)
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
  buildFilter(params) {
    if (this.typeComponent == TypeComponent.page) {
      if (this.firstExecute) {
        this.firstExecute = false;
        this.resetFilter();
        this.filterProveedor = this.getFilterFromUrl(this.filterProveedor, params, [], ['activo']);
      }
    }
  }
  getEntidadString(entidad: Entidad) {
    if (entidad.tipo == 'PERSONA') {
      return entidad.persona.apellido + ' ' + entidad.persona.nombre;
    }
    if (entidad.tipo == 'EMPRESA') {
      return entidad.empresa.razonSocial;
    }
  }
  pageChanged() {
    this.refresh.next();
  }
  query() {
    this.setParamQuery(this.filterProveedor);
  }
  reset() {
    this.resetFilter();
    this.initFocus();
    this.setParamQuery(this.filterProveedor);
  }
  resetFilter() {
    this.filterProveedor = {
      dni: "", apellido: "", nombre: "", activo: 1, cuit: "", tipoProveedor: null
    };
  }
  initFocus() {
    setTimeout(() => {
      $("#txtFiltroCUIT").focus();
    },100);
  }
  enabledOrDisabled(value) {
    this.proveedorHTTPService.enableOrDisableProveedor(this.listChecked, value).subscribe(resp => {
      if (value) {
        this.messageToast.success("Se activarón correctamente el/lo/s proveedor/es seleccionado/s");
      } else {
        this.messageToast.success("Se desactivarón correctamente el/lo/s proveedor/es seleccionado/s");
      }
      this.pageChanged();
    });
  };
  select(entity: any) {
    this.selectProv.emit(entity);
  }
  detail(entity: any) {
    let modalDetail = this.modalService.open(DialogGenComponent, { size: 'xl', backdrop: 'static' });
    modalDetail.componentInstance.item = new AdItem(DetailProvComponent, { idEntity: entity.id });
    if (entity.entidad.tipo == "PERSONA") {
      modalDetail.componentInstance.title = 'Detalle del proveedor ' + entity.entidad.persona.nombre + ' ' + entity.entidad.persona.apellido;
    }
    if (entity.entidad.tipo == "EMPRESA") {
      modalDetail.componentInstance.title = 'Detalle del proveedor ' + entity.entidad.empresa.razonSocial;
    }
  }
  remove(entity: any) {
    let modalConfirmRemove = this.modalService.open(ConfirmComponent);
    modalConfirmRemove.componentInstance.title = "Confirmación";
    this.confirmationService.confirm({
      message: 'Confirma eliminación  - ' + entity.razonSocial,
      accept: () => {
        this.proveedorHTTPService.deleteProveedor(entity.id.toString()).subscribe(resp => {
          this.messageToast.success('Se elimino correctamente el proveedor  ' + entity.razonSocial);
          this.pageChanged();
        });
      },
      reject: () => {
      }
    });
  }
  setParamQuery(data: any) {
    if (this.typeComponent == TypeComponent.page) {
      let tempParam: any = {};
      tempParam.dni = encodeURI(data.dni);
      tempParam.nombre = encodeURI(data.nombre);
      tempParam.apellido = encodeURI(data.apellido);
      tempParam.activo = encodeURI(data.activo);
      tempParam.cuit = encodeURI(data.cuit);
      tempParam.tipoProveedor = encodeURI(data.tipoProveedor);
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

