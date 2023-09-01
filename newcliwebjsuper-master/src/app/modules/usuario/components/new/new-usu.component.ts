import { Component, OnInit, ViewChild, ChangeDetectionStrategy } from '@angular/core';
import { IDataForm } from '../../../../core/models/idata-form';
import { ComponentPage } from '../../../../core/component-page';
import { UsuarioHTTPService } from '../../../../core/services/http/usuario-http.service';
import { FieldsUsuComponent } from '../fields/fields-usu.component';
declare var $: any;

@Component({
  selector: 'new-usu',
  templateUrl: './new-usu.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NewUsuComponent extends ComponentPage implements OnInit {

  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsUsuComponent, { static: false })
  fieldsUsuComponent: FieldsUsuComponent;
  constructor(
    private usuarioHTTPService: UsuarioHTTPService 
  ) {
    super();
  }

  ngOnInit() {

  }

  public save(option: number) {
    this.dataForm = { ...this.dataForm, submitted: true };
    if (this.fieldsUsuComponent.formUsuario.valid) {
      this.usuarioHTTPService.insertUsuario(this.fieldsUsuComponent.formUsuario.value).subscribe(resp => {
        this.messageToast.success("Se agregó correctamente el usuario: " + resp.data.usuario);
        if (option == 1) {
          this.fieldsUsuComponent.reset();
        }
        if (option == 2) {
          this.location.back();
        }
      });
    }
  }
}

