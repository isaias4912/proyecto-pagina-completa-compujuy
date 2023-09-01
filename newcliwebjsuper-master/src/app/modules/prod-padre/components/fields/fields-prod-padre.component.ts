import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ComponentPage } from 'src/app/core/component-page';
// import * as Editor from 'src/ckeditor5/ckeditor'; // segun el tuto hay que ponerlo en src
import { IDataForm } from 'src/app/core/models/idata-form';
import { ProductoPadre } from 'src/app/core/models/producto-padre';
import { Fields } from 'src/app/core/fields';

declare var $: any;

@Component({
  selector: 'fields-prod-padre',
  templateUrl: './fields-prod-padre.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FieldsProdPadreComponent extends ComponentPage implements OnInit, AfterViewInit, Fields {

  @Input()
  public dataForm: IDataForm = { submitted: false };
  @Input()
  public prodPadre: ProductoPadre;
  // public Editor = Editor;
  public formProdPadre: FormGroup;

  constructor(
    private formBuilder: FormBuilder
  ) {
    super();
  }

  ngOnInit() {
    this.formProdPadre = this.formBuilder.group({
      id: [this.prodPadre ? this.prodPadre.id : null, []],
      nombre: [this.prodPadre ? this.prodPadre.nombre : null, [Validators.required, Validators.maxLength(30)]],
      nombreCorto: [this.prodPadre ? this.prodPadre.nombreCorto : null, [Validators.required, Validators.maxLength(20), Validators.minLength(2)]],
      familia: [this.prodPadre ? this.prodPadre.familia : null, [Validators.required]],
      detalle: [this.prodPadre ? this.prodPadre.detalle : null, [Validators.maxLength(500)]],
    });
  }
  ngAfterViewInit(): void {
    this.initFocus();
  }
  reset() {
    this.formProdPadre.patchValue({
      id: null,
      nombre: null,
      nombreCorto: null,
      detalle: null,
      familia: null
    });
    this.dataForm = { submitted: false };
    this.initFocus();
  }
  initFocus() {
    setTimeout(() => {
      $("#txtNombreProdPadre").focus();
    });
  }
  get f() {
    return this.formProdPadre.controls;
  }
}

