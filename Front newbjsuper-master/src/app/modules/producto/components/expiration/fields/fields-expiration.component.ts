import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IDataForm } from '../../../../../core/models/idata-form';
import { DateComponent } from '../../../../shared/components/date/date.component';
import { SelectFactCompProdComponent } from '../../../../compra/modules/factura-comp/components/select-prod/select-fact-comp-prod.component';
import { SelectProdComponent } from '../../../modules/shared-prod/components/select/select-prod.component';

declare var $: any;

@Component({
  selector: 'fields-expiration',
  templateUrl: './fields-expiration.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FieldsExpirationComponent implements OnInit, AfterViewInit {
  public formExpiration: FormGroup;
  @Input()
  public dataForm: IDataForm = { submitted: false };
  @Input()
  public expiration: any = null;
  @Input()
  public lastFocus: string = 'default';
  @ViewChild(SelectProdComponent, { static: false })
  selectProdComponent: SelectProdComponent;
  @ViewChild(SelectFactCompProdComponent, { static: false })
  selectInvoiceProdComponent: SelectFactCompProdComponent;
  @ViewChild(DateComponent, { static: false })
  dateComponent: DateComponent;
  public maxCantidad = 99999999;
  public dataOptions = {
    optionsActivo: [{ value: true, name: "Activo" }, { value: false, name: "Inactivo" }]
  };
  constructor(
    private formBuilder: FormBuilder,
  ) {
  }

  ngOnInit() {
    this.formExpiration = this.formBuilder.group({
      fechaVencimiento: [this.expiration ? this.expiration.fechaVencimiento : null, [Validators.required]],
      tipo: [this.expiration ? this.expiration.tipo : 1, [Validators.required]],
      lote: [this.expiration ? this.expiration.lote : null, []],
      alertaVencimientos: [this.expiration ? this.expiration.alertaVencimientos : false, [Validators.required]],
      diasAlerta: [this.expiration ? this.expiration.diasAlerta : 15, [Validators.required]],
      descripcion: [this.expiration ? this.expiration.descripcion : null, []],
      cantidadProductos: [this.expiration ? this.expiration.cantidadProductos : 0, [Validators.required, Validators.max(999999999), Validators.min(0.001)]],
      producto: [this.expiration ? this.expiration.producto : null, [Validators.required]],
      facturaDet: [this.expiration ? this.expiration.facturaDet : null, []],
    });
    this.formExpiration.get('facturaDet').valueChanges.subscribe(resp => {
      if (resp) {
        this.maxCantidad = resp.cantidad;
        this.formExpiration.patchValue({ cantidadProductos: resp.cantidad });
        this.formExpiration.get('cantidadProductos').setValidators([Validators.required, Validators.min(0.001), Validators.max(resp.cantidad)]);
      }
    });
    this.formExpiration.get('producto').valueChanges.subscribe(resp => {
      this.maxCantidad = 999999999;
      this.formExpiration.get('cantidadProductos').setValidators([Validators.required, Validators.min(0.001), Validators.max(999999999)]);
    });
  }
  ngAfterViewInit(): void {
    this.initFocus();
  }
  changeTipo() {
    if (this.formExpiration.get('tipo').value == 1) {
      this.formExpiration.get('facturaDet').clearValidators();
      this.formExpiration.get('producto').setValidators([Validators.required]);
    }
    if (this.formExpiration.get('tipo').value == 2) {
      this.formExpiration.get('producto').clearValidators();
      this.formExpiration.get('facturaDet').setValidators([Validators.required]);
    }
    this.formExpiration.get('producto').updateValueAndValidity();
    this.formExpiration.get('facturaDet').updateValueAndValidity();
  }
  reset() {
    this.formExpiration.patchValue({
      fechaVencimiento: null,
      tipo: 1,
      lote: "",
      alertaVencimientos: false,
      diasAlerta: 15,
      descripcion: "",
      cantidadProductos: 0,
      producto: null,
      facturaDet: null
    });
    this.dataForm.submitted = false;
    this.selectInvoiceProdComponent.resetData();
    this.selectProdComponent.resetData();
    this.dateComponent.setToday();
    // this.
    this.initFocus();
  }
  initFocus() {
    setTimeout(() => {
      $("#cmbTipo").focus();
    });
  }
  get f() {
    return this.formExpiration.controls;
  }


}

