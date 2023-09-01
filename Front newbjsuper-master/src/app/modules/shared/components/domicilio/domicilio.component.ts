import { Component, OnInit, Input, AfterViewChecked, AfterViewInit, ChangeDetectionStrategy, forwardRef, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl, FormArray, ValidatorFn, NG_VALUE_ACCESSOR, ControlValueAccessor, FormControl } from '@angular/forms';
import { IDataForm } from 'src/app/core/models/idata-form';
import { Subscription } from 'rxjs';
import { Domicilio } from 'src/app/core/models/domicilio';
import { ResizeService } from '../size-detector/resize.service';

declare var $: any;
export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => DomicilioComponent),
  multi: true
};
@Component({
  selector: 'domicilio',
  templateUrl: './domicilio.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR],
})
export class DomicilioComponent implements OnInit, AfterViewInit, ControlValueAccessor, OnDestroy {


  public formDomicilio: FormGroup;
  @Input()
  public dataForm: IDataForm = { submitted: false };
  @Input()
  public domicilios: Array<Domicilio>;
  private subArray: Subscription;
  private subSize: Subscription;
  public innerValue: any = '';
  public sizeWindow: number;
  propagateChange = (_: any) => { }
  constructor(
    private formBuilder: FormBuilder,
    private resizeSvc: ResizeService
  ) {
  }


  ngOnInit() {
    this.formDomicilio = this.formBuilder.group({
      domicilios: new FormArray([]),
    }, { validator: this.validateExistePrincipal });
    this.subArray = this.formDomicilio.get('domicilios').valueChanges.subscribe((resp) => {
      this.innerValue = resp;
      this.propagateChange(this.innerValue);
    });
    if (this.domicilios && this.domicilios.length > 0) {
      this.domicilios.forEach(dom => {
        let f: FormGroup = this.formBuilder.group({
          id: [dom.id, []],
          calle: [dom.calle, [Validators.required]],
          numero: [dom.numero, [Validators.required]],
          piso: [dom.piso, []],
          dpto: [dom.dpto, []],
          provincia: [dom.provincia, []],
          ciudad: [dom.ciudad, []],
          detalle: [dom.detalle, []],
          principal: [dom.principal, [Validators.required]],
          principalDom: [dom.principal, [Validators.required]],
        });
        this.fd.push(f);
      });

    }
    this.subSize = this.resizeSvc.onResize$
      .subscribe(x => {
        // setTimeout(() => {
        this.sizeWindow = x;
        // });
        // console.log('this.sizeWindow', this.sizeWindow)
        this.changeSizes();
      });
  }
  ngAfterViewInit(): void {
    if (this.domicilios && this.domicilios.length > 0) {
      this.innerValue = this.formDomicilio.get('domicilios').value;
      this.propagateChange(this.innerValue);
    }
  }
  changeSizes() {
    setTimeout(() => {
      // $('.btn-action').removeClass('width-150');
      $('.item-dom-calle').removeClass('width-200');
      if (this.sizeWindow == 0) {
        // $('.btn-action').addClass('width-150');
        $('.item-dom-calle').addClass('width-200');
      }
    }, 500);
  }
  changePrincipal(formSelected: FormGroup) {
    this.fd.controls.forEach(f => {
      f.patchValue({ principal: f == formSelected })
      f.patchValue({ principalDom: f == formSelected })
    });
  }
  removeDomicilio(formSelected: FormGroup) {
    this.fd.removeAt(this.fd.controls.indexOf(formSelected));
    this.formDomicilio.updateValueAndValidity();
  }

  addDomicilio() {
    this.dataForm = { ...this.dataForm, submitted: true };
    if (this.formDomicilio.controls.domicilios.valid) {
      let f: FormGroup = this.formBuilder.group({
        id: [null, []],
        calle: [null, [Validators.required]],
        numero: [null, [Validators.required]],
        piso: [null, []],
        dpto: [null, []],
        provincia: [null, []],
        ciudad: [null, []],
        detalle: [null, []],
        principal: [this.fd.length == 0 ? true : false, [Validators.required]],
        principalDom: [this.fd.length == 0 ? true : false, [Validators.required]],
      });
      this.fd.push(f);
      this.dataForm = { ...this.dataForm, submitted: false };
      setTimeout(() => {
        $("#txtCalleDom" + (this.fd.length - 1)).focus();
      });

      this.changeSizes();
    }
  }
  /**
   * POr lo menos un dom debe ser true
   * @param c 
   */
  validateExistePrincipal(c: AbstractControl): { invalid_principal: boolean } {
    let valid = false;
    if (c.get('domicilios').value == null || c.get('domicilios').value.length == 0) {
      return null;
    }
    c.get('domicilios').value.forEach(dom => {
      if (dom.principalDom) {
        valid = true;
      }
    });
    return valid ? null : { invalid_principal: true };
  }
  get f() { return this.formDomicilio.controls; }
  get fd() { return this.f.domicilios as FormArray; }
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
