import { Component, OnInit, Input, AfterViewChecked, AfterViewInit, ChangeDetectionStrategy, forwardRef, Output, EventEmitter } from '@angular/core';
import 'select2';
import { AuthService } from 'src/app/core/services/auth/auth.service';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { Select2 } from '../../../../../core/select2';

declare var $: any;
export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => SelectSucursalComponent),
  multi: true
};
@Component({
  selector: 'select-sucursal',
  templateUrl: './select-sucursal.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR],
})
export class SelectSucursalComponent implements OnInit, AfterViewInit, ControlValueAccessor, Select2 {

  @Input()
  data: any = [];
  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Output()
  finishRender = new EventEmitter<any>();
  public innerValue: any = '';
  propagateChange = (_: any) => { }

  constructor(
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.validateData();
  }

  ngAfterViewInit(): void {
    this.resetData();
    this.load();
    this.finishRender.emit(true)
  }
  private resetData() {
    this.authService.getSucursales().forEach(sucursal => {
      this.data.push({ id: sucursal.id, text: sucursal.nombre, selected: false });
    });
  }
  //controlamos  lo que se envie en data
  validateData() {
    let ban = true;
    if (this.data) {
      this.data.forEach(item => {
        if (item == null || item == undefined) {
          ban = false;
        }
      });
    }
    if (!ban) {
      this.data = [];
    }
  }
  load() {
    $('#' + this.name).select2({
      theme: 'bootstrap4',
      width: '100%',
      data: this.data,
    });
    $('#' + this.name).on("select2:select", (e) => {
      this.changeSelect2();
    });
    $('#' + this.name).on("select2:unselect", (e) => {
      this.changeSelect2();
    });
  }
  loadData(data: Array<number | string>) {
    this.data = [];
    if (data) {
      if (data.length > 0) {
        this.authService.getSucursales().forEach(sucursalA => {
          let selected = false;
          data.forEach((sucursalB) => {
            if (sucursalA.id == sucursalB) {
              selected = true;
            }
          });
          this.data.push({ id: sucursalA.id, text: sucursalA.nombre, selected: selected });
        });
        this.setData(this.data);
      } else {
        this.resetData();
        this.setData(this.data);
      }
    } else {
      this.resetData();
      this.setData(this.data);
    }
  }
  changeSelect2() {
    let data = $('#' + this.name).select2('data');
    this.innerValue = data;
    this.propagateChange(this.innerValue);
  }

  public getData(): [] {
    let tempSucursales = $('#' + this.name).select2('data');
    let sucursales: any;
    sucursales = [];
    if (tempSucursales) {
      tempSucursales.forEach(element => {
        sucursales.push({ id: parseInt(element.id), nombre: element.text });
      });
    }
    return sucursales;
  }

  public getDataInt(): [] {
    let tempSucursales = $('#' + this.name).select2('data');
    let sucursales: any;
    sucursales = [];
    if (tempSucursales) {
      tempSucursales.forEach(element => {
        sucursales.push(parseInt(element.id));
      });
    }
    return sucursales;
  }
  setData(data: any) {
    this.data = data;
    $('#' + this.name).find('option').remove();
    $('#' + this.name).select2('destroy');
    this.load()
  }
  clear() {
    $('#' + this.name).val(null).trigger('change');
  }
  setDisabled(value: boolean) {
    $('#' + this.name).prop("disabled", value);
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
