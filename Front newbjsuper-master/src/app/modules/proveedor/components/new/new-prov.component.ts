import { Component, OnInit, ViewChild, ChangeDetectionStrategy } from '@angular/core';
import { IDataForm } from '../../../../core/models/idata-form';
import { ComponentPage } from '../../../../core/component-page';
import { ProveedorHTTPService } from '../../../../core/services/http/proveedor-http.service';
import { FieldsProvComponent } from '../fields/fields-prov.component';

declare var $: any;

@Component({
  selector: 'new-prov',
  templateUrl: './new-prov.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NewProvComponent extends ComponentPage implements OnInit {

  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsProvComponent, { static: false })
  fieldsProvComponent: FieldsProvComponent;
  constructor(
    private proveedorHTTPService: ProveedorHTTPService,
  ) {
    super();
  }

  ngOnInit() {

  }

  public save(option: number) {
    this.dataForm = { ...this.dataForm, submitted: true };
    if (this.fieldsProvComponent.formProveedor.valid) {
      this.proveedorHTTPService.insertProveedor(this.fieldsProvComponent.formProveedor.value).subscribe(resp => {
        if (resp.data.entidad.tipo == 'PERSONA') {
          this.messageToast.success("Se agregó correctamente el proveedor :" + resp.data.id + "-" + resp.data.entidad.persona.apellido +' '+ resp.data.entidad.persona.nombre);
        } else {
          this.messageToast.success("Se agregó correctamente el proveedor :" + resp.data.id + "-" + resp.data.entidad.empresa.razonSocial);
        }
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

