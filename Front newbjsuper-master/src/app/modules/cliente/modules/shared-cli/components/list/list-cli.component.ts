import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, Input, Output, EventEmitter } from '@angular/core';
import { ComponentPage, TypeComponent } from '../../../../../../core/component-page';
import { Observable, BehaviorSubject, iif, of } from 'rxjs';
import { RespPagination } from '../../../../../../core/models/resp-pagination';
import { ClienteHTTPService } from '../../../../../../core/services/http/cliente-http.service';
import { switchMap, tap, pluck } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../../../shared/services/confirmation-service.service';
import { List } from '../../../../../../core/list';
import { Cliente } from '../../../../../../core/models/cliente';
import { CtaCte } from '../../../../../../core/models/cta-cte';
import { NewCtaCteModal } from '../../modal/new/new-cta-cte.modal';
import { UpdateCtaCteModal } from '../../modal/update/update-cta-cte.modal';
import { DialogGenComponent } from 'src/app/modules/shared/components/dialog/gen/dialog-gen.component';
import { DetailCliComponent } from '../detail/detail-cli.component';
import { AdItem } from 'src/app/modules/shared/models/ad-item';
import { ConfirmComponent } from 'src/app/modules/shared/components/confirm/confirm.component';
import { ActivatedRoute } from '@angular/router';
import { Entidad } from 'src/app/core/models/entidad';
import { TipoSearch } from 'src/app/core/enums';
import { NavigationTableComponent } from 'src/app/modules/shared/components/navigation-table/navigation-table.component';
declare var $: any;

@Component({
  selector: 'list-cli',
  templateUrl: './list-cli.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListCliComponent extends ComponentPage implements OnInit, AfterViewInit, List {

  public TipoSearch = TipoSearch;
  @Input()
  public data: RespPagination<any>;
  @Input()
  public typeView = 'LIST'; // puede ser list o list_select
  @Output()
  selectCli = new EventEmitter<any>();
  public clientes$: Observable<any> = null;
  private refresh = new BehaviorSubject<void>(null);
  public filterCliente = {
    dni: "", apellido: "", nombre: "", activo: 1, cuit: "", tipoCliente: null
  };
  public dataOptions = {
    filterActivo: [{ value: 0, name: "Inactivo" }, { value: 1, name: "Activo" }, { value: 2, name: "Todos" }],
    filterTipo: [
      { value: null, name: "Todos" },
      { value: 'CONSUMIDOR_FINAL', name: "Consumidor final" },
      { value: 'MONOTRIBUTO', name: "Monotributo" },
      { value: 'RESPONSABLE_INSCRIPTO', name: "Responsable Inscripto" },
      { value: 'EXCENTO', name: "Excento" }
    ]
  };
  @ViewChild("navigationTableComponentListCli", { static: false })
  navigationTableComponent: NavigationTableComponent;
  @Input()
  public typeSearch: TipoSearch = TipoSearch.SEARCH_FILTER;
  constructor(
    private clienteHTTPService: ClienteHTTPService,
    private modalService: NgbModal,
    protected confirmationService: ConfirmationServiceService,
    private activatedRoute: ActivatedRoute
  ) {
    super();
  }

  ngOnInit() {
    this.clientes$ = this.refresh.asObservable().pipe(
      switchMap(() =>
        this.activatedRoute.queryParams.pipe(tap(params => {
          this.buildFilter(params);
        }),
          switchMap(() =>
            iif(() => this.data == null || this.data == undefined
              , this.clienteHTTPService.getAllPagination(this.paramPagination, this.filterCliente)
              , of(this.data).pipe(tap(() => {
                this.data = null; // la primera  vez no mas deberia completar los datos
              }))
            )
          ),
          tap((resp: RespPagination<any>) => {
            console.log('resp', resp)
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
        this.filterCliente = this.getFilterFromUrl(this.filterCliente, params, [], ['activo']);
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
    this.setParamQuery(this.filterCliente);
  }
  reset() {
    this.resetFilter();
    this.initFocus();
    this.setParamQuery(this.filterCliente);
  }
  resetFilter() {
    this.filterCliente = {
      dni: "", apellido: "", nombre: "", activo: 1, cuit: "", tipoCliente: null
    };
  }
  initFocus() {
    setTimeout(() => {
      if (this.typeSearch == TipoSearch.SEARCH_WITHOUT_FILTER) {
        this.navigationTableComponent.initFocus();
      }
      if (this.typeSearch == TipoSearch.SEARCH_FILTER) {
        $("#txtFiltroDNI").focus();
      }
    });
  }
  enabledOrDisabled(value) {
    this.clienteHTTPService.enableOrDisableCliente(this.listChecked, value).subscribe(resp => {
      if (value) {
        this.messageToast.success("Se activarón correctamente el/lo/s cliente/s seleccionado/s");
      } else {
        this.messageToast.success("Se desactivarón correctamente el/lo/s cliente/s seleccionado/s");
      }
      this.pageChanged();
    });
  };

  createCtaCte(cliente: Cliente) {
    const modalNewCtaCte = this.modalService.open(NewCtaCteModal, { size: 'xl', backdrop: 'static' });
    modalNewCtaCte.componentInstance.cliente = cliente;
    modalNewCtaCte.result.then((persona) => {
      this.pageChanged();
    }, (reason) => {
    });
  }
  updateCtaCte(cliente: Cliente, ctaCte: CtaCte) {
    const modalUpdateCtaCte = this.modalService.open(UpdateCtaCteModal, { size: 'xl', backdrop: 'static' });
    modalUpdateCtaCte.componentInstance.cliente = cliente;
    modalUpdateCtaCte.componentInstance.ctaCte = ctaCte;
    modalUpdateCtaCte.result.then((persona) => {
      this.pageChanged();
    }, (reason) => {
    });
  }
  remove(entity: any) {
    console.log('entity', entity)
    let modalConfirmRemove = this.modalService.open(ConfirmComponent);
    modalConfirmRemove.componentInstance.title = "Confirmación";
    this.confirmationService.confirm({
      message: 'Confirma eliminación  - ' + entity.nombreCompleto,
      accept: () => {
        this.clienteHTTPService.deleteCliente(entity.id.toString()).subscribe(resp => {
          this.messageToast.success('Se elimino correctamente el cliente ' + entity.nombreCompleto);
          this.pageChanged();
        });
      },
      reject: () => {
      }
    });
  }
  select(entity: any) {
    this.selectCli.emit(entity);
  }
  detail(entity: Cliente) {
    let modalDetail = this.modalService.open(DialogGenComponent, { size: 'xl', backdrop: 'static' });
    modalDetail.componentInstance.item = new AdItem(DetailCliComponent, { idEntity: entity.id });
    modalDetail.componentInstance.title = 'Detalle del cliente ' + entity.nombreCompleto;
  }

  more() {
    this.paramPagination.page++;
    this.pageChanged();
  }
  setParamQuery(data: any) {
    if (this.typeComponent == TypeComponent.page) {
      let tempParam: any = {};
      tempParam.dni = encodeURI(data.dni);
      tempParam.nombre = encodeURI(data.nombre);
      tempParam.apellido = encodeURI(data.apellido);
      tempParam.activo = encodeURI(data.activo);
      tempParam.cuit = encodeURI(data.cuit);
      tempParam.tipoCliente = encodeURI(data.tipoCliente);
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

