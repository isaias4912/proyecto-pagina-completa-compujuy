import { Component, OnInit, Input, AfterViewChecked, AfterViewInit, ChangeDetectionStrategy, forwardRef, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl, FormArray, ValidatorFn, NG_VALUE_ACCESSOR, ControlValueAccessor, FormControl } from '@angular/forms';
import { Subscription } from 'rxjs';
import { IDataForm } from '../../../../core/models/idata-form';
import { Telefono } from 'src/app/core/models/telefono';
import { ResizeService } from '../size-detector/resize.service';

declare var $: any;
export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => TelefonoComponent),
  multi: true
};
@Component({
  selector: 'telefono',
  templateUrl: './telefono.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR],
})

export class TelefonoComponent implements OnInit, AfterViewInit, ControlValueAccessor, OnDestroy {
  public formTelefono: FormGroup;
  @Input()
  public dataForm: IDataForm = { submitted: false };
  @Input()
  public telefonos: Array<Telefono>;
  public innerValue: any = '';
  private subArray: Subscription;
  private subSize: Subscription;
  public sizeWindow: number;

  tiposTelefono = [
    { id: 1, value: "Tel. Celular" },
    { id: 2, value: "Tel. Fijo" },
  ];
  propagateChange = (_: any) => { }
  constructor(
    private formBuilder: FormBuilder,
    private resizeSvc: ResizeService
  ) {
  }


  ngOnInit() {
    this.formTelefono = this.formBuilder.group({
      telefonos: new FormArray([]),
    }, { validator: this.validateExistePrincipal });
    this.subArray = this.formTelefono.get('telefonos').valueChanges.subscribe((resp) => {
      this.innerValue = resp;
      this.propagateChange(this.innerValue);
    });
    if (this.telefonos && this.telefonos.length > 0) {
      this.telefonos.forEach(tel => {
        let f: FormGroup = this.formBuilder.group({
          id: [tel.id, []],
          tipo: [tel.tipo, [Validators.required]],
          prefijo: [tel.prefijo, [Validators.required]],
          numero: [tel.numero, [Validators.required]],
          detalle: [tel.detalle, []],
          principal: [tel.principal, [Validators.required]],
          principalTel: [tel.principal, [Validators.required]],
        });
        this.ft.push(f);
      });
    }
    this.subSize = this.resizeSvc.onResize$
    .subscribe(x => {
      this.sizeWindow = x;
      this.changeSizes();
    });
  }
  ngAfterViewInit(): void {
    if (this.telefonos && this.telefonos.length > 0) {
      this.innerValue = this.formTelefono.get('telefonos').value;
      this.propagateChange(this.innerValue);
    }
  }
  changeSizes() {
    setTimeout(() => {
      $('.item-tel-tipo, .item-tel-pref, .item-tel-num').removeClass('width-100');
      if (this.sizeWindow == 0) {
        $('.item-tel-tipo, .item-tel-pref, .item-tel-num').addClass('width-100');
      }
    });
  }
  changePrincipal(formSelected: FormGroup) {
    this.ft.controls.forEach(f => {
      f.patchValue({ principal: f == formSelected })
      f.patchValue({ principalTel: f == formSelected })
    });
  }
  removeTelefono(formSelected: FormGroup) {
    this.ft.removeAt(this.ft.controls.indexOf(formSelected));
    this.formTelefono.updateValueAndValidity();
  }

  addTelefono() {
    this.dataForm.submitted = true;
    if (this.formTelefono.controls.telefonos.valid) {
      let f: FormGroup = this.formBuilder.group({
        id: [0, []],
        tipo: [1, [Validators.required]],
        prefijo: [null, [Validators.required]],
        numero: [null, [Validators.required]],
        detalle: [null, []],
        principal: [this.ft.length == 0 ? true : false, [Validators.required]],
        principalTel: [this.ft.length == 0 ? true : false, [Validators.required]],
      });
      this.ft.push(f);
      this.dataForm.submitted = false;
      setTimeout(() => {
        $("#txtTipoTelefono" + (this.ft.length - 1)).focus();
      });
      this.changeSizes();
    }

  }

  /**
    * POr lo menos un telefono debe ser true
    * @param c 
    */
  validateExistePrincipal(c: AbstractControl): { invalid_principal: boolean } {
    let valid = false;
    if (c.get('telefonos').value == null || c.get('telefonos').value.length == 0) {
      return null;
    }
    c.get('telefonos').value.forEach(tel => {
      if (tel.principalTel) {
        valid = true;
      }
    });
    return valid ? null : { invalid_principal: true };
  }
  get f() { return this.formTelefono.controls; }
  get ft() { return this.f.telefonos as FormArray; }
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
  ngOnDestroy(): void {
    if (this.subArray) {
      this.subArray.unsubscribe();
    }
    if (this.subSize) {
      this.subSize.unsubscribe();
    }
  }
}
