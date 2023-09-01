import { Component, OnInit, ViewChild, ChangeDetectionStrategy } from '@angular/core';
import { IDataForm } from '../../../../core/models/idata-form';
import { ComponentPage } from '../../../../core/component-page';
import { FieldsCliComponent } from '../fields/fields-cli.component';
import { ClienteHTTPService } from '../../../../core/services/http/cliente-http.service';
import { Observable } from 'rxjs';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { pluck } from 'rxjs/operators';
declare var $: any;

@Component({
  selector: 'update-cli',
  templateUrl: './update-cli.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UpdateCliComponent extends ComponentPage implements OnInit {

  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsCliComponent, { static: false })
  fieldsCliComponent: FieldsCliComponent;
  public cliente$: Observable<any>

  constructor(
    private clienteHTTPService: ClienteHTTPService,
    private activatedRoute: ActivatedRoute,

  ) {
    super();
  }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(
      (params: ParamMap) => {
        this.id = this.decrypt(params.get("idCliente"));
        if (this.id) {
          this.cliente$ = this.clienteHTTPService.getCliente(this.id).pipe(pluck('data'));
        }
      });
  }

  public save(option: number) {
    this.dataForm = { ...this.dataForm, submitted: true };
    if (this.fieldsCliComponent.formCliente.valid) {
      this.clienteHTTPService.updateCliente(this.id, this.fieldsCliComponent.formCliente.value).subscribe(resp => {
        if (resp.data.entidad.tipo == 'PERSONA') {
          this.messageToast.success("Se modificó correctamente el cliente :" + resp.data.entidad.persona.apellido + ' ' + resp.data.entidad.persona.nombre);
        } else {
          this.messageToast.success("Se modificó correctamente el cliente :" + resp.data.entidad.empresa.razonSocial);
        }        if (option == 1) {
          this.fieldsCliComponent.initFocus();
        }
        if (option == 2) {
          this.location.back();
        }
      });
    }
  }
}

