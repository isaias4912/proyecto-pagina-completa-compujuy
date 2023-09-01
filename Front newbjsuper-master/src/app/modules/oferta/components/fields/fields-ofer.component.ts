import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input, ViewChild, ElementRef, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { UtilPage } from '../../../../core/util-page';
import { IDataForm } from '../../../../core/models/idata-form';
import { Fields } from '../../../../core/fields';
import { Oferta } from 'src/app/core/models/oferta';
import * as moment from 'moment';
import { InputMaskComponent } from 'src/app/modules/shared/components/input-mask/input-mask.component';
declare var $: any;

@Component({
  selector: 'fields-ofer',
  templateUrl: './fields-ofer.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FieldsOferComponent extends UtilPage implements OnInit, AfterViewInit, Fields {

  @Input()
  public dataForm: IDataForm = { submitted: false };
  @Input()
  public formOferta: FormGroup;
  @Input()
  public oferta: Oferta;
  private msgHelpValor: string = "";
  fechaHastaOut: InputMaskComponent;

  constructor(
    private formBuilder: FormBuilder,
    private cdr: ChangeDetectorRef
  ) {
    super();
  }

  ngOnInit() {
    this.formOferta = this.formBuilder.group({
      nombre: [this.oferta ? this.oferta.nombre : null, [Validators.required, Validators.minLength(3), Validators.maxLength(45)]],
      activo: [this.oferta ? this.oferta.activo : true, [Validators.required]],
      fechaDesde: [this.oferta ? this.oferta.fechaDesde : moment(new Date()).format("DD-MM-YYYY HH:mm"), [Validators.required, this.dateValidator('DD-MM-YYYY HH:mm'), this.dateValidatorNow()]],
      fechaHasta: [this.oferta ? this.oferta.fechaHasta : moment(new Date()).add(1, 'days').format("DD-MM-YYYY HH:mm"), [Validators.required, this.dateValidator('DD-MM-YYYY HH:mm')]],
      categoriaOferta: [this.oferta ? this.oferta.categoriaOferta : null, [Validators.required]],
      tipoOferta: [this.oferta ? this.oferta.tipoOferta : null, [Validators.required]],
      cantidad: [this.oferta ? this.oferta.cantidad : null, []],
      tipoOfertaTipo: [this.oferta ? this.oferta.tipoOfertaTipo : null, []],
      tipoDescuento: [this.oferta ? this.oferta.tipoDescuento : null, [Validators.required]],
      valor: [this.oferta ? this.oferta.valor : null, []],
      prioridad: [this.oferta ? this.oferta.prioridad : null, [Validators.required, Validators.min(1), Validators.max(9999)]],
      detalle: [this.oferta ? this.oferta.detalle : null, []],
    }, { validators: [this.dateValidatorDesde()] });

  }
  ngAfterViewInit(): void {
    this.initFocus();
    this.loadComponents();
    this.loadHelp();
  }
  reset() {
    this.formOferta.patchValue({
      nombre: null,
      activo: true,
      fechaDesde: moment(new Date()).format("DD-MM-YYYY HH:mm"),
      fechaHasta: moment(new Date()).add(1, 'days').format("DD-MM-YYYY HH:mm"),
      categoriaOferta: null,
      tipoOferta: null,
      cantidad: null,
      tipoOfertaTipo: null,
      tipoDescuento: null,
      valor: null,
      prioridad: null,
      detalle: null
    });
    this.dataForm = { ...this.dataForm, submitted: false };
    this.cdr.markForCheck();
    this.initFocus();
  }
  initFocus() {
    setTimeout(() => {
      $("#txtNombre").focus();
    });
  }
  loadComponents() {
  }
  get f() {
    return this.formOferta.controls;
  }
  changeTipo() {
    if (this.formOferta.get('categoriaOferta').value == "POR_CADA_PRODUCTO") {
      if (this.formOferta.get('tipoOferta').value == "POR_CADA_PRODUCTO") {
        if (this.formOferta.get('tipoDescuento').value == "PORCENTAJE") {
          this.msgHelpValor = "Por cada producto se realiza un descuento del porcentaje que ingrese al total de lo que cueste el producto.";
        }
        if (this.formOferta.get('tipoDescuento').value == "CANTIDAD") {
          this.msgHelpValor = "Por cada producto se realiza un descuento de la cantidad en dinero que ingrese.";
        }
      }
      if (this.formOferta.get('tipoOferta').value == "CADA_N_PRODUCTO") {
        if (this.formOferta.get('tipoOfertaTipo').value == "POR_CADA_PRODUCTO") {
          if (this.formOferta.get('tipoDescuento').value == "PORCENTAJE") {
            this.msgHelpValor = "Cada N productos que se compre, se descontara el porcentaje que ingrese a cada producto comprendido.";
          }
          if (this.formOferta.get('tipoDescuento').value == "CANTIDAD") {
            this.msgHelpValor = "Cada N productos que se compre, se descontara la cantidad en dinero que ingrese a cada producto comprendido.";
          }
        }
        if (this.formOferta.get('tipoOfertaTipo').value == "POR_TOTALIDAD") {
          if (this.formOferta.get('tipoDescuento').value == "PORCENTAJE") {
            this.msgHelpValor = "Cada N productos que se compre, se descontara el porcentaje que ingrese a la totalidad de lo que cueste los N productos.";
          }
          if (this.formOferta.get('tipoDescuento').value == "CANTIDAD") {
            this.msgHelpValor = "Cada N productos que se compre, se descontara la cantidad en dinero que ingrese a la totalidad de lo que cuesten los N productos.";
          }
        }
      }
    }
    this.setValidators();
    setTimeout(() => {
      this.loadHelp();
    });
  };

  setValidators() {
    if (this.formOferta.get('tipoOferta').value == 'POR_CADA_PRODUCTO') {
      this.formOferta.get('tipoOfertaTipo').setValidators([]);
      this.formOferta.get('cantidad').setValidators([]);
      this.formOferta.get('tipoOfertaTipo').updateValueAndValidity();
      this.formOferta.get('cantidad').updateValueAndValidity();
    }
    if (this.formOferta.get('tipoOferta').value == 'CADA_N_PRODUCTO') {
      this.formOferta.get('tipoOfertaTipo').setValidators([Validators.required]);
      this.formOferta.get('cantidad').setValidators([Validators.required, Validators.min(0.01), Validators.max(99999)]);
      this.formOferta.get('tipoOfertaTipo').updateValueAndValidity();
      this.formOferta.get('cantidad').updateValueAndValidity();

    }
    if (this.formOferta.get('tipoOferta').value == 'MAS_DE_N_PRODUCTO') {
      this.formOferta.get('tipoOfertaTipo').setValidators([Validators.required]);
      this.formOferta.get('cantidad').setValidators([Validators.required, Validators.min(0.01), Validators.max(99999)]);
      this.formOferta.get('tipoOfertaTipo').updateValueAndValidity();
      this.formOferta.get('cantidad').updateValueAndValidity();

    }
    if (this.formOferta.get('tipoDescuento').value == 'PORCENTAJE') {
      this.formOferta.get('valor').setValidators([Validators.required, Validators.min(0.01), Validators.max(9999)]);
      this.formOferta.get('valor').updateValueAndValidity();

    }
    if (this.formOferta.get('tipoDescuento').value == 'CANTIDAD') {
      this.formOferta.get('valor').setValidators([Validators.required, Validators.min(0.01), Validators.max(9999)]);
      this.formOferta.get('valor').updateValueAndValidity();
    }
    if (this.formOferta.get('tipoDescuento').value == 'VALOR') {
      this.formOferta.get('valor').setValidators([]);
      this.formOferta.get('valor').updateValueAndValidity();
    }
    this.formOferta.updateValueAndValidity();
  }
  dateValidator(format = "DD/MM/YYYY"): any {
    return (control: FormControl): { [key: string]: any } => {
      const val = moment(control.value, format, true);
      if (!val.isValid()) {
        return { invalidDate: true };
      }
      return null;
    };
  }
  dateValidatorNow(): any {
    return (control: FormControl): { [key: string]: any } => {
      let fechaSelDesde = moment(control.value, "DD-MM-YYYY HH:mm", true);
      let fechaActual = moment();
      if (fechaSelDesde.isSameOrAfter(fechaActual, "day")) {
        return null;
      }
      return { invalidDateNow: true };
    };
  }
  dateValidatorDesde(): any {
    return (control: FormGroup): { [key: string]: any } => {
      let fechaSelDesde = moment(control.get('fechaDesde').value, "DD-MM-YYYY HH:mm", true);
      let fechaSelHasta = moment(control.get('fechaHasta').value, "DD-MM-YYYY HH:mm", true);
      if (fechaSelHasta.isAfter(fechaSelDesde)) {
        return null;
      }
      return { invalidDateDesde: true };
    };
  }
}

