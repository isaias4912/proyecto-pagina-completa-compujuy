import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input, ViewChild, ElementRef, ChangeDetectorRef, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ValidatorFn, AbstractControl, FormArray, FormControl } from '@angular/forms';
import { ComponentPage } from 'src/app/core/component-page';
import { IDataForm } from 'src/app/core/models/idata-form';
import { Fields } from 'src/app/core/fields';
import { Persona } from 'src/app/core/models/persona';
import { Observable, fromEvent, Subscription, iif, from, of } from 'rxjs';
import { map, filter, distinctUntilChanged, debounceTime, switchMap } from 'rxjs/operators';
import { PersonaHTTPService } from 'src/app/core/services/http/persona-http.service';
import { DomicilioComponent } from 'src/app/modules/shared/components/domicilio/domicilio.component';
import { TelefonoComponent } from 'src/app/modules/shared/components/telefono/telefono.component';
import { ContactoComponent } from 'src/app/modules/shared/components/contacto/contacto.component';

declare var $: any;

@Component({
  selector: 'fields-per',
  templateUrl: './fields-per.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FieldsPerComponent extends ComponentPage implements OnInit, AfterViewInit, Fields, OnDestroy {
  @ViewChild("txtDNIPerForm", { static: false })
  txtDNIPer: ElementRef;
  @ViewChild(DomicilioComponent, { static: false })
  domicilioComponent: DomicilioComponent;
  @ViewChild(TelefonoComponent, { static: false })
  telefonoComponent: TelefonoComponent;
  @ViewChild(ContactoComponent, { static: false })
  contactoComponent: ContactoComponent;
  @Input()
  public dataForm: IDataForm = { submitted: false };
  @Input()
  public formPersona: FormGroup;
  @Input()
  public persona: Persona;
  public searchValue$: Observable<any>;
  public subDNI: Subscription;
  public lastDNI: string = "";
  public dataOptions = {
    sexos: [{ value: 0, name: "Sin especificar" }, { value: 1, name: "Masculino" }, { value: 2, name: "Femenino" }, { value: 3, name: "Otro" }],
  };
  constructor(
    private formBuilder: FormBuilder,
    private personaHTTPService: PersonaHTTPService,
    private cdr: ChangeDetectorRef
  ) {
    super();
  }

  ngOnInit() {
    this.formPersona = this.formBuilder.group({
      id: [this.persona ? this.persona.id : null, []],
      dni: [this.persona ? this.persona.dni : null, [Validators.required, this.validateExisteDNI(), this.validateDNI(), this.validateLengthDNI()]],
      nombre: [this.persona ? this.persona.nombre : null, [Validators.required]],
      apellido: [this.persona ? this.persona.apellido : null, [Validators.required]],
      cuil: [this.persona ? this.persona.cuil : null, []],
      fechaNac: [this.persona ? this.persona.fechaNac : null, []],
      sexo: [this.persona ? this.persona.sexo : 0, []],
      domicilios: new FormControl([]),
      contactos: new FormControl([]),
      telefonos: new FormControl([])
    });
    if (this.persona && this.persona.dni) {
      this.lastDNI = this.persona.dni;// para  no validar en la modificacion el dni original
      this.formPersona.controls['dni']['existe_dni'] = false;
      this.formPersona.controls['dni']['validate_dni'] = true;
    }
  }
  ngAfterViewInit(): void {
    this.initFocus();
    this.searchValue$ = fromEvent(this.txtDNIPer.nativeElement, 'keyup').pipe(
      map((event: any) => event.target.value), filter(key => (key != null && key != '' && key != undefined && key.length > 6 && key.length < 9)), distinctUntilChanged(), debounceTime(280),
      switchMap(search =>
        iif(() => search != this.lastDNI
          , this.personaHTTPService.getPersonaByDni(search, true)
          , of({ data: { existe: false } })
        ))
    );
    this.subDNI = this.searchValue$.subscribe(search => {
      this.formPersona.controls['dni']['existe_dni'] = search.data.existe;
      this.formPersona.controls['dni']['validate_dni'] = true;
      this.formPersona.controls['dni'].updateValueAndValidity();
      this.cdr.markForCheck();
    });
  }
  reset() {
    this.domicilioComponent.fd.clear();
    this.telefonoComponent.ft.clear();
    this.contactoComponent.fc.clear();
    this.formPersona.reset();
    this.formPersona.patchValue({
      id: null,
      dni: null,
      nombre: null,
      apellido: null,
      cuil: null,
      fechaNac: null,
      sexo: 0,
      domicilios: [],
      contactos: [],
      telefonos: []
    });
    this.initFocus();
    this.dataForm = { submitted: false };
    // this.formPersona.updateValueAndValidity();
    this.cdr.markForCheck();
  }
  initFocus() {
    setTimeout(() => {
      $("#txtDNIPer").focus();
    });
  }
  get f() {
    return this.formPersona.controls;
  }
  validateExisteDNI(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      if (control['existe_dni']) {
        return { 'existe_dni': true };
      }
      return null;
    };
  }
  isValid() {
    return this.domicilioComponent.formDomicilio.valid && this.telefonoComponent.formTelefono.valid
      && this.contactoComponent.formContacto.valid && this.formPersona.valid;
  }
  validateDNI(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      if (control['validate_dni']) {
        return null;
      }
      return { 'no_validate_dni': true };
    };
  }
  validateLengthDNI(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      if (control.value) {
        if (control.value.toString().length < 7) {
          return { 'length_dni': true };
        }
      }
      return null;
    };
  }
  ngOnDestroy(): void {
    if (this.subDNI) {
      this.subDNI.unsubscribe();
    }
  }
}

