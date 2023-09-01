import { Component, OnInit, Input, AfterViewInit, ChangeDetectionStrategy, forwardRef } from '@angular/core';
import 'select2';
import { AuthService } from '../../../../../core/services/auth/auth.service';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';

declare var $: any;
export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => SelectDualListComponent),
  multi: true
};
@Component({
  selector: 'select-suc-dual-list',
  templateUrl: './select-dual-list.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class SelectDualListComponent implements OnInit, AfterViewInit, ControlValueAccessor {

  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Input()
  enter: string = null;
  @Input()
  typeResult: number = 1; //1 manda un objeto, 2 manda el id nada mas
  public innerValue: any = '';
  propagateChange = (_: any) => { }
  public sucursales: Array<any>;
  public sucursal: any = null;
  private component: any = null;
  constructor(
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.sucursales = this.authService.getSucursales();
  }

  ngAfterViewInit(): void {
    // this.load();
  }
  reset() {
    $('.removeall').trigger('click');
  }
  load() {
    if (this.innerValue) {
      this.sucursales.forEach((suc) => {
        this.innerValue.forEach((sucUser) => {
          if (sucUser.id == suc.id) {
            $("#option_" + suc.id).prop("selected", true);
          }
        });
      });
    }
    this.component = $('#selectDual').bootstrapDualListbox({
      infoTextFiltered: '<span class="label label-lg">Filtrando</span>',
      filterPlaceHolder: 'Filtro',
      filterTextClear: 'Mostrar todas',
      infoTextEmpty: 'Sin items',
      infoText: 'Mostrando {0}'
    });
    let container1 = this.component.bootstrapDualListbox('getContainer');
    let elem = container1.find('.btn-group.buttons'); //.addClass('btn-primary btn-info btn-bold');
    elem.find('.btn').addClass('btn-transition btn-outline-primary');
    elem.find('.btn:nth-child(1)').html('<span class="fa fa-angle-double-right" ></span>');
    elem.find('.btn:nth-child(2)').html('<span class="fa fa-angle-double-left" ></span>');
    $('#selectDual').on('change', e => {
      let sucursalesSeleccionadas = $('#selectDual').val();
      let tempSucursales = [];
      this.sucursales.forEach(suc => {
        sucursalesSeleccionadas.forEach(sucSel => {
          if (suc.nombre == sucSel) {
            tempSucursales.push(suc);
          }
        })
      })
      this.innerValue = tempSucursales;
      this.propagateChange(this.innerValue);
    })
  
  }
  writeValue(obj: any): void {
    setTimeout(() => {
      this.innerValue = obj;
      this.load();
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
