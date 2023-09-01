import { Component, OnInit, ViewChild, ChangeDetectionStrategy } from '@angular/core';
import { IDataForm } from 'src/app/core/models/idata-form';
import { ComponentPage } from 'src/app/core/component-page';
import { CajaHTTPService } from 'src/app/core/services/http/caja-http.service';
import { FieldsCajaComponent } from '../fields/fields-caja.component';
declare var $: any;

@Component({
  selector: 'new-caja',
  templateUrl: './new-caja.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NewCajaComponent extends ComponentPage implements OnInit {

  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsCajaComponent, { static: false })
  fieldsCajaComponent: FieldsCajaComponent;
  constructor(
    private cajaHTTPService: CajaHTTPService
  ) {
    super();
  }

  ngOnInit() {

  }

  public save(option: number) {
    this.dataForm = { ...this.dataForm, submitted: true };
    if (this.fieldsCajaComponent.formCaja.valid) {
      this.cajaHTTPService.insertCaja(this.fieldsCajaComponent.formCaja.value).subscribe(resp => {
        this.messageToast.success("Se agregó correctamente la caja :" + resp.data.id + "-" + resp.data.nombre);
        if (option == 1) {
          this.fieldsCajaComponent.reset();
        }
        if (option == 2) {
          this.location.back();
        }
      });
    }
  }
}

