import { Component, OnInit, ViewChild, ChangeDetectionStrategy } from '@angular/core';
import { IDataForm } from '../../../../core/models/idata-form';
import { ComponentPage } from '../../../../core/component-page';
import { ProveedorHTTPService } from '../../../../core/services/http/proveedor-http.service';
import { FieldsEmpComponent } from '../fields/fields-emp.component';
import { EmpresaHTTPService } from 'src/app/core/services/http/empresa-http.service';

declare var $: any;

@Component({
  selector: 'new-emp',
  templateUrl: './new-emp.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NewEmpComponent extends ComponentPage implements OnInit {

  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsEmpComponent, { static: false })
  fieldsProvComponent: FieldsEmpComponent;
  constructor(
    private empresaHTTPService: EmpresaHTTPService,
  ) {
    super();
  }

  ngOnInit() {

  }

  public save(option: number) {
    this.dataForm = { ...this.dataForm, submitted: true };
    if (this.fieldsProvComponent.isValid()) {
      this.empresaHTTPService.insertEmpresa(this.fieldsProvComponent.formEmpresa.value).subscribe(resp => {
        this.messageToast.success("Se agregó correctamente la empresa :" + resp.data.id + "-" + resp.data.razonSocial);
        if (option == 1) {
          this.fieldsProvComponent.reset();
        }
        if (option == 2) {
          this.location.back();
        }
      });
    }
  }
}

