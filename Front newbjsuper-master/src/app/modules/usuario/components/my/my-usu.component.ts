import { Component, OnInit, ViewChild, ChangeDetectionStrategy } from '@angular/core';
import { IDataForm } from '../../../../core/models/idata-form';
import { ComponentPage } from '../../../../core/component-page';
import { FieldsUsuComponent } from '../fields/fields-usu.component';
import { UsuarioHTTPService } from '../../../../core/services/http/usuario-http.service';
import { Observable } from 'rxjs';
import { pluck, tap } from 'rxjs/operators';
declare var $: any;

@Component({
  selector: 'my-usu',
  templateUrl: './my-usu.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class MyUsuComponent extends ComponentPage implements OnInit {

  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsUsuComponent, { static: false })
  fieldsUsuComponent: FieldsUsuComponent;
  public usuario$: Observable<any>

  constructor(
    private usuarioHTTPService: UsuarioHTTPService,
  ) {
    super();
  }

  ngOnInit() {
    this.usuario$ = this.usuarioHTTPService.getMy().pipe(pluck('data'), tap(resp => {
      this.id = resp.id;
    }));
  }

  public save(option: number) {
    this.dataForm = { ...this.dataForm, submitted: true };
    if (this.fieldsUsuComponent.formUsuario.valid) {
      this.usuarioHTTPService.updateUsuario(this.id, this.fieldsUsuComponent.formUsuario.value).subscribe(resp => {
        this.messageToast.success("Se modificó mis datos correctamente.");
        if (option == 1) {
          this.fieldsUsuComponent.initFocus();
        }
        if (option == 2) {
          this.location.back();
        }
      });
    }
  }
}

