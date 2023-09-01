import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { IDataForm } from 'src/app/core/models/idata-form';
import { ProductosHTTPService } from '../../../../../../core/services/http/product-http.service';
import { ComponentPage } from 'src/app/core/component-page';
import { SelectSucursalSimpleComponent } from 'src/app/modules/shared/components/sucursal/select-simple/select-scursal-simple.component';
import { SelectProdComponent } from 'src/app/modules/producto/modules/shared-prod/components/select/select-prod.component';

declare var $: any;

@Component({
  selector: 'new-pase',
  templateUrl: './new-pase.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class StockNewPaseComponent extends ComponentPage implements OnInit, AfterViewInit, OnDestroy {

  public formPase: FormGroup;
  public dataForm: IDataForm = { submitted: false };
  @ViewChild(SelectProdComponent, { static: false })
  private selectProdComponent: SelectProdComponent;
  @ViewChild("sucursalOrigenForm", { static: false })
  selectSucursalOrigSimpleComponent: SelectSucursalSimpleComponent;
  @ViewChild("sucursalDestinoForm", { static: false })
  selectSucursalDestComponent: SelectSucursalSimpleComponent;
  constructor(
    private formBuilder: FormBuilder,
    private productosHTTPService: ProductosHTTPService
  ) {
    super();
  }

  ngOnInit() {
    this.formPase = this.formBuilder.group({
      producto: [null, [Validators.required]],
      cantidad: [null, [Validators.required]],
      sucursalOrigen: [null, [Validators.required]],
      sucursalDestino: [null, [Validators.required]],
      descripcion: [null, [Validators.maxLength(200)]],
    }, { validator: this.validateSucursal });
  }
  ngAfterViewInit(): void {
    this.reset();
  }
  reset() {
    this.formPase.patchValue({
      producto: null,
      cantidad: null,
      sucursalOrigen: null,
      sucursalDestino: null,
      descripcion: null,
    });
    setTimeout(() => {
      this.selectProdComponent.resetData();
      this.selectSucursalOrigSimpleComponent.resetData();
      this.selectSucursalOrigSimpleComponent.resetData();
      $("#txtProducto").focus();
    });
  }
  get fp() {
    return this.formPase.controls;
  }

  savePase(option: number = 0) {
    this.dataForm = { ...this.dataForm, submitted: true }
    if (this.formPase.valid) {
      this.productosHTTPService.addExistenciasStockPase(this.formPase.value).subscribe(resp => {
        this.messageToast.success("Se agrego el pase correctamente.");
        if (option == 1) {
          this.reset();
        }
        if (option == 2) {
          this.location.back();
        }
      });
    }
  }
  ngOnDestroy(): void {

  }

  validateSucursal(c: AbstractControl): { invalid: boolean } {
    if (c.get('sucursalOrigen').value == c.get('sucursalDestino').value) {
      return { invalid: true };
    }
  }

}

