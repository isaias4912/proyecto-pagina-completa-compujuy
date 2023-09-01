import { Component, OnInit, ViewChild, ChangeDetectionStrategy } from '@angular/core';
import { IDataForm } from '../../../../core/models/idata-form';
import { ComponentPage } from '../../../../core/component-page';
import { FieldsUsuComponent } from '../fields/fields-usu.component';
import { UsuarioHTTPService } from '../../../../core/services/http/usuario-http.service';
import { Observable } from 'rxjs';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { pluck } from 'rxjs/operators';
declare var $: any;

@Component({
  selector: 'update-usu',
  templateUrl: './update-usu.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UpdateUsuComponent extends ComponentPage implements OnInit {

  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsUsuComponent, { static: false })
  fieldsUsuComponent: FieldsUsuComponent;
  public usuario$: Observable<any>

  constructor(
    private usuarioHTTPService: UsuarioHTTPService,
    private activatedRoute: ActivatedRoute,

  ) {
    super();
  }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(
      (params: ParamMap) => {
        this.id = this.decrypt(params.get("idUsuario"));
        if (this.id) {
          this.usuario$ = this.usuarioHTTPService.getUsuario(this.id).pipe(pluck('data'));
        }
      });
  }

  public save(option: number) {
    this.dataForm = { ...this.dataForm, submitted: true };
    if (this.fieldsUsuComponent.formUsuario.valid) {
      this.usuarioHTTPService.updateUsuario(this.id, this.fieldsUsuComponent.formUsuario.value).subscribe(resp => {
        this.messageToast.success("Se modificó correctamente el usuario " + resp.data.usuario);
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

