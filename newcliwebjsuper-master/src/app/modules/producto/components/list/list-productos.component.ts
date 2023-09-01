import { Component, OnInit, ViewChild, AfterViewInit, ElementRef, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { ParamPagination } from 'src/app/core/models/param-pagination';
import { Producto } from 'src/app/core/models/producto';
import { ProductosHTTPService } from 'src/app/core/services/http/product-http.service';
import { FilterProducto } from '../../models/filter-producto';
import { ComponentPage } from '../../../../core/component-page';
import { Observable, BehaviorSubject } from 'rxjs';
import { switchMap, tap, pluck } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from 'src/app/modules/shared/services/confirmation-service.service';
import { ActivatedRoute } from '@angular/router';
import { ListMovStockComponent } from '../stock/list-mov/list-mov-stock.component';
import { ListMovPriceComponent } from '../prices/list-mov/list-mov-price.component';
import { ParentsModalComponent } from '../parent-child/parents/parents-modal.component';
import * as download from 'downloadjs';
declare var $: any;
import * as moment from 'moment';
import { BarcodeModalComponent } from '../barcode/barcode-modal/barcode-modal.component';
import { StockProdModalComponent } from '../stock/stock-prod/stock-prod-modal.component';
import { ConfirmComponent } from 'src/app/modules/shared/components/confirm/confirm.component';
import { RespPagination } from 'src/app/core/models/resp-pagination';
import { SelectSucursalComponent } from 'src/app/modules/shared/components/sucursal/select-multiple/select-scursal.component';
import { DetailProdModal } from '../../modules/shared-prod/modals/detail/detail-prod.modal';
import { ResizeService } from 'src/app/modules/shared/components/size-detector/resize.service';
import { SelectFamilyComponent } from 'src/app/modules/familia/components/select/select-family.component';
import { PathService } from 'src/app/core/services/utils/path.service';

@Component({
  selector: 'productos-list',
  templateUrl: './list-productos.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ListProductosComponent extends ComponentPage implements OnInit, AfterViewInit {
  @ViewChild(SelectFamilyComponent, { static: false })
  selectFamily: SelectFamilyComponent;
  @ViewChild(SelectSucursalComponent, { static: false })
  selectSucursal: SelectSucursalComponent;
  public paramPagination: ParamPagination;
  public productos: Producto[];
  public dataFilter: FilterProducto = new FilterProducto();
  public listProductos$: Observable<Producto[]>;
  private refresh = new BehaviorSubject<void>(null);
  public productos$: Observable<Producto[]>;
  public bandera: any;
  public data: any = {
    type: true,
    showStock: false,
    filterActivo: [{ value: 0, name: "Inactivo" }, { value: 1, name: "Activo" }, { value: 2, name: "Todos" }],
    filterTipo: [{ value: 1, name: "Simples" }, { value: 2, name: "Compuestos" }, { value: 0, name: "Todos" }],
    filterStock: [{ value: 1, name: "Abajo del stock mínimo" }, { value: 2, name: "Abajo del pto. de repo." }, { value: 0, name: "Todos" }, { value: 3, name: "Sin stock" }],
    filterExtra: true
  }

  constructor(
    public productosHTTPService: ProductosHTTPService,
    private cdRef: ChangeDetectorRef,
    private modalService: NgbModal,
    protected confirmationService: ConfirmationServiceService,
    private activatedRoute: ActivatedRoute,
    private resizeSvc: ResizeService,
    public pathService: PathService
  ) {
    super();
    this.paramPagination = new ParamPagination();
  }

  ngOnInit() {
    this.setCantView(2);
    this.productos$ = this.refresh.asObservable().pipe(
      switchMap(() =>
        this.activatedRoute.queryParams.pipe(tap(params => {
          if (this.firstExecute) {
            this.firstExecute = false;
            this.resetFilter();
            this.buildFilter(params);
            this.loadComponents();
          }
        }),
          switchMap(() => this.productosHTTPService.list(this.dataFilter, this.paramPagination)),
          tap((resp: RespPagination<any>) => {
            resp.data.forEach((o: any) => o.$$activo = false)
            this.checkAll = false;
            this.productos = resp.data;
            console.log('this.productos', this.productos)
            this.disabledButtonsTable = true;
            this.setValuePagination(resp);
            this.loadHelpStock();
          }), pluck('data')
        )));
    // cambios en el size del window
    this.resizeSvc.onResize$
      .subscribe(x => {
        this.sizeWindow = x;
        this.changeSizes(x);
      });
  }

  ngAfterViewInit(): void {
    this.cdRef.markForCheck();
    this.initView();
    this.initFocus();
    setTimeout(() => {
      this.loadHelp();
    }, 250);
  }
  changeSizes(size: number) {
    setTimeout(() => {
      $('.btn-action').removeClass('width-150');
      $('.nombre-final').removeClass('width-200');
      if (size == 0) {
        $('.btn-action').addClass('width-150');
        $('.nombre-final').addClass('width-200');
      }
    }, 500);
  }
  buildFilter(params: any) {
    if (params.nombre) {
      this.dataFilter.producto.nombre = decodeURI(params.nombre);
    }
    if (params.codigo) {
      this.dataFilter.producto.codigoBarras = [];
      this.dataFilter.producto.codigoBarras.push({ codigo: decodeURI(params.codigo) });;
    }
    if (params.codigoEspecial) {
      this.dataFilter.producto.codigoEspecial = decodeURI(params.codigoEspecial);
    }
    if (params.familias) {
      let tempFamilias = [].concat(params.familias);
      let tempFam: any = [];
      tempFamilias.forEach(fam => {
        tempFam.push({ id: parseInt(fam) })
      });
      this.dataFilter.familias = tempFam;
    }
    if (params.sucursales) {
      let tempSucursales = [].concat(params.sucursales);
      let tempSuc: any = [];
      tempSucursales.forEach(suc => {
        tempSuc.push({ id: parseInt(suc) })
      });
      this.dataFilter.sucursales = tempSuc;
    }
    if (params.tipo) {
      this.dataFilter.tipo = parseInt(params.tipo);
    }
    if (params.activo) {
      this.dataFilter.activo = parseInt(params.activo);
    }
    if (params.extraFilter) {
      this.dataFilter.extraFilter = params.extraFilter == 1 ? true : false;
    }
    if (params.filterStock) {
      this.dataFilter.filterStock = parseInt(params.filterStock);
    }
    if (params.showStock) {
      this.dataFilter.showStock = params.showStock == 1 ? true : false;
    }
    this.updateFinishView(1);
    this.finishRender();
  }
  initView() {
    $('#divFilterExtra').on('shown.bs.collapse', () => {
      this.dataFilter.extraFilter = true;
    });
    $('#divFilterExtra').on('hidden.bs.collapse', () => {
      this.dataFilter.extraFilter = false;
    });
    if (this.dataFilter.extraFilter) {
      $("#divFilterExtra").collapse('show');
    }
    // lo siguiente es para q eldrop down menu aparezca en la ultima fila, cuando queda un solo registro
    setTimeout(() => {
      $('.dropdown').on('show.bs.dropdown', () => {
        $('.table-responsive').css("overflow", "inherit");
      });
      $('.dropdown').on('hide.bs.dropdown', () => {
        $('.table-responsive').css("overflow", "auto");
      });
    }, 1000);
  }
  pageChanged() {
    this.refresh.next();
  }
  query() {
    this.setParamQuery(this.dataFilter);
  }
  loadSel2Suc() {
    setTimeout(() => {
      if (this.selectSucursal) {
        let tempArray = [];
        this.dataFilter.sucursales.forEach((item: any) => {
          tempArray.push(item.id);
        });
        this.selectSucursal.loadData(tempArray);
      }
    });

  }
  loadHelpStock() {
    if (this.dataFilter.showStock) {
      this.data.showStock = this.dataFilter.showStock;
      setTimeout(() => {
        $('.spnStock:not(".tooltipstered")').tooltipster({
          theme: ['tooltipster-shadow'],
          contentAsHTML: true,
          interactive: true,
          minWidth: 250,
          maxWidth: 350,
        });
      });
    }
  }
  loadComponents() {
    this.loadSel2Suc();
  }
  resetFilter() {
    this.dataFilter = new FilterProducto();
  }
  reset() {
    this.resetFilter();
    this.initFocus();
    this.setParamQuery(this.dataFilter);
  }
  initFocus() {
    setTimeout(() => {
      $("#txtFiltroNombre").focus();
    });
  }
  setParamQuery(data: FilterProducto) {
    let tempParam: any = {};
    tempParam.nombre = encodeURI(data.producto.nombre);
    tempParam.codigoEspecial = encodeURI(data.producto.codigoEspecial);
    tempParam.codigo = encodeURI(data.producto.codigoBarras[0].codigo);
    tempParam.familias = this.getParamURLFromArray(data.familias);
    tempParam.sucursales = this.getParamURLFromArray(data.sucursales);
    tempParam.tipo = encodeURI(data.tipo.toString());
    tempParam.activo = encodeURI(data.activo.toString());
    tempParam.extraFilter = encodeURI(data.extraFilter ? '1' : '0');
    tempParam.filterStock = encodeURI(data.filterStock.toString());
    tempParam.showStock = encodeURI(data.showStock ? '1' : '0');
    this.setParamURL(tempParam).then(resp => {
      if (!resp) {
        this.refresh.next();
      }
    });
  }

  changueShowStock() {
    this.dataFilter = { ...this.dataFilter, showStock: this.data.showStock };
    this.setParamQuery(this.dataFilter)
  }
  changueType() {
    this.paramPagination = { ...this.paramPagination, type: this.data.type ? 'AND' : 'OR', getSize: () => this.paramPagination.totalItems };
  }

  enabledOrDisabled(value) {
    this.productosHTTPService.enableOrDisableProducto(this.listChecked, value).subscribe(resp => {
      if (value) {
        this.messageToast.success("Se activarón correctamente el/lo/s producto/s seleccionado/s");
      } else {
        this.messageToast.success("Se desactivarón correctamente el/lo/s producto/s seleccionado/s");
      }
      this.refresh.next();
    });
  };

  copytoClipboard(text, elem) {
    try {
      var $temp = $("<input>");
      $("body").append($temp);
      $temp.style = "display: none";
      $temp.val(text).select();
      var success = document.execCommand("copy");
      $temp.remove();
      if (success) {
        this.messageToast.info(elem + ' copiado correctamente', '', {
          positionClass: 'toast-bottom-center',
          timeOut: 1000,
        });
      } else {
        this.messageToast.error('No se pudo copiar', 'Error', {
          positionClass: 'toast-bottom-center',
          timeOut: 1000,
        });
      }
    } catch (err) {
      this.messageToast.error('No se pudo copiar', 'Error', {
        positionClass: 'toast-bottom-center',
        timeOut: 1000,
      });
    }
  };

  finishRenderSel2Suc() {
    this.updateFinishView(2);
    this.finishRender();
  }
  finishRender() {
    if (this.isFinishView()) {
      if ((!this.dataFilter.sucursales || this.dataFilter.sucursales.length == 0) &&
        (this.dataFilter.filterStock == 0)
      ) {
        this.data.filterExtra = true;
        $("#divFilterExtra").collapse('hide');
      } else {
        this.data.filterExtra = false;
      }
    }
  }
  showDetailProduct(entity: any) {
    let modalDetail = this.modalService.open(DetailProdModal, { size: 'xl', backdrop: 'static' });
    modalDetail.componentInstance.title = 'Detalle del producto ' + entity.nombre;
    modalDetail.componentInstance.idEntity = entity.id;
  }

  showListMovimientosProd(producto: Producto) {
    const modalListStockProduct = this.modalService.open(ListMovStockComponent, { size: 'xl', backdrop: 'static' });
    modalListStockProduct.componentInstance.producto = producto;
  }

  showListMovimientosPriceProd(producto: Producto) {
    const modallistPriceProduct = this.modalService.open(ListMovPriceComponent, { size: 'xl', backdrop: 'static' });
    modallistPriceProduct.componentInstance.producto = producto;
  }

  showParents(producto: Producto) {
    const modallistPriceProduct = this.modalService.open(ParentsModalComponent, { size: 'xl', backdrop: 'static' });
    modallistPriceProduct.componentInstance.producto = producto;
  }
  showStock(producto: Producto) {
    const modalStockProd = this.modalService.open(StockProdModalComponent, { size: 'xl', backdrop: 'static' });
    modalStockProd.componentInstance.producto = producto;
  }

  barCodeProducto(producto: Producto) {
    if (producto.codigoBarras.length > 0) {
      const modalBarCodeProduct = this.modalService.open(BarcodeModalComponent, { size: 'xl', backdrop: 'static' });
      modalBarCodeProduct.componentInstance.producto = producto;
      this.confirmationService.confirm({
        cancel: (option) => {
          if (option == 1) {
            this.messageToast.error('No existe codigos de barra para este producto');
          }
        }
      });
    } else {
      this.messageToast.error('No existe codigos de barra para este producto');
    }
  }

  enabledOrDisabledOne(producto: Producto, value) {
    let prodTemp = [];
    prodTemp.push({ id: producto.id });
    this.productosHTTPService.enableOrDisableProducto(prodTemp, value).subscribe(() => {
      if (value) {
        this.messageToast.success('Se activo correctamente el producto ' + producto.nombreFinal);
      } else {
        this.messageToast.success('Se desactivo correctamente el producto ' + producto.nombreFinal);
      }
      this.refresh.next();
    });
  };
  printDetail(producto: Producto) {
    this.productosHTTPService.printDetail(producto.id.toString()).subscribe((response: any) => {
      var nameFile = producto.nombreFinal.replace(/\s/g, "").substring(0, 10) + '_' + producto.id + "_" + moment().format('DMMYYYYhmmss');
      var blob = new Blob([response], { type: "application/pdf" });
      download(blob, nameFile + ".pdf", "file/pdf");
    });
  };
  printList(typePrint) {
    this.productosHTTPService.printList(this.dataFilter, this.paramPagination, typePrint).subscribe((response: any) => {
      var nameFile = "ListaProductos-" + moment().format('DMMYYYYhmmss');
      var blob = new Blob([response], { type: "application/" + typePrint });
      download(blob, nameFile + "." + typePrint, "file/" + typePrint);
    });
  };
  showOrHideExtraFilter() {
    $("#divFilterExtra").collapse('hide');
    if (this.dataFilter.extraFilter) {
      $("#divFilterExtra").collapse('show');
    }
  }
  delete(producto: Producto) {
    let modalConfirim = this.modalService.open(ConfirmComponent);
    modalConfirim.componentInstance.title = "Eliminación del producto " + producto.nombreFinal;
    this.confirmationService.confirm({
      message: 'Acepta la eliminación del producto ' + producto.nombreFinal + ' ?',
      accept: () => {
        this.productosHTTPService.deleteProducto(producto.id.toString()).subscribe(resp => {
          this.messageToast.success('Se elimino correctamente el producto ' + producto.id + ' - ' + producto.nombreFinal);
          this.refresh.next();
        });
      },
      cancel: () => {

      }
    });
  }
}

