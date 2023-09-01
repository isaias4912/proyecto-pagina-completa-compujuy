import { Component, Renderer2, OnInit, ViewChild, AfterViewInit, ElementRef, ChangeDetectionStrategy, ChangeDetectorRef, Input, forwardRef } from '@angular/core';
import { ComponentPage } from '../../../../../core/component-page';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { VentaHTTPService } from '../../../../../core/services/http/venta-http.service';
import { Response } from '../../../../../core/services/utils/response';
import { ConfirmationServiceService } from '../../../../shared/services/confirmation-service.service';
import { SearchTransCompModal } from '../../../modals/search/search-trans.modal';
import { DialogGenComponent } from 'src/app/modules/shared/components/dialog/gen/dialog-gen.component';
import { AdItem } from 'src/app/modules/shared/models/ad-item';
import { DetailTransComponent } from '../movimiento/detail/detail-trans.component';
declare var $: any;
export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => SelectTransComponent),
  multi: true
};
@Component({
  selector: 'select-trans',
  templateUrl: './select-trans.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR],
})
export class SelectTransComponent extends ComponentPage implements OnInit, AfterViewInit, ControlValueAccessor {

  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Input()
  nextFocus: string = null;
  @Input()
  showSearch: boolean = true;
  @Input()
  showList: boolean = false;
  @Input()
  data: any = null;
  @Input()
  type: number = 1; //1: alta/ sin autocompletado . 2: modif
  @ViewChild('inputForm', { static: false }) input: ElementRef;
  public innerValue: any = '';
  public searching: boolean = false;
  @Input()
  private precios: number = 0;
  propagateChange = (_: any) => { }

  constructor(
    protected confirmationService: ConfirmationServiceService,
    private ventaHTTPService: VentaHTTPService,
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
      this.ventaHTTPService.getDetalleTransaccion(value).subscribe((resp: Response<any>) => {
        this.searching = false;
        if (resp.data) {
          this.setData(resp.data)
        } else {
          this.messageToast.warning("Sin resultados para la busqueda (" + value + ")");
          this.cdr.markForCheck();
        }
      })
    } else {
    }
  }

  setData(data: any) {
    if (data.tipo == 3) {
      if (data.asociada) {
        this.messageToast.error("<p>La transacción ya esta asociada.</p>" +
          "Id/Código: " + data.id + " </br>" +
          "Transacción de caja: " + data.transaccionCaja.id + " </br>" +
          "Caja: " + data.transaccionCaja.caja.nombre + " - " + data.transaccionCaja.caja.id + " </br>" +
          "Monto: " + data.monto + "</br>" +
          "Fecha: " + data.fecha + "</br>" +
          "Motivo: " + data.motivo + "</br>" +
          "Tipo asociada: " + data.asociadaTipo + "</br>" +
          "Codigo de asociada: " + data.asociadaId + "</br>"
        );
      } else {
        this.messageToast.success("Id/Código: " + data.id + " </br>" +
          "Transacción de caja: " + data.transaccionCaja.id + " </br>" +
          "Caja: " + data.transaccionCaja.caja.nombre + " - " + data.transaccionCaja.caja.id + " </br>" +
          "Monto: " + data.monto + "</br>" +
          "Fecha: " + data.fecha + "</br>" +
          "Motivo: " + data.motivo + "</br>"
        );
        this.innerValue = data;
        this.renderer.setProperty(this.input.nativeElement, 'value', this.innerValue.id + " - $ " + data.monto +' - '+ data.motivo);
        this.renderer.setProperty(this.input.nativeElement, 'disabled', true);
        this.propagateChange(this.innerValue);
        if (this.nextFocus) {
          setTimeout(() => {
            $("#" + this.nextFocus).focus()
          });
        }
      }
    } else {
      this.messageToast.error("<p>La transacción no corresponde a un pago a proveedores</p>" +
        "Id/Código: " + data.id + " </br>" +
        "Transacción de caja: " + data.transaccionCaja.id + " </br>" +
        "Caja: " + data.transaccionCaja.caja.nombre + " - " + data.transaccionCaja.caja.id + " </br>" +
        "Monto: " + data.monto + "</br>" +
        "Fecha: " + data.fecha + "</br>" +
        "Motivo: " + data.motivo + "</br>"
      );
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
    if (value == null) {
      this.messageToast.error("Debe ingresar una busqueda");
      return false;
    }
    if (value.replace(/\s/g, "").length == 0) {
      this.messageToast.error("Debe ingresar una busqueda");
      return false;
    }
    if (!this.isNumberInt(value)){
      this.messageToast.error("Debe ingresar un número para la busqueda de la transacción");
      return false;
    }
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
    let modalDetail = this.modalService.open(DialogGenComponent, { size: 'lg', backdrop: 'static' });
    modalDetail.componentInstance.item = new AdItem(DetailTransComponent, { idEntity: this.innerValue });
    modalDetail.componentInstance.title = 'Detalle de datos de la transaccion |  ' + this.innerValue;
  }
  showSearchTrans() {
    const modalSearch = this.modalService.open(SearchTransCompModal, { size: 'xl', backdrop: 'static' });
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

