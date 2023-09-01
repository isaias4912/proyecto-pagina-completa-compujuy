import { Component, OnInit, ViewChild, ChangeDetectionStrategy } from '@angular/core';
import { IDataForm } from 'src/app/core/models/idata-form';
import { ComponentPage } from 'src/app/core/component-page';
import { FieldsProdPadreComponent } from '../fields/fields-prod-padre.component';
import { ProductoPadreHTTPService } from 'src/app/core/services/http/producto-padre-http.service';
declare var $: any;

@Component({
  selector: 'new-prod-padre',
  templateUrl: './new-prod-padre.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NewProdPadreComponent extends ComponentPage implements OnInit {

  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsProdPadreComponent, { static: false })
  fieldsProdPadreComponent: FieldsProdPadreComponent;
  constructor(
    private productoPadreHTTPService: ProductoPadreHTTPService
  ) {
    super();
  }

  ngOnInit() {

  }

  public save(option: number) {
    this.dataForm = { ...this.dataForm, submitted: true };
    if (this.fieldsProdPadreComponent.formProdPadre.valid) {
      this.productoPadreHTTPService.insertProductoPadre(this.fieldsProdPadreComponent.formProdPadre.value).subscribe(resp => {
        this.messageToast.success("Se agregó correctamente el producto padre :" + resp.data.id + "-" + resp.data.nombre);
        if (option == 1) {
          this.fieldsProdPadreComponent.reset();
        }
        if (option == 2) {
          this.location.back();
        }
      });
    }
  }
}

