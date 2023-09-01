import { Component, OnInit, ViewChild, ChangeDetectionStrategy } from '@angular/core';
import { IDataForm } from '../../../../core/models/idata-form';
import { ComponentPage } from '../../../../core/component-page';
import { ProveedorHTTPService } from '../../../../core/services/http/proveedor-http.service';
import { Observable } from 'rxjs';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { pluck } from 'rxjs/operators';
import { FieldsEmpComponent } from '../fields/fields-emp.component';
import { EmpresaHTTPService } from 'src/app/core/services/http/empresa-http.service';

declare var $: any;

@Component({
  selector: 'update-emp',
  templateUrl: './update-emp.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UpdateEmpComponent extends ComponentPage implements OnInit {

  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsEmpComponent, { static: false })
  fieldsProvComponent: FieldsEmpComponent;
  public empresa$: Observable<any>

  constructor(
    private empresaHTTPService: EmpresaHTTPService,
    private activatedRoute: ActivatedRoute,

  ) {
    super();
  }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(
      (params: ParamMap) => {
        this.id = this.decrypt(params.get("idEmpresa"));
        if (this.id) {
          this.empresa$ = this.empresaHTTPService.getEmpresa(this.id).pipe(pluck('data'));
        }
      });
  }

  public save(option: number) {
    this.dataForm = { ...this.dataForm, submitted: true };
    if (this.fieldsProvComponent.isValid()) {
      this.empresaHTTPService.updateEmpresa(this.id, this.fieldsProvComponent.formEmpresa.value).subscribe(resp => {
        this.messageToast.success("Se modificó correctamente la empresa :" + resp.data.id + "-" + resp.data.razonSocial);
        if (option == 1) {
          this.fieldsProvComponent.initFocus();
        }
        if (option == 2) {
          this.location.back();
        }
      });
    }
  }
}

