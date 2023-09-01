import { Component, OnInit, ViewChild, ChangeDetectionStrategy } from '@angular/core';
import { IDataForm } from '../../../../core/models/idata-form';
import { ComponentPage } from '../../../../core/component-page';
import { FieldsProdPadreComponent } from '../fields/fields-prod-padre.component';
import { ProductoPadreHTTPService } from '../../../../core/services/http/producto-padre-http.service';
import { Observable } from 'rxjs';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { pluck, tap } from 'rxjs/operators';
import { BreadcrumbService } from 'src/app/core/components/breadcrumb/breadcrumb.service';
declare var $: any;

@Component({
  selector: 'update-prod-padre',
  templateUrl: './update-prod-padre.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UpdateProdPadreComponent extends ComponentPage implements OnInit {

  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsProdPadreComponent, { static: false })
  fieldsProdPadreComponent: FieldsProdPadreComponent;
  public productoPadre$: Observable<any>

  constructor(
    private productoPadreHTTPService: ProductoPadreHTTPService,
    private activatedRoute: ActivatedRoute,
    private breadcrumbService: BreadcrumbService

  ) {
    super();
  }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(
      (params: ParamMap) => {
        this.id = this.decrypt(params.get("idProdPadre"));
        if (this.id) {
          this.productoPadre$ = this.productoPadreHTTPService.getProductoPadreById(this.id).pipe(pluck('data'), tap(resp=>{
            this.breadcrumbService.changeCustomText(resp.nombre);
          }));
        }
      });
  }

  public save(option: number) {
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

