import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input, ViewChild, ChangeDetectorRef, OnDestroy, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ComponentPage } from '../../../../../../core/component-page';
import { Fields } from '../../../../../../core/fields';
import { IDataForm } from '../../../../../../core/models/idata-form';
import { Observable, Subscription } from 'rxjs';
import { VentaHTTPService } from '../../../../../../core/services/http/venta-http.service';
import { pluck, tap, finalize, switchMap } from 'rxjs/operators';
import { ItemsCbteComponent } from '../../../../../comprobante/components/items/items-cbte.component';
import { ItemsPagoComponent } from '../../../../../comprobante/components/pago/items/items-pago.component';
import { IItemCbte, ITotalCbte, IPago, ICbte, ITributos, IHeaderCbte } from '../../../../../../core/interfaces';
import * as moment from 'moment';
import { AuthService } from 'src/app/core/services/auth/auth.service';
import { Comprobante, TipoCbte } from 'src/app/core/enums';
import { MessageService } from 'src/app/modules/shared/services/message.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ResizeService } from 'src/app/modules/shared/components/size-detector/resize.service';
import { ComprobanteService } from 'src/app/modules/comprobante/services/comprobante.service';
import { HeaderCbteComponent } from 'src/app/modules/comprobante/components/header/header-cbte.component';
// import { DOCUMENT } from '@angular/common';
import { SelectService } from 'src/app/modules/shared/services/select.service';

declare var $: any;


