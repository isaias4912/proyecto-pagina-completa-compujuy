import { Component, OnInit, ViewChild, AfterViewInit, ElementRef, ChangeDetectionStrategy, ChangeDetectorRef, Input, forwardRef } from '@angular/core';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';
declare var $: any;
export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => ChangePriceComponent),
  multi: true
};
@Component({
  selector: 'change-price',
  templateUrl: './change-price.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR],
})
export class ChangePriceComponent implements OnInit, AfterViewInit, ControlValueAccessor {
  @Input()
  public tooltip: string = "tooltip";
  public porcentaje: any = {
    one: 0,
    five: 0,
    ten: 0,
    twenty: 0,
  }
  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Input()
  public preciofinal: number;
  @Input()
  public enter: string = 'default';
  public precioactual: number;
  public diferencia: number;
  public precioAddPercent: number;
  public precioAddValue: number;
  public innerValue: number = null;
  public searching: boolean = false;
  @Input()
  private nextfocusclose: string = null;
  propagateChange = (_: number) => { }
  constructor(
    private cdr: ChangeDetectorRef
  ) {
  }

  ngOnInit() {
    this.precioactual = this.preciofinal;
    if (!this.nextfocusclose) {
      this.nextfocusclose = this.tooltip;
    }
  }

  ngAfterViewInit(): void {
    $('#' + this.tooltip).tooltipster({
      contentAsHTML: true,
      interactive: true,
      theme: ['tooltipster-shadow', 'my-custom-theme'],
      side: 'right',
      minWidth: 250,
      minHeight: 400,
      maxWidth: 300,
      trigger: 'click'
    });
    this.cdr.detectChanges();
  }
  changeValue(value: string | number) {
    this.innerValue = Number(value);
    this.propagateChange(this.innerValue);
  }
  changuePrice(optionPorcentaje, optionAddMinus) {
    var porcentaje = 0;
    switch (optionPorcentaje) {
      case "one":
        optionAddMinus == "add" ? this.porcentaje.one++ : this.porcentaje.one--;
        porcentaje = (this.porcentaje.one * this.precioactual) / 100;
        break;
      case "five":
        optionAddMinus == "add" ? this.porcentaje.five += 5 : this.porcentaje.five -= 5;
        porcentaje = (this.porcentaje.five * this.precioactual) / 100;
        break;
      case "ten":
        optionAddMinus == "add" ? this.porcentaje.ten += 10 : this.porcentaje.ten -= 10;
        porcentaje = (this.porcentaje.ten * this.precioactual) / 100;
        break;
      case "twenty":
        optionAddMinus == "add" ? this.porcentaje.twenty += 20 : this.porcentaje.twenty -= 20;
        porcentaje = (this.porcentaje.twenty * this.precioactual) / 100;
        break;

    }
    this.preciofinal = this.precioactual + porcentaje;
    this.preciofinal = Math.round(this.preciofinal * 100) / 100;
    this.diferencia = this.preciofinal - this.precioactual;
    this.diferencia = Math.round(this.diferencia * 100) / 100;
    this.changeValue(this.preciofinal);
  }
  addPriceCustomValue() {
    this.preciofinal = this.precioactual + this.precioAddValue;
    this.preciofinal = Math.round(this.preciofinal * 100) / 100;
    this.diferencia = this.preciofinal - this.precioactual;
    this.diferencia = Math.round(this.diferencia * 100) / 100;
  }
  addPriceCustomPercent() {
    let porcentaje = (Math.abs(this.precioAddPercent) * this.precioactual) / 100;
    if (this.precioAddPercent < 0) {
      this.preciofinal = this.precioactual - porcentaje;
    } else {
      this.preciofinal = this.precioactual + porcentaje;
    }
    this.preciofinal = Math.round(this.preciofinal * 100) / 100;
    this.diferencia = this.preciofinal - this.precioactual;
    this.diferencia = Math.round(this.diferencia * 100) / 100;
  }
  closeHelpPrecioVenta() {
    $('#' + this.tooltip).tooltipster('hide');
    $("#" + this.nextfocusclose).focus();
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

