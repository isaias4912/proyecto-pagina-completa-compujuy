import { Component, OnInit, ViewChild, ChangeDetectionStrategy } from '@angular/core';
import { IDataForm } from '../../../../../core/models/idata-form';
import { ComponentPage } from '../../../../../core/component-page';
// import { FieldsCliComponent } from '../fields/fields-cli.component';
import { ClienteHTTPService } from '../../../../../core/services/http/cliente-http.service';
import { Observable } from 'rxjs';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { pluck } from 'rxjs/operators';
import { ConfigHTTPService } from '../../../../../core/services/http/config-http.service';
import { FieldsAppComponent } from '../fields/fields-app.component';
declare var $: any;

@Component({
  selector: 'update-app',
  templateUrl: './update-app.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UpdateAppComponent extends ComponentPage implements OnInit {

  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsAppComponent, { static: false })
  fieldsAppComponent: FieldsAppComponent;
  public app$: Observable<any>

  constructor(
    private configHTTPService: ConfigHTTPService ,
    private activatedRoute: ActivatedRoute,

  ) {
    super();
  }

  ngOnInit() {
    this.app$ = this.configHTTPService.getAppData().pipe(pluck('data'));
  }

  public save(option: number) {
    this.dataForm = { ...this.dataForm, submitted: true };
    if (this.fieldsAppComponent.formApp.valid) {
      this.configHTTPService.update(this.fieldsAppComponent.formApp.value).subscribe(resp => {
        this.messageToast.success("Se modificó correctamente los datos de la aplicacion <b>" + resp.data.nombre+"</b>");
        if (option == 1) {
          this.fieldsAppComponent.initFocus();
        }
        if (option == 2) {
          this.location.back();
        }
      });
    }
  }
}

