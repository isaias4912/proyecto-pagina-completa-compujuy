import { Component, Renderer2, OnInit, ViewChild, AfterViewInit, ElementRef, ChangeDetectionStrategy, ChangeDetectorRef, Input, forwardRef } from '@angular/core';
import { ComponentPage } from '../../../../../../core/component-page';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EncMovimiento } from '../../../../../../core/models/acc-enc-mov';
import { ConfirmationServiceService } from '../../../../../shared/services/confirmation-service.service';
import { SearchFactCompProdModal } from '../../modals/search-prod/search-fact-comp-prod.modal';
import { CompraHTTPService } from 'src/app/core/services/http/compra-http.service';
declare var $: any;
export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => SelectFactCompProdComponent),
  multi: true
};
@Component({
  selector: 'select-fact-comp-prod',
  templateUrl: './select-fact-comp-prod.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR],
})
export class SelectFactCompProdComponent extends ComponentPage implements OnInit, AfterViewInit, ControlValueAccessor {

  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Input()
  nextFocus: string = null;
  @Input()
  showSearch: boolean = true;
  @Input()
  showList: boolean = true;
  @Input()
  data: EncMovimiento = null;
  @Input()
  type: number = 1; //1: alta/ sin autocompletado . 2: modif
  @ViewChild('inputForm', { static: false }) input: ElementRef;
  public innerValue: any = '';
  public searching: boolean = false;
  @Input()
  public precios: number = 0;
  propagateChange = (_: EncMovimiento) => { }
  public titleInput: string = '';
  constructor(
    protected confirmationService: ConfirmationServiceService,
    private compraHTTPService: CompraHTTPService,
    private cdr: ChangeDetectorRef,
    private renderer: Renderer2,
    private modalService: NgbModal,

  ) {
    super();
  }

  ngOnInit() {
  }

  setData(data: EncMovimiento) {
    this.innerValue = data;
    this.titleInput = this.innerValue.id + "-" + this.innerValue.nombreProducto + "(" + this.innerValue.$$facturaNumero + "/" + this.innerValue.$$facturaId + ")";
    this.renderer.setProperty(this.input.nativeElement, 'value', this.innerValue.id + "-" + this.innerValue.nombreProducto + "(" + this.innerValue.$$facturaNumero + "/" + this.innerValue.$$facturaId + ")");
    this.propagateChange(this.innerValue);
    if (this.nextFocus) {
      setTimeout(() => {
        $("#" + this.nextFocus).focus()
      });
    }
    this.cdr.markForCheck();
  }
  resetData() {
    this.innerValue = null;
    this.renderer.setProperty(this.input.nativeElement, 'value', '');
    this.propagateChange(this.innerValue);
    setTimeout(() => {
      $("#" + this.name).focus()
    });
    this.cdr.markForCheck();
  }
 
  ngAfterViewInit(): void {
    if (this.type == 2 && this.data) {
      this.setData(this.data);
    }

  }
 
  showSearchInvoice() {
    const modalSearchInvoiceProd = this.modalService.open(SearchFactCompProdModal, { size: 'xl', backdrop: 'static' });
    this.confirmationService.confirm({
      select: (item) => {
        this.setData(item);
      }
    });
  }
  writeValue(obj: any): void {
    this.innerValue = obj;
  }
  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }
  registerOnTouched(fn: any): void {
  }
  setDisabledState?(isDisabled: boolean): void {
  }

}

