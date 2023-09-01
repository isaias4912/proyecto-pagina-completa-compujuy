import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, Input } from '@angular/core';
import { ComponentPage } from '../../../../core/component-page';
import { Observable, BehaviorSubject, iif } from 'rxjs';
import { RespPagination } from '../../../../core/models/resp-pagination';
import { switchMap, tap, pluck } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../shared/services/confirmation-service.service';
import { List } from '../../../../core/list';
import { ConfirmComponent } from 'src/app/modules/shared/components/confirm/confirm.component';
import { ActivatedRoute } from '@angular/router';
import { DialogGenComponent } from 'src/app/modules/shared/components/dialog/gen/dialog-gen.component';
import { AdItem } from 'src/app/modules/shared/models/ad-item';
import { DetailEmpComponent } from '../../modules/shared-prov/components/detail/detail-emp.component';
import { EmpresaHTTPService } from 'src/app/core/services/http/empresa-http.service';
declare var $: any;

@Component({
  selector: 'list-emp',
  templateUrl: './list-emp.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListEmpComponent extends ComponentPage implements OnInit, AfterViewInit, List {

  public empresas$: Observable<any> = null;
  private refresh = new BehaviorSubject<void>(null);
  @Input()
  public typeView = 'LIST'; // puede ser list o list_select
  public filterEmpresa = {
    cuit: "", razonSocial: "", tipoEmpresa: null
  };
  public dataOptions = {
    filterTipo: [
      { value: null, name: "Todos" },
      { value: 'MONOTRIBUTO', name: "Monotributo" },
      { value: 'RESPONSABLE_INSCRIPTO', name: "Responsable Inscripto" },
      { value: 'EXCENTO', name: "Excento" }
    ]
  };
  constructor(
    private empresaHTTPService: EmpresaHTTPService,
    private modalService: NgbModal,
    protected confirmationService: ConfirmationServiceService,
    private activatedRoute: ActivatedRoute
  ) {
    super();
  }

  ngOnInit() {
    this.empresas$ = this.refresh.asObservable().pipe(
      switchMap(() =>
        this.activatedRoute.queryParams.pipe(tap(params => {
          this.resetFilter();
          this.filterEmpresa = this.getFilterFromUrl(this.filterEmpresa, params, [], ['activo', 'cuit']);
        }),
          switchMap(() =>
            this.empresaHTTPService.getAllPagination(this.paramPagination, this.filterEmpresa)
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
    this.setParamQuery(this.filterEmpresa);
  }
  reset() {
    this.resetFilter();
    this.initFocus();
    this.setParamQuery(this.filterEmpresa);
  }
  resetFilter() {
    this.filterEmpresa = {
      cuit: "", razonSocial: "", tipoEmpresa: null
    };
  }
  initFocus() {
    setTimeout(() => {
      $("#txtFiltroCuit").focus();
    });
  }
  detail(entity: any) {
    let modalDetail = this.modalService.open(DialogGenComponent, { size: 'lg', backdrop: 'static' });
    modalDetail.componentInstance.item = new AdItem(DetailEmpComponent, { idEntity: entity.id });
    modalDetail.componentInstance.title = 'Detalle de datos | ' + entity.razonSocial;
  }
  remove(entity: any) {
    let modalConfirmRemove = this.modalService.open(ConfirmComponent);
    modalConfirmRemove.componentInstance.title = "Confirmación";
    this.confirmationService.confirm({
      message: 'Confirma eliminación  - ' + entity.razonSocial,
      accept: () => {
        this.empresaHTTPService.deleteEmpresa(entity.id.toString()).subscribe(resp => {
          this.messageToast.success('Se elimino correctamente el empresa  ' + entity.razonSocial);
          this.pageChanged();
        });
      },
      reject: () => {
      }
    });
  }
  setParamQuery(data: any) {
    let tempParam: any = {};
    tempParam.cuit = encodeURI(data.cuit);
    tempParam.razonSocial = encodeURI(data.razonSocial);
    tempParam.tipoEmpresa = encodeURI(data.tipoEmpresa);
    this.setParamURL(tempParam).then(resp => {
      if (!resp) {
        this.refresh.next();
      }
    });
  }
}

