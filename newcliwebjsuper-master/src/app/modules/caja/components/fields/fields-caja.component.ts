import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input, ChangeDetectorRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { ComponentPage } from 'src/app/core/component-page';
import { Fields } from 'src/app/core/fields';
import { IDataForm } from 'src/app/core/models/idata-form';
import { Caja } from 'src/app/core/models/caja';
import { Observable } from 'rxjs';
import { CajaHTTPService } from 'src/app/core/services/http/caja-http.service';
import { finalize, pluck } from 'rxjs/operators';
import { UtilPage } from 'src/app/core/util-page';
import 'select2';
import { SelectDualListComponent } from 'src/app/modules/shared/components/sucursal/select-dual-list/select-dual-list.component';
declare var $: any;

@Component({
  selector: 'fields-caja',
  templateUrl: './fields-caja.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FieldsCajaComponent extends UtilPage implements OnInit, AfterViewInit, Fields {

  @Input()
  public dataForm: IDataForm = { submitted: false };
  @Input()
  public formCaja: FormGroup;
  @Input()
  public caja: Caja;
  public dataCaja$: Observable<any> = null;
  @ViewChild(SelectDualListComponent, { static: false })
  selectDualListComponent: SelectDualListComponent;

  dataCbtes = [
    { id: 1, text: "Facturas electrónicas", selected: false },
    { id: 20, text: "Tickes no validos", selected: false }
  ];
  constructor(
    private formBuilder: FormBuilder,
    private cajaHTTPService: CajaHTTPService,
    private cdr: ChangeDetectorRef
  ) {
    super();
  }

  ngOnInit() {
    this.dataCaja$ = this.cajaHTTPService.getDataCaja().pipe(pluck('data'), finalize(() => {
      this.loadingFinishView(1);
      this.loadComponents([...this.dataCbtes]);
    }));
    this.formCaja = this.formBuilder.group({
      nombre: [this.caja ? this.caja.nombre : null, [Validators.required, Validators.minLength(3), Validators.maxLength(15)]],
      nombreMaquina: [this.caja ? this.caja.nombreMaquina : null, [Validators.required, Validators.minLength(2), Validators.maxLength(25)]],
      limiteConsumidorFinal: [this.caja ? this.caja.limiteConsumidorFinal : null, [Validators.required, Validators.min(1), Validators.max(999999)]],
      idPuntoVenta: [this.caja ? this.caja.idPuntoVenta : null, []],
      observacion: [this.caja ? this.caja.nombre : null, [Validators.maxLength(5000)]],
      modificaPrecio: [this.caja ? (this.caja.modificaPrecio ? this.caja.modificaPrecio : false) : false, [Validators.required]],
      modificaDescuento: [this.caja ? (this.caja.modificaDescuento ? this.caja.modificaDescuento : false) : false, [Validators.required]],
      modificaAdicional: [this.caja ? (this.caja.modificaAdicional ? this.caja.modificaAdicional : false) : false, [Validators.required]],
      conControlEstricto: [this.caja ? (this.caja.conControlEstricto ? this.caja.conControlEstricto : false) : false, [Validators.required]],
      comprobantesHab: [this.caja ? this.caja.comprobantesHab : null, [Validators.required]],
      sucursales: [this.caja ? this.caja.sucursales : null, [Validators.required]],
      // sucursales: new FormControl([])
    });
  }
  ngAfterViewInit(): void {
    this.initFocus();
  }
  reset() {
    this.formCaja.patchValue({
      nombre: null,
      nombreMaquina: null,
      limiteConsumidorFinal: null,
      idPuntoVenta: null,
      observacion: null,
      modificaPrecio: false,
      modificaDescuento: false,
      modificaAdicional: false,
      conControlEstricto: false,
      comprobantesHab: null,
      sucursales: null
    });
    this.dataForm = { ...this.dataForm, submitted: false };
    this.selectDualListComponent.reset();
    $('#cmbCbteHab').val(null).trigger('change');
    this.initFocus();
  }
  initFocus() {
    setTimeout(() => {
      $("#txtCajaNombre").focus();
    });
  }
  get f() {
    return this.formCaja.controls;
  }
  loadComponents(data) {
    if (this.caja) {
      if (this.caja.comprobantesHab) {
        let comprobantes = JSON.parse(this.caja.comprobantesHab);
        data.forEach((c1) => {
          comprobantes.forEach((c2) => {
            if (c1.id == c2) {
              c1.selected = true;
            }
          });
        });
      }
    }
    setTimeout(() => {
      $("#cmbCbteHab").select2({
        data: data,
        theme: 'bootstrap4',
        placeholder: "Seleccione una sucursal"
      });
      $('#cmbCbteHab').on("select2:select", (e) => {
        this.formCaja.patchValue({ comprobantesHab: this.getComprobantes() });
        this.formCaja.updateValueAndValidity();
        this.cdr.markForCheck();
      });
      $('#cmbCbteHab').on("select2:unselect", (e) => {
        this.formCaja.patchValue({ comprobantesHab: this.getComprobantes() });
        this.formCaja.updateValueAndValidity();
        this.cdr.markForCheck();
      });
      this.initFocus();
      this.loadHelp()
    });
  }


  getComprobantes() {
    let tempCbtes = $("#cmbCbteHab").select2("val");
    if (tempCbtes != null) {
      let tempRes = [];
      tempCbtes.forEach((id) => {
        tempRes.push(parseInt(id));
      });
      let resp = JSON.stringify(tempRes);
      return resp == '[]' ? null : resp;
    } else {
      return null;
    }
  }
}

