import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input, ViewChild, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IDataForm } from '../../../../core/models/idata-form';
import { Cliente } from '../../../../core/models/cliente';
import { Fields } from '../../../../core/fields';
import { FieldsCliProv } from '../../../../modules/entidad/modules/shared-ent/components/fields-cli-prov/fields-cli-prov.component';
import { Entidad } from 'src/app/core/models/entidad';

declare var $: any;

@Component({
  selector: 'fields-cli',
  templateUrl: './fields-cli.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FieldsCliComponent extends FieldsCliProv implements OnInit, AfterViewInit, Fields {

  @Input()
  public dataForm: IDataForm = { submitted: false };
  @Input()
  public formCliente: FormGroup;
  @Input()
  public cliente: Cliente;

  constructor(
    private formBuilder: FormBuilder,
    private cdr: ChangeDetectorRef
  ) {
    super();
  }

  ngOnInit() {
    this.formCliente = this.formBuilder.group({
      entidad: [this.cliente ? this.cliente.entidad : null, [Validators.required]],
      tipoCliente: [this.cliente ? this.cliente.tipoCliente : null, [Validators.required]],
      tipoDocCliente: [this.cliente ? this.cliente.tipoDocCliente : null, [Validators.required]],
      nroDocCliente: [this.cliente ? this.cliente.nroDocCliente : null, [Validators.required]],
      observacion: [this.cliente ? this.cliente.observacion : null, []],
    });
    this.formCliente.get('entidad').valueChanges.subscribe(resp => {
      if (resp) {
        this.setDataEntidad(resp);
      } else {
        this.formCliente.patchValue({
          tipoCliente: null,
          tipoDocCliente: null,
          nroDocCliente: null,
        });
        this.loadTipos([]);
        this.loadDocs([]);
      }
    });
    if (this.cliente) {
      this.reloadTipos();
      this.reloadDocs();
      this.checkData();
    }
  }
  ngAfterViewInit(): void {
    this.initFocus();
  }
  checkData() {
    let entidad = this.formCliente.get('entidad').value;
    let tipoDoc = this.formCliente.get('tipoDocCliente').value;
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
      this.formCliente.patchValue({ tipoCliente: 'CONSUMIDOR_FINAL' });
      this.formCliente.patchValue({ nroDocCliente: entidad.persona.dni });
    }
    if (entidad.tipo == 'EMPRESA') {
      this.formCliente.patchValue({ tipoCliente: entidad.empresa.tipoEmpresa });
      this.formCliente.patchValue({ nroDocCliente: entidad.empresa.cuit });
    }
    this.reloadTipos();
    this.changeTipoCliente();
  }
  reloadTipos() {
    let entidad = this.formCliente.get('entidad').value;
    if (entidad.tipo == 'PERSONA') {
      this.loadTipos();
    }
    if (entidad.tipo == 'EMPRESA') {
      this.loadTipos(['MONOTRIBUTO', 'RESPONSABLE_INSCRIPTO', 'EXCENTO']);
    }
  }

  changeTipoCliente() {
    let tipoCliente = this.formCliente.get('tipoCliente').value;
    if (tipoCliente == 'CONSUMIDOR_FINAL') {
      this.formCliente.patchValue({ tipoDocCliente: 'DNI' });
      this.loadDocs();
    } else {
      this.formCliente.patchValue({ tipoDocCliente: 'CUIT' }); // para toddos los demas exigimos cuit (monotr, resp inscr, excento, )
    }
    this.reloadDocs();
    this.changeTipoDoc();
  }

  reloadDocs() {
    let tipoCliente = this.formCliente.get('tipoCliente').value;
    if (tipoCliente == 'CONSUMIDOR_FINAL') {
      this.loadDocs();
    } else { // para todos los demas exigimos cuit
      this.loadDocs(['CUIT']);
    }
  }

  changeTipoDoc() {
    let entidad = this.formCliente.get('entidad').value;
    let tipoDoc = this.formCliente.get('tipoDocCliente').value;
    if (entidad.tipo == 'PERSONA') {
      if (tipoDoc == 'DNI') {
        this.formCliente.patchValue({ nroDocCliente: entidad.persona.dni });
        this.enableNroDoc = false;
      } else {
        let cuit = null;
        if (this.cliente) {
          if (this.cliente.tipoDocCliente == 'CUIT') {
            cuit = this.cliente.nroDocCliente;
          }
        }
        this.formCliente.patchValue({ nroDocCliente: cuit });
        this.enableNroDoc = true;
      }
    }
    if (entidad.tipo == 'EMPRESA') {
      if (tipoDoc == 'CUIT') {
        this.formCliente.patchValue({ nroDocCliente: entidad.empresa.cuit });
        this.enableNroDoc = false;
      } else {
        this.enableNroDoc = true;
      }
    }
  }

  reset() {
    this.formCliente.patchValue({
      entidad: null,
      tipoCliente: null,
      tipoDocCliente: null,
      nroDocCliente: null,
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
      if (this.cliente){
        $("#txtTipo").focus();
      }else{
        $("#selectEntidad").focus();
      }
    });
  }
  get f() {
    return this.formCliente.controls;
  }
}

