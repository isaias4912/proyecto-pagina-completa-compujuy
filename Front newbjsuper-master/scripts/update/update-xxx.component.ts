import { Component, OnInit, ViewChild, ChangeDetectionStrategy } from '@angular/core';
import { IDataForm } from '../../../../core/models/idata-form';
import { ComponentPage } from '../../../../core/component-page';
import { FieldsProdPadreComponent } from '../fields/fields-xxx.component';
import { ProductoPadreHTTPService } from '../../../../core/services/http/producto-padre-http.service';
import { Observable } from 'rxjs';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { pluck } from 'rxjs/operators';
declare var $: any;

@Component({
  selector: 'update-xxx',
  templateUrl: './update-xxx.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class zzzComponent extends ComponentPage implements OnInit {

  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsProdPadreComponent, { static: false })
  fieldsProdPadreComponent: FieldsProdPadreComponent;
  private productoPadre$: Observable<any>

  constructor(
    private productoPadreHTTPService: ProductoPadreHTTPService,
    private activatedRoute: ActivatedRoute,

  ) {
    super();
  }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(
      (params: ParamMap) => {
        this.id = this.decrypt(params.get("idProdPadre"));
        if (this.id) {
          this.productoPadre$ = this.productoPadreHTTPService.getProductoPadreById(this.id).pipe(pluck('data'));
        }
      });
  }

  private save(option: number) {
    this.dataForm = { ...this.dataForm, submitted: true };
    if (this.fieldsProdPadreComponent.formProdPadre.valid) {
      this.productoPadreHTTPService.updateProductoPadre(this.fieldsProdPadreComponent.formProdPadre.value).subscribe(resp => {
        this.messageToast.success("Se modificó correctamente el producto padre :" + resp.data.id + "-" + resp.data.nombre);
        if (option == 1) {
          this.fieldsProdPadreComponent.initFocus();
        }
        if (option == 2) {
          this.location.back();
        }
      });
    }
  }
}

