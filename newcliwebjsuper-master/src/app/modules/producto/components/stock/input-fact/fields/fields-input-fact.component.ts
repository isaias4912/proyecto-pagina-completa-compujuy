import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, Input, OnDestroy, ChangeDetectorRef, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';
import { AuthService } from 'src/app/core/services/auth/auth.service';
import { DateComponent } from 'src/app/modules/shared/components/date/date.component';
import { IDataForm } from 'src/app/core/models/idata-form';
import { EncMovimiento } from 'src/app/core/models/acc-enc-mov';
import { Subscription } from 'rxjs';
import { SelectFactCompComponent } from 'src/app/modules/compra/modules/factura-comp/components/select/select-fact-comp.component';
import { CompraHTTPService } from 'src/app/core/services/http/compra-http.service';
import { ActivatedRoute } from '@angular/router';
import { tap, pluck } from 'rxjs/operators';
import { UtilPage } from 'src/app/core/util-page';

declare var $: any;

@Component({
  selector: 'fields-input-fact',
  templateUrl: './fields-input-fact.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class FieldsInputFactComponent extends UtilPage implements OnInit, AfterViewInit, OnDestroy {

  public formFactura: FormGroup;
  public sucursales: any;
  @Input()
  public dataForm: IDataForm = { submitted: false };
  public dataFacturaAddStock: EncMovimiento;
  @ViewChild(SelectFactCompComponent, { static: false })
  private selectInvoiceComponent: SelectFactCompComponent;
  private subsFactura: Subscription;
  private invoice: any = null;
  private id: string = null;
  @Output()
  activeEvent = new EventEmitter<null>();
  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private compraHTTPService: CompraHTTPService,
    private cdr: ChangeDetectorRef,
    private activatedRoute: ActivatedRoute
  ) {
    super();
  }

  ngOnInit() {
    this.activatedRoute.queryParams.subscribe(resp => {
      if (resp.factura) {
        this.id = this.decrypt(resp.factura);
        if (this.id) {
          this.compraHTTPService.getFactura(this.id).pipe(pluck('data')).subscribe(resp => {
            // this.formFactura.patchValue({ $$factura: resp })
            // this.setFactura(resp);
            this.selectInvoiceComponent.setData(resp);
            this.activeEvent.emit();
          });
        }
      }
    });
    this.sucursales = this.authService.getSucursales();
    this.formFactura = this.formBuilder.group({
      id: [null, []],
      completa: [null, []],
      fechaFactura: [null, [Validators.maxLength(200)]],
      idProveedor: [null, []],
      idSucursal: [null, []],
      numero: [null, []],
      proveedor: [null, []],
      subtotal: [null, []],
      tipoFactura: [null, []],
      total: [null, []],
      totalAdicionales: [null, []],
      totalDescuentos: [null, []],
      totalImpuestos: [null, []],
      $$factura: [null, [Validators.required]],
      items: new FormArray([])
    });
  }

  ngAfterViewInit(): void {
    this.reset();
    this.subsFactura = this.formFactura.get('$$factura').valueChanges.subscribe(resp => {
      this.setFactura(resp);
    });
  }

  get ff() { return this.formFactura.controls; }
  get it() { return this.ff.items as FormArray; }
  reset() {
    this.selectInvoiceComponent.resetData();
    this.it.clear();
    this.formFactura.reset();
    this.invoice = null;
    this.dataFacturaAddStock = null;
    this.dataForm.submitted = false;
    setTimeout(() => {
      $("#txtProducto").focus()
    });
    this.cdr.markForCheck();
  }

  public getFormFactura() {
    return this.formFactura;
  }
  public setFactura(resp: any) {
    this.invoice = resp;
    if (resp) {
      this.formFactura.patchValue(resp)
      this.getPreviewAddStock(resp.id);
    } else {
      this.it.clear();
      this.dataFacturaAddStock = null;
    }
  }
  public getFactura() {
    let factura: any = {};
    factura = this.formFactura.value;
    return factura;
  }

  getPreviewAddStock(id) {
    this.compraHTTPService.getPreviewAddStock(id).subscribe((response) => {
      this.dataFacturaAddStock = response.data;
      this.setDataItem(this.dataFacturaAddStock.items)
    });
  }
  public renewItems() {
    this.it.clear();
    this.getPreviewAddStock(this.invoice.id);
    this.cdr.markForCheck();
  }
  setDataItem(data: Array<any>) {
    data.forEach((item: any) => {
      this.it.push(this.formBuilder.group({
        cantidad: [{ value: item.maximo, disabled: item.completa }, [Validators.required, Validators.min(item.minimo), Validators.max(item.maximo)]],
        cantidadAgregada: [item.cantidadAgregada, []],
        cantidadComprada: [item.cantidadComprada, []],
        completa: [item.completa, []],
        id: [item.id, []],
        idProducto: [item.idProducto, []],
        iva: [item.iva, []],
        maximo: [item.maximo, []],
        minimo: [item.minimo, []],
        nombreProducto: [item.nombreProducto, []],
        precioConIva: [item.precioConIva, []],
        precioSinIva: [item.precioSinIva, []],
        subtotal: [item.subtotal, []],
      }));
    });
    if (this.dataFacturaAddStock.items.length) {
      setTimeout(() => {
        $('#cantidadItem0').focus();
      });
    }
    this.cdr.markForCheck();
  }

  ngOnDestroy(): void {
    if (this.subsFactura) {
      this.subsFactura.unsubscribe;
    }
  }

  // getProducto(id: string) {
  //   this.productosHTTPService.getProductoSubMinById(id).subscribe((response) => {
  //     this.selectProdComponent.setData(response.data);
  //   });
  // }
}

