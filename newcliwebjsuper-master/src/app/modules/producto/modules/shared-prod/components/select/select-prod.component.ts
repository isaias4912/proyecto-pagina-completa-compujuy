import { Component, Renderer2, OnInit, ViewChild, AfterViewInit, ElementRef, ChangeDetectionStrategy, ChangeDetectorRef, Input, forwardRef } from '@angular/core';
import { ComponentPage } from 'src/app/core/component-page';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';
import { RespPagination } from 'src/app/core/models/resp-pagination';
import { ConfirmationServiceService } from 'src/app/modules/shared/services/confirmation-service.service';
import { Producto } from 'src/app/core/models/producto';
import { ProductosHTTPService } from '../../../../../../core/services/http/product-http.service';
import { SearchProdComponent } from '../search/search-prod.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DetailProductoComponent } from '../detail-product/detail-product.component';
import { DialogGenComponent } from 'src/app/modules/shared/components/dialog/gen/dialog-gen.component';
import { AdItem } from 'src/app/modules/shared/models/ad-item';
import { ObjectValidator } from 'src/app/core/utils/object-validator';
import { TipoSearch } from 'src/app/core/enums';
declare var $: any;
export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => SelectProdComponent),
  multi: true
};
@Component({
  selector: 'select-prod',
  templateUrl: './select-prod.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR],
})
export class SelectProdComponent extends ComponentPage implements OnInit, AfterViewInit, ControlValueAccessor {

  public TipoSearch = TipoSearch;
  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Input()
  class: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Input()
  nextFocus: string = null;
  @Input()
  data: Producto = null;
  @Input()
  type: number = 1; //1: alta/ sin autocompletado . 2: modif
  @Input()
  typeResult: number = 1; //1: objeto / 2: id del producto
  @ViewChild('inputForm', { static: false }) input: ElementRef;
  public searching: boolean = false;
  @Input()
  public precios: number = 0;
  @Input()
  public index: number = 0;
  @Input()
  public autoFocus: boolean = false;
  @Input()
  public typeSearch: TipoSearch = TipoSearch.SEARCH_FILTER;
  public innerValue: any = '';
  propagateChange = (_: Producto) => { }
  private objectValidator: ObjectValidator = new ObjectValidator();

  constructor(
    protected confirmationService: ConfirmationServiceService,
    private productosHTTPService: ProductosHTTPService,
    private cdr: ChangeDetectorRef,
    private renderer: Renderer2,
    private modalService: NgbModal,

  ) {
    super();
  }

  ngOnInit() {

  }
  search(value: string) {
    if (this.isValid(value)) {
      this.searching = true;
      this.productosHTTPService.getProductoByMultipleFilter(value, "id", 1, 10, true, this.precios).subscribe((resp: RespPagination<Producto>) => {
        this.searching = false;
        if (resp.total > 0) {
          if (resp.total == 1) {
            this.setData(resp.data[0]);
          } else {
            const search = this.modalService.open(SearchProdComponent, { size: 'xl', backdrop: 'static' });
            search.componentInstance.typeSearch = this.typeSearch;
            search.componentInstance.data = resp;
            search.componentInstance.text = value;
            search.componentInstance.precios = 1; // para que busque con precios
            search.result.then((data) => {
              this.setData(data);
            }, (reason) => {
              this.cdr.markForCheck();
              this.eventInitFocus();
            });
          }
        } else {
          this.messageToast.warning("Sin resultados para la busqueda (" + value + ")");
          this.cdr.markForCheck();
        }
      })
    } else {
      this.messageToast.error("Debe ingresar una busqueda");
    }
  }
  private loadData(data: any) {
    let loadData = false;
    // primer filtro para comprobar que sea una persona lo que llega
    if (data) {
      if (this.objectValidator.isValid(data, ['id', 'nombreFinal', 'nombre', 'codigoEspecial'])) {
        loadData = true;
        this.setLabel(data);
      }
      if (!loadData) {
        if (data.hasOwnProperty('id')) {
          loadData = true;
          this.productosHTTPService.getProductoMinById(data.id).subscribe(resp => {
            this.setData(resp.data, false);
          });
        }
      }
      if (!loadData) {
        this.resetData(false);
      }
    } else {
      this.resetData(false);
    }
  }
  setLabel(data: any) {
    this.renderer.setProperty(this.input.nativeElement, 'value', data.nombreFinal + " - " + (data.codigoEspecial));
    this.renderer.setProperty(this.input.nativeElement, 'disabled', true);
    this.cdr.markForCheck();
  }
  setData(data: Producto, event = true) {
    if (this.isNotEqualValue(data)) {
      this.setLabel(data);
      this.innerValue = data;
      if (event) {
        this.propagateChange(this.innerValue);
        this.eventNextFocus();
      }
      this.cdr.markForCheck();
    }
  }
  /**
  * Propagamos o cambiamos solo si el valor no es igual al que ya estaba antes, como los componentes existentes
  */
  isNotEqualValue(data) {
    let equal = true;
    if (data == this.innerValue) {
      equal = false;
    } else {
      if (data && this.innerValue) {
        if (data.id == this.innerValue.id &&
          data.nombreFinal == this.innerValue.nombreFinal &&
          data.codigoEspecial == this.innerValue.codigoEspecial) {
          equal = false;
        }
      }
    }
    return equal;
  }
  resetData(propagate = true, autoFocus: boolean = this.autoFocus) {
    this.innerValue = null;
    this.renderer.setProperty(this.input.nativeElement, 'value', '');
    this.renderer.setProperty(this.input.nativeElement, 'disabled', false);
    if (propagate) {
      this.propagateChange(this.innerValue);
    }
    if (autoFocus) {
      this.eventInitFocus();
    }
    this.cdr.markForCheck();
  }

  isValid(value: string) {
    if (value == null) return false;
    if (value.replace(/\s/g, "").length == 0) return false;
    return true;
  }

  ngAfterViewInit(): void {
    if (this.type == 2 && this.data) {
      this.setData(this.data);
    }

  }
  eventNextFocus() {
    if (this.nextFocus) {
      setTimeout(() => {
        $("#" + this.nextFocus).focus()
        $("#" + this.nextFocus).select()
      });
    }
  }
  eventInitFocus() {
    setTimeout(() => {
      $("#" + this.name).focus()
    });
  }
  showSearchProd() {
    const modalSearchProv = this.modalService.open(SearchProdComponent, { size: 'xl', backdrop: 'static' });
    modalSearchProv.componentInstance.precios = 1; // para que busque con precios
    modalSearchProv.result.then((data) => {
      this.setData(data);
    }, (reason) => {
      this.eventInitFocus();
    });
  }
  showDetailProduct() {
    let id = null;
    if (this.typeResult == 1) {
      id = this.innerValue.id;
    }
    if (this.typeResult == 2) {
      id = this.innerValue;
    }
    let modalDetail = this.modalService.open(DialogGenComponent, { size: 'xl', backdrop: 'static' });
    modalDetail.componentInstance.item = new AdItem(DetailProductoComponent, { idEntity: id });
  }
  writeValue(obj: any): void {
    setTimeout(() => {
      this.innerValue = obj;
      this.loadData(this.innerValue);
    });
  }
  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }
  registerOnTouched(fn: any): void {
  }
  setDisabledState?(isDisabled: boolean): void {
  }

}

