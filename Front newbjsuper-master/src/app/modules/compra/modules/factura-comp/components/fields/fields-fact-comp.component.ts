import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input, ViewChild, ChangeDetectorRef, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ComponentPage, TypeComponent } from '../../../../../../core/component-page';
import { Fields } from '../../../../../../core/fields';
import { IDataForm } from '../../../../../../core/models/idata-form';
import { Observable, Subscription } from 'rxjs';
import { CompraHTTPService } from '../../../../../../core/services/http/compra-http.service';
import { pluck, tap, finalize } from 'rxjs/operators';
import { ItemsCbteComponent } from 'src/app/modules/comprobante/components/items/items-cbte.component';
import { ItemsPagoComponent } from 'src/app/modules/comprobante/components/pago/items/items-pago.component';
import { ICbteCompra, IItemCbte, ITotalCbte, IPago, IImpuesto, ITributos } from '../../../../../../core/interfaces';
import * as moment from 'moment';
import { AuthService } from 'src/app/core/services/auth/auth.service';
import { Comprobante } from 'src/app/core/enums';

declare var $: any;


@Component({
  selector: 'fields-fact-comp',
  templateUrl: './fields-fact-comp.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FieldsFactCompComponent extends ComponentPage implements OnInit, AfterViewInit, Fields, OnDestroy {


  @Input()
  public dataForm: IDataForm = { submitted: false };
  @Input()
  public formFactComp: FormGroup;
  @ViewChild(ItemsCbteComponent, { static: false })
  itemsCbteComponent: ItemsCbteComponent
  @ViewChild(ItemsPagoComponent, { static: false })
  itemsPagoComponent: ItemsPagoComponent
  public dataCompra$: Observable<any> = null;
  public dataCompra: any = null;
  public subProveedor: Subscription;
  public sucursales: any;

  public mask = ['(', /[1-9]/, /\d/, /\d/, ')', ' ', /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/];
  constructor(
    private formBuilder: FormBuilder,
    private compraHTTPService: CompraHTTPService,
    private cdr: ChangeDetectorRef,
    private authService: AuthService

  ) {
    super();
  }

  ngOnInit() {
    this.sucursales = [...this.authService.getSucursales()];
    this.formFactComp = this.formBuilder.group({
      id: [0, [Validators.required]],
      tipoCbte: [null, [Validators.required]],
      numero: ['', [Validators.required]],
      fechaCbte: [moment(new Date()).format("DD-MM-YYYY"), [Validators.required]],
      fechaVencCbte: ['', []],
      proveedor: [null, [Validators.required]],
      nombreProveedor: [null, []],
      direccionProveedor: [null, []],
      tipoDocProveedor: [null, []],
      nroDocProveedor: [null, []],
      tipoProveedor: [null, []],
      sucursal: [null, [Validators.required]],
      cae: [null, [Validators.maxLength(30)]],
      caeVenc: ['', []],
      observacion: [null, [Validators.maxLength(500)]],
      concepto: ['PRODUCTOS', [Validators.required]],
      tipoFactura: ['FACTURA_ELECTRONICA', [Validators.required]],
    });

    this.dataCompra$ = this.compraHTTPService.getDataCompras().pipe(pluck('data'), tap(resp => {
      this.dataCompra = resp;
      setTimeout(() => {
        this.initValue();
      });
    }), finalize(() => {
      this.loadingFinishView(1);
      this.resetComponents();
    }));
    this.subProveedor = this.formFactComp.get('proveedor').valueChanges.subscribe(resp => {
      this.setTipoCbteFromTipoProv(resp);
      this.setDataProv(resp)
    });
  }
  ngAfterViewInit(): void {
    this.initFocus();
  }
  finishViewItems() {
    setTimeout(() => {
      this.initFocus();
      this.loadHelp();
    });
  }
  isValid() {
    let validItems = this.itemsCbteComponent.formItems.valid;
    let validPagos = this.itemsPagoComponent.formItems.valid;
    let validFields = this.formFactComp.valid;
    return validItems && validFields && validPagos;
  }
  getData(): ICbteCompra {
    let data: ICbteCompra = {} as any;
    let items: Array<IItemCbte> = this.itemsCbteComponent.getDataItems();
    let tributos: Array<ITributos> = this.itemsCbteComponent.getDataTributos();
    let totales: ITotalCbte = this.itemsCbteComponent.getDataTotales();;
    let pagos: Array<IPago> = this.itemsPagoComponent.getDataPagos();
    // set datos
    data.proveedor = this.getMinData(this.formFactComp.get('proveedor').value, 'id', 'razonSocial');
    data.nombreProveedor = this.formFactComp.get('nombreProveedor').value;
    data.direccionProveedor = this.formFactComp.get('direccionProveedor').value;
    data.tipoDocProveedor = this.formFactComp.get('tipoDocProveedor').value;
    data.tipoProveedor = this.formFactComp.get('tipoProveedor').value;
    data.nroDocProveedor = this.formFactComp.get('nroDocProveedor').value;
    data.sucursal = this.getMinData(this.formFactComp.get('sucursal').value);
    data.tipoCbte = this.formFactComp.get('tipoCbte').value;
    data.tipoFactura = this.formFactComp.get('tipoFactura').value;
    data.numero = this.formFactComp.get('numero').value;
    data.fechaCbte = this.formFactComp.get('fechaCbte').value;
    data.fechaVencCbte = this.formFactComp.get('fechaVencCbte').value;
    data.fechaVencCbte = this.formFactComp.get('fechaVencCbte').value;
    data.cae = this.formFactComp.get('cae').value;
    data.caeVenc = this.formFactComp.get('caeVenc').value;
    data.observacion = this.formFactComp.get('observacion').value;
    data.concepto = this.formFactComp.get('concepto').value;
    //set totales
    data.total = totales.total;
    data.subtotal = totales.subtotal;
    data.totalAdicionales = totales.totalAdicionales;
    data.totalBaseImp = totales.totalBaseImp;
    data.totalDescuentos = totales.totalDescuentos;
    data.totalIVAs = totales.totalIVAs;
    data.totalImpuestos = totales.totalImpuestos;
    data.totalTributos = totales.totalTributos;
    //set pagos e items del cbe
    data.pagos = pagos;
    data.items = items;
    data.tributos = tributos;
    return data;
  }
  reset() {
    this.formFactComp.patchValue({
      id: 0,
      tipoCbte: 0,
      numero: '',
      fechaCbte: moment(new Date()).format("DD-MM-YYYY"),
      fechaVencCbte: null,
      proveedor: null,
      nombre: null,
      direccionProveedor: null,
      tipoDocProveedor: null,
      nroDocProveedor: null,
      tipoProveedor: null,
      concepto: 'PRODUCTOS',
      cae: null,
      caeVenc: '',
      observacion: null,
      tipoFactura: 'FACTURA_ELECTRONICA',
    });
    this.dataForm = { ...this.dataForm, submitted: false };
    this.resetComponents();
    this.cdr.markForCheck();
    this.initFocus();
  }
  resetComponents() {
    setTimeout(() => {
      this.itemsCbteComponent.reset()
      this.itemsPagoComponent.reset();
    });
  }
  initFocus() {
    setTimeout(() => {
      $("#selProvCbte").focus();
    }, 100);
  }
  initValue() {
    this.changeTipoCbte();
    // iniciamos la sucursal
    this.sucursales.forEach(suc => {
      if (suc.principal) {
        this.changeSucursal(suc);
      }
    });
  }
  setDataProv(proveedor) {
    if (proveedor) {
      this.formFactComp.patchValue({
        nombreProveedor: proveedor.nombreCompleto,
        tipoProveedor: proveedor.tipoProveedor,
        nroDocProveedor: proveedor.nroDocProveedor,
        tipoDocProveedor: proveedor.tipoDocProveedor
      });
    } else {
      this.formFactComp.patchValue({ nombreProveedor: null, tipoDocProveedor: null, nroDocProveedor: null });
    }
    this.changeTipoCbte();
  }
  setTipoCbteFromTipoProv(proveedor) {
    if (proveedor) {
      if (proveedor.tipoProveedor == 'MONOTRIBUTO') {
        this.formFactComp.patchValue({ tipoCbte: 'C' });
      }
      if (proveedor.tipoProveedor == 'RESPONSABLE_INSCRIPTO') {
        if (this.dataCompra.empresa.tipo == "RESPONSABLE_INSCRIPTO") {
          this.formFactComp.patchValue({ tipoCbte: 'A' });
        } else {
          this.formFactComp.patchValue({ tipoCbte: 'B' });
        }
      }
      if (proveedor.tipoProveedor == 'EXCENTO') {
        this.formFactComp.patchValue({ tipoCbte: 'C' });
      }
      if (proveedor.tipoProveedor == 'SIN_ESPECIFICAR') {
        this.formFactComp.patchValue({ tipoCbte: 'X' });
      }
    } else {
      this.formFactComp.patchValue({ tipoCbte: null });
    }
  }
  changeSucursal(sucursal: any) {
    this.sucursales.forEach(suc => {
      suc.active = false;
    });
    sucursal.active = true;
    this.formFactComp.patchValue({ sucursal: sucursal });
  }
  changeTipoCbte() {
    let tipoCBte = this.formFactComp.get('tipoCbte').value;
    const comp = tipoCBte as Comprobante;
    this.itemsCbteComponent.setComprobante(comp);
  }
  get f() {
    return this.formFactComp.controls;
  }
  ngOnDestroy(): void {
    if (this.subProveedor) {
      this.subProveedor.unsubscribe();
    }
  }

}

