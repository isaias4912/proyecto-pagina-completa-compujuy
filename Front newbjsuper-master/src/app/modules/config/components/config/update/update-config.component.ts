import { Component, OnInit, ViewChild, ChangeDetectionStrategy } from '@angular/core';
import { IDataForm } from '../../../../../core/models/idata-form';
import { ComponentPage } from '../../../../../core/component-page';
// import { FieldsCliComponent } from '../fields/fields-cli.component';
import { ClienteHTTPService } from '../../../../../core/services/http/cliente-http.service';
import { Observable } from 'rxjs';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { pluck } from 'rxjs/operators';
import { ConfigHTTPService } from '../../../../../core/services/http/config-http.service';
import { FieldsConfigComponent } from '../fields/fields-config.component';
import { ConfigurationService } from 'src/app/modules/shared/services/configuration.service';
// import { FieldsAppComponent } from '../fields/fields-app.component';
declare var $: any;

@Component({
  selector: 'update-config',
  templateUrl: './update-config.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UpdateConfigComponent extends ComponentPage implements OnInit {

  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsConfigComponent, { static: false })
  fieldsConfigComponent: FieldsConfigComponent;
  public config$: Observable<any>

  constructor(
    private configHTTPService: ConfigHTTPService ,
    private activatedRoute: ActivatedRoute,
    private configurationService: ConfigurationService

  ) {
    super();
  }

  ngOnInit() {
    this.config$ = this.configHTTPService.getConfiguracion().pipe(pluck('data'));
  }

  public save(option: number) {
    this.dataForm = { ...this.dataForm, submitted: true };
    if (this.fieldsConfigComponent.formConfig.valid) {
      this.configHTTPService.updateConfiguracion(this.fieldsConfigComponent.formConfig.value).subscribe(resp => {
        this.messageToast.success("Se modificó correctamente los datos de configuración");
        // refrescamos la configuracion ya guardada
        this.configurationService.clearConfig();
        this.configurationService.checkConfiguration();
        if (option == 1) {
          this.fieldsConfigComponent.initFocus();
        }
        if (option == 2) {
          this.location.back();
        }
      });
    }else{
      this.messageToast.error('Existe un campo invalido en una de las pestañas, revise y corrija los errores.');
    }
  }
}

