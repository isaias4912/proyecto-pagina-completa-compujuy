import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, OnDestroy } from '@angular/core';
import { ComponentPage } from '../../../../../core/component-page';
import { IDataFormProducto } from '../../../models/idata-form-producto';
import { Observable, Subscription } from 'rxjs';
import { Producto } from '../../../../../core/models/producto';
import { ProductosHTTPService } from '../../../../../core/services/http/product-http.service';
import { Response } from '../../../../../core/services/utils/response';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { SelectProdComponent } from '../../../modules/shared-prod/components/select/select-prod.component';
import { IDataForm } from 'src/app/core/models/idata-form';
declare var $: any;

@Component({
  selector: 'update-price',
  templateUrl: './update-price.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UpdatePriceComponent extends ComponentPage implements OnInit, AfterViewInit, OnDestroy {
  public dataForm: IDataForm = { submitted: false };
  private dataNewUpdate$: Observable<Producto[]>;
  public producto$: Observable<Response<Producto>>;
  public idProducto: string;
  public producto: Producto;
  public productoRel: Producto;
  public formPriceProd: FormGroup;
  public formPriceProdRel: FormGroup;
  private subValueChange: Subscription;
  public dataParentChild: any = null;
  @ViewChild(SelectProdComponent, { static: false })
  private selectProdComponent: SelectProdComponent;
  public data: any = {
    title: "Selección del producto"
  }
  constructor(
    private activatedRoute: ActivatedRoute,
    private productosHTTPService: ProductosHTTPService,
    private formBuilder: FormBuilder,
    private cdr: ChangeDetectorRef

  ) {
    super();
  }

  ngOnInit() {
    this.formPriceProd = this.formBuilder.group({
      producto: [null, [Validators.required]],
      precioCosto: ['', [Validators.required]],
      precioVenta: ['', [Validators.required]],
      precioVentaInicial: ['', [Validators.required]],
      precioCostoInicial: ['', [Validators.required]]
    });
    this.formPriceProdRel = this.formBuilder.group({
      producto: [null, [Validators.required]],
      precioCosto: ['', [Validators.required]],
      precioVenta: ['', [Validators.required]],
      precioVentaInicial: ['', [Validators.required]],
      precioCostoInicial: ['', [Validators.required]]
    });
    this.activatedRoute.paramMap.subscribe(
      (params: ParamMap) => {
        this.idProducto = this.decrypt(params.get("idProducto"));
        if (this.idProducto) {
          this.getProducto(this.idProducto);
        }
      });
    this.subValueChange = this.formPriceProd.get('producto').valueChanges.subscribe(item => {
      this.setProducto(item);
    })
  }
  ngAfterViewInit(): void {
    setTimeout(() => {
      $('#txtProducto').focus();
    });
    this.loadHelp();
  }
  getProducto(id: string) {
    this.productosHTTPService.getProductoSubMinById(id).subscribe((response) => {
      this.formPriceProd.patchValue({ producto: response.data });
      setTimeout(() => {
        $('#txtPrecioCosto').focus();
      });
    });
  }
  setProducto(item) {
    this.producto = item;
    if (this.producto) {
      this.data.title = "Producto seleccionado";
      this.formPriceProd.patchValue({ precioCosto: this.producto.precioCosto });
      this.formPriceProd.patchValue({ precioVenta: this.producto.precioVenta });
      this.formPriceProd.patchValue({ precioVentaInicial: this.producto.precioVenta });
      this.formPriceProd.patchValue({ precioCostoInicial: this.producto.precioCosto });
      this.getParentsAndChilds(this.producto.id);
      setTimeout(() => {
        this.loadHelp();
      });
    } else {
      this.productoRel = null;
      this.data.title = "Selección del producto";
      this.dataParentChild = null;
      this.cdr.markForCheck();
    }
  }
  setProductoRel(item) {
    this.productoRel = item;
    if (this.productoRel) {
      this.formPriceProdRel.patchValue({ producto: this.productoRel });
      this.formPriceProdRel.patchValue({ precioCosto: this.productoRel.precioCosto });
      this.formPriceProdRel.patchValue({ precioVenta: this.productoRel.precioVenta });
      this.formPriceProdRel.patchValue({ precioVentaInicial: this.productoRel.precioVenta });
      this.formPriceProdRel.patchValue({ precioCostoInicial: this.productoRel.precioCosto });
    } else {
      this.formPriceProdRel.reset();
    }
    this.cdr.markForCheck();
  }
  selectParentOrChild(data: any) {
    this.productosHTTPService.getProductoSubMinById(data.id).subscribe((response) => {
      this.setProductoRel(response.data);
      // this.productoRel = response.data;
      // this.formPriceProdRel.patchValue({ producto: response.data });
      // this.formPriceProdRel.patchValue({ precioCosto: this.producto.precioCosto });
      // this.formPriceProdRel.patchValue({ precioVenta: this.producto.precioVenta });
      // this.formPriceProdRel.patchValue({ precioVentaInicial: this.producto.precioVenta });
      // this.formPriceProdRel.patchValue({ precioCostoInicial: this.producto.precioCosto });
      // this.cdr.markForCheck();
    });
  }
  getParentsAndChilds(idProducto) {
    this.productosHTTPService.getParentsAndChilds(idProducto).subscribe((response) => {
      this.dataParentChild = { ...response.data };
      this.cdr.markForCheck();
    });
  };

  savePorProducto(option = 0) {
    this.dataForm.submitted = true;
    if (this.formPriceProd.valid) {
      this.productosHTTPService.updatePrecioPorProducto(this.producto.id.toString(), this.formPriceProd.value).subscribe((response) => {
        this.messageToast.success('Precio del producto ' + this.producto.nombreFinal + 'modificado correctamente');
        if (option == 1) {
          this.dataForm.submitted = false;
          this.selectProdComponent.resetData();
        }
        if (option == 2) {
          this.location.back();
        }
      });
    }
  }
  savePorProductoRel() {
    if (this.formPriceProdRel.valid) {
      this.productosHTTPService.updatePrecioPorProducto(this.productoRel.id.toString(), this.formPriceProdRel.value).subscribe((response) => {
        this.messageToast.success('Precio del producto relacionado ' + this.productoRel.nombreFinal + 'modificado correctamente');
        this.setProductoRel(null);
      });
    }
  }
  cancelProdRel() {
    this.setProductoRel(null);
  }

  get f() {
    return this.formPriceProd.controls;
  }
  ngOnDestroy(): void {
    if (this.subValueChange) {
      this.subValueChange.unsubscribe();
    }
  }
}

