import { Component, OnInit, ViewChild, ChangeDetectionStrategy } from '@angular/core';
import { IDataForm } from 'src/app/core/models/idata-form';
import { ComponentPage } from 'src/app/core/component-page';
import { OfertaHTTPService } from 'src/app/core/services/http/oferta-http.service';
import { FieldsOferComponent } from '../fields/fields-ofer.component';
declare var $: any;

@Component({
  selector: 'new-ofer',
  templateUrl: './new-ofer.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NewOferComponent extends ComponentPage implements OnInit {

  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsOferComponent, { static: false })
  fieldsOferComponent: FieldsOferComponent;
  constructor(
    private ofertaHTTPService: OfertaHTTPService
  ) {
    super();
  }

  ngOnInit() {

  }

  public save(option: number) {
    this.dataForm = { ...this.dataForm, submitted: true };
    if (this.fieldsOferComponent.formOferta.valid) {
      this.ofertaHTTPService.insertOferta(this.fieldsOferComponent.formOferta.value).subscribe(resp => {
        this.messageToast.success("Se agregó correctamente la oferta:" + resp.data.id + "-" + resp.data.nombre);
        if (option == 1) {
          this.fieldsOferComponent.reset();
        }
        if (option == 2) {
          this.location.back();
        }
      });
    }
  }
}

