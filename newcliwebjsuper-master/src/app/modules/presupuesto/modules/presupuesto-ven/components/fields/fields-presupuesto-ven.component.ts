import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input, ViewChild, ChangeDetectorRef, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ComponentPage } from '../../../../../../core/component-page';
import { Fields } from '../../../../../../core/fields';
import { IDataForm } from '../../../../../../core/models/idata-form';
import { Observable, Subscription } from 'rxjs';
import { VentaHTTPService } from '../../../../../../core/services/http/venta-http.service';
import { pluck, tap, finalize } from 'rxjs/operators';
import { ItemsCbteComponent } from '../../../../../comprobante/components/items/items-cbte.component';
import { ItemsPagoComponent } from '../../../../../comprobante/components/pago/items/items-pago.component';
import { IItemCbte, ITotalCbte, IPago, ICbte, ITributos, IHeaderCbte } from '../../../../../../core/interfaces';
import * as moment from 'moment';
import { AuthService } from 'src/app/core/services/auth/auth.service';
import { Comprobante, TipoCbte } from 'src/app/core/enums';
import { MessageService } from 'src/app/modules/shared/services/message.service';
import { Router } from '@angular/router';
import { ResizeService } from 'src/app/modules/shared/components/size-detector/resize.service';
import { ComprobanteService } from 'src/app/modules/comprobante/services/comprobante.service';
import { HeaderCbteComponent } from 'src/app/modules/comprobante/components/header/header-cbte.component';
import { PresupuestoHTTPService } from 'src/app/core/services/http/presupuesto-http.service';

declare var $: any;


