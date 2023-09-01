import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, ViewChildren, QueryList, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, Validators } from '@angular/forms';
import { ComponentPage } from '../../../../core/component-page';
import { IDataForm } from '../../../../core/models/idata-form';
import { Comprobante, TipoSearch } from '../../../../core/enums';
import { SelectProdComponent } from '../../../producto/modules/shared-prod/components/select/select-prod.component';
import { CompraHTTPService } from 'src/app/core/services/http/compra-http.service';
import { IItemCbte, ITotalCbte, ITributos } from '../../../../core/interfaces';
import { ComprobanteService } from '../../services/comprobante.service';
import { TributosComponent } from '../tributos/tributos.component';
import { ResizeService } from 'src/app/modules/shared/components/size-detector/resize.service';
import { SelectService } from 'src/app/modules/shared/services/select.service';
import { TributoEventService } from '../tributos/tributo-event.service';
declare var $: any;


@Component({
  selector: 'items-cbte',
  templateUrl: './items-cbte.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ItemsCbteComponent extends ComponentPage implements OnInit, AfterViewInit {

  public formItems: FormGroup;
  @Input()
  public dataForm: IDataForm;
  @Input()
  public enableIva: boolean = true;
  @ViewChildren('selectsProd')
  selects: QueryList<SelectProdComponent>;
  @ViewChild(TributosComponent, { static: false })
  tributosComponent: TributosComponent;
  @Input()
  public data: any;
  @Input()
  public typeCbte: 'COMPRA' | 'VENTA';
  @Input()
  public comprobante: Comprobante;
  @Output()
  eventChange = new EventEmitter<any>();
  public dataCbte: any = {};
  public dataView: any = { showTributo: true, showObservacion: true };
  public typeSearch: TipoSearch = TipoSearch.SEARCH_WITHOUT_FILTER;

  constructor(
    private formBuilder: FormBuilder,
    private cdr: ChangeDetectorRef,
    private compraHTTPService: CompraHTTPService,
    private comprobanteService: ComprobanteService,
    private resizeSvc: ResizeService,
    private selectService: SelectService,
    private tributoEventService: TributoEventService
  ) {
    super();
  }

  ngOnInit() {
    this.resetDataCbte();
    this.formItems = this.formBuilder.group({
      items: new FormArray([]),
    });
    // listeners
    this.comprobanteService.totalTributos$.subscribe((resp: number) => {
      this.dataCbte.totalTributos = resp;
      this.changeValueSubtotal(false);
    });
    this.comprobanteService.listaPrecio$.subscribe((resp: number) => {
      this.dataCbte.listaPrecio = resp;
      this.refreshPrecio();
    });
    // cambios en el size del window
    this.resizeSvc.onResize$
      .subscribe(x => {
        this.sizeWindow = x;
        this.changeSizes(x);
      });
    this.tributoEventService.data$.subscribe(data => {
      if (data == "emptyTributo") {
        this.dataView = { ...this.dataView, showTributo: true };
      }
    });

  }
  reset(initFocus = true) {
    this.resetDataCbte();
    if (!this.dataView.showObservacion) {
      this.tributosComponent.reset();
    }
    this.fi.clear();
    this.formItems.reset();
    this.formItems.updateValueAndValidity();
    this.addItem(0);
    this.cdr.markForCheck();
  }
  resetDataCbte() {
    this.dataCbte.totalAdicionales = 0;
    this.dataCbte.totalDescuentos = 0;
    this.dataCbte.totalImpuestos = 0;
    this.dataCbte.totalTributos = 0;
    this.dataCbte.totalIVAs = 0;
    this.dataCbte.totalBaseImp = 0;
    this.dataCbte.subtotal = 0;
    this.dataCbte.total = 0;
    this.dataCbte.ivaIncluido = true;
  }
  initValue(initFocus: boolean) {
    this.addItem(0, initFocus);
    if (this.data.presupuesto) {
      if (this.data.presupuesto.estadoCbte == "PRES_APROBADO") {
        this.setData(this.data.presupuesto);
      }
    }
    this.dataForm = { submitted: false };
  }
  setData(data: any) {
    for (let index = 0; index < data.detalleVentas.length; index++) {
      this.fi.controls[index].patchValue({
        $$producto: data.detalleVentas[index].producto,
        cantidad: data.detalleVentas[index].cantidad,
        porcentajeDescuento: data.detalleVentas[index].porcentajeDescuento,
        infoProdAdic: data.detalleVentas[index].infoProdAdic,
        $$showInfoAdic: ((data.detalleVentas[index].infoProdAdic && data.detalleVentas[index].infoProdAdic != null && data.detalleVentas[index].infoProdAdic != "") ? true : false)
      });
      this.changeValueCantidad(this.fi.controls[index] as FormGroup);
      if (index < (data.detalleVentas.length - 1)) {
        this.addItem();
      }
    }
    // this.refreshPrecio();
    // this.refreshCalc();
  }
  addItem(index = 0, initFocus = true) {
    this.dataForm.submitted = true;
    if (this.f.items.valid) {
      this.dataForm.submitted = false;
      let f: FormGroup = this.formBuilder.group({
        id: [0, [Validators.required]],
        $$producto: [null, [Validators.required]],
        producto: [null, [Validators.required]],
        precio: [null, [Validators.required]],
        precioCosto: [null, [Validators.required]],
        descripcion: [null, [Validators.required]],
        idProducto: [null, [Validators.required]],
        nombreProducto: [null, [Validators.required]],
        cantidad: [null, [Validators.required, Validators.min(0)]],
        precioSinIva: [null, [Validators.required]],
        descuento: [null, [Validators.required]],
        porcentajeDescuento: [null, []],
        baseImponible: [null, [Validators.required, Validators.min(0)]],
        iva: [null, [Validators.required]],
        ivaId: [null, [Validators.required]],
        ivaDes: [null, [Validators.required]],
        baseImponibleTotal: [null, [Validators.required, Validators.min(0)]],
        ivaMonto: [null, [Validators.required, Validators.min(0)]],
        ivaUnidad: [null, [Validators.required, Validators.min(0)]],
        ivaCalculado: [null, [Validators.required, Validators.min(0)]],
        precioConIva: [null, [Validators.required, Validators.min(0)]],
        ivaTotal: [null, [Validators.required, Validators.min(0)]],
        subtotal: [null, [Validators.required, Validators.min(0)]],
        infoProdAdic: [null, [Validators.minLength(5)]],
        $$showInfoAdic: [null, []],
        index: [index, []],
      });
      this.fi.push(f);
      f.get('$$producto').valueChanges.subscribe(resp => {
        if (this.validateProd(resp, f)) {
          this.selectProducto(f, resp)
        } else {
          this.resetValues(f);
          this.resetComponent(f);
        }
      });
      f.get('iva').valueChanges.subscribe(resp => {
        if (resp) {
          this.formItems.patchValue({ iva_id: resp.id, iva_des: resp.descripcion });
        } else {
          this.formItems.patchValue({ iva_id: null, iva_des: null });
        }
      });
      f.get('cantidad').valueChanges.subscribe(resp => {
      });
      if (initFocus) {
        setTimeout(() => {
          $("#txtProducto" + (this.fi.controls.length - 1)).focus();
        });
      }
      this.setSelectedIVA(21, f); // por defecto le mandamos el iva en 21
    }
  }
  selectProducto(fg: FormGroup, resp) {// cuando se selecciona un producto
    fg.patchValue({ producto: resp });
    fg.patchValue({ idProducto: resp.id });
    fg.patchValue({ nombreProducto: resp.nombreFinal });
    fg.patchValue({ descripcion: resp.nombreFinal });
    fg.updateValueAndValidity();
    // fg.patchValue({ precioConIva: resp.precioVenta });
    fg.patchValue({ precioConIva: this.getPrecioProducto(resp) });
    fg.patchValue({ precio: resp.precioVenta });
    fg.patchValue({ precioCosto: resp.precioCosto });
    fg.patchValue({ cantidad: 1 });
    fg.patchValue({ descuento: 0 });
    fg.patchValue({ porcentajeDescuento: 0 });
    this.setSelectedIVA(resp.iva, fg);
    fg.updateValueAndValidity();
    this.changeValuePriceConIVA(fg);

  };
  getPrecioProducto(producto: any) {
    let precio = 0;
    if (this.dataCbte.listaPrecio) {
      if (producto.precios && producto.precios.length > 0) {
        producto.precios.forEach(item => {
          if (item.id == this.dataCbte.listaPrecio) {
            precio = item.precio;
          }
        });
        return precio;
      }
    }
    return producto.precioVenta;
  }
  setSelectedIVA(montoIva, fg: FormGroup) {
    if (this.comprobante == Comprobante.FACTURA_C) {
      //si es un comprobante tipo c el iva siempre sera 0
      montoIva = 0;
    }
    this.data.ivas.forEach(iva => {
      // let reg = new RegExp("%");
      // let value = parseFloat((iva.descripcion.replace(reg, '')).trim());
      if (iva.value == montoIva) {
        fg.patchValue({ iva: iva });
        fg.patchValue({ ivaDes: iva.descripcion });
        fg.patchValue({ ivaId: iva.id });
      }
    });
  }
  getIVASelected(fg: FormGroup) {
    //verificamos si es comprobante c o monotributista, el iva es 0
    if (this.comprobante == Comprobante.FACTURA_C) {
      return 0;
    }
    // si el comrpbante es b no se discrimina el iva entonces es 0 tambien
    if (this.comprobante == Comprobante.FACTURA_B || this.comprobante == Comprobante.FACTURA_A) {
      let iva = 21;
      try {
        iva = fg.get('iva').value.value;
      } catch (e) {
        this.setSelectedIVA(21, fg)
      }
      return iva;
    }
    if (this.comprobante == Comprobante.FACTURA_x) {
      return 0;
    }
    return 0;
  }
  enabledIva() {
    this.enableIva = true;
    this.cdr.markForCheck();
  }
  disabledIva() {
    this.enableIva = false;
    this.cdr.markForCheck();
  }
  setComprobante(cbte: Comprobante) {
    this.comprobante = cbte;
    if (this.comprobante == Comprobante.FACTURA_A) {
      this.enabledIva();
      setTimeout(() => {
        this.loadHelp("label-help-iva");
      });
    }
    if (this.comprobante == Comprobante.FACTURA_B) {
      this.disabledIva();
    }
    if (this.comprobante == Comprobante.FACTURA_C) {
      this.disabledIva();
    }
    if (this.comprobante == Comprobante.FACTURA_x) {
      this.disabledIva();
    }
    this.refreshIva();
  }
  setTotalDescuentos(totalDescuentos: number) {
    this.dataCbte.totalDescuentos = totalDescuentos;
  }
  setTotalAdicionales(totalAdicionales: number) {
    this.dataCbte.totalAdicionales = totalAdicionales;
  }
  setTotalImpuestos(totalImpuestos: number) {
    this.dataCbte.totalImpuestos = totalImpuestos;
  }
  setTotalTributos(totalTributos: number) {
    this.dataCbte.totalTributos = totalTributos;
  }
  refreshPrecio() {
    this.fi.controls.forEach((f: FormGroup) => {
      if (f.get('producto').value) {
        f.patchValue({ precioConIva: this.getPrecioProducto(f.get('producto').value) })
        this.changeValuePriceConIVA(f);
      }
    });
  }
  refreshCalc() {
    this.fi.controls.forEach((f: FormGroup) => {
      this.changeValueCantidad(f);
    });
  }
  refreshIva() {
    this.fi.controls.forEach((f: FormGroup) => {
      this.changeValueIVA(f);
    });
  }
  changeValuePriceConIVA(fg: FormGroup) {
    fg.patchValue({ ivaMonto: this.getIVASelected(fg) })
    let precioSinIva = fg.get('precioConIva').value / ((fg.get('ivaMonto').value / 100) + 1);
    fg.patchValue({ precioSinIva: this.round2(precioSinIva) })
    let ivaCalculado = precioSinIva * (fg.get('ivaMonto').value / 100);
    fg.patchValue({ ivaCalculado: this.round2(ivaCalculado) })
    fg.patchValue({ baseImponible: precioSinIva * fg.get('cantidad').value })
    fg.updateValueAndValidity();
    this.setSubtotal(fg);
    this.changeValueSubtotal();
  };
  changeValuePriceSinIVA(fg: FormGroup) {
    fg.patchValue({ ivaMonto: this.getIVASelected(fg) })
    let ivaCalculado = fg.get('precioSinIva').value * (fg.get('ivaMonto').value / 100);
    fg.patchValue({ ivaCalculado: this.round2(ivaCalculado) })
    let precioConIva = fg.get('precioSinIva').value + fg.get('ivaCalculado').value;
    fg.patchValue({ precioConIva: this.round2(precioConIva) })
    fg.patchValue({ baseImponible: fg.get('precioSinIva').value * fg.get('cantidad').value })
    fg.updateValueAndValidity();
    this.setSubtotal(fg);
    this.changeValueSubtotal();
  }
  changeValueCantidad(fg: FormGroup) {
    let baseImponible = fg.get('precioSinIva').value * fg.get('cantidad').value;
    fg.patchValue({ baseImponible: this.round2(baseImponible) })
    fg.updateValueAndValidity();
    this.setSubtotal(fg);
    this.changeValueSubtotal();
  }

  changeValueDescuento(fg: FormGroup) {
    this.setSubtotal(fg);
    this.changeValueSubtotal();
  }
  setSubtotal(fg: FormGroup) {
    let descuento = (fg.get('porcentajeDescuento').value * fg.get('precioConIva').value) / 100;
    descuento = descuento * fg.get('cantidad').value;
    fg.patchValue({ descuento: this.round2(descuento) })
    let subtotal = fg.get('precioConIva').value * fg.get('cantidad').value;
    subtotal = subtotal - descuento;
    fg.patchValue({ subtotal: this.round2(subtotal) })
    fg.updateValueAndValidity();
  }
  changeValueIVA(fg: FormGroup) {
    let iva = fg.get('iva').value;
    fg.patchValue({ ivaDes: iva.descripcion });
    fg.patchValue({ ivaId: iva.id });
    this.changeValuePriceConIVA(fg);
  }
  changeValueSubtotal(emit = true) {
    this.dataCbte.subtotal = 0;
    this.fi.controls.forEach((f: FormGroup) => {
      if (f.get('subtotal').value) {
        this.dataCbte.subtotal = this.dataCbte.subtotal + f.get('subtotal').value;
      }
    });
    this.dataCbte.subtotal = this.dataCbte.subtotal - this.dataCbte.totalDescuentos;
    this.dataCbte.subtotal = this.dataCbte.subtotal + this.dataCbte.totalAdicionales;
    this.dataCbte.subtotal = this.round2(this.dataCbte.subtotal);
    this.changeValueTotalIVA();
    this.changeValueTotalBaseImp(emit);
    this.changeValueTotal();
    if (emit) {
      this.comprobanteService.changeSubtotal(this.dataCbte.subtotal);
    }
    this.eventChange.emit(this.dataCbte);
    this.cdr.markForCheck();
  }
  changeValueTotal() {
    this.dataCbte.total = 0;
    this.dataCbte.total = this.dataCbte.total + this.dataCbte.subtotal;
    this.dataCbte.total = this.dataCbte.total + this.dataCbte.totalImpuestos;
    this.dataCbte.total = this.dataCbte.total + this.dataCbte.totalTributos;
    this.dataCbte.total = this.round2(this.dataCbte.total);
    this.comprobanteService.changeTotal(this.dataCbte.total);
  }
  changeValueTotalIVA() {
    this.dataCbte.totalIVAs = 0;
    let tempIVA = [];
    this.fi.controls.forEach((f: FormGroup) => {
      let precioSinIva = f.get('subtotal').value / ((f.get('ivaMonto').value / 100) + 1);
      let ivaCalculado = precioSinIva * (f.get('ivaMonto').value / 100);
      f.patchValue({ ivaTotal: this.round2(ivaCalculado) });
      f.patchValue({ ivaUnidad: this.round2(ivaCalculado / f.get('cantidad').value) })
      this.dataCbte.totalIVAs = this.dataCbte.totalIVAs + ivaCalculado;
      this.dataCbte.totalIVAs = this.round2(this.dataCbte.totalIVAs);
    });
  }
  changeValueTotalBaseImp(emit = true) {
    this.dataCbte.totalBaseImp = 0;
    this.fi.controls.forEach((f: FormGroup) => {
      let baseImponible = f.get('subtotal').value - f.get('ivaTotal').value;
      f.patchValue({ baseImponibleTotal: this.round2(baseImponible) })
      this.dataCbte.totalBaseImp = this.dataCbte.totalBaseImp + baseImponible;
      this.dataCbte.totalBaseImp = this.round2(this.dataCbte.totalBaseImp);
    });
    if (emit) {
      this.comprobanteService.changeTotalBaseImponible(this.dataCbte.totalBaseImp);
    }
  }

  removeItem(item: FormGroup) {
    this.fi.removeAt(this.fi.controls.indexOf(item));
    this.formItems.updateValueAndValidity();
    this.refreshCalc();
  }
  ngAfterViewInit(): void {
    this.loadingFinishView(1);
    this.finishViewEvent.emit(2);
  }

  resetValues(fg: FormGroup) {
    this.fi.controls.forEach((f: FormGroup) => {
      if (f == fg) {
        f.patchValue({ producto: null });
        f.patchValue({ idProducto: null });
        f.patchValue({ nombreProducto: null });
        f.patchValue({ precioOferta: null });
        f.patchValue({ precioActual: null });
        f.patchValue({ descuento: null });
        f.updateValueAndValidity();
      }
    });
  }
  resetComponent(fg: FormGroup) {
    this.selects.forEach((s: SelectProdComponent) => {
      if (s.index == fg.get('index').value) {
        s.resetData(false);
      }
    })
  }
  public markAsTouched() {
    this.formItems.markAllAsTouched();
    this.tributosComponent.formItems.markAllAsTouched();
  }
  getDataItems(): Array<IItemCbte> {
    let items: Array<IItemCbte> = this.formItems.get('items').value.map(item => {
      return <IItemCbte>
        {
          baseImponibleTotal: item.baseImponibleTotal,
          cantidad: item.cantidad,
          descuento: item.descuento,
          adicional: 0,
          id: item.id,
          ivaId: item.ivaId,
          ivaDes: item.ivaDes,
          ivaTotal: item.ivaTotal,
          ivaUnidad: item.ivaUnidad,
          porcentajeDescuento: item.porcentajeDescuento,
          precioConIva: item.precioConIva,
          precioSinIva: item.precioSinIva,
          precio: item.precio,
          precioCosto: item.precioCosto,
          producto: this.getMinData(item.producto),
          idProducto: item.idProducto,
          nombreProducto: item.nombreProducto,
          descripcion: item.descripcion,
          total: item.subtotal,
          subtotal: item.subtotal,
          infoProdAdic: item.infoProdAdic
        };
    });
    return items;
  }
  getDataTotales(): ITotalCbte {
    let data: ITotalCbte = {} as any;
    data.total = this.dataCbte.total;
    data.subtotal = this.dataCbte.subtotal;
    data.totalAdicionales = this.dataCbte.totalAdicionales;
    data.totalBaseImp = this.dataCbte.totalBaseImp;
    data.totalDescuentos = this.dataCbte.totalDescuentos;
    data.totalIVAs = this.dataCbte.totalIVAs;
    data.totalImpuestos = this.dataCbte.totalImpuestos;
    data.totalTributos = this.dataCbte.totalTributos;
    return data;
  }
  getDataTributos(): Array<ITributos> {
    return this.tributosComponent.getDataTributos();
  }
  changeSizes(size: number) {
    setTimeout(() => {
      $('.item-cbte').removeClass('width-200');
      $('.item-cantidad').removeClass('width-100');
      $('.item-precio-ci, .item-precio-si, .item-iva, .item-desc, .item-subtotal').removeClass('width-150');
      if (size == 0) {
        $('.item-cbte').addClass('width-200');
        $('.item-cantidad').addClass('width-100');
        $('.item-precio-ci, .item-precio-si, .item-iva, .item-desc, .item-subtotal').addClass('width-150');
      }
    });
  }
  validateProd(producto, fg: FormGroup) {
    let response = true;
    if (producto) {
      return response;
    } else {
      return false;
    }
  }

  showOrHideInfoAdic(fg: FormGroup, value: boolean) {
    fg.patchValue({ $$showInfoAdic: value });
    if (!value) {
      fg.patchValue({ infoProdAdic: null });
    }

  }

  selectTributo() {
    this.dataView = { ...this.dataView, showTributo: false };
    this.selectService.select({ id: "addTributo" });
  }
  selectObservacion() {
    this.dataView = { ...this.dataView, showObservacion: false };
    this.selectService.select({ id: "addObservacion" });
  }
  get f() { return this.formItems.controls; }
  get fi() { return this.f.items as FormArray; }
}

