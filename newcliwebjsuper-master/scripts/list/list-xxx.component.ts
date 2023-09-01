import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ComponentPage } from '../../../../core/component-page';
import { Observable, BehaviorSubject } from 'rxjs';
import { RespPagination } from '../../../../../core/models/resp-pagination';
import { switchMap, tap, pluck } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../../../shared/services/confirmation-service.service';
import { List } from '../../../../core/list';
declare var $: any;

@Component({
  selector: 'list-xxx',
  templateUrl: './list-xxx.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class zzzComponent extends ComponentPage implements OnInit, AfterViewInit, List {

  public entity$: Observable<any> = null;
  private refresh = new BehaviorSubject<void>(null);
  private dataFilter = {
  };
  constructor(
    private service: Service,
    private modalService: NgbModal,
    protected confirmationService: ConfirmationServiceService,
    private activatedRoute: ActivatedRoute

  ) {
    super();
  }

  ngOnInit() {
    this.entity$ = this.refresh.asObservable().pipe(
      switchMap(() =>
        this.activatedRoute.queryParams.pipe(tap(params => {
          this.resetFilter();
          this.dataFilter = this.getFilterFromUrl(this.dataFilter, params, [], ['activo']);
        }),
        switchMap(() => this.service.getAllPagination(this.paramPagination, this.dataFilter)),
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
    this.setParamQuery(this.dataFilter);
  }
  reset() {
    this.resetFilter();
    this.initFocus();
    this.setParamQuery(this.dataFilter);
  }
  resetFilter() {
    this.dataFilter = {
    };
  }
  initFocus() {
    setTimeout(() => {
      $("#txtFiltroDNI").focus();
    });
  }
  remove(entity: any) {
    let modalConfirmRemove = this.modalService.open(ConfirmComponent);
    modalConfirmRemove.componentInstance.title = "Confirmación";
    this.confirmationService.confirm({
      message: 'Confirma eliminación  - ' + entity.nombre,
      accept: () => {
        this.service.delete(entity.id.toString()).subscribe(resp => {
          this.messageToast.success('Se elimino correctamente  - ' + entity.nombre);
          this.pageChanged();
        });
      },
      reject: () => {
      }
    });
  }
  setParamQuery(data: any) {
    let tempParam: any = {};
    tempParam.field = encodeURI(data.field);
    tempParam.field = encodeURI(data.field);
    tempParam.field = encodeURI(data.field);
    tempParam.field = encodeURI(data.field);
    this.setParamURL(tempParam).then(resp => {
      if (!resp) {
        this.refresh.next();
      }
    });
  }
}

