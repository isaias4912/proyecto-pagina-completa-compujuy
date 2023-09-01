import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, Input, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ComponentPage, TypeComponent } from '../../../../../../core/component-page';
import { Observable, BehaviorSubject } from 'rxjs';
import { RespPagination } from '../../../../../../core/models/resp-pagination';
import { switchMap, tap, pluck, finalize } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../../../shared/services/confirmation-service.service';
import { List } from '../../../../../../core/list';
import { VentaHTTPService } from '../../../../../../core/services/http/venta-http.service';
import { DetailFactCompModalComponent } from 'src/app/modules/compra/modules/factura-comp/modules/shared-fact-comp/components/detail/detail-fact-comp-modal.component';

declare var $: any;

@Component({
  selector: 'list-trans-mov',
  templateUrl: './list-trans-mov.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListTransMovComponent extends ComponentPage implements OnInit, AfterViewInit, List {

  public detTransacciones$: Observable<any> = null;
  private refresh = new BehaviorSubject<void>(null);
  public dataFilter$: Observable<any> = null;
  @Input()
  public typeView = 'LIST'; // puede ser list o list_select
  @Output()
  selectTrans = new EventEmitter<any>();
  public typeComponent: any;
  public filterDetTransacciones = {
    fechaInicial: "",
    fechaFinal: "",
    caja: null,
    usuario: null,
    totalMinimo: null,
    totalMaximo: null,
    transaccion: null
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
    this.dataFilter$ = this.ventaHTTPService.getDataFilter().pipe(pluck('data'), finalize(() => {
      this.detTransacciones$ = this.refresh.asObservable().pipe(
        switchMap(() =>
          this.activatedRoute.queryParams.pipe(tap(params => {
            this.buildFilter(params);
          }),
            switchMap(() => this.ventaHTTPService.getDetTransaccionesAllPagination(this.paramPagination, this.filterDetTransacciones)),
            tap((resp: RespPagination<any>) => {
              this.setValuePagination(resp);
            }), pluck('data')
          )));
    }))

  }
  ngAfterViewInit(): void {
    this.initFocus();
    this.showFilter = true;
  }

  pageChanged() {
    this.refresh.next();
  }
  query() {
    this.setParamQuery(this.filterDetTransacciones);
  }
  reset() {
    this.resetFilter();
    this.initFocus();
    this.setParamQuery(this.filterDetTransacciones);
  }
  resetFilter() {
    this.filterDetTransacciones = {
      fechaInicial: "",
      fechaFinal: "",
      caja: null,
      usuario: null,
      totalMinimo: null,
      totalMaximo: null,
      transaccion: null
    };
  }
  buildFilter(params) {
    if (this.typeComponent == TypeComponent.page) {
      if (this.firstExecute) {
        this.firstExecute = false;
        this.resetFilter();
        this.filterDetTransacciones = this.getFilterFromUrl(this.filterDetTransacciones, params, [], ['caja'], [], [], ['usuario']);
      }
    }
  }

  initFocus() {
    setTimeout(() => {
      $("#fechaDesdeFilter").focus();
    });
  }
  remove(entity: any) {
  }
  setParamQuery(data: any) {
    if (this.typeComponent == TypeComponent.page) {
      let tempParam: any = {};
      tempParam.fechaInicial = encodeURI(data.fechaInicial);
      tempParam.fechaFinal = encodeURI(data.fechaFinal);
      tempParam.caja = encodeURI(data.caja);
      tempParam.usuario = encodeURI(data.usuario ? data.usuario.id : null);
      tempParam.totalMinimo = encodeURI(data.totalMinimo);
      tempParam.totalMaximo = encodeURI(data.totalMaximo);
      tempParam.transaccion = encodeURI(data.transaccion);
      this.setParamURL(tempParam).then(resp => {
        if (!resp) {
          this.refresh.next();
        }
      });
    } else {
      this.refresh.next();
    }
  }
  showDetailInvoice(id) {
    const modalInvoice = this.modalService.open(DetailFactCompModalComponent, { size: 'xl', backdrop: 'static' });
    modalInvoice.componentInstance.idInvoice = id;
  }
  select(transaccion: any) {
    this.selectTrans.emit(transaccion);
  }
}

