import { Component, OnInit, ViewChild, ChangeDetectionStrategy, Output, EventEmitter, Input } from '@angular/core';
import { IDataForm } from 'src/app/core/models/idata-form';
import { ComponentPage } from 'src/app/core/component-page';
import { FamiliaHTTPService } from 'src/app/core/services/http/familia-http.service';
import { FieldsFamComponent } from '../fields/fields-fam.component';
declare var $: any;

@Component({
  selector: 'new-fam',
  templateUrl: './new-fam.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NewFamComponent extends ComponentPage implements OnInit {
  @Input()
  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsFamComponent, { static: false })
  fieldsFamComponent: FieldsFamComponent;
  @Output()
  eventSave = new EventEmitter<any>();
  @Input()
  public buttonAction: boolean = true;
  constructor(
    private familiaHTTPService: FamiliaHTTPService
  ) {
    super();
  }

  ngOnInit() {

  }

  public save(option: number) {
    this.dataForm = { ...this.dataForm, submitted: true };
    if (this.fieldsFamComponent.formFamilia.valid) {
      if (this.fieldsFamComponent.validateNivel()) {
        this.familiaHTTPService.insertFamilia(this.fieldsFamComponent.formFamilia.value).subscribe(response => {
          this.messageToast.success("Se agrego correctamente la familia de productos :" + response.data.id + "-" + response.data.nombre);
          if (option == 1) {
            this.fieldsFamComponent.reset();
          }
          if (option == 2) {
            this.location.back();
          }
          this.eventSave.emit(response);
        });
      }
    }
  }
}

