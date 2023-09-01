import { Component, OnInit, ViewChild, ChangeDetectionStrategy } from '@angular/core';
import { IDataForm } from '../../../../core/models/idata-form';
import { ComponentPage } from '../../../../core/component-page';
import { ClienteHTTPService } from '../../../../core/services/http/cliente-http.service';
import { FieldsCliComponent } from '../fields/fields-cli.component';
import { Cliente } from 'src/app/core/models/cliente';
import { Response } from 'src/app/core/services/utils/response';

declare var $: any;

@Component({
  selector: 'new-cli',
  templateUrl: './new-cli.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NewCliComponent extends ComponentPage implements OnInit {

  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsCliComponent, { static: false })
  fieldsCliComponent: FieldsCliComponent;
  constructor(
    private clienteHTTPService: ClienteHTTPService
  ) {
    super();
  }

  ngOnInit() {

  }

  public save(option: number) {
    this.dataForm = { ...this.dataForm, submitted: true };
    if (this.fieldsCliComponent.formCliente.valid) {
      this.clienteHTTPService.insertCliente(this.fieldsCliComponent.formCliente.value).subscribe((resp: Response<Cliente>) => {
        if (resp.data.entidad.tipo == 'PERSONA') {
          this.messageToast.success("Se agregó correctamente el cliente :" + resp.data.entidad.persona.apellido + ' ' + resp.data.entidad.persona.nombre);
        } else {
          this.messageToast.success("Se agregó correctamente el cliente :" + resp.data.entidad.empresa.razonSocial);
        }
        if (option == 1) {
          this.fieldsCliComponent.reset();
        }
        if (option == 2) {
          this.location.back();
        }
      });
    }
  }
}

