import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, Validators } from '@angular/forms';
import { IDataForm } from '../../../../core/models/idata-form';
import { UtilPage } from '../../../../core/util-page';
import { ComprobanteService } from '../../services/comprobante.service';
declare var $: any;
import { ITributos } from '../../../../core/interfaces';
import { ResizeService } from 'src/app/modules/shared/components/size-detector/resize.service';
import { SelectService } from 'src/app/modules/shared/services/select.service';
import { TributoEventService } from './tributo-event.service';

@Component({
  selector: 'tributos',
  templateUrl: './tributos.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TributosComponent extends UtilPage implements OnInit {

  public formItems: FormGroup;
  public dataForm: IDataForm;
  @Input()
  public tributos: Array<any>;
  @Input()
  public baseImponible: number;
  @Input()
  public showAddItem: boolean = true;
  public totalTributos: number;
  @Output()
  eventChange = new EventEmitter<any>();
  constructor(
    private formBuilder: FormBuilder,
    private cdr: ChangeDetectorRef,
    private comprobanteService: ComprobanteService,
    private resizeSvc: ResizeService,
    private selectService: SelectService,
    private tributoEventService: TributoEventService
  ) {
    super();
  }

  ngOnInit() {
    this.formItems = this.formBuilder.group({
      items: new FormArray([]),
    });
    this.dataForm = { submitted: false };
    // listeners
    this.comprobanteService.totalBaseImponiblleChange$.subscribe((resp: number) => {
      this.baseImponible = resp;
      this.changeBaseImponible(resp);
    });
    // cambios en el size del window
    this.resizeSvc.onResize$
      .subscribe(x => {
        this.sizeWindow = x;
        this.changeSizes();
      });
    this.selectService.select$.subscribe(resp => {
      if (resp.id == "addTributo") {
        this.addItem();
      }
    });
  }
  public addItem(index = 0) {
    this.dataForm.submitted = true;
    if (this.f.items.valid) {
      this.dataForm.submitted = false;
      let f: FormGroup = this.formBuilder.group({
        id: [null, []],
        tributo: [null, [Validators.required]],
        idAfipTpoTributo: [null, [Validators.required]],
        baseImponible: [this.baseImponible, [Validators.required]],
        descAfipTpoTributo: [null, [Validators.required]],
        alicuota: [0, [Validators.required, Validators.min(0)]],
        importe: [null, [Validators.required, Validators.min(0)]],
        descripcion: [null, []],
        index: [index, []],
      });
      this.fi.push(f);
      this.fi.updateValueAndValidity();
      this.cdr.markForCheck();
      setTimeout(() => {
        $("#selTributo" + (this.fi.controls.length - 1)).focus();
      });
      this.changeSizes();
    }
  }
  setSubtotal(subtotal: number) {
    this.baseImponible = subtotal;
    this.refreshCalc();
  }
  changeBaseImponible(baseImponible: number) {
    this.fi.controls.forEach((f: FormGroup) => {
      f.patchValue({ baseImponible: baseImponible });
    });
  }
  private refreshCalc() {
    if (this.fi.controls.length > 0) {
      this.fi.controls.forEach((f: FormGroup) => {
        this.updateImporte(f);
      });
    } else {
      this.changeTotal();
    }
  }
  public changeTributo(fg: FormGroup) {
    let tributo = fg.get('tributo').value;
    if (tributo) {
      fg.patchValue({ idAfipTpoTributo: tributo.id, descAfipTpoTributo: tributo.descripcion });
    } else {
      fg.patchValue({ idAfipTpoTributo: null, descAfipTpoTributo: null });
    }
    fg.updateValueAndValidity();
    this.updateImporte(fg);
  };

  public updateImporte(fg: FormGroup) {
    let alicuota = fg.get('alicuota').value;
    let importe = (alicuota * this.baseImponible) / 100;
    fg.patchValue({ importe: this.round2(importe) });
    this.changeTotal();
  }
  public changeAlicuota(fg: FormGroup) {
    this.updateImporte(fg);
  };
  public changeTotal() {
    this.totalTributos = 0;
    this.fi.controls.forEach((f: FormGroup) => {
      if (f.get('importe').value) {
        this.totalTributos = this.totalTributos + f.get('importe').value;
      }
    });
    this.totalTributos = this.round2(this.totalTributos);;
    this.comprobanteService.changeTotalTributos(this.totalTributos);
  };
  reset() {
    this.fi.clear();
    this.formItems.reset();
    this.formItems.updateValueAndValidity();
    this.cdr.markForCheck();
    this.tributoEventService.changeData("emptyTributo");
  }
  removeItem(item: FormGroup) {
    this.fi.removeAt(this.fi.controls.indexOf(item));
    this.formItems.updateValueAndValidity();
    this.refreshCalc();
    if (this.fi.length == 0) {
      this.tributoEventService.changeData("emptyTributo");
    }
  }
  getDataTributos(): Array<ITributos> {
    this.formItems.markAllAsTouched();
    let items: Array<ITributos> = this.formItems.get('items').value.map(item => {
      return <ITributos>
        {
          id: item.id,
          idAfipTpoTributo: item.idAfipTpoTributo,
          descAfipTpoTributo: item.descAfipTpoTributo,
          descripcion: item.descripcion,
          importe: item.importe,
          alicuota: item.alicuota,
          baseImponible: item.baseImponible,
        };
    });
    return items;
  }
  changeSizes(size: number = this.sizeWindow) {
    setTimeout(() => {
      $('.item-trib-tipo').removeClass('width-100');
      $('.item-trib-desc, .item-trib-bi, .item-trib-alic, .item-trib-subtotal').removeClass('width-100');
      if (size == 0) {
        $('.item-trib-tipo').addClass('width-100');
        $('.item-trib-desc, .item-trib-bi, .item-trib-alic, .item-trib-subtotal').addClass('width-100');
      }
    });
  }
  get f() { return this.formItems.controls; }
  get fi() { return this.f.items as FormArray; }
}

