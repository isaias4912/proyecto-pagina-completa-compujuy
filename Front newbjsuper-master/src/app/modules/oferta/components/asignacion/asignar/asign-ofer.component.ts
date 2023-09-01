import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, Input, Output, EventEmitter, Renderer2, ElementRef, ViewChildren, QueryList } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, Validators, AbstractControl, ValidatorFn, FormControl } from '@angular/forms';
import { ComponentPage } from '../../../../../core/component-page';
import { IDataForm } from '../../../../../core/models/idata-form';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { OfertaHTTPService } from 'src/app/core/services/http/oferta-http.service';
import { Observable } from 'rxjs';
import { pluck, tap } from 'rxjs/operators';
import { SelectProdComponent } from 'src/app/modules/producto/modules/shared-prod/components/select/select-prod.component';
declare var $: any;

@Component({
  selector: 'asign-ofer',
  templateUrl: './asign-ofer.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AsignOferComponent extends ComponentPage implements OnInit, AfterViewInit {

  private formItems: FormGroup;
  public dataForm: IDataForm;
  private dataPrint: any;
  @ViewChildren('selectsProd') selects: QueryList<SelectProdComponent>;
  public oferta$: Observable<any>;
  public oferta: any;
  public dataOption = {
    estados: [{ val: true, label: 'Activa' }, { val: false, label: 'Inactiva' }]
  }
  constructor(
    private formBuilder: FormBuilder,
    private cdr: ChangeDetectorRef,
    private activatedRoute: ActivatedRoute,
    private ofertaHTTPService: OfertaHTTPService
  ) {
    super();
  }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(
      (params: ParamMap) => {
        this.id = this.decrypt(params.get("idOferta"));
        if (this.id) {
          this.oferta$ = this.ofertaHTTPService.getOfertaAndProductos(this.id).pipe(pluck('data'), tap(resp => {
            this.oferta = resp;
            this.addItem();
          }));
        }
      });
    this.formItems = this.formBuilder.group({
      items: new FormArray([]),
    });
    this.dataForm = { submitted: false };
  }
  addItem(index = 0) {
    this.dataForm.submitted = true;
    if (this.f.items.valid) {
      this.dataForm.submitted = false;
      let f: FormGroup = this.formBuilder.group({
        id: [0, [Validators.required]],
        $$producto: [null, [Validators.required]],
        producto: [null, [Validators.required]],
        estado: [true, [Validators.required]],
        descuento: [null, [Validators.required]],
        precioActual: [null, [Validators.required]],
        precioOferta: [null, [Validators.required, Validators.min(0)]],
        index: [index, []],
      });
      this.fi.push(f);
      f.get('$$producto').valueChanges.subscribe(resp => {
        if (this.validateProd(resp, f)) {
          f.patchValue({ producto: resp });
          f.updateValueAndValidity();
          if (this.oferta.tipoDescuento == "PORCENTAJE") {
            this.calcPorcentaje(resp);
          }
          if (this.oferta.tipoDescuento == "CANTIDAD") {
            this.calcCantidad(resp);
          }
          if (this.oferta.tipoDescuento == "VALOR") {
            this.calcValor(resp);
          }
        } else {
          this.resetValues(f);
          this.resetComponent(f);
        }
      });
      setTimeout(() => {
        $("#txtProducto" + (this.fi.controls.length - 1)).focus();
      });
    }
  }
  arrayToOfertaProductos() {
    let ofertasProductos = [];
    this.fi.controls.forEach((f: FormGroup) => {
      let producto = {
        id: f.get('producto').value.id,
        nombre: f.get('producto').value.nombreFinal
      };
      ofertasProductos.push({
        descuento: f.get('descuento').value,
        precio: f.get('precioOferta').value,
        estado: f.get('estado').value,
        producto: producto
      });
    });
    return ofertasProductos;
  };
  save(option) {
    this.dataForm.submitted = true;
    if (this.f.items.valid) {
      let data = { ...this.oferta };
      data.ofertaProductos = this.arrayToOfertaProductos();
      this.ofertaHTTPService.insertOfertaProductos(data).subscribe(resp => {
        this.messageToast.success('Se asignarón los productos correctamente a la oferta.');
        if (option == 1) {
          this.reset();
        }
        if (option == 2) {
          this.location.back();
        }
      })
    }
  }
  reset() {
    this.fi.clear();
    this.formItems.reset();
    this.formItems.updateValueAndValidity();
    this.addItem(0);
    this.cdr.markForCheck();
  }
  changeTipo() {
    this.fi.controls.forEach((f: FormGroup) => {
      f.get('codigo').updateValueAndValidity();
    });
  }
  removeItem(item) {
    this.fi.removeAt(this.fi.controls.indexOf(item));
    this.formItems.updateValueAndValidity();
  }
  ngAfterViewInit(): void {
  }
  validateProd(producto, fg: FormGroup) {
    let response = true;
    if (producto) {
      this.oferta.ofertaProductos.forEach((p) => {
        if (producto.id == p.producto.id) {
          this.messageToast.error('El producto seleccionado ya fue asignado a esta oferta.');
          response = false;
        }
      });
      if (response) {
        this.fi.controls.forEach((f: FormGroup) => {
          if (fg != f) {
            if (f.get('producto').value) {
              if (producto.id == f.get('producto').value.id) {
                this.messageToast.error('El producto seleccionado ya ingresado a esta oferta.');
                response = false;
              }
            }
          }
        });
      }
      return response;
    } else {
      return false;
    }
  }
  calcPorcentaje(producto) {
    this.fi.controls.forEach((f: FormGroup) => {
      let porcentaje = null;
      let precioOferta = null;
      let precioVenta = null;
      let descuento = null;
      if (producto && f.get('producto').value) {
        if (f.get('producto').value.id == producto.id) {
          porcentaje = this.round2((this.oferta.valor * producto.precioVenta) / 100);
          precioVenta = this.round2(producto.precioVenta);
          precioOferta = this.round2(producto.precioVenta - porcentaje);
          descuento = precioVenta - precioOferta;
          f.patchValue({ precioOferta: precioOferta });
          f.patchValue({ precioActual: precioVenta });
          f.patchValue({ descuento: descuento });
        }
      }
    });
  }
  calcCantidad(producto) {
    this.fi.controls.forEach((f: FormGroup) => {
      let porcentaje = null;
      let precioOferta = null;
      let precioVenta = null;
      let descuento = null;
      if (producto && f.get('producto').value) {
        if (f.get('producto').value.id == producto.id) {
          precioOferta = this.round2(producto.precioVenta - this.oferta.valor);
          precioVenta = this.round2(producto.precioVenta);
          descuento = this.round2(precioVenta - precioOferta);
          f.patchValue({ precioOferta: precioOferta });
          f.patchValue({ precioActual: precioVenta });
          f.patchValue({ descuento: descuento });
        }
      }
    });
  }
  calcValor(producto) {
    this.fi.controls.forEach((f: FormGroup) => {
      let precioOferta = null;
      let precioVenta = null;
      let descuento = null;
      if (producto && f.get('producto').value) {
        if (f.get('producto').value.id == producto.id) {
          descuento = 0;
          precioOferta = this.round2(producto.precioVenta);
          precioVenta = this.round2(producto.precioVenta);
          f.patchValue({ precioOferta: precioOferta });
          f.patchValue({ precioActual: precioVenta });
          f.patchValue({ descuento: descuento });
        }
      }
    });
  }
  changeValor(f: any) {
    try {
      let descuento = f.get('precioActual').value - f.get('precioOferta').value;
      descuento = this.round2(descuento);
      f.patchValue({ descuento: descuento });
    } catch (e) {
      f.patchValue({ descuento: null });
    }
  }
  resetValues(fg: FormGroup) {
    this.fi.controls.forEach((f: FormGroup) => {
      if (f == fg) {
        f.patchValue({ producto: null });
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
  replaceGuionBajo(value) {
    if (value) {
      return value.replace(/_/g, " ");
    }
    return '';
  };
  checkTipoOferta(value, cantidad) {
    if (value == "CADA_N_PRODUCTO") {
      return value.replace(/_N_/g, " " + cantidad + " ");
    } else {
      return value;
    }
  };
  get f() { return this.formItems.controls; }
  get fi() { return this.f.items as FormArray; }
}

