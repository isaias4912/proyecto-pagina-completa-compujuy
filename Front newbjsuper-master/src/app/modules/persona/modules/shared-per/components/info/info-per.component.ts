import { Component, OnInit, Input, AfterViewChecked, AfterViewInit, ChangeDetectionStrategy } from '@angular/core';
import 'select2';
import { PersonaHTTPService } from '../../../../../../core/services/http/persona-http.service';
import { UtilPage } from '../../../../../../core/util-page';
import { Persona } from '../../../../../../core/models/persona';

declare var $: any;

@Component({
  selector: 'info-per',
  templateUrl: './info-per.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class InfoPerComponent extends UtilPage implements OnInit, AfterViewInit {

  @Input()
  persona: Persona;
  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  constructor(
    private personaHTTPService: PersonaHTTPService
  ) {
    super();
  }

  ngOnInit() {
  }
  ngAfterViewInit(): void {
    $('#' + this.name).tooltipster({
      content: this.contentLoadPreview,
      contentAsHTML: true,
      interactive: true,
      delay: 500,
      theme: ['tooltipster-shadow', 'my-custom-theme'],
      minWidth: 250,
      maxWidth: 350,
      functionBefore: (instance, helper) => {
        let $origin = $(helper.origin);
        if ($origin.data('loaded') !== true) {
          this.personaHTTPService.getPersona(this.persona.id, true).subscribe((data: any) => {
            let content = '<div class="form-group" >'
              + '<div class="col-md-12 text-bold" ><b>' + data.data.nombre + ' ' + data.data.apellido + ' - ' + data.data.id + '</b></div>'
              + '<div class="col-md-12" >DNI: <span class="red" >' + data.data.dni + '</span></div>'
              + '<div class="col-md-12" >CUIL: <span class="red" >' + data.data.cuil + '</span></div>'
              + '<div class="col-md-12" >Sexo: <span class="red" >' + data.data.sexo + '</span></div>'
              + '<div class="col-md-12" >Fecha Nac.: <span class="red" >' + data.data.fechaNac + '</span></div>'
              + '</div>';
            instance.content(content);
          })
        }
      }
    });
  }

}
