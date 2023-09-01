import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input, ViewChild, ChangeDetectorRef, OnDestroy, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable, Subscription } from 'rxjs';
import { pluck, tap, finalize } from 'rxjs/operators';
import * as moment from 'moment';
import { AuthService } from 'src/app/core/services/auth/auth.service';
import { Comprobante, TipoCbte, TipoClienteDesc, TipoSearch } from 'src/app/core/enums';
import { MessageService } from 'src/app/modules/shared/services/message.service';
import { Router } from '@angular/router';
import { ResizeService } from 'src/app/modules/shared/components/size-detector/resize.service';
import { ComprobanteService } from 'src/app/modules/comprobante/services/comprobante.service';
import { ComponentPage } from 'src/app/core/component-page';
import { Fields } from 'src/app/core/fields';
import { IDataForm } from 'src/app/core/models/idata-form';
import { VentaHTTPService } from 'src/app/core/services/http/venta-http.service';
import { IHeaderCbte, IPago } from 'src/app/core/interfaces';

declare var $: any;


@Component({
  selector: 'header-cbte',
  templateUrl: './header-cbte.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HeaderCbteComponent extends ComponentPage implements OnInit, AfterViewInit, Fields, OnDestroy {

  public TipoSearch = TipoSearch;
  TipoCbte = TipoCbte;
  @Input()
  tipoCbte: TipoCbte;
  @Input()
  public dataForm: IDataForm = { submitted: false };
  @Input()
  public formHeaderCbte: FormGroup;
  @Input()
  public data: any;
  private subCliente: Subscription;
  public sucursales: any;
  public listasPrecio: any;
  @Output()
  eventChangeCbte = new EventEmitter<any>();
  public mask = ['(', /[1-9]/, /\d/, /\d/, ')', ' ', /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/];
  public typeSearch: TipoSearch = TipoSearch.SEARCH_WITHOUT_FILTER;

  constructor(
    private formBuilder: FormBuilder,
    private ventaHTTPService: VentaHTTPService,
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
    this.formHeaderCbte = this.formBuilder.group({
      cliente: [null, []],
      nombreCliente: [null, []],
      direccionCliente: [null, []],
      tipoDocCliente: [null, []],
      nroDocCliente: [null, []],
      tipoCliente: [null, []],
      id: [0, [Validators.required]],
      tipoCbte: [null, [Validators.required]],
      concepto: ['PRODUCTOS', [Validators.required]],
      fechaCbte: [moment(new Date()).format("DD-MM-YYYY"), [Validators.required]],
      fechaVencCbte: [moment(new Date()).add(30, 'days').format("DD-MM-YYYY"), []],
      fechaVigencia: [moment(new Date()).add(30, 'days').format("DD-MM-YYYY"), []],
      fechaServDesde: [null, []],
      fechaServHasta: [null, []]
    });
    // setTimeout(() => {
    //   this.initValue();
    // });
    this.subCliente = this.formHeaderCbte.get('cliente').valueChanges.subscribe(resp => {
      console.log('cliente', resp)
      this.setTipoCbteFromTipoCli(resp);
      this.setDataCli(resp)
    });
    // cambios en el size del window
    this.resizeSvc.onResize$
      .subscribe(x => {
      });
  }
  ngAfterViewInit(): void {
    setTimeout(() => {
      this.loadHelp();
      this.loadHelpByClass('button-help');
    }, 500);
    this.finishViewEvent.emit(1);
  }

  setData(data: any) {
    this.formHeaderCbte.patchValue({
      tipoCbte: data.tipoCbte,
      fechaCbte: moment(new Date()).format("DD-MM-YYYY"),
      fechaVencCbte: null,
      cliente: data.cliente,
      concepto: data.concepto,
      fechaServDesde: data.fechaServDesde,
      fechaServHasta: data.fechaServHasta
    });
    this.cdr.markForCheck();
    this.initFocus();
  }
  reset() {
    this.formHeaderCbte.patchValue({
      id: 0,
      tipoCbte: 0,
      numero: '',
      fechaCbte: moment(new Date()).format("DD-MM-YYYY"),
      fechaVigencia: moment(new Date()).add(30, 'days').format("DD-MM-YYYY"),
      fechaVencCbte: moment(new Date()).add(30, 'days').format("DD-MM-YYYY"),
      cliente: null,
      nombreCliente: null,
      direccionCliente: null,
      tipoDocCliente: null,
      nroDocCliente: null,
      tipoCliente: null,
      concepto: 'PRODUCTOS',
      fechaServDesde: null,
      fechaServHasta: null,
    });
    this.formHeaderCbte.updateValueAndValidity();
    this.dataForm = { ...this.dataForm, submitted: false };
    this.cdr.markForCheck();
  }
  initFocus() {
    setTimeout(() => {
      $("#selCliCbte").focus();
    });
  }
  initValue() {
    if (this.data.empresa.tipo == "MONOTRIBUTO") {
      // si es monotributista la empresa siempre sera  tipo c, independientemente del cliente, no puede tener otra factura
      this.formHeaderCbte.patchValue({ tipoCbte: 'C' });
    }
    if (this.data.empresa.tipo == "RESPONSABLE_INSCRIPTO") {
      // aqui hay que ver que es el cliente
      this.formHeaderCbte.patchValue({ tipoCbte: 'B' });
    }
    if (this.data.empresa.tipo == "EXCENTO") {
      // si es excento  es solo facturas c segun afip
      this.formHeaderCbte.patchValue({ tipoCbte: 'C' });
    }
    if (this.data.presupuesto) {
      if (this.data.presupuesto.estadoCbte == "PRES_APROBADO") {
        this.setData(this.data.presupuesto);
      }
    }
    this.changeTipoCbte();
    this.cdr.markForCheck();
    // this.changeFactura(this.data.facturas[1]);
  }
  setDataCli(cliente) {
    if (cliente) {
      this.formHeaderCbte.patchValue({
        nombreCliente: cliente.nombreCompleto,
        tipoCliente: cliente.tipoCliente,
        nroDocCliente: cliente.nroDocCliente,
        tipoDocCliente: cliente.tipoDocCliente
      });
    } else {
      this.formHeaderCbte.patchValue({ nombreCliente: null, tipoDocCliente: null, nroDocCliente: null });
    }
    this.changeTipoCbte();
  }
  setTipoDoc(id: number) {
    this.data.docs.forEach(doc => {
      if (doc.id == id) {
        this.formHeaderCbte.patchValue({ tipoDocCliente: doc.nombre });
      }
    });
  }
  getDescFromTipoFact(tipoFactura: any) {
    if (tipoFactura) {
      return this.data.facturas.find(x => x.nombre == tipoFactura).abrev;
    }
  }
  // changeFactura(factura: any) {
  //   this.data.facturas.forEach(fact => {
  //     fact.active = false;
  //   });
  //   factura.active = true;
  //   this.formHeaderCbte.patchValue({ tipoFactura: factura.nombre });
  // }
  changeSucursal(sucursal: any) {
    this.sucursales.forEach(suc => {
      suc.active = false;
    });
    sucursal.active = true;
    this.formHeaderCbte.patchValue({ sucursal: sucursal });
  }
  changeTipoCbte() {
    let tipoCBte = this.formHeaderCbte.get('tipoCbte').value;
    const comp = tipoCBte as Comprobante;
    this.eventChangeCbte.emit(comp);
    //  this.itemsCbteComponent.setComprobante(comp);
  }
  setTipoCbteFromTipoCli(cliente) {
    console.log('cliente', cliente)
    if (this.data.empresa.tipo == "MONOTRIBUTO") {
      // si es monotributista la empresa siempre sera  tipo c, independientemente del cliente, no puede tener otra factura
      this.formHeaderCbte.patchValue({ tipoCbte: 'C' });
    }
    if (this.data.empresa.tipo == "RESPONSABLE_INSCRIPTO") {
      // aqui hay que ver que es el cliente
      if (cliente) {
        if (cliente.tipoCliente == 'MONOTRIBUTO') {
          this.formHeaderCbte.patchValue({ tipoCbte: 'B' });
        }
        if (cliente.tipoCliente == 'RESPONSABLE_INSCRIPTO') {
          this.formHeaderCbte.patchValue({ tipoCbte: 'A' });
        }
        if (cliente.tipoCliente == 'EXCENTO') {
          this.formHeaderCbte.patchValue({ tipoCbte: 'B' });
        }
        if (cliente.tipoCliente == 'CONSUMIDOR_FINAL') {
          this.formHeaderCbte.patchValue({ tipoCbte: 'B' });
        }
        if (cliente.tipoCliente == 'SIN_ESPECIFICAR') {
          this.formHeaderCbte.patchValue({ tipoCbte: 'B' });
        }
        if (cliente.tipoCliente == 'OTRO') {
          this.formHeaderCbte.patchValue({ tipoCbte: 'B' });
        }
      } else {
        this.formHeaderCbte.patchValue({ tipoCbte: 'B' });
      }
    }
    if (this.data.empresa.tipo == "EXCENTO") {
      // si es excento  es solo facturas c segun afip
      this.formHeaderCbte.patchValue({ tipoCbte: 'C' });
    }
  }
  public markAsTouched(){
    this.formHeaderCbte.markAllAsTouched();
  }
  getDataHeaderCbte(): IHeaderCbte {
    this.formHeaderCbte.markAllAsTouched();
    let data: IHeaderCbte = {} as any;
    data.cliente = this.formHeaderCbte.get('cliente').value;
    data.nombreCliente = this.formHeaderCbte.get('nombreCliente').value;
    data.direccionCliente = this.formHeaderCbte.get('direccionCliente').value;
    data.tipoDocCliente = this.formHeaderCbte.get('tipoDocCliente').value;
    data.tipoCliente = this.formHeaderCbte.get('tipoCliente').value;
    data.nroDocCliente = this.formHeaderCbte.get('nroDocCliente').value;
    // data.sucursal = this.getMinData(this.formHeaderCbte.get('sucursal').value);
    data.tipoCbte = this.formHeaderCbte.get('tipoCbte').value;
    data.fechaCbte = this.formHeaderCbte.get('fechaCbte').value;
    data.fechaVencCbte = this.formHeaderCbte.get('fechaVencCbte').value;
    data.fechaVigencia = this.formHeaderCbte.get('fechaVigencia').value;
    // data.tipoFactura = this.formHeaderCbte.get('tipoFactura').value;
    data.concepto = this.formHeaderCbte.get('concepto').value;
    return data;
  }
  changeSizes() {

  }
  getDescTipoCliente(tipoCli: string) {
    return TipoClienteDesc[tipoCli];
  }
  getEnterFecCbte():string{
    if (this.tipoCbte== TipoCbte.CBTE_VENTA){
      return "fechaVencCbte";
    }
    if (this.tipoCbte== TipoCbte.CBTE_PRESUPUESTO){
      return "fechaVigencia";
    }
    return "txtProducto0"; // default
  }
  get f() {
    return this.formHeaderCbte.controls;
  }
  ngOnDestroy(): void {
    if (this.subCliente) {
      this.subCliente.unsubscribe();
    }
  }

}

