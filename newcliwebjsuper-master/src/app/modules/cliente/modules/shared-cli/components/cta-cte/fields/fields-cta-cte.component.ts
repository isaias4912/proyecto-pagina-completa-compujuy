import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IDataForm } from '../../../../../../../core/models/idata-form';
import { ComponentPage } from '../../../../../../../core/component-page';
import { Fields } from '../../../../../../../core/fields';
import { CtaCte } from '../../../../../../../core/models/cta-cte';
import { Cliente } from 'src/app/core/models/cliente';
import { Entidad } from 'src/app/core/models/entidad';

declare var $: any;

@Component({
  selector: 'fields-cta-cte',
  templateUrl: './fields-cta-cte.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FieldsCtaCteComponent extends ComponentPage implements OnInit, AfterViewInit, Fields {

  @Input()
  public dataForm: IDataForm = { submitted: false };
  @Input()
  public formCtaCte: FormGroup;
  @Input()
  public ctaCte: CtaCte;
  @Input()
  public cliente: Cliente;

  constructor(
    private formBuilder: FormBuilder,
  ) {
    super();
  }

  ngOnInit() {
    this.formCtaCte = this.formBuilder.group({
      id: [this.ctaCte ? this.ctaCte.id : null, []],
      activo: [this.ctaCte ? this.ctaCte.activo : true, [Validators.required]],
      limite: [this.ctaCte ? this.ctaCte.limite : 0, [Validators.required, Validators.max(9999999), Validators.min(1)]],
      descripcion: [this.ctaCte ? this.ctaCte.descripcion : null, []],
    });
  }
  ngAfterViewInit(): void {
    this.initFocus();
  }
  getEntidadString(entidad: Entidad) {
    if (entidad.tipo == 'PERSONA') {
      return entidad.persona.apellido + ' ' + entidad.persona.nombre;
    }
    if (entidad.tipo == 'EMPRESA') {
      return entidad.empresa.razonSocial;
    }
  }
  reset() {
    this.formCtaCte.patchValue({
    });
    this.dataForm = { ...this.dataForm, submitted: false };
    this.initFocus();
  }
  initFocus() {
    setTimeout(() => {
      $("#cmbEstadoCtaCte").focus();
    });
  }
  get f() {
    return this.formCtaCte.controls;
  }
}

