import { Component, OnInit, Input, AfterViewInit, ChangeDetectionStrategy, forwardRef, ChangeDetectorRef } from '@angular/core';
import 'select2';
import { AuthService } from '../../../../../core/services/auth/auth.service';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';

declare var $: any;
export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => SelectSucursalSimpleComponent),
  multi: true
};
@Component({
  selector: 'select-sucursal-simple',
  templateUrl: './select-sucursal-simple.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class SelectSucursalSimpleComponent implements OnInit, AfterViewInit, ControlValueAccessor {

  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Input()
  enter: string = null;
  @Input()
  typeResult: number = 1; //1 manda un objeto, 2 manda el id nada mas, 3 manda un array (cuando seleccionaa null van todas las suc en el [])
  @Input()
  textFirstOpt: string = 'Sel. sucursal'
  // @Input()
  // data: any = null;
  public innerValue: any = '';
  propagateChange = (_: any) => { }
  public sucursales: Array<any>;
  public sucursal: any = null;

  constructor(
    private authService: AuthService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit() {
    this.sucursales = this.authService.getSucursales();
  }

  loadData(data: any) {
    if (data) {
      if (this.typeResult == 3) {
        if (data.length == 1) {
          this.sucursal = this.sucursales.find(x => x.id == data[0].id);
        }
        if (data.length > 1) {
          this.sucursal = null;
        }
      }
    }
  }
  ngAfterViewInit(): void {

  }

  public changeSelect() {
    if (this.typeResult == 1) {
      this.innerValue = this.sucursal;
    }
    if (this.typeResult == 2) {
      if (this.sucursal) {
        this.innerValue = this.sucursal.id;
      } else {
        this.innerValue = null;
      }
    }
    if (this.typeResult == 3) {
      if (this.sucursal) {
        this.innerValue = [{ id: this.sucursal.id }];
      } else {
        this.innerValue = [];
        this.sucursales.forEach(s => {
          this.innerValue.push({ id: s.id });
        });
      }
    }
    this.propagateChange(this.innerValue);
  }
  public resetData() {
    this.sucursal = null;
    this.changeSelect();
  }
  writeValue(obj: any): void {
    setTimeout(() => {
      this.innerValue = obj;
      this.loadData(this.innerValue);
      this.cdr.markForCheck();
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
