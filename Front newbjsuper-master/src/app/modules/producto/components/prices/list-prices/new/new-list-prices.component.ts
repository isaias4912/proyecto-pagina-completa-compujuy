import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, OnDestroy } from '@angular/core';
import { IDataForm } from '../../../../../../core/models/idata-form';
import { ProductosHTTPService } from '../../../../../../core/services/http/product-http.service';
import { ComponentPage } from '../../../../../../core/component-page';
import { FieldsListPricesComponent } from '../fields/fields-list-prices.component';

declare var $: any;

@Component({
  selector: 'new-list-prices',
  templateUrl: './new-list-prices.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NewListPricesComponent extends ComponentPage implements OnInit, AfterViewInit, OnDestroy {

  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsListPricesComponent, { static: false })
  private fields: FieldsListPricesComponent;
  constructor(
    private productosHTTPService: ProductosHTTPService
  ) {
    super();
  }

  ngOnInit() {
  }
  ngAfterViewInit(): void {
  }

  saveListPrice(option: number = 0) {
    this.dataForm = { ...this.dataForm, submitted: true }
    if (this.fields.formLista.valid) {
      this.productosHTTPService.saveListaPrecio(this.fields.formLista.value).subscribe(resp => {
        this.messageToast.success("Se agregó correctamente la lista de precios "+ resp.data.nombre);
        if (option == 1) {
          this.fields.reset();
        }
        if (option == 2) {
          this.location.back();
        }
      });
    }
  }
  ngOnDestroy(): void {
  }
}