import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, Validators } from '@angular/forms';
import { IDataForm } from '../../../../core/models/idata-form';
import { UtilPage } from 'src/app/core/util-page';
import { ComprobanteService } from '../../services/comprobante.service';
declare var $: any;
import { IImpuesto } from '../../../../core/interfaces';

@Component({
  selector: 'impuestos',
  templateUrl: './impuestos.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ImpuestosComponent extends UtilPage implements OnInit, AfterViewInit {

  public formItems: FormGroup;
  public dataForm: IDataForm;
  @Input()
  public impuestos: Array<any>;
  @Input()
  public subtotal: number;
  public totalimpuestos: number;
  @Output()
  eventChange = new EventEmitter<any>();
  constructor(
    private formBuilder: FormBuilder,
    private cdr: ChangeDetectorRef,
    private comprobanteService: ComprobanteService
  ) {
    super();
  }

  ngOnInit() {
    this.formItems = this.formBuilder.group({
      items: new FormArray([]),
    });
    this.dataForm = { submitted: false };
    // listeners
    this.comprobanteService.subtotalChange$.subscribe((resp: number) => {
      this.subtotal = resp;
      this.refreshCalc();
    })
  }
  addItem(index = 0) {
    this.dataForm.submitted = true;
    if (this.f.items.valid) {
      this.dataForm.submitted = false;
      let f: FormGroup = this.formBuilder.group({
        id: [null, []],
        nombre: [null, [Validators.required]],
        impuesto: [null, [Validators.required]],
        porcentaje: [null, [Validators.required]],
        importe: [null, [Validators.required]],
        index: [index, []],
      });
      this.fi.push(f);
      setTimeout(() => {
        $("#selImpuesto" + (this.fi.controls.length - 1)).focus();
      });
    }
  }
  setSubtotal(subtotal: number) {
    this.subtotal = subtotal;
    this.refreshCalc();
  }
  private refreshCalc() {
    if (this.fi.controls.length > 0) {
      this.fi.controls.forEach((f: FormGroup) => {
        this.changePorcentaje(f);
      });
    } else {
      this.changeTotal();
    }
  }
  public changeImpuesto(fg: FormGroup) {
    fg.patchValue({ porcentaje: fg.get('impuesto').value.porcentaje });
    let importe = (fg.get('impuesto').value.porcentaje * this.subtotal) / 100;
    fg.patchValue({ importe: this.round2(importe) });
    fg.patchValue({ nombre: fg.get('impuesto').value.nombre });
    fg.updateValueAndValidity();
    this.changeTotal();
  };

  public changePorcentaje(fg: FormGroup) {
    let importe = (fg.get('porcentaje').value * this.subtotal) / 100;
    fg.patchValue({ importe: this.round2(importe) });
    fg.updateValueAndValidity();
    this.changeTotal();
  };
  public changeTotal() {
    this.totalimpuestos = 0;
    this.fi.controls.forEach((f: FormGroup) => {
      if (f.get('importe').value) {
        this.totalimpuestos = this.totalimpuestos + f.get('importe').value;
      }
    });
    this.totalimpuestos = this.round2(this.totalimpuestos);;
    // this.eventChange.emit(this.totalimpuestos);
    this.comprobanteService.changeTotalImpuestos(this.totalimpuestos);
  };
  reset() {
    this.fi.clear();
    this.formItems.reset();
    this.formItems.updateValueAndValidity();
    this.cdr.markForCheck();
  }
  removeItem(item: FormGroup) {
    this.fi.removeAt(this.fi.controls.indexOf(item));
    this.formItems.updateValueAndValidity();
    this.refreshCalc();
  }
  getDataImpuestos(): Array<IImpuesto> {
    let items: Array<IImpuesto> = this.formItems.get('items').value.map(item => {
      return <IImpuesto>
        {
          id: item.id,
          nombre: item.nombre,
          importe: item.importe,
          porcentaje: item.porcentaje,
          impuesto: item.impuesto,
        };
    });
    return items;
  }
  ngAfterViewInit(): void {
  }

  get f() { return this.formItems.controls; }
  get fi() { return this.f.items as FormArray; }
}

