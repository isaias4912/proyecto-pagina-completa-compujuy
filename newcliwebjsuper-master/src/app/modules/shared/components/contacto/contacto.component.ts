import { Component, OnInit, Input, AfterViewChecked, AfterViewInit, ChangeDetectionStrategy, forwardRef, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl, FormArray, ValidatorFn, NG_VALUE_ACCESSOR, ControlValueAccessor, FormControl } from '@angular/forms';
import { Subscription } from 'rxjs';
import { IDataForm } from '../../../../core/models/idata-form';
import { Contacto } from 'src/app/core/models/contacto';
import { ResizeService } from '../size-detector/resize.service';

declare var $: any;
export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => ContactoComponent),
  multi: true
};
@Component({
  selector: 'contacto',
  templateUrl: './contacto.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR],
})

export class ContactoComponent implements OnInit, AfterViewInit, ControlValueAccessor, OnDestroy {
  public formContacto: FormGroup;
  @Input()
  public dataForm: IDataForm = { submitted: false };
  @Input()
  public contactos: Array<Contacto>;
  public innerValue: any = '';
  private subArray: Subscription;
  private subSize: Subscription;
  public sizeWindow: number;

  tiposContacto = [
    { id: 1, value: "Mail" },
    { id: 2, value: "Facebook" },
    { id: 3, value: "Twiter" },
    { id: 4, value: "Otro" }
  ];
  propagateChange = (_: any) => { }
  constructor(
    private formBuilder: FormBuilder,
    private resizeSvc: ResizeService
  ) {
  }


  ngOnInit() {
    this.formContacto = this.formBuilder.group({
      contactos: new FormArray([]),
    });
    this.subArray = this.formContacto.get('contactos').valueChanges.subscribe((resp) => {
      this.innerValue = resp;
      this.propagateChange(this.innerValue);
    });
    if (this.contactos && this.contactos.length > 0) {
      this.contactos.forEach(con => {
        let f: FormGroup = this.formBuilder.group({
          id: [con.id, []],
          tipo: [con.tipo, [Validators.required]],
          descripcion: [con.descripcion, [Validators.required, Validators.email]],
          detalle: [con.detalle, []],
        });
        this.fc.push(f);
        this.changeTipo(f);
      });
    }
    this.subSize = this.resizeSvc.onResize$
    .subscribe(x => {
      this.sizeWindow = x;
      this.changeSizes();
    });
  }
  ngAfterViewInit(): void {
    if (this.contactos && this.contactos.length > 0) {
      this.innerValue = this.formContacto.get('contactos').value;
      this.propagateChange(this.innerValue);
    }
  }
  changeSizes() {
    setTimeout(() => {
      $('.item-cont-tipo, .item-cont-cuenta').removeClass('width-100');
      if ( this.sizeWindow == 0) {
        $('.item-cont-tipo, .item-cont-cuenta').addClass('width-100');
      }
    });
  }
  removeContacto(formSelected: FormGroup) {
    this.fc.removeAt(this.fc.controls.indexOf(formSelected));
    this.formContacto.updateValueAndValidity();
  }
  changeTipo(formSelected: FormGroup) {
    formSelected.get('descripcion').clearValidators();
    if (formSelected.get('tipo').value == 1) {
      formSelected.get('descripcion').setValidators([Validators.required, Validators.email]);
    } else {
      formSelected.get('descripcion').setValidators([Validators.required]);
    }
    formSelected.get('descripcion').updateValueAndValidity();
  }
  addContacto() {
    this.dataForm.submitted = true;
    if (this.formContacto.controls.contactos.valid) {
      let f: FormGroup = this.formBuilder.group({
        id: [0, []],
        tipo: [1, [Validators.required]],
        descripcion: [null, [Validators.required, Validators.email]],
        detalle: [null, []],
      });
      this.fc.push(f);
      this.dataForm.submitted = false;
      setTimeout(() => {
        $("#txtTipoContacto" + (this.fc.length - 1)).focus();
      });
      this.changeSizes();
    }
  }


  get f() { return this.formContacto.controls; }
  get fc() { return this.f.contactos as FormArray; }
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