@Component({
  selector: 'fields-presupuesto-ven',
  templateUrl: './fields-presupuesto-ven.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FieldsPresupuestoVenComponent extends ComponentPage implements OnInit, AfterViewInit, Fields, OnDestroy {


  @Input()
  public dataForm: IDataForm = { submitted: false };
  @Input()
  public formFactVen: FormGroup;
  @ViewChild(ItemsCbteComponent, { static: false })
  itemsCbteComponent: ItemsCbteComponent
  // @ViewChild(ItemsPagoComponent, { static: false })
  // itemsPagoComponent: ItemsPagoComponent
  @ViewChild(HeaderCbteComponent, { static: false })
  headerCbteComponent: HeaderCbteComponent
  public dataPresupuesto$: Observable<any> = null;
  private dataPresupuesto: any = null;
  private subCliente: Subscription;
  public sucursales: any;
  public listasPrecio: any;
  public TipoCbte = TipoCbte;
  public mask = ['(', /[1-9]/, /\d/, /\d/, ')', ' ', /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/];
  constructor(
    private formBuilder: FormBuilder,
    private presupuestoHTTPService: PresupuestoHTTPService,
    private cdr: ChangeDetectorRef,
    private authService: AuthService,
    private messageService: MessageService,
    private router: Router,
    private resizeSvc: ResizeService,
    private comprobanteService: ComprobanteService
  ) {
    super();
  }

  ngOnInit() {
    this.sucursales = [...this.authService.getSucursales()];
    this.formFactVen = this.formBuilder.group({
      sucursal: [null, [Validators.required]],
      listaPrecio: [null, []],
      listaPrecioData: [null, []],
      // ptoVenta: [null, [Validators.required]],
      $$listaPrecio: [null, [Validators.required]],
      observacion: [null, [Validators.maxLength(500)]],
      fe: [false, [Validators.required]],
      tipoFactura: ['TIKE_FACTURA_NO_VALIDA', [Validators.required]]
    });
    this.dataPresupuesto$ = this.presupuestoHTTPService.getDataPresupuestos().pipe(pluck('data'), tap(resp => {
      this.dataPresupuesto = resp;
      if (!this.dataPresupuesto.enabled) {
        if (!this.dataPresupuesto.enabled) {
          let message = this.messageService.warning('Atención', 'Para poder realizar presupuestos debe completar los datos de la empresa.', 'lg');
          message.result.then((data) => {
            this.router.navigate(['config/data']);
          }, (reason) => {
            this.router.navigate(['config/data']);
          });
        }
      }
      setTimeout(() => {
        this.initValue();
      });
    }), finalize(() => {
      this.loadingFinishView(1);
      this.resetComponents();
    }));
    this.resizeSvc.onResize$
      .subscribe(x => {
      });
  }
  ngAfterViewInit(): void {
    this.initFocus();
    setTimeout(() => {
      this.loadHelp();
      this.loadHelpByClass('button-help');
    }, 500);
  }
  finishViewItems() {
    setTimeout(() => {
      this.initFocus();
    });
  }
  isValid() {
    let validItems = this.itemsCbteComponent.formItems.valid;
    console.log('validItems', validItems)
    // let validPagos = this.itemsPagoComponent.formItems.valid;
    // console.log('validPagos', validPagos)
    let validHeader = this.headerCbteComponent.formHeaderCbte.valid;
    console.log('validHeader', validHeader)
    let validFields = this.formFactVen.valid;
    console.log('this.formFactVen', this.formFactVen)
    console.log('validFields', validFields)
    // if (validHeader && validItems && validFields && validPagos) {
    if (validHeader && validItems && validFields ) {
      return this.isValidCliente();
    } else {
      return false;
    }
  }
  isValidCliente() {
    let resp = true;
    // TODO to make at future
    return resp;
  }
  getData(): ICbte {
    let configuracion = this.authService.getConfiguracion();
    let data: ICbte = {} as any;
    let items: Array<IItemCbte> = this.itemsCbteComponent.getDataItems();
    let tributos: Array<ITributos> = this.itemsCbteComponent.getDataTributos();
    let totales: ITotalCbte = this.itemsCbteComponent.getDataTotales();;
    // let pagos: Array<IPago> = this.itemsPagoComponent.getDataPagos();
    let headerCbte: IHeaderCbte = this.headerCbteComponent.getDataHeaderCbte();
    // set datos
    data = { ...data, ...headerCbte };
    data.sucursal = this.getMinData(this.formFactVen.get('sucursal').value);
    data.observacion = this.formFactVen.get('observacion').value;
    data.origen = 'PUNTO_VENTA_WEB';
    // data.ptoVenta = this.formFactVen.get('ptoVenta').value;
    //set totales
    data.total = totales.total;
    data.subtotal = totales.subtotal;
    data.totalAdicionales = totales.totalAdicionales;
    data.totalBaseImp = totales.totalBaseImp;
    data.totalDescuentos = totales.totalDescuentos;
    data.totalIva = totales.totalIVAs;
    data.totalImpuestos = totales.totalImpuestos;
    data.totalTrib = totales.totalTributos;
    //set pagos e items del cbe
    // data.pagosCbte = pagos;
    data.detalleVentas = items;
    data.tributos = tributos;
    // datos de empresa
    data.condicionEmpresa = configuracion.tipoEmpresa;
    data.cuitEmpresa = configuracion.cuitEmpresa;
    data.domComercialEmpresa = configuracion.domicilioComercial;
    data.razonSocialEmpresa = configuracion.razonSocial;
    data.fechaIniActEmpresa = configuracion.fechaIniAct;
    data.ingBrutosEmpresa = configuracion.ingresosBrutos;
    // seteamos si es que hay lista de precios
    if (this.formFactVen.get('$$listaPrecio').value.id == 0) {
      data.listaPrecio = false;
      data.listaPrecioData = null;
    } else {
      data.listaPrecio = true;
      data.listaPrecioData = JSON.stringify(this.formFactVen.get('$$listaPrecio').value);
    }
    return data;
  }
  reset() {
    this.formFactVen.patchValue({
      id: 0,
      tipoCbte: 0,
      numero: '',
      fechaCbte: moment(new Date()).format("DD-MM-YYYY"),
      fechaVencCbte: null,
      cliente: null,
      nombreCliente: null,
      direccionCliente: null,
      tipoDocCliente: null,
      nroDocCliente: null,
      tipoCliente: null,
      concepto: 'PRODUCTOS',
      fechaServDesde: null,
      fechaServHasta: null,
      observacion: null,
    });
    this.dataForm = { ...this.dataForm, submitted: false };
    this.resetComponents();
    this.cdr.markForCheck();
    this.initFocus();
  }
  resetComponents() {
    setTimeout(() => {
      this.itemsCbteComponent.reset();
      this.headerCbteComponent.reset();
      // this.itemsPagoComponent.reset();
    });
  }
  initFocus() {
    setTimeout(() => {
      $("#selCliCbte").focus();
    }, 100);
  }
  initValue() {
    // iniciamos la sucursal
    this.sucursales.forEach(suc => {
      if (suc.principal) {
        this.changeSucursal(suc);
      }
    });
    // this.changeFactura(this.dataVenta.facturas[1]);
    // this.changePtoVenta(this.dataVenta.puntosventa[0]);
    this.loadListasPrecio();
    this.changeListaPrecio(this.listasPrecio[0]);
  }
  loadListasPrecio() {
    this.listasPrecio = [];
    //agregamos la lista de precios que seria la normal
    let temp: any = {};
    temp.activo = true;
    temp.detalle = '';
    temp.id = 0;
    temp.nombre = 'Normal';
    temp.tipo = 0;
    temp.valor = 0;
    this.listasPrecio.push(temp);
    this.dataPresupuesto.listaPrecios.forEach(lista => {
      this.listasPrecio.push(lista);
    });
  }

  // getDescForPtoVenta(puntoVenta: any) {
  //   if (puntoVenta) {
  //     return this.dataPresupuesto.puntosventa.find(x => x.nro == puntoVenta).descripcion;
  //   }
  // }
  getDescFromTipoFact(tipoFactura: any) {
    if (tipoFactura) {
      return this.dataPresupuesto.facturas.find(x => x.nombre == tipoFactura).abrev;
    }
  }
  changeSucursal(sucursal: any) {
    this.sucursales.forEach(suc => {
      suc.active = false;
    });
    sucursal.active = true;
    this.formFactVen.patchValue({ sucursal: sucursal });
  }
  // changePtoVenta(ptoVta: any) {
  //   this.dataVenta.puntosventa.forEach(pto => {
  //     pto.active = false;
  //   });
  //   ptoVta.active = true;
  //   this.formFactVen.patchValue({ ptoVenta: ptoVta.nro });
  // }
  changeListaPrecio(lista: any) {
    this.listasPrecio.forEach(lista => {
      lista.active = false;
    });
    lista.active = true;
    this.formFactVen.patchValue({ $$listaPrecio: lista });
    this.comprobanteService.changeListaPrecio(lista.id);
  }
  changeTipoCbte(comp: Comprobante) {
    this.itemsCbteComponent.setComprobante(comp);
  }
  changeSizes() {

  }
  get f() {
    return this.formFactVen.controls;
  }
  ngOnDestroy(): void {
    if (this.subCliente) {
      this.subCliente.unsubscribe();
    }
  }

}

