import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy } from '@angular/core';
import { ComponentPage } from '../../../../core/component-page';
import { Observable, BehaviorSubject } from 'rxjs';
import { RespPagination } from '../../../../core/models/resp-pagination';
import { switchMap, tap, pluck, filter } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../shared/services/confirmation-service.service';
import { List } from '../../../../core/list';
import { UsuarioHTTPService } from '../../../../core/services/http/usuario-http.service';
import { ConfirmComponent } from '../../../shared/components/confirm/confirm.component';
import { environment } from '../../../../../environments/environment'
import { DialogGenComponent } from 'src/app/modules/shared/components/dialog/gen/dialog-gen.component';
import { AdItem } from 'src/app/modules/shared/models/ad-item';
import { DetailPersonaComponent } from 'src/app/modules/shared/components/persona/detail/detail-per-modal.component';
import { ActivatedRoute } from '@angular/router';
import { PathService } from 'src/app/core/services/utils/path.service';

declare var $: any;

@Component({
  selector: 'list-usu',
  templateUrl: './list-usu.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListUsuComponent extends ComponentPage implements OnInit, AfterViewInit, List {

  public usuarios$: Observable<any> = null;
  private refresh = new BehaviorSubject<void>(null);
  public baseURLImg: string = environment.baseURLImgUsers;

  public filterUsuario = {
    usuario: "", apellido: "", nombre: "", activo: 1, id: 0, root:0
  };
  public dataOptions = {
    filterActivo: [{ value: 0, name: "Inactivo" }, { value: 1, name: "Activo" }, { value: 2, name: "Todos" }]
  };
  constructor(
    private usuarioHTTPService: UsuarioHTTPService,
    private modalService: NgbModal,
    protected confirmationService: ConfirmationServiceService,
    private activatedRoute: ActivatedRoute,
    public pathService: PathService
  ) {
    super();
  }

  ngOnInit() {
    this.usuarios$ = this.refresh.asObservable().pipe(
      switchMap(() =>
        this.activatedRoute.queryParams.pipe(tap(params => {
          if (this.firstExecute) {
            this.firstExecute = false;
            this.resetFilter();
            this.filterUsuario = this.getFilterFromUrl(this.filterUsuario, params, ['id'], ['activo']);
            this.filterUsuario.root=0;
          }
        }),
          switchMap(() => this.usuarioHTTPService.getAllPagination(this.paramPagination, this.filterUsuario))
          , tap((resp: RespPagination<any>) => {
            this.setValuePagination(resp);
          }), pluck('data')
        )));
    this.paramPagination.order = 'usuario';
  }
  ngAfterViewInit(): void {
    this.initFocus();
  }

  pageChanged() {
    this.refresh.next();
  }
  query() {
    this.setParamQuery(this.filterUsuario);
  }
  reset() {
    this.resetFilter();
    this.initFocus();
    this.setParamQuery(this.filterUsuario);
  }
  resetFilter() {
    this.filterUsuario = {
      usuario: "", apellido: "", nombre: "", activo: 1, id: 0, root:0
    };
  }
  initFocus() {
    setTimeout(() => {
      $("#txtFiltroUsuario").focus();
    });
  }
  remove(entity: any) {
    let modalConfirmRemove = this.modalService.open(ConfirmComponent);
    modalConfirmRemove.componentInstance.title = "Confirmación";
    this.confirmationService.confirm({
      message: 'Confirma eliminación del usuario ' + entity.usuario,
      accept: () => {
        this.usuarioHTTPService.deleteUsuario(entity.id.toString()).subscribe(resp => {
          this.messageToast.success('Se elimino correctamente  - ' + entity.usuario);
          this.pageChanged();
        });
      },
      cancel: () => {
      }
    });
  }
  enabledOrDisabled(value) {
    this.usuarioHTTPService.enableOrDisableUsuario(this.listChecked, value).subscribe(resp => {
      if (value) {
        this.messageToast.success("Se activarón correctamente el/lo/s usuario/s seleccionado/s");
      } else {
        this.messageToast.success("Se desactivarón correctamente el/lo/s usuario/s seleccionado/s");
      }
      this.pageChanged();
    });
  };
  showDetailPersona(persona: any) {
    const modalDetail = this.modalService.open(DialogGenComponent, { size: 'lg', backdrop: 'static' });
    modalDetail.componentInstance.item = new AdItem(DetailPersonaComponent, { idEntity: persona.id });
    modalDetail.componentInstance.title = 'Detalle de datos de ' + persona.nombre + ' ' + persona.apellido;

  }
  detail(usuario: any) {

  }
  setParamQuery(data: any) {
    let tempParam: any = {};
    tempParam.usuario = encodeURI(data.usuario);
    tempParam.apellido = encodeURI(data.apellido);
    tempParam.nombre = encodeURI(data.nombre);
    tempParam.activo = encodeURI(data.activo);
    this.setParamURL(tempParam).then(resp => {
      if (!resp) {
        this.refresh.next();
      }
    });
  }
}

