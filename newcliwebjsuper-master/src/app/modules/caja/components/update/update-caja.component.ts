import { Component, OnInit, ViewChild, ChangeDetectionStrategy } from '@angular/core';
import { IDataForm } from '../../../../core/models/idata-form';
import { ComponentPage } from '../../../../core/component-page';
import { CajaHTTPService } from '../../../../core/services/http/caja-http.service';
import { Observable } from 'rxjs';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { pluck } from 'rxjs/operators';
import { FieldsCajaComponent } from '../fields/fields-caja.component';

declare var $: any;

@Component({
  selector: 'update-caja',
  templateUrl: './update-caja.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UpdateCajaComponent extends ComponentPage implements OnInit {

  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsCajaComponent, { static: false })
  fieldsCajaComponent: FieldsCajaComponent;
  public caja$: Observable<any>

  constructor(
    private cajaHTTPService: CajaHTTPService,
    private activatedRoute: ActivatedRoute,

  ) {
    super();
  }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(
      (params: ParamMap) => {
        this.id = this.decrypt(params.get("idCaja"));
        if (this.id) {
          this.caja$ = this.cajaHTTPService.getCaja(this.id).pipe(pluck('data'));
        }
      });
  }

  public save(option: number) {
    this.dataForm = { ...this.dataForm, submitted: true };

    if (this.fieldsCajaComponent.formCaja.valid) {
      this.cajaHTTPService.updateCaja(this.id, this.fieldsCajaComponent.formCaja.value).subscribe(resp => {
        this.messageToast.success("Se modificó correctamentela caja :" + resp.data.id + "-" + resp.data.nombre);
        if (option == 1) {
          this.fieldsCajaComponent.initFocus();
        }
        if (option == 2) {
          this.location.back();
        }
      });
    }
  }
}

