import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray, FormControl } from '@angular/forms';
import { AuthService } from 'src/app/core/services/auth/auth.service';
import { DateComponent } from 'src/app/modules/shared/components/date/date.component';
import { IDataForm } from 'src/app/core/models/idata-form';
import { ProductosHTTPService } from 'src/app/core/services/http/product-http.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { SelectProdComponent } from 'src/app/modules/producto/modules/shared-prod/components/select/select-prod.component';
import { UtilPage } from 'src/app/core/util-page';
import * as moment from 'moment';

declare var $: any;

@Component({
  selector: 'fields-input-prod',
  templateUrl: './fields-input-prod.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class FieldsInputProdComponent extends UtilPage implements OnInit, AfterViewInit {

  public formFactura: FormGroup;
  public sucursales: any;
  @ViewChild('fechaIngresoForm', { static: false }) fechaIngreso: DateComponent;
  @Input()
  public dataForm: IDataForm = { submitted: false };
  @ViewChild(SelectProdComponent, { static: false })
  private selectProdComponent: SelectProdComponent;
  private idProducto: string;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private productosHTTPService: ProductosHTTPService,
    private activatedRoute: ActivatedRoute,

  ) {
    super();
  }

  ngOnInit() {
    this.sucursales = this.authService.getSucursales();
    this.formFactura = this.formBuilder.group({
      fechaIngreso: [null, [Validators.required, this.dateValidator('DD-MM-YYYY')]],
      descripcion: [null, [Validators.maxLength(200)]],
      numero: [null, [Validators.required, Validators.maxLength(200)]],
      proveedor: [null, []],
      subtipo: [1, []],
      sucursal: [null, [Validators.required]],
      tipo: [2, []],
      movimientos: new FormArray([])
    });
    this.mo.push(this.formBuilder.group({
      producto: [null, [Validators.required]],
      cantidad: [null, [Validators.required]],
      tipo: [1, []],
    }));
    this.activatedRoute.paramMap.subscribe(
      (params: ParamMap) => {
        this.idProducto = this.decrypt(params.get("idProducto"));
        if (this.idProducto) {
          this.getProducto(this.idProducto);
        }
      });
  }

  ngAfterViewInit(): void {
    this.reset();
  }

  get ff() { return this.formFactura.controls; }
  get mo() { return this.ff.movimientos as FormArray; }
  resetForm() {
    this.formFactura.patchValue({
      fechaIngreso: null,
      descripcion: null,
      numero: null,
      proveedor: null,
      subtipo: 1,
      sucursal: null,
      tipo: 2,
    });
    this.mo.controls[0].patchValue({
      producto: null,
      cantidad: null,
      tipo: 1,
    });
  }
  reset() {
    this.selectProdComponent.resetData();
    this.resetForm();
    this.fechaIngreso.setToday();
    this.dataForm.submitted = false;
    setTimeout(() => {
      $("#txtProducto").focus()
    });
  }

  public getFormFactura() {
    return this.formFactura;
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
  public getFactura() {
    let factura: any = {};
    factura = this.formFactura.value;
    return factura;
  }
  getProducto(id: string) {
    this.productosHTTPService.getProductoSubMinById(id).subscribe((response) => {
      // this.selectProdComponent.setData(response.data); // con el patch value deberia setear ya el componente
      this.mo.controls[0].patchValue({ producto: response.data });
      setTimeout(() => {
        $("#txtCantidadMov").focus()
      });
    });
  }
}

