import { Component, OnInit, ViewChild, ChangeDetectionStrategy } from '@angular/core';
import { IDataForm } from '../../../../../../core/models/idata-form';
import { ComponentPage } from '../../../../../../core/component-page';
import { CompraHTTPService } from '../../../../../../core/services/http/compra-http.service';
import { FieldsFactCompComponent } from '../fields/fields-fact-comp.component';
declare var $: any;

@Component({
  selector: 'new-fact-comp',
  templateUrl: './new-fact-comp.component.html',
  styleUrls: ['./new-fact-comp.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NewFactCompComponent extends ComponentPage implements OnInit {

  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsFactCompComponent, { static: false })
  fieldsFactCompComponent: FieldsFactCompComponent;
  constructor(
    private compraHTTPService: CompraHTTPService
  ) {
    super();
  }

  ngOnInit() {

  }
  public reset() {
    this.fieldsFactCompComponent.reset();
  }
  public save(option: number) {
    this.dataForm = { ...this.dataForm, submitted: true };
    if (this.fieldsFactCompComponent.isValid()) {
      this.compraHTTPService.insertCbteComp(this.fieldsFactCompComponent.getData()).subscribe(resp => {
        this.messageToast.success('Factura de compra correctamente cargada.');
        if (option == 1) {
          this.fieldsFactCompComponent.reset();
        }
        if (option == 2) {
          this.location.back();
        }
      })
    }
  }
}

