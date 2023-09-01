import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input, ViewChild, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Fields } from '../../../../core/fields';
import { IDataForm } from '../../../../core/models/idata-form';
import { Proveedor } from 'src/app/core/models/proveedor';
import { Entidad } from 'src/app/core/models/entidad';
import { FieldsCliProv } from 'src/app/modules/entidad/modules/shared-ent/components/fields-cli-prov/fields-cli-prov.component';

declare var $: any;

@Component({
  selector: 'fields-prov',
  templateUrl: './fields-prov.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FieldsProvComponent extends FieldsCliProv implements OnInit, AfterViewInit, Fields {

  @Input()
  public dataForm: IDataForm = { submitted: false };
  @Input()
  public formProveedor: FormGroup;
  @Input()
  public proveedor: Proveedor;

  constructor(
    private formBuilder: FormBuilder,
    private cdr: ChangeDetectorRef
  ) {
    super();
  }

  ngOnInit() {
    this.formProveedor = this.formBuilder.group({
      entidad: [this.proveedor ? this.proveedor.entidad : null, [Validators.required]],
      tipoProveedor: [this.proveedor ? this.proveedor.tipoProveedor : null, [Validators.required]],
      tipoDocProveedor: [this.proveedor ? this.proveedor.tipoDocProveedor : null, [Validators.required]],
      nroDocProveedor: [this.proveedor ? this.proveedor.nroDocProveedor : null, [Validators.required]],
      observacion: [this.proveedor ? this.proveedor.observacion : null, []],
    });
    this.formProveedor.get('entidad').valueChanges.subscribe(resp => {
      if (resp) {
        this.setDataEntidad(resp);
      } else {
        this.formProveedor.patchValue({
          tipoProveedor: null,
          tipoDocProveedor: null,
          nroDocProveedor: null,
        });
        this.loadTipos([]);
        this.loadDocs([]);
      }
    });
    if (this.proveedor) {
      this.reloadTipos();
      this.reloadDocs();
      this.checkData();
    }
  }
  ngAfterViewInit(): void {
    this.initFocus();
  }
  checkData() {
    let entidad = this.formProveedor.get('entidad').value;
    let tipoDoc = this.formProveedor.get('tipoDocProveedor').value;
    if (entidad.tipo == 'PERSONA') {
      if (tipoDoc == 'DNI') {
        this.enableNroDoc = false;
      }
    }
    if (entidad.tipo == 'EMPRESA') {
      if (tipoDoc == 'CUIT') {
        this.enableNroDoc = false;
      }
    }
  }
  setDataEntidad(entidad: Entidad) {
    if (entidad.tipo == 'PERSONA') {
      this.formProveedor.patchValue({ tipoProveedor: 'MONOTRIBUTO' });
      this.formProveedor.patchValue({ nroDocProveedor: null });
    }
    if (entidad.tipo == 'EMPRESA') {
      this.formProveedor.patchValue({ tipoProveedor: entidad.empresa.tipoEmpresa });
      this.formProveedor.patchValue({ nroDocProveedor: entidad.empresa.cuit });
    }
    this.reloadTipos();
    this.changeTipoProveedor();
  }
  reloadTipos() {// por el momento para los dos tipos serian solo estos tipos de condicion de afiñ
    let entidad = this.formProveedor.get('entidad').value;
    if (entidad.tipo == 'PERSONA') {
      this.loadTipos(['MONOTRIBUTO', 'RESPONSABLE_INSCRIPTO', 'EXCENTO', 'SIN_ESPECIFICAR']);
    }
    if (entidad.tipo == 'EMPRESA') {
      this.loadTipos(['MONOTRIBUTO', 'RESPONSABLE_INSCRIPTO', 'EXCENTO']);
    }
  }
  changeTipoProveedor() {
    let tipoProveedor = this.formProveedor.get('tipoProveedor').value;
    if (tipoProveedor == 'MONOTRIBUTO') {
      this.formProveedor.patchValue({ tipoDocProveedor: 'CUIT' });
    }
    if (tipoProveedor == 'RESPONSABLE_INSCRIPTO') {
      this.formProveedor.patchValue({ tipoDocProveedor: 'CUIT' });
    }
    if (tipoProveedor == 'EXCENTO') {
      this.formProveedor.patchValue({ tipoDocProveedor: 'CUIT' });
    }
    if (tipoProveedor == 'SIN_ESPECIFICAR') {
      this.formProveedor.patchValue({ tipoDocProveedor: 'DNI' });
    }
    this.reloadDocs();
    this.changeTipoDoc();
  }
  reloadDocs() {
    let tipoProveedor = this.formProveedor.get('tipoProveedor').value;
    if (tipoProveedor == 'SIN_ESPECIFICAR') {
      this.loadDocs();
    } else { // para todos los demas exigimos cuit
      this.loadDocs(['CUIT']);
    }
  }
  changeTipoDoc() {
    let entidad = this.formProveedor.get('entidad').value;
    let tipoDoc = this.formProveedor.get('tipoDocProveedor').value;
    if (entidad.tipo == 'PERSONA') {
      if (tipoDoc == 'DNI') {
        this.formProveedor.patchValue({ nroDocProveedor: entidad.persona.dni });
        this.enableNroDoc = false;
      } else {
        let cuit = null;
        if (this.proveedor) {
          if (this.proveedor.tipoDocProveedor == 'CUIT') {
            cuit = this.proveedor.nroDocProveedor;
          }
        }
        this.formProveedor.patchValue({ nroDocProveedor: cuit });
        this.enableNroDoc = true;
      }
    }
    if (entidad.tipo == 'EMPRESA') {
      if (tipoDoc == 'CUIT') {
        this.formProveedor.patchValue({ nroDocProveedor: entidad.empresa.cuit });
        this.enableNroDoc = false;
      } else {
        this.enableNroDoc = true;
      }
    }
  }
  reset() {
    this.formProveedor.patchValue({
      entidad: null,
      tipoProveedor: null,
      tipoDocProveedor: null,
      nroDocProveedor: null,
      observacion: null
    });
    this.loadTipos([]);
    this.loadDocs([]);
    this.initFocus();
    this.dataForm = { ...this.dataForm, submitted: false };
    this.cdr.markForCheck();
  }

  initFocus() {
    setTimeout(() => {
      $("#selectEntidad").focus();
    });
  }
  get f() {
    return this.formProveedor.controls;
  }
}

