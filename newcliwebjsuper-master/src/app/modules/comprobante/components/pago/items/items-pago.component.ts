import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, Validators, AbstractControl } from '@angular/forms';
import { IDataForm } from '../../../../../core/models/idata-form';
import { UtilPage } from '../../../../../core/util-page';
import { ComprobanteService } from '../../../services/comprobante.service';
import { IPago } from '../../../../../core/interfaces';
import { ResizeService } from 'src/app/modules/shared/components/size-detector/resize.service';

declare var $: any;

@Component({
  selector: 'items-pago',
  templateUrl: './items-pago.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ItemsPagoComponent extends UtilPage implements OnInit, AfterViewInit {

  public formItems: FormGroup;
  @Input()
  public dataForm: IDataForm;
  @Input()
  public formasPago: Array<any>;
  @Input()
  public asociada: boolean = true;
  @Input()
  public order: number = 1;
  // @Input()
  // private total: number;
  @Output()
  eventChange = new EventEmitter<any>();
  // @Output()
  // protected afterViewInit = new EventEmitter<string>();
  public dataPago: any = {};
  public dataOptions = {
    tarjetasCred: ["INDISTINTO", "NARANJA", "VISA", "MASTERCARD", "SHOPING"],
    tarjetasDeb: ["INDISTINTO", "MACRO", "NACION"]
  }
  constructor(
    private formBuilder: FormBuilder,
    private cdr: ChangeDetectorRef,
    private comprobanteService: ComprobanteService,
    private resizeSvc: ResizeService
  ) {
    super();
  }

  ngOnInit() {
    this.formItems = this.formBuilder.group({
      total: [null, [Validators.required]],
      items: new FormArray([]),
    }, { validators: this.validateTotal });
    this.dataForm = { submitted: false };
    // this.addItem();
    this.comprobanteService.totalChange$.subscribe(resp => {
      this.formItems.patchValue({ total: resp });
      this.dataPago.total = resp;
      this.refreshCalc();
    })
    // cambios en el size del window
    this.resizeSvc.onResize$
      .subscribe(x => {
        this.sizeWindow = x;
        this.changeSizes();
      });
  }
  ngAfterViewInit(): void {
    this.finishViewEvent.emit(this.order);
  }
  addItem(index = 0) {
    this.dataForm.submitted = true;
    if (this.f.items.valid) {
      this.dataForm.submitted = false;
      let f: FormGroup = this.formBuilder.group({
        formaPago: [null, [Validators.required]],
        monto: [null, [Validators.required, Validators.min(0.001)]],
        tarjeta: [null, []],
        numero: [null, []],
        asociadaId: [null, []],
        asociadaTipo: [null, []],
        $$asociada: [null, []],
        descripcion: [null, []],
        index: [index, []],
      });
      this.fi.push(f);
      this.setFormaPago(f, 'EFECTIVO'); // por defecto le mandamos siempre efectivo por ahora
      setTimeout(() => {
        $("#selTipoPago" + (this.fi.controls.length - 1)).focus();
      });
      this.changeValueTipo(f);
      // si  tiene mas de un pago autocompletamos con el saldo
      if (this.fi.controls.length > 1) {
        f.patchValue({ monto: this.dataPago.saldo });
      }
      this.refreshCalc();
      f.get('$$asociada').valueChanges.subscribe(resp => {
        if (resp) {
          f.patchValue({ asociadaId: resp.id });
          f.patchValue({ monto: resp.monto });
          // f.get('monto').disable();
          this.refreshCalc();
        } else {
          f.patchValue({ asociadaId: null });
          // f.get('monto').enable();
          this.refreshCalc();
        }
      });

    }
  }

  setFormaPago(f: FormGroup, formaPago: string) {
    this.formasPago.forEach((t: any) => {
      if (formaPago == t.descripcion) {
        f.patchValue({ formaPago: t });
      }
    });
  }
  private refreshCalc() {
    // si hay  un solo pago y es del tipo efectivo autocompletamos el pago automaticamente (&& this.fi.controls[0].value.tipo.descripcion == 'EFECTIVO')
    if (this.fi.controls.length == 1) {
      if (!this.fi.controls[0].value.asociadaId) {
        this.fi.controls[0].patchValue({ monto: this.dataPago.total });
      }
    }
    this.changeValuePago();
  }
  public changeValueTipo(f: FormGroup) {
    if (f.get('formaPago').value.descripcion == 'EFECTIVO') {
      f.get('numero').setValidators([]);
      f.get('tarjeta').setValidators([]);
      f.patchValue({ tarjeta: null });
      f.patchValue({ numero: null });
      f.patchValue({ asociadaTipo: 'TRANSACCION' }); // por ahora hay unt tipo de asociada nomas
    } else if (f.get('formaPago').value.descripcion == 'CUENTA CORRIENTE') {
      f.get('numero').setValidators([]);
      f.get('tarjeta').setValidators([]);
      f.patchValue({ asociadaId: null });
      f.patchValue({ asociadaTipo: null });
      f.patchValue({ tarjeta: null });
      f.patchValue({ numero: null });
    } else {
      f.get('numero').setValidators([Validators.required]);
      f.get('tarjeta').setValidators([Validators.required]);
      f.patchValue({ asociadaId: null });
      f.patchValue({ asociadaTipo: null });
      f.patchValue({ tarjeta: 'INDISTINTO' });
      f.patchValue({ numero: null });
    }
    this.changeSizes();
  }
  public changeValuePago() {
    this.dataPago.pago = 0;
    this.fi.controls.forEach((f: FormGroup) => {
      this.dataPago.pago = f.get('monto').value + this.dataPago.pago;
      this.dataPago.pago = this.round2(this.dataPago.pago);
    });
    this.dataPago.saldo = this.dataPago.total - this.dataPago.pago;
    this.dataPago.saldo = this.round2(this.dataPago.saldo);
    this.dataPago.vuelto = this.dataPago.pago - this.dataPago.total;
    this.dataPago.vuelto = this.round2(this.dataPago.vuelto);
    this.cdr.markForCheck();
  }
  initValue() {
    this.addItem(0);
  }

  reset() {
    this.dataPago = {};
    this.dataPago.total = 0;
    this.dataPago.saldo = 0;
    this.dataPago.vuelto = 0;
    this.dataPago.pago = 0;
    this.fi.clear();
    this.formItems.reset();
    this.formItems.updateValueAndValidity();
    this.addItem(0);
    this.cdr.markForCheck();
  }
  removeItem(item: FormGroup) {
    this.fi.removeAt(this.fi.controls.indexOf(item));
    this.formItems.updateValueAndValidity();
    this.refreshCalc();
  }
  public markAsTouched() {
    this.formItems.markAllAsTouched();
  }
  getDataPagos(): Array<IPago> {
    this.formItems.markAllAsTouched();
    let items: Array<IPago> = this.formItems.get('items').value.map(item => {
      return <IPago>
        {
          asociadaTipo: item.asociadaId ? item.asociadaTipo : null,
          asociadaId: item.asociadaId,
          descripcion: item.descripcion,
          monto: item.monto,
          numero: item.numero,
          tarjeta: item.tarjeta,
          formaPago: item.formaPago,
        };
    });
    return items;
  }

  validateTotal(c: AbstractControl): { pagoMenor: boolean } {
    let pago = 0;
    console.log('c.get(\'items\')[\'controls\']', c.get('items')['controls'])
    c.get('items')['controls'].forEach((f: FormGroup) => {
      pago = f.get('monto').value + pago;
    });
    console.log('pago', pago)
    console.log('c.get(\'total\').value', c.get('total').value)
    return pago < c.get('total').value ? { pagoMenor: true } : null;
  }
  changeSizes(size: number = this.sizeWindow) {
    setTimeout(() => {
      $('.item-pago').removeClass('width-200');
      $('.item-pago-tipo').removeClass('width-200');
      if (size == 0) {
        $('.item-pago').addClass('width-200');
        $('.item-pago-tipo').addClass('width-200');
      }
    });
  }
  get f() { return this.formItems.controls; }
  get fi() { return this.f.items as FormArray; }
}

