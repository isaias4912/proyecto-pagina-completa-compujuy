import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ComponentPage } from '../../../../core/component-page';
import { Observable, BehaviorSubject } from 'rxjs';
import { RespPagination } from '../../../../core/models/resp-pagination';
import { switchMap, tap, pluck } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../shared/services/confirmation-service.service';
import { List } from '../../../../core/list';
import { CajaHTTPService } from 'src/app/core/services/http/caja-http.service';
import { ConfirmComponent } from 'src/app/modules/shared/components/confirm/confirm.component';
import { Caja } from 'src/app/core/models/caja';
import { DialogGenComponent } from 'src/app/modules/shared/components/dialog/gen/dialog-gen.component';
import { AdItem } from 'src/app/modules/shared/models/ad-item';
import { DetailCajaComponent } from '../detail/detail-caja.component';
import { StorageService } from 'src/app/core/services/utils/storage.service';
declare var $: any;

@Component({
  selector: 'list-caja',
  templateUrl: './list-caja.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListCajaComponent extends ComponentPage implements OnInit, AfterViewInit, List {

  public cajas$: Observable<any> = null;
  private refresh = new BehaviorSubject<void>(null);
  public filterCaja = {
    nombre: "", nombreMaquina: "", activo: 1
  };
  public dataOptions = {
    filterActivo: [{ value: 0, name: "Inactivo" }, { value: 1, name: "Activo" }, { value: 2, name: "Todos" }],
  };

  constructor(
    private cajaHTTPService: CajaHTTPService,
    private modalService: NgbModal,
    protected confirmationService: ConfirmationServiceService,
    private activatedRoute: ActivatedRoute,
    private storageService: StorageService

  ) {
    super();
  }

  ngOnInit() {
    this.cajas$ = this.refresh.asObservable().pipe(
      switchMap(() =>
        this.activatedRoute.queryParams.pipe(tap(params => {
          if (this.firstExecute) {
            this.firstExecute = false;
            this.resetFilter();
            this.filterCaja = this.getFilterFromUrl(this.filterCaja, params, [], ['activo']);
          }
        }),
          switchMap(() => this.cajaHTTPService.getAllPagination(this.paramPagination, this.filterCaja)),
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
    this.setParamQuery(this.filterCaja);
  }
  reset() {
    this.resetFilter();
    this.initFocus();
    this.setParamQuery(this.filterCaja);
  }
  resetFilter() {
    this.filterCaja = {
      nombre: "", nombreMaquina: "", activo: 1
    };
  }
  initFocus() {
    setTimeout(() => {
      $("#txtFiltroDNI").focus();
    });
  }
  enabledOrDisabled(value) {
    this.cajaHTTPService.enableOrDisableCaja(this.listChecked, value).subscribe(resp => {
      if (value) {
        this.messageToast.success("Se activarón correctamente la/s caja/s seleccionada/s");
      } else {
        this.messageToast.success("Se desactivarón correctamente la/s caja/s seleccionada/s");
      }
      this.pageChanged();
    });
  };
  remove(entity: any) {
    let modalConfirmRemove = this.modalService.open(ConfirmComponent);
    modalConfirmRemove.componentInstance.title = "Confirmación";
    this.confirmationService.confirm({
      message: 'Confirma eliminación  - ' + entity.nombre,
      accept: () => {
        this.cajaHTTPService.deleteCaja(entity.id.toString()).subscribe(resp => {
          this.messageToast.success('Se elimino correctamente la caja - ' + entity.nombre);
          this.pageChanged();
        });
      },
      reject: () => {
      }
    });
  }
  detail(entity: Caja) {
    let modalDetail = this.modalService.open(DialogGenComponent, { size: 'xl', backdrop: 'static' });
    modalDetail.componentInstance.item = new AdItem(DetailCajaComponent, { idEntity: entity.id });
    modalDetail.componentInstance.title = 'Detalle de datos | ' + entity.nombre + ' | ' + entity.nombre;
  }
  setParamQuery(data: any) {
    let tempParam: any = {};
    tempParam.nombre = encodeURI(data.nombre);
    tempParam.nombreMaquina = encodeURI(data.nombreMaquina);
    tempParam.activo = encodeURI(data.activo);
    this.setParamURL(tempParam).then(resp => {
      if (!resp) {
        this.refresh.next();
      }
    });
  }
}

