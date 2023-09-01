import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, Input, Output, EventEmitter } from '@angular/core';
import { ComponentPage } from '../../../../core/component-page';
import { PersonaHTTPService } from '../../../../core/services/http/persona-http.service';
import { Observable, BehaviorSubject, iif, of } from 'rxjs';
import { RespPagination } from '../../../../core/models/resp-pagination';
import { switchMap, tap, pluck } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../shared/services/confirmation-service.service';
import { List } from '../../../../core/list';
import { ConfirmComponent } from 'src/app/modules/shared/components/confirm/confirm.component';
import { DialogGenComponent } from 'src/app/modules/shared/components/dialog/gen/dialog-gen.component';
import { AdItem } from 'src/app/modules/shared/models/ad-item';
import { DetailPersonaComponent } from 'src/app/modules/shared/components/persona/detail/detail-per-modal.component';
import { Persona } from 'src/app/core/models/persona';
import { ActivatedRoute } from '@angular/router';
declare var $: any;

@Component({
  selector: 'list-per',
  templateUrl: './list-per.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListPerComponent extends ComponentPage implements OnInit, AfterViewInit, List {

  @Input()
  public data: RespPagination<any>;
  public personas$: Observable<any> = null;
  private refresh = new BehaviorSubject<void>(null);
  @Input()
  public typeView = 'LIST'; // puede ser list o list_select
  @Output()
  selectPersona = new EventEmitter<any>();
  public filterPersona = {
    dni: null, apellido: "", nombre: "", id: 0
  };
  constructor(
    private personaHTTPService: PersonaHTTPService,
    private modalService: NgbModal,
    protected confirmationService: ConfirmationServiceService,
    private activatedRoute: ActivatedRoute
  ) {
    super();
  }

  ngOnInit() {
    this.personas$ = this.refresh.asObservable().pipe(
      switchMap(() =>
        this.activatedRoute.queryParams.pipe(tap(params => {
          this.resetFilter();
          this.filterPersona = this.getFilterFromUrl(this.filterPersona, params, ['id']);
        }),
          switchMap(() =>
            iif(() => this.data == null || this.data == undefined
              , this.personaHTTPService.getAllPagination(this.paramPagination, this.filterPersona)
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
    this.setParamQuery(this.filterPersona);
  }
  reset() {
    this.resetFilter();
    this.initFocus();
    this.setParamQuery(this.filterPersona);
  }
  resetFilter() {
    this.filterPersona = {
      dni: "", apellido: "", nombre: "", id: 0
    };
  }
  initFocus() {
    setTimeout(() => {
      $("#txtFiltroDni").focus();
    });
  }
  remove(entity: any) {
    let modalConfirmRemove = this.modalService.open(ConfirmComponent);
    modalConfirmRemove.componentInstance.title = "Confirmación";
    this.confirmationService.confirm({
      message: 'Confirmar eliminación  - ' + entity.nombre,
      accept: () => {
        this.personaHTTPService.deletePersona(entity.id.toString()).subscribe(resp => {
          this.messageToast.success('Se elimino correctamente  la persona  ' + entity.nombre);
          this.pageChanged();
        });
      },
      cancel: () => {
      }
    });
  }
  detail(entity: Persona) {
    let modalDetail = this.modalService.open(DialogGenComponent, { size: 'lg', backdrop: 'static' });
    modalDetail.componentInstance.item = new AdItem(DetailPersonaComponent, { idEntity: entity.id });
    modalDetail.componentInstance.title = 'Detalle de datos de ' + entity.nombre + ' ' + entity.apellido;
  }
  select(persona: any) {
    this.selectPersona.emit(persona);
  }
  setParamQuery(data: any) {
    let tempParam: any = {};
    tempParam.dni = encodeURI(data.dni);
    tempParam.apellido = encodeURI(data.apellido);
    tempParam.nombre = encodeURI(data.nombre);
    this.setParamURL(tempParam).then(resp => {
      if (!resp) {
        this.refresh.next();
      }
    });
  }
}

