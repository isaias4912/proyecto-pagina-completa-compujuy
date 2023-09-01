import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { RespPagination } from '../../../../../../core/models/resp-pagination';
import { ConfirmationServiceService } from '../../../../../shared/services/confirmation-service.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ParamPagination } from '../../../../../../core/models/param-pagination';
import { Observable, of, BehaviorSubject, iif } from 'rxjs';
import { switchMap, tap, pluck } from 'rxjs/operators';
import { ComponentPage, TypeComponent } from '../../../../../../core/component-page';
import { DetailFactCompModalComponent } from '../../modules/shared-fact-comp/components/detail/detail-fact-comp-modal.component';
import { ActivatedRoute } from '@angular/router';
import { CompraHTTPService } from 'src/app/core/services/http/compra-http.service';
declare var $: any;
@Component({
  selector: 'list-fact-comp',
  templateUrl: './list-fact-comp.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListFactCompComponent extends ComponentPage implements OnInit, AfterViewInit {

  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Input()
  public data: RespPagination<any>;
  @Input()
  title: string = "Busqueda de Productos"
  @Input()
  public text: string = ""
  @Input()
  private precios: number = 0;
  public paramPagination: ParamPagination;
  public facturas$: Observable<any> = null;
  private refresh = new BehaviorSubject<void>(null);
  @Input()
  public typeView = 'LIST'; // puede ser list o list_select
  public formFilter: FormGroup;
  public filterFacturas = {
    fechaInicial: "",
    fechaFinal: "",
    totalMinimo: null,
    totalMaximo: null,
    proveedor: null
  };
  @Output()
  selectInvoice = new EventEmitter<any>();
  constructor(
    private compraHTTPService: CompraHTTPService,
    protected confirmationService: ConfirmationServiceService,
    private modalService: NgbModal,
    private activatedRoute: ActivatedRoute,
  ) {
    super();
    this.paramPagination = new ParamPagination();
  }

  ngOnInit() {
    this.facturas$ = this.refresh.asObservable().pipe(
      switchMap(() =>
        this.activatedRoute.queryParams.pipe(tap(params => {
          this.buildFilter(params);
        }),
          switchMap(() =>
            iif(() => this.data == null || this.data == undefined
              , this.compraHTTPService.getAllFacturasComprasPagination(this.paramPagination, this.filterFacturas)
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
    if (this.firstExecute) {
      this.firstExecute = false;
      if (this.typeComponent == TypeComponent.page) {
        this.resetFilter();
        this.filterFacturas = this.getFilterFromUrl(this.filterFacturas, params, [], [], [],[], ['proveedor']);
      }
    }
  }
  pageChanged() {
    this.refresh.next();
  }
  query() {
    this.setParamQuery(this.filterFacturas);
  }
  reset() {
    this.resetFilter();
    this.setParamQuery(this.filterFacturas);
    this.initFocus();
  }
  resetFilter() {
    this.filterFacturas = {
      fechaInicial: "",
      fechaFinal: "",
      totalMinimo: null,
      totalMaximo: null,
      proveedor: null
    };
  }
  initFocus() {
    setTimeout(() => {
      $("#selProveedor").focus();
    });
  }
  selectFactura(factura: any) {
    this.selectInvoice.emit(factura);
    if (this.typeView == 'SELECT_PROD_MOD') {
    }
    if (this.typeView == 'SELECT_INV') {
    }
  }

  showDetail(factura: any) {
    const modalInvoice = this.modalService.open(DetailFactCompModalComponent, { size: 'xl', backdrop: 'static' });
    modalInvoice.componentInstance.idInvoice = factura.id;
  }
  isCuentaCorriente(pagos) {
    let rta = false;
    pagos.forEach((pago) => {
      if (pago.formaPago.id == 4) { // por ahora esta hardcode el id de la cta cte
        rta = true;
      }
    });
    return rta;
  }
  setParamQuery(data: any) {
    if (this.typeComponent == TypeComponent.page) {
      let tempParam: any = {};
      tempParam.fechaInicial = encodeURI(data.fechaInicial);
      tempParam.fechaFinal = encodeURI(data.fechaFinal);
      tempParam.totalMinimo = encodeURI(data.totalMinimo);
      tempParam.totalMaximo = encodeURI(data.totalMaximo);
      tempParam.proveedor = encodeURI(data.proveedor?data.proveedor.id:null);
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

