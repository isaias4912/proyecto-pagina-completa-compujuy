import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectionStrategy, OnDestroy } from '@angular/core';
import { IDataForm } from '../../../../../core/models/idata-form';
import { ProductosHTTPService } from '../../../../../core/services/http/product-http.service';
import { ComponentPage } from '../../../../../core/component-page';
import { FieldsExpirationComponent } from '../fields/fields-expiration.component';

declare var $: any;

@Component({
  selector: 'new-expiration',
  templateUrl: './new-expiration.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NewExpirationComponent extends ComponentPage implements OnInit, AfterViewInit, OnDestroy {

  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsExpirationComponent, { static: false })
  private fields: FieldsExpirationComponent;
  constructor(
    private productosHTTPService: ProductosHTTPService
  ) {
    super();
  }

  ngOnInit() {
  }
  ngAfterViewInit(): void {
  }

  saveExpiration(option: number = 0) {
    this.dataForm = { ...this.dataForm, submitted: true }
    if (this.fields.formExpiration.valid) {
      this.productosHTTPService.insertVencimiento(this.fields.formExpiration.value).subscribe(resp => {
        this.messageToast.success("Se agregó correctamente el vencimiento (id/código " + resp.data.id + ")");
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