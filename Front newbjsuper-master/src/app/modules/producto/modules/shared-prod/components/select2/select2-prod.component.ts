import { Component, OnInit, Input, AfterViewInit, Output, EventEmitter, ViewChild, ElementRef, forwardRef, ChangeDetectionStrategy } from '@angular/core';
import { environment } from '../../../../../../../environments/environment';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';
import { ProductosHTTPService } from 'src/app/core/services/http/product-http.service';
import { Select2 } from '../../../../../../core/select2';
import 'select2';
declare var $: any;
export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => Select2ProdComponent),
  multi: true
};
@Component({
  selector: 'select2-prod',
  templateUrl: './select2-prod.component.html',
  styleUrls: ['./select2-prod.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR],

})
export class Select2ProdComponent implements OnInit, AfterViewInit, ControlValueAccessor, Select2 {

  @Input()
  data: any;
  @Input()
  multiple: true;
  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Output()
  finishRender = new EventEmitter<any>();
  public innerValue: any = '';
  propagateChange = (_: any) => { }

  constructor(
    private productosHTTPService: ProductosHTTPService
  ) { }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
    if (!this.multiple) {
      $('#' + this.name).removeAttr('multiple');
    }
    this.load();
    this.finishRender.emit(true)
  }
  loadData(data: any) {
    this.data = [];
    if (data) {
      if (Array.isArray(data) && data.length > 0) {
        this.productosHTTPService.getProductosMinObjs(data).subscribe(resp => {
          resp.data.forEach(item => {
            item.selected = true;
          });
          this.data = resp.data;
          this.setData(this.data);
        });
      } else {
        if (data.hasOwnProperty('id') && data.hasOwnProperty('nombreFinal')) {
          data.select = true;
          this.data = [data];
        }
      }
    }
    this.setData(this.data);
  }

  load() {
    $('#' + this.name).select2({
      theme: 'bootstrap4',
      data: this.data,
      ajax: {
        headers: {
          "Authorization": 'Bearer ' + localStorage.getItem('access_token')
        },
        url: environment.baseAPIURL + "productos/query/search?tipoBarCode=1&like=1&active=2",
        dataType: 'json',
        data: function (params) {
          var query = {
            q: params.term
          }
          return query;
        },
        processResults: function (data) {
          return {
            results: data.data
          };
        },
        cache: true
      },
      placeholder: 'Busque el producto',
      minimumInputLength: 2,
      templateResult: function (prod) {
        if (prod.productoPadre) {
          return prod.nombreFinal + " - " + prod.id + " - " + prod.productoPadre.familia.nombre;
        } else {
          return prod;
        }
      },
      templateSelection: function (data) {
        if (data.id == "") {
          return data.text;
        } else {
          return data.nombreFinal + " - " + data.id + " - " + data.productoPadre.familia.nombre;
        }
      },
      escapeMarkup: function (m) {
        return m;
      }
    });
    $('#' + this.name).on("select2:select", (e) => {
      this.changeSelect2(e);
    });
    $('#' + this.name).on("select2:unselect", (e) => {
      this.changeSelect2(e);
    });
  }
  setData(data: any) {
    this.data = data;
    $('#' + this.name).find('option').remove();
    $('#' + this.name).select2('destroy');
    this.load()
  }
  clear() {
    $('#' + this.name).find('option').remove();
    $('#' + this.name).val(null).trigger('change');
    this.innerValue = null;
    this.propagateChange(this.innerValue);
  }
  setDisabled(value: boolean) {
    $('#' + this.name).prop("disabled", value);
  }
  changeSelect2(e = null) {
    let data = $('#' + this.name).select2('data');
    if (this.multiple) {
      this.innerValue = data;
    } else {
      this.innerValue = data[0] ? data[0] : null;
    }
    this.propagateChange(this.innerValue);
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
