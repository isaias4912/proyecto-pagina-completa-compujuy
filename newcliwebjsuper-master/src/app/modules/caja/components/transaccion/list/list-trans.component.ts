import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, Input, Directive, Output, EventEmitter, ViewChildren, QueryList } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ComponentPage } from '../../../../../core/component-page';
import { Observable, BehaviorSubject } from 'rxjs';
import { RespPagination } from '../../../../../core/models/resp-pagination';
import { switchMap, tap, pluck } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../../shared/services/confirmation-service.service';
import { List } from '../../../../../core/list';
import { VentaHTTPService } from '../../../../../core/services/http/venta-http.service';
import { DialogGenComponent } from 'src/app/modules/shared/components/dialog/gen/dialog-gen.component';
import { AdItem } from 'src/app/modules/shared/models/ad-item';
import { SummaryTransComponent } from '../summary/summary-trans.component';

declare var $: any;

@Component({
  selector: 'list-trans',
  templateUrl: './list-trans.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListTransComponent extends ComponentPage implements OnInit, AfterViewInit, List {
  public transacciones$: Observable<any> = null;
  private refresh = new BehaviorSubject<void>(null);
  public filterTransacciones = {
    fechaInicial: "",
    fechaFinal: "",
    id: null,
    caja: null,
    usuario: null
  };
  constructor(
    private ventaHTTPService: VentaHTTPService,
    private modalService: NgbModal,
    protected confirmationService: ConfirmationServiceService,
    private activatedRoute: ActivatedRoute

  ) {
    super();
  }

  ngOnInit() {
    this.transacciones$ = this.refresh.asObservable().pipe(
      switchMap(() =>
        this.activatedRoute.queryParams.pipe(tap(params => {
          if (this.firstExecute) {
            this.firstExecute = false;
            this.resetFilter();
            this.filterTransacciones = this.getFilterFromUrl(this.filterTransacciones, params);
          }
        }),
          switchMap(() => this.ventaHTTPService.getTransaccionesAllPagination(this.paramPagination, this.filterTransacciones)),
          tap((resp: RespPagination<any>) => {
            this.setValuePagination(resp);
          }), pluck('data')
        )));
  }
  ngAfterViewInit(): void {
    this.initFocus();
    this.showFilter = true;
  }

  pageChanged() {
    this.refresh.next();
  }
  query() {
    this.setParamQuery(this.filterTransacciones);
  }
  reset() {
    this.resetFilter();
    this.initFocus();
    this.setParamQuery(this.filterTransacciones);
  }
  resetFilter() {
    this.filterTransacciones = {
      fechaInicial: "",
      fechaFinal: "",
      id: null,
      caja: null,
      usuario: null
    };
  }
  
  initFocus() {
    setTimeout(() => {
      $("#txtFiltroDNI").focus();
    });
  }
  summary(entity: any) {
    let modalSummary = this.modalService.open(DialogGenComponent, { size: 'xl', backdrop: 'static' });
    modalSummary.componentInstance.item = new AdItem(SummaryTransComponent, { idEntity: entity.id });
    modalSummary.componentInstance.title = 'Resumen de la transaccion de caja | ' + entity.id + ' | ' + entity.caja.nombre;
  }
  remove(entity: any) {
  }
  setParamQuery(data: any) {
    let tempParam: any = {};
    tempParam.fechaInicial = encodeURI(data.fechaInicial);
    tempParam.fechaFinal = encodeURI(data.fechaFinal);
    tempParam.id = encodeURI(data.id);
    this.setParamURL(tempParam).then(resp => {
      if (!resp) {
        this.refresh.next();
      }
    });
  }
}

