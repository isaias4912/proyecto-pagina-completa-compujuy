import { Component, OnInit, Input, AfterViewInit, Output, EventEmitter, ViewChild, ElementRef, forwardRef, ChangeDetectionStrategy } from '@angular/core';
import 'select2';
import { environment } from '../../../../../../../environments/environment';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UsuarioHTTPService } from 'src/app/core/services/http/usuario-http.service';
import { even } from '@rxweb/reactive-form-validators';

declare var $: any;
export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => Select2UsuComponent),
  multi: true
};
@Component({
  selector: 'select2-usu',
  templateUrl: './select2-usu.component.html',
  styleUrls: ['./select2-usu.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR],

})
export class Select2UsuComponent implements OnInit, AfterViewInit, ControlValueAccessor {

  @Input()
  data: any;
  @Input()
  multiple: boolean=true;
  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Output()
  selectValue = new EventEmitter<any>(false);
  @Input()
  allowClear: boolean = false;;
  @Input()
  typeResult: number = 1; //1 manda un objeto, 2 manda el id nada mas
  @ViewChild('buttonClickSelect2', { static: false }) buttonSelect2: ElementRef<HTMLElement>;
  public innerValue: any = '';
  propagateChange = (_: any) => { }

  constructor(
    private usuarioHTTPService: UsuarioHTTPService
  ) { }

  ngOnInit() {
    // this.validateData();
  }

  ngAfterViewInit(): void {
    if (!this.multiple) {
      $('#' + this.name).removeAttr('multiple');
    }
    this.load();
  }
  /**
   * actualmente sirve para que traiga un usuario nada mas, se deberia cambiar igual que productos
   */
  loadData(data: any) {
    let loadData = false;
    // primer filtro para comprobar que sea una persona lo que llega
    if (data) {
      if (data.hasOwnProperty('persona') && data.hasOwnProperty('id')) {
        let tempPer = data.persona;
        if (tempPer.hasOwnProperty('apellido') && tempPer.hasOwnProperty('nombre')) {
          if (data.persona.apellido != '' && data.persona.nombre != '' && data.persona.dni != '') {
            loadData = true;
            data.selected = true;
            this.data = [data];
            this.setData(data);
          }
        }
      }
      if (!loadData) {
        if (data.hasOwnProperty('id')) {
          loadData = true;
          this.usuarioHTTPService.getUsuarioMin(data.id).subscribe((resp: any) => {
            resp.data.selected = true;
            this.data = [resp.data];
            this.setData(this.data);
          });
        }
      }
      if (!loadData) {
        this.clear(false);
      }
    } else {
      this.clear(false);
    }
  }
  private languaje = {
    inputTooShort: function () {
      return "Ingrese mas de 2 caracteres...";
    },
    searching: function () {
      return "Buscando…";
    },
    noResults: function () {
      return "No se encontraron resultados";
    }
  };
  private load() {
    $('#' + this.name).select2({
      theme: 'bootstrap4',
      data: this.data,
      allowClear: this.allowClear,
      ajax: {
        headers: {
          "Authorization": 'Bearer ' + localStorage.getItem('access_token')
        },
        url: environment.baseAPIURL + "usuarios/all/",
        dataType: 'json',
        data: function (params) {
          var query = {
            user: params.term
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
      placeholder: 'Busque el usuario',
      language: this.languaje,
      minimumInputLength: 2,
      templateResult: function (us) {
        if (us.usuario) {
          return us.usuario + " - " + us.persona.apellido + " " + us.persona.nombre;
        } else {
          return us;
        }
      },
      templateSelection: function (data) {
        if (data.id == "") {
          return data.text;
        } else {
          return data.usuario + " - " + data.persona.apellido + " " + data.persona.nombre;
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
    $('#' + this.name).on("select2:clearing", (e) => {
      this.changeSelect2(e);
    });
  }
  public setData(data: any) {
    this.data = data;
    $('#' + this.name).find('option').remove();
    $('#' + this.name).select2('destroy');
    this.load()
  }
  public clear(event = true) {
    $('#' + this.name).find('option').remove();
    $('#' + this.name).val(null).trigger('change');
    this.innerValue = null;
    if (event) {
      this.propagateChange(this.innerValue);
    }
  }
  public setDisabled(value: boolean) {
    $('#' + this.name).prop("disabled", value);
  }
  changeSelect2(e = null) {
    let data = $('#' + this.name).select2('data');
    if (this.multiple) {
      this.innerValue = data;
    } else {
      this.innerValue = data[0] ? data[0] : null;
    }
    this.innerValue = this.innerValue;
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