@Component({
  selector: 'fields-fact-ven',
  templateUrl: './fields-fact-ven.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FieldsFactVenComponent extends ComponentPage implements OnInit, AfterViewInit, Fields, OnDestroy {

  public TipoCbte = TipoCbte;
  @Input()
  public dataForm: IDataForm = { submitted: false };
  @Input()
  public formFactVen: FormGroup;
  @ViewChild(ItemsCbteComponent, { static: false })
  itemsCbteComponent: ItemsCbteComponent
  @ViewChild(ItemsPagoComponent, { static: false })
  itemsPagoComponent: ItemsPagoComponent
  @ViewChild(HeaderCbteComponent, { static: false })
  headerCbteComponent: HeaderCbteComponent
  public dataVenta$: Observable<any> = null;
  private dataVenta: any = null;
  private subCliente: Subscription;
  public sucursales: any;
  public listasPrecio: any;
  public dataView: any = { showObservacion: false };

  public mask = ['(', /[1-9]/, /\d/, /\d/, ')', ' ', /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/];
  constructor(
    private formBuilder: FormBuilder,
    private ventaHTTPService: VentaHTTPService,
    private cdr: ChangeDetectorRef,
    private authService: AuthService,
    private messageService: MessageService,
    private router: Router,
    private resizeSvc: ResizeService,
    private comprobanteService: ComprobanteService,
    private activatedRoute: ActivatedRoute,
    // @Inject(DOCUMENT) private document: Document,
    private selectService: SelectService
  ) {
    super();
  }

  ngOnInit() {
    this.setCantView(3);
    this.sucursales = [...this.authService.getSucursales()];
    this.formFactVen = this.formBuilder.group({
      sucursal: [null, [Validators.required]],
      listaPrecio: [null, []],
      listaPrecioData: [null, []],
      ptoVenta: [null, [Validators.required]],
      $$listaPrecio: [null, [Validators.required]],
      observacion: [null, [Validators.maxLength(500)]],
      fe: [false, [Validators.required]],
      tipoFactura: ['TIKE_FACTURA_NO_VALIDA', [Validators.required]],
      source: [null, []],
      source_id: [null, []]
    });
    this.dataVenta$ = this.activatedRoute.queryParams.pipe(tap(x => {
    }), switchMap((params) => {
      return this.ventaHTTPService.getDataVentas(params).pipe(pluck('data'), tap(resp => {
        this.dataVenta = resp;
        if (!this.dataVenta.enabled) {
          if (!this.dataVenta.enabled) {
            let message = this.messageService.warning('Atención', 'Para poder realizar ventas debe completar los datos de la empresa.', 'lg');
            message.result.then((data) => {
              this.router.navigate(['config/data']);
            }, (reason) => {
              this.router.navigate(['config/data']);
            });
          }
        }
      }), finalize(() => {
      }))
    }));
    this.resizeSvc.onResize$
      .subscribe(x => {
      });
    this.selectService.select$.subscribe(resp => {
      if (resp.id == "addObservacion") {
        this.dataView = { ...this.dataView, showObservacion: true }
        setTimeout(() => {
          this.loadHelp("label-help-observacion");
        });
      }
    });
    // when finish load thre components, header, item cbte and item pago
    this.finishViewEvent.subscribe(() => {
      setTimeout(() => {
        this.initValue();
        this.initValueComponents();
        this.checkData(this.dataVenta);
        this.initFocus();
      });
    });
  }
  ngAfterViewInit(): void {
    setTimeout(() => {
      this.loadHelp();
      this.loadHelpByClass('button-help');
    }, 500);

  }
  reset() {
    this.formFactVen.patchValue({
      id: 0,
      observacion: null,
    });
    this.dataForm = { ...this.dataForm, submitted: false };
    this.itemsCbteComponent.reset();
    this.itemsPagoComponent.reset();
    this.headerCbteComponent.reset();
    this.cdr.markForCheck();
    this.initFocus();
  }
  finishViewItems() {
    setTimeout(() => {
      this.initFocus();
    });
  }
  isValid() {
    let validItems = this.itemsCbteComponent.formItems.valid;
    // console.log('this.itemsCbteComponent.formItems.valid', this.itemsCbteComponent.formItems.valid)
    // console.log('this.itemsCbteComponent.formItems.', this.itemsCbteComponent.formItems)
    let validPagos = this.itemsPagoComponent.formItems.valid;
    // console.log('this.itemsPagoComponent.formItems.valid', this.itemsPagoComponent.formItems.valid)
    // console.log('this.itemsPagoComponent.formItems', this.itemsPagoComponent.formItems)
    let validHeader = this.headerCbteComponent.formHeaderCbte.valid;
    // console.log('this.headerCbteComponent.formHeaderCbte.valid', this.headerCbteComponent.formHeaderCbte.valid)
    // console.log('this.headerCbteComponent.formHeaderCbte', this.headerCbteComponent.formHeaderCbte)
    let validFields = this.formFactVen.valid;
    // console.log(' this.formFactVen',  this.formFactVen)
    // console.log('this.formFactVen.valid', this.formFactVen.valid)
    if (validHeader && validItems && validFields && validPagos) {
      return this.isValidCliente();
    } else {
      return false;
    }
  }
  isValidCliente() {
    let resp = true;
    let data = this.getData();
    let pagos: Array<IPago> = data.pagosCbte;
    let ctaCte: number = 0;
    pagos.forEach((pago: IPago) => {
      if (pago.formaPago.id == 4) {
        ctaCte = ctaCte + pago.monto;
      }
    });
    if (ctaCte > 0) {
      if (data.cliente && data.cliente.cuentaCorriente && data.cliente.cuentaCorriente.margen) {
        if (data.cliente.cuentaCorriente.margen < ctaCte) {
          resp = false;
          this.messageToast.error('El cliente tiene un margen  de ' + data.cliente.cuentaCorriente.margen + ', es menor al total del pago a imputar como cuenta corriente.');
        }
      } else {
        resp = false;
        this.messageToast.error('Debe seleccionar un cliente valido, con cuenta corriente');
      }
    }
    return resp;
  }
  checkData(data: any) {
    // if exist presupuesto, so set some properties
    if (data.presupuesto) {
      if (data.presupuesto.estadoCbte == "PRES_APROBADO") {
        if (data.presupuesto.listaPrecio) {
          let listaPrecioData = JSON.parse(data.presupuesto.listaPrecioData);
          let listaDb = this.listasPrecio.find(x => x.id == listaPrecioData.id);
          // set list price
          this.changeListaPrecio(listaDb);
        }
        this.formFactVen.patchValue({
          observacion: data.presupuesto.observacion,
          source: TipoCbte.CBTE_VENTA,
          source_id: data.presupuesto.id,
        });
        this.formFactVen.updateValueAndValidity();
      } else {
        this.messageToast.error("El presupuesto(" + data.presupuesto.id + "- " + data.presupuesto.estadoCbte + ") que se quiere referenciar, no esta aprobado.");
      }
    }
  }
  markAsTouched(): void {
    this.itemsCbteComponent.markAsTouched();
    this.itemsPagoComponent.markAsTouched();
    this.headerCbteComponent.markAsTouched();
    this.formFactVen.markAllAsTouched();
  }
  getData(): ICbte {
    let configuracion = this.authService.getConfiguracion();
    let data: ICbte = {} as any;
    let items: Array<IItemCbte> = this.itemsCbteComponent.getDataItems();
    let tributos: Array<ITributos> = this.itemsCbteComponent.getDataTributos();
    let totales: ITotalCbte = this.itemsCbteComponent.getDataTotales();;
    let pagos: Array<IPago> = this.itemsPagoComponent.getDataPagos();
    let headerCbte: IHeaderCbte = this.headerCbteComponent.getDataHeaderCbte();
    // set datos
    data = { ...data, ...headerCbte };
    data.sucursal = this.getMinData(this.formFactVen.get('sucursal').value);
    data.observacion = this.formFactVen.get('observacion').value;
    data.origen = 'PUNTO_VENTA_WEB';
    data.ptoVenta = this.formFactVen.get('ptoVenta').value;
    data.tipoFactura = this.formFactVen.get('tipoFactura').value;
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
    data.pagosCbte = pagos;
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

  finishLoadComponent(evt: number) {
    this.loadingFinishView(evt);
  }
  resetComponents(){

  }
  initValueComponents() {
    this.headerCbteComponent.initValue();
    this.itemsPagoComponent.initValue();
    this.itemsCbteComponent.initValue(false)
  }
  initFocus() {
    setTimeout(() => {
      this.headerCbteComponent.initFocus();
    });
  }
  initValue() {
    // iniciamos la sucursal
    this.sucursales.forEach(suc => {
      if (suc.principal) {
        this.changeSucursal(suc);
      }
    });
    this.changeFactura(this.dataVenta.facturas[1]);
    this.changePtoVenta(this.dataVenta.puntosventa[0]);
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
    this.dataVenta.listaPrecios.forEach(lista => {
      this.listasPrecio.push(lista);
    });
  }

  getDescForPtoVenta(puntoVenta: any) {
    if (puntoVenta) {
      return this.dataVenta.puntosventa.find(x => x.nro == puntoVenta).descripcion;
    }
  }
  getDescFromTipoFact(tipoFactura: any) {
    if (tipoFactura) {
      return this.dataVenta.facturas.find(x => x.nombre == tipoFactura).abrev;
    }
  }
  changeSucursal(sucursal: any) {
    this.sucursales.forEach(suc => {
      suc.active = false;
    });
    sucursal.active = true;
    this.formFactVen.patchValue({ sucursal: sucursal });
  }
  changeFactura(factura: any) {
    this.dataVenta.facturas.forEach(fact => {
      fact.active = false;
    });
    factura.active = true;
    this.formFactVen.patchValue({ tipoFactura: factura.nombre });
  }
  changePtoVenta(ptoVta: any) {
    this.dataVenta.puntosventa.forEach(pto => {
      pto.active = false;
    });
    ptoVta.active = true;
    this.formFactVen.patchValue({ ptoVenta: ptoVta.nro });
  }
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

