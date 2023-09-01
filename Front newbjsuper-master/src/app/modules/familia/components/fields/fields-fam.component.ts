import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input, ViewChild, ElementRef, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ValidatorFn, AbstractControl } from '@angular/forms';
import { Fields } from 'src/app/core/fields';
import { ComponentPage } from 'src/app/core/component-page';
import { IDataForm } from 'src/app/core/models/idata-form';
import { Familia } from 'src/app/core/models/familia';
import { Observable, fromEvent, Subscription } from 'rxjs';
import { map, distinctUntilChanged, debounceTime, switchMap, filter } from 'rxjs/operators';
import { FamiliaHTTPService } from 'src/app/core/services/http/familia-http.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';

declare var $: any;

@Component({
  selector: 'fields-fam',
  templateUrl: './fields-fam.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FieldsFamComponent extends ComponentPage implements OnInit, AfterViewInit, Fields {

  @ViewChild("inputNombreCortoForm", { static: false })
  inputNombreCorto: ElementRef;
  @Input()
  public dataForm: IDataForm = { submitted: false };
  @Input()
  public familia: Familia;
  public formFamilia: FormGroup;
  public searchValue$: Observable<any>;
  private subsFamilia: Subscription;

  opcionNivel = {
    niveles: [{ value: 0, text: "NO" }, { value: 1, text: "SI" }],
    nivelSeleccionado: 0
  }
  constructor(
    private formBuilder: FormBuilder,
    private familiaHTTPService: FamiliaHTTPService,
    private modalService: NgbModal,
    private cdr: ChangeDetectorRef,
    private toastrService: ToastrService
  ) {
    super();
  }

  ngOnInit() {
    this.formFamilia = this.formBuilder.group({
      id: [this.familia ? this.familia.id : null, []],
      nombre: [this.familia ? this.familia.nombre : null, [Validators.required, Validators.maxLength(80)]],
      nombreCorto: [this.familia ? this.familia.nombreCorto : null, [Validators.required, Validators.maxLength(20), Validators.minLength(2), this.validateExisteNombreCortoDB()]],
      nivelSeleccionado: [this.familia ? this.familia.nivel == 1 ? 1 : 0 : null, [Validators.required]],
      nivel: [this.familia ? this.familia.nivel : null, [Validators.required]],
      familia: [this.familia ? this.familia.familia : null, [Validators.required]],
      // detalle: [this.familia ? this.familia.detalle : null, [Validators.maxLength(500)]],
    });
    setTimeout(() => {
      this.setFieldFamilia();
    });
  }
  ngAfterViewInit(): void {
    this.initFocus();
    this.searchValue$ = fromEvent(this.inputNombreCorto.nativeElement, 'keyup').pipe(
      map((event: any) => event.target.value), filter(key => (key != null && key != '' && key != undefined)), distinctUntilChanged(), debounceTime(280),
      switchMap((search) => this.familiaHTTPService.isExistNombreCorto(search, true))
    );
    this.searchValue$.subscribe(search => {
      this.formFamilia.controls['nombreCorto']['existe_nombre_corto'] = search.data.existe;
      this.formFamilia.controls['nombreCorto'].updateValueAndValidity();
      this.cdr.markForCheck();
    });
    this.subsFamilia = this.formFamilia.get('familia').valueChanges.subscribe(resp => {
      if (resp) {
        this.formFamilia.patchValue({ nivel: resp.nivel + 1 })
      }
      setTimeout(() => {
        $("#btnSavFam").focus();
      });
    });
  }
  validateExisteNombreCortoDB(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      if (control['existe_nombre_corto']) {
        return { 'exist': true };
      }
      return null;
    };
  }
  reset() {
    this.formFamilia.patchValue({
      id: null,
      nombre: null,
      nombreCorto: null,
      nivelSeleccionado: 0,
      nivel: 0,
      familia: null
    });
    // this.selectFamilyComponent.clear();
    this.dataForm = { submitted: false };
    this.initFocus();
  }
  initFocus() {
    setTimeout(() => {
      $("#txtNombreFam").focus();
    });
  }

  changeNivel() {
    setTimeout(() => {
      this.formFamilia.patchValue({ familia: null });
      this.setFieldFamilia();
    });
  };
  setFieldFamilia() {
    if (this.formFamilia.get('nivelSeleccionado').value == 1) {
      $("#txtFamiliaPadre" + this.internalId).prop("disabled", true);
      this.formFamilia.get('familia').clearValidators();
      this.formFamilia.get('familia').updateValueAndValidity();
      this.formFamilia.patchValue({ nivel: 1 })
    } else {
      this.formFamilia.get('familia').setValidators([Validators.required]);
      this.formFamilia.get('familia').updateValueAndValidity();
      $("#txtFamiliaPadre" + this.internalId).prop("disabled", false);
    }
  }
  validateNivel() {
    let ban = true;
    if (this.formFamilia.get('nivelSeleccionado').value == 1) {
      this.formFamilia.patchValue({ nivel: 1 })
      this.formFamilia.patchValue({ familia: null })
    }
    if (this.formFamilia.get('nivelSeleccionado').value == 0) {
      if (this.formFamilia.get('familia').value === null || this.formFamilia.get('familia').value == "") {
        this.toastrService.error('Debe seleccionar una familia padre.');
        ban = false;
      }
      if (ban) {
        if (this.familia) {
          if (this.familia.id == this.formFamilia.get('nivelSeleccionado').value) {
            this.toastrService.error('Debe seleccionar una familia padre distinta a la que quiere modificar.');
            ban = false;
          }
        }
      }
    }
    return ban;
  }

  get f() {
    return this.formFamilia.controls;
  }
}

