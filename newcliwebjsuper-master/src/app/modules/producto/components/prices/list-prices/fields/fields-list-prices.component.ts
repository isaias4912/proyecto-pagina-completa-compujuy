import { Component, OnInit, AfterViewInit,  ChangeDetectionStrategy, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IDataForm } from '../../../../../../core/models/idata-form';

declare var $: any;

@Component({
  selector: 'fields-list-prices',
  templateUrl: './fields-list-prices.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FieldsListPricesComponent implements OnInit, AfterViewInit {
  public formLista: FormGroup;
  @Input()
  public dataForm: IDataForm = { submitted: false };
  @Input()
  public lista: any =null;
  public dataOptions = {
    optionsActivo: [{ value: true, name: "Activo" }, { value: false, name: "Inactivo" }]
  };
  constructor(
    private formBuilder: FormBuilder,
  ) {
  }

  ngOnInit() {
    this.formLista = this.formBuilder.group({
      activo: [this.lista?this.lista.activo:null, [Validators.required]],
      detalle: [this.lista?this.lista.detalle:null, []],
      nombre: [this.lista?this.lista.nombre:null, [Validators.required]],
      tipo: [this.lista?this.lista.tipo:1, [Validators.required]],
      valor: [this.lista?this.lista.valor:null, [Validators.required, Validators.min(-100)]],
    });
  }
  ngAfterViewInit(): void {
    this.initFocus();
  }
  reset() {
    this.formLista.patchValue({
      activo: true,
      detalle: "",
      nombre: "",
      tipo: 1,
      valor: null
    });
    this.dataForm.submitted = false;
    this.initFocus();
  }
  initFocus(){
    setTimeout(() => {
      $("#txtNombre").focus();
    });
  }
  get f() {
    return this.formLista.controls;
  }


}

