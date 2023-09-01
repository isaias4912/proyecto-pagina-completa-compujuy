import { Component, OnInit, ViewChild, ChangeDetectionStrategy } from '@angular/core';
import { IDataForm } from 'src/app/core/models/idata-form';
import { ComponentPage } from 'src/app/core/component-page';
declare var $: any;

@Component({
  selector: 'new-xxx',
  templateUrl: './new-xxx.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class zzzComponent extends ComponentPage implements OnInit {

  pripublicvate dataForm: IDataForm = { submitted: false };
  // @ViewChild(FieldsProdPadreComponent, { static: false })
  // fieldsProdPadreComponent: FieldsProdPadreComponent;
  constructor(
    private service: Service
  ) {
    super();
  }

  ngOnInit() {

  }

  private save(option: number) {
    this.dataForm = { ...this.dataForm, submitted: true };
    // if (this.fieldsProdPadreComponent.formProdPadre.valid) {
    //   this.productoPadreHTTPService.insertProductoPadre(this.fieldsProdPadreComponent.formProdPadre.value).subscribe(resp => {
    //     this.messageToast.success("Se agregó correctamente el producto padre :" + resp.data.id + "-" + resp.data.nombre);
    //     if (option == 1) {
    //       this.fieldsProdPadreComponent.reset();
    //     }
    //     if (option == 2) {
    //       this.location.back();
    //     }
    //   });
    // }
  }
}

