import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, Input, HostListener, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { RespPagination } from '../../../../../../core/models/resp-pagination';
import { Dialog } from '../../../../../../core/dialog';
import { ConfirmationServiceService } from '../../../../../shared/services/confirmation-service.service';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ParamPagination } from '../../../../../../core/models/param-pagination';
import { Observable, of, BehaviorSubject, iif, finalize, take } from 'rxjs';
import { switchMap, tap, pluck, map } from 'rxjs/operators';
import { ProductosHTTPService } from '../../../../../../core/services/http/product-http.service';
import { Producto } from '../../../../../../core/models/producto';
import { DetailProductoComponent } from '../detail-product/detail-product.component';
import { DialogGenComponent } from 'src/app/modules/shared/components/dialog/gen/dialog-gen.component';
import { AdItem } from 'src/app/modules/shared/models/ad-item';
import { NavigationTableComponent } from 'src/app/modules/shared/components/navigation-table/navigation-table.component';
import { TipoSearch } from 'src/app/core/enums';
import { NavigationTableService } from 'src/app/modules/shared/components/navigation-table/navigation-table.service';
declare var $: any;
@Component({
  selector: 'search-prod',
  templateUrl: './search-prod.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SearchProdComponent extends Dialog implements OnInit, AfterViewInit {

  public TipoSearch = TipoSearch;
  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Input()
  public data: RespPagination<Producto>;
  @Input()
  title: string = "Busqueda de Productos"
  @Input()
  public text: string = ""
  @Input()
  public precios: number = 0;
  @Input()
  public typeSearch: TipoSearch = TipoSearch.SEARCH_FILTER;
  @Input()
  public tableHover: boolean = false;
  public paramPagination: ParamPagination;
  public productos$: Observable<any> = null;
  public productosLoaded$: Observable<any> = null;
  private refresh = new BehaviorSubject<any | void>(null);
  private type = 0;
  public formFilter: FormGroup;
  @ViewChild("navigationTableComponent", { static: false })
  navigationTableComponent: NavigationTableComponent;
  private productosLoaded: Array<any> = [];

  constructor(
    private productosHTTPService: ProductosHTTPService,
    protected confirmationService: ConfirmationServiceService,
    public activeModal: NgbActiveModal,
    private formBuilder: FormBuilder,
    private cdr: ChangeDetectorRef,
    private modalService: NgbModal,
    private navigationTableService: NavigationTableService
  ) {
    super(activeModal, confirmationService);
    this.paramPagination = new ParamPagination();
  }

  ngOnInit() {
    this.formFilter = this.formBuilder.group({
      q: ['', []]
    });
    this.productos$ = this.refresh.asObservable().pipe(
      switchMap(() =>
        iif(() => this.data == null || this.data == undefined
          , this.productosHTTPService.getProductoByMultipleFilter(this.formFilter.get('q').value, this.paramPagination.order, this.paramPagination.page, 10, this.paramPagination.reverse, this.precios)
          , of(this.data).pipe(tap(() => {
            this.data = null; // la primera  vez no mas deberia completar los datos
          }))
        )
      ),
      tap((resp: RespPagination<any>) => {
        this.setValuePagination(resp);
      }),
      pluck('data'),
      map(x => [...this.productosLoaded, ...x]),
      tap((resp: Array<any>) => {
        this.productosLoaded = [...resp];
        this.navigationTableService.finishLoad(null);
        this.setTitle();
      })
    );
  }
  ngAfterViewInit(): void {
    setTimeout(() => {
      // if (this.typeSearch = TipoSearch.SEARCH_WITHOUT_FILTER) {
      //   this.navigationTableComponent.initFocus();
      // }
      if (this.typeSearch == TipoSearch.SEARCH_FILTER) {
        $("#txtFiltro").focus();
      }
    });
    this.formFilter.patchValue({ q: this.text });
  }

  private setTitle() {
    this.title = "Busqueda de Productos - " + this.productosLoaded.length + " de " + this.paramPagination.totalItems;
  }
  pageChanged() {
    this.refresh.next(null);
  }

  more() {
    this.paramPagination.page++;
    this.pageChanged();
  }

  selectProducto(producto: Producto) {
    this.activeModal.close(producto);
  }
  showDetailProduct(entity: any) {
    let modalDetail = this.modalService.open(DialogGenComponent, { size: 'xl', backdrop: 'static' });
    modalDetail.componentInstance.item = new AdItem(DetailProductoComponent, { idEntity: entity.id });
    modalDetail.componentInstance.title = 'Detalle del producto ' + entity.nombre + ' ' + entity.apellido;
  }

}

