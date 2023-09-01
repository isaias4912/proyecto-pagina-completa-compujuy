import { Component, OnInit, ViewChild, ChangeDetectionStrategy } from '@angular/core';
import { IDataForm } from '../../../../core/models/idata-form';
import { ComponentPage } from '../../../../core/component-page';
import { ProveedorHTTPService } from '../../../../core/services/http/proveedor-http.service';
import { Observable } from 'rxjs';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { pluck } from 'rxjs/operators';
import { FieldsProvComponent } from '../fields/fields-prov.component';

declare var $: any;

@Component({
  selector: 'update-prov',
  templateUrl: './update-prov.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UpdateProvComponent extends ComponentPage implements OnInit {

  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsProvComponent, { static: false })
  fieldsProvComponent: FieldsProvComponent;
  public proveedor$: Observable<any>

  constructor(
    private proveedorHTTPService: ProveedorHTTPService,
    private activatedRoute: ActivatedRoute,

  ) {
    super();
  }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(
      (params: ParamMap) => {
        this.id = this.decrypt(params.get("idProveedor"));
        if (this.id) {
          this.proveedor$ = this.proveedorHTTPService.getProveedor(this.id).pipe(pluck('data'));
        }
      });
  }

  public save(option: number) {
    this.dataForm = { ...this.dataForm, submitted: true };
    if (this.fieldsProvComponent.formProveedor.valid) {
      this.proveedorHTTPService.updateProveedor(this.id, this.fieldsProvComponent.formProveedor.value).subscribe(resp => {
        if (resp.data.entidad.tipo == 'PERSONA') {
          this.messageToast.success("Se agregó correctamente el proveedor :" + resp.data.id + "-" + resp.data.entidad.persona.apellido +' '+ resp.data.entidad.persona.nombre);
        } else {
          this.messageToast.success("Se agregó correctamente el proveedor :" + resp.data.id + "-" + resp.data.entidad.empresa.razonSocial);
        }
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

