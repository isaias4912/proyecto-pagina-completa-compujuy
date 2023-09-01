import { Component, OnInit, ViewChild, ChangeDetectionStrategy, Input, Output, EventEmitter } from '@angular/core';
import { IDataForm } from 'src/app/core/models/idata-form';
import { ComponentPage } from 'src/app/core/component-page';
import { PersonaHTTPService } from 'src/app/core/services/http/persona-http.service';
import { FieldsPerComponent } from '../fields/fields-per.component';
declare var $: any;

@Component({
  selector: 'new-per',
  templateUrl: './new-per.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NewPerComponent extends ComponentPage implements OnInit {

  @Input()
  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsPerComponent, { static: false })
  fieldsPerComponent: FieldsPerComponent;
  @Input()
  public buttonAction: boolean = true;
  @Output()
  eventSave = new EventEmitter<any>();
  constructor(
    private personaHTTPService: PersonaHTTPService,
  ) {
    super();
  }

  ngOnInit() {

  }

  public save(option: number) {
    this.dataForm = { ...this.dataForm, submitted: true };
    if (this.fieldsPerComponent.isValid()) {
      this.personaHTTPService.insertPersona(this.fieldsPerComponent.formPersona.value).subscribe(resp => {
        this.messageToast.success("Se agrego correctamente la persona: " + resp.data.apellido + " " + resp.data.nombre);
        if (option == 1) {
          this.fieldsPerComponent.reset();
        }
        if (option == 2) {
          this.location.back();
        }
        this.eventSave.emit(resp)
      });
    }
  }
}

