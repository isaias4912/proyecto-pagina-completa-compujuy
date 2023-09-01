import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy } from '@angular/core';
import { ComponentPage } from '../../../../../core/component-page';
import { IDataForm } from '../../../../../core/models/idata-form';
import { ProductosHTTPService } from '../../../../../core/services/http/product-http.service';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';
import { DateComponent } from 'src/app/modules/shared/components/date/date.component';
import { AuthService } from 'src/app/core/services/auth/auth.service';
import { SelectProdComponent } from '../../../modules/shared-prod/components/select/select-prod.component';


@Component({
  selector: 'out-stock',
  templateUrl: './out-stock.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class OutStockComponent extends ComponentPage implements OnInit, AfterViewInit {
  public sucursales: any;
  @ViewChild(SelectProdComponent, { static: false })
  private selectProdComponent: SelectProdComponent;
  @ViewChild('fechaIngresoForm', { static: false }) fechaIngreso: DateComponent;
  public dataForm: IDataForm = { submitted: false };
  public control: any = {
    tipo: 1
  }
  public formEgreso: FormGroup;

  constructor(
    private productosHTTPService: ProductosHTTPService,
    private authService: AuthService,
    private formBuilder: FormBuilder,
  ) {
    super();
  }

  ngOnInit() {
    this.sucursales = this.authService.getSucursales();
    this.formEgreso = this.formBuilder.group({
      fechaIngreso: [null, [Validators.required]],
      descripcion: [null, [Validators.maxLength(200)]],
      numero: [null, [Validators.required, Validators.maxLength(200)]],
      proveedor: [null, []],
      subtipo: [null, [Validators.required]],
      sucursal: [null, [Validators.required]],
      tipo: [3, []],
      movimientos: new FormArray([])
    });
    this.mo.push(this.formBuilder.group({
      producto: [null, [Validators.required]],
      cantidad: [null, [Validators.required]],
      tipo: [2, []],
    }));
  }

  reset() {
    this.formEgreso.patchValue({
      fechaIngreso: null,
      descripcion: null,
      numero: null,
      proveedor: null,
      subtipo: null,
      sucursal: null,
      tipo: 3
    });
    this.mo.controls[0].patchValue({
      producto: null,
      cantidad: null,
      tipo: 2,
    });
    this.selectProdComponent.resetData();
    setTimeout(() => {
      this.fechaIngreso.setToday();
      $("#txtTipoMovEgreso").focus()
    });
    this.dataForm = {...this.dataForm, submitted: false };
  }
  ngAfterViewInit(): void {
    setTimeout(() => {
      this.fechaIngreso.setToday();
      $("#txtTipoMovEgreso").focus()
    });
  }
  get ff() { return this.formEgreso.controls; }
  get mo() { return this.ff.movimientos as FormArray; }

  saveEgreso(option = 0) {
    this.dataForm = { ...this.dataForm, submitted: true }
    if (this.formEgreso.valid) {
      let data = this.formEgreso.value;
      this.productosHTTPService.removeExistenciasStock(data).subscribe(resp => {
        this.messageToast.success('Se guardo correctamente el movimiento sobre el producto ' + data.movimientos[0].producto.nombreFinal);
        if (option == 1) {
          this.reset();
        }
        if (option == 2) {
          this.location.back();
        }
      });
    }
  }

}

