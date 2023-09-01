import { Component, OnInit, ViewChild, ChangeDetectionStrategy } from '@angular/core';
import { IDataForm } from '../../../../core/models/idata-form';
import { ComponentPage } from '../../../../core/component-page';
import { Observable } from 'rxjs';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { pluck, tap } from 'rxjs/operators';
import { FieldsFamComponent } from '../fields/fields-fam.component';
import { FamiliaHTTPService } from '../../../../core/services/http/familia-http.service';
import { BreadcrumbService } from 'src/app/core/components/breadcrumb/breadcrumb.service';

declare var $: any;

@Component({
  selector: 'update-fam',
  templateUrl: './update-fam.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UpdateFamComponent extends ComponentPage implements OnInit {

  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsFamComponent, { static: false })
  fieldsFamComponent: FieldsFamComponent;
  public familia$: Observable<any>

  constructor(
    private familiaHTTPService: FamiliaHTTPService,
    private activatedRoute: ActivatedRoute,
    private breadcrumbService: BreadcrumbService
  ) {
    super();
  }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(
      (params: ParamMap) => {
        this.id = this.decrypt(params.get("idFamilia"));
        if (this.id) {
          this.familia$ = this.familiaHTTPService.getFamilia(this.id).pipe(pluck('data'), tap(resp=>{
            this.breadcrumbService.changeCustomText(resp.nombre);
          }
          ));
        }
      });
  }

  public save(option: number) {
    this.dataForm = { ...this.dataForm, submitted: true };
    if (this.fieldsFamComponent.formFamilia.valid) {
      this.familiaHTTPService.updateFamilia(this.id, this.fieldsFamComponent.formFamilia.value).subscribe(resp => {
        this.messageToast.success('Se modifico correctamente la familia de productos :' + resp.data.id + '-' + resp.data.nombre);
        if (option == 1) {
          this.fieldsFamComponent.initFocus();
        }
        if (option == 2) {
          this.location.back();
        }
      });
    }
  }
}

