import { Component, Renderer2, OnInit, ViewChild, AfterViewInit, ElementRef, ChangeDetectionStrategy, ChangeDetectorRef, Input, forwardRef } from '@angular/core';
import { ComponentPage } from '../../../../../../core/component-page';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EncMovimiento } from '../../../../../../core/models/acc-enc-mov';
import { Response } from '../../../../../../core/services/utils/response';
import { ConfirmationServiceService } from '../../../../../shared/services/confirmation-service.service';
import { SearchFactCompModal } from '../../modals/search/search-fact-comp.modal';
import { DetailFactCompModalComponent } from '../../modules/shared-fact-comp/components/detail/detail-fact-comp-modal.component';
import { CompraHTTPService } from 'src/app/core/services/http/compra-http.service';
declare var $: any;
export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => SelectFactCompComponent),
  multi: true
};
@Component({
  selector: 'select-fact-comp',
  templateUrl: './select-fact-comp.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR],
})
export class SelectFactCompComponent extends ComponentPage implements OnInit, AfterViewInit, ControlValueAccessor {

  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Input()
  nextFocus: string = null;
  @Input()
  showSearch: boolean = true;
  @Input()
  showList: boolean = false;
  @Input()
  data: EncMovimiento = null;
  @Input()
  type: number = 1; //1: alta/ sin autocompletado . 2: modif
  @ViewChild('inputForm', { static: false }) input: ElementRef;
  public innerValue: any = '';
  public searching: boolean = false;
  @Input()
  private precios: number = 0;
  propagateChange = (_: EncMovimiento) => { }

  constructor(
    protected confirmationService: ConfirmationServiceService,
    private compraHTTPService: CompraHTTPService,
    private cdr: ChangeDetectorRef,
    private renderer: Renderer2,
    private modalService: NgbModal
  ) {
    super();
  }

  ngOnInit() {
  }
  search(value: string) {
    if (this.isValid(value)) {
      this.searching = true;
      this.compraHTTPService.getFacturaByNum(value).subscribe((resp: Response<EncMovimiento>) => {
        this.searching = false;
        if (resp.data) {
          this.setData(resp.data)
        } else {
          this.messageToast.warning("Sin resultados para la busqueda (" + value + ")");
        }
      })
    } else {
      this.messageToast.error("Debe ingresar una busqueda");
    }
  }

  setData(data: EncMovimiento) {
    this.innerValue = data;
    this.renderer.setProperty(this.input.nativeElement, 'value', this.innerValue.id + " - " + this.innerValue.numero);
    this.renderer.setProperty(this.input.nativeElement, 'disabled', true);
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
    this.renderer.setProperty(this.input.nativeElement, 'disabled', false);
    this.propagateChange(this.innerValue);
    setTimeout(() => {
      $("#" + this.name).focus()
    });
    this.cdr.markForCheck();
  }

  isValid(value: string) {
    if (value == null) return false;
    if (value.replace(/\s/g, "").length == 0) return false;
    return true;
  }
  removeSelectedProd() {
    this.innerValue = null;
    this.propagateChange(this.innerValue);
    this.renderer.setProperty(this.input.nativeElement, 'disabled', false);
    this.renderer.setProperty(this.input.nativeElement, 'value', '');
    setTimeout(() => {
      $("#" + this.name).focus()
    });
  }
  ngAfterViewInit(): void {
    if (this.type == 2 && this.data) {
      this.setData(this.data);
    }

  }
  showDetail() {
    const modalInvoice = this.modalService.open(DetailFactCompModalComponent, { size: 'xl', backdrop: 'static' });
    modalInvoice.componentInstance.idInvoice = this.innerValue.id;
  }
  showSearchInvoice() {
    const modalSearchInvoice = this.modalService.open(SearchFactCompModal, { size: 'xl', backdrop: 'static' });
    this.confirmationService.confirm({
      select: (factura) => {
        this.setData(factura);
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

