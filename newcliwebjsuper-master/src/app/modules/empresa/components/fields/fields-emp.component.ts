import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input, ViewChild, ChangeDetectorRef, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl, ValidatorFn, AbstractControl } from '@angular/forms';
import { ComponentPage } from '../../../../core/component-page';
import { Fields } from '../../../../core/fields';
import { IDataForm } from '../../../../core/models/idata-form';
import { DomicilioComponent } from 'src/app/modules/shared/components/domicilio/domicilio.component';
import { TelefonoComponent } from 'src/app/modules/shared/components/telefono/telefono.component';
import { ContactoComponent } from 'src/app/modules/shared/components/contacto/contacto.component';
import { Empresa } from 'src/app/core/models/empresa';
import { EmpresaHTTPService } from 'src/app/core/services/http/empresa-http.service';
import { Observable, Subscription, fromEvent, iif, of } from 'rxjs';
import { map, filter, debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

declare var $: any;

@Component({
  selector: 'fields-emp',
  templateUrl: './fields-emp.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FieldsEmpComponent extends ComponentPage implements OnInit, AfterViewInit, Fields {
  @ViewChild("txtCUITEmp", { static: false })
  txtDNIPer: ElementRef;
  @Input()
  public dataForm: IDataForm = { submitted: false };
  @Input()
  public formEmpresa: FormGroup
  @Input()
  public empresa: Empresa;
  @ViewChild(DomicilioComponent, { static: false })
  domicilioComponent: DomicilioComponent;
  @ViewChild(TelefonoComponent, { static: false })
  telefonoComponent: TelefonoComponent;
  @ViewChild(ContactoComponent, { static: false })
  contactoComponent: ContactoComponent;
  public searchValue$: Observable<any>;
  public subCUIT: Subscription;
  public lastCUIT: string = "";
  public dataOptions = {
    tipos: [
      { value: 'MONOTRIBUTO', name: "Monotributo" },
      { value: 'RESPONSABLE_INSCRIPTO', name: "Responsable Inscripto" },
      { value: 'EXCENTO', name: "Excento" },
    ]
  };
  constructor(
    private formBuilder: FormBuilder,
    private cdr: ChangeDetectorRef,
    private empresaHTTPService: EmpresaHTTPService
  ) {
    super();
  }

  ngOnInit() {
    this.formEmpresa = this.formBuilder.group({
      cuit: [this.empresa ? this.empresa.cuit : null, [Validators.required, this.validateExisteCUIT(), this.validateCUIT(), Validators.min(9999999999)]],
      razonSocial: [this.empresa ? this.empresa.razonSocial : null, [Validators.required, Validators.maxLength(80)]],
      persona: [this.empresa ? this.empresa.persona : null, []],
      tipoEmpresa: [this.empresa ? this.empresa.tipoEmpresa : 'MONOTRIBUTO', [Validators.required]],
      domicilios: new FormControl([]),
      contactos: new FormControl([]),
      telefonos: new FormControl([])
    });
    if (this.empresa && this.empresa.cuit) {
      this.lastCUIT = this.empresa.cuit;// para  no validar en la modificacion el dni original
      this.formEmpresa.controls['cuit']['existe_cuit'] = false;
      this.formEmpresa.controls['cuit']['validate_cuit'] = true;
    }
  }
  ngAfterViewInit(): void {
    this.initFocus();
    this.searchValue$ = fromEvent(this.txtDNIPer.nativeElement, 'keyup').pipe(
      map((event: any) => event.target.value), filter(key => (key != null && key != '' && key != undefined && key.length > 9 && key.length < 12)), distinctUntilChanged(), debounceTime(280),
      switchMap(search =>
        iif(() => search != this.lastCUIT
          , this.empresaHTTPService.getEmpresaByCuit(search, true)
          , of({ data: { existe: false } })
        ))
    );
    this.subCUIT = this.searchValue$.subscribe(search => {
      this.formEmpresa.controls['cuit']['existe_cuit'] = search.data.existe;
      this.formEmpresa.controls['cuit']['validate_cuit'] = true;
      this.formEmpresa.controls['cuit'].updateValueAndValidity();
      this.cdr.markForCheck();
    });
  }
  reset() {
    this.domicilioComponent.fd.clear();
    this.telefonoComponent.ft.clear();
    this.contactoComponent.fc.clear();
    this.formEmpresa.reset();
    // this.selectPerComponent.resetData();
    this.formEmpresa.patchValue({
      cuit: null,
      razonSocial: null,
      persona: null,
      tipoEmpresa: 'MONOTRIBUTO',
      domicilios: new FormControl([]),
      contactos: new FormControl([]),
      telefonos: new FormControl([])
    });
    this.initFocus();
    this.dataForm = { ...this.dataForm, submitted: false };
    this.cdr.markForCheck();
  }
  isValid() {
    return this.domicilioComponent.formDomicilio.valid && this.telefonoComponent.formTelefono.valid
      && this.contactoComponent.formContacto.valid && this.formEmpresa.valid;
  }
  validateExisteCUIT(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      if (control['existe_cuit']) {
        return { 'existe_cuit': true };
      }
      return null;
    };
  }

  validateCUIT(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      if (control['validate_cuit']) {
        return null;
      }
      return { 'no_validate_cuit': true };
    };
  }
  initFocus() {
    setTimeout(() => {
      $("#txtCuilProv").focus();
    });
  }
  get f() {
    return this.formEmpresa.controls;
  }
}

