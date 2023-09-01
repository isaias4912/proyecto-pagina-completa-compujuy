import { Component, OnInit, ViewChild, ChangeDetectionStrategy } from '@angular/core';
import { IDataForm } from '../../../../core/models/idata-form';
import { ComponentPage } from '../../../../core/component-page';
import { PersonaHTTPService } from '../../../..//core/services/http/persona-http.service';
import { Observable } from 'rxjs';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { pluck } from 'rxjs/operators';
import { FieldsPerComponent } from '../fields/fields-per.component';

declare var $: any;

@Component({
  selector: 'update-per',
  templateUrl: './update-per.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UpdatePerComponent extends ComponentPage implements OnInit {

  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsPerComponent, { static: false })
  fieldsPerComponent: FieldsPerComponent;
  public persona$: Observable<any>

  constructor(
    private personaHTTPService: PersonaHTTPService,
    private activatedRoute: ActivatedRoute,

  ) {
    super();
  }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(
      (params: ParamMap) => {
        this.id = this.decrypt(params.get("idPersona"));
        if (this.id) {
          this.persona$ = this.personaHTTPService.getPersona(this.id).pipe(pluck('data'));
        }
      });
  }

  public save(option: number) {
    this.dataForm = { ...this.dataForm, submitted: true };
    if (this.fieldsPerComponent.isValid()) {
      this.personaHTTPService.updatePersona(this.id,this.fieldsPerComponent.formPersona.value).subscribe(resp => {
        this.messageToast.success("Se modifico correctamente la Persona : <b>" + resp.data.id + "-" + resp.data.apellido + " " + resp.data.nombre + "</b>");
        if (option == 1) {
          this.fieldsPerComponent.initFocus();
        }
        if (option == 2) {
          this.location.back();
        }
      });
    }
  }
}

