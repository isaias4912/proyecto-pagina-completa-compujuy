import { Component, OnInit, Input, AfterViewChecked, AfterViewInit } from '@angular/core';
import 'select2';
import { FamiliaHTTPService } from '../../../../../../core/services/http/familia-http.service';
import { Familia } from '../../../../../../core/models/familia';
import { UtilPage } from '../../../../../../core/util-page';

declare var $: any;

@Component({
  selector: 'info-family',
  templateUrl: './info-family.component.html',
})
export class InfoFamilyComponent extends UtilPage implements OnInit, AfterViewInit {

  @Input()
  familia: Familia;
  @Input()
  lnk: number = 1;// 1 enlace, 2 ventana , 3 nada
  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  constructor(
    private familiaHTTPService: FamiliaHTTPService
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
          this.familiaHTTPService.getFamilia(this.familia.id, true).subscribe((data: any) => {
            let padres = null;
            if ((data.data.familia != undefined) && (data.data.familia != null)) {
              padres = this.getTreeFamilia(data.data);
            } else {
              padres = "Sin padres";
            }
            let content = '<div class="form-group" >'
              + '<div class="col-md-12 text-bold" >' + data.data.nombre + ' - ' + data.data.id + '</div>'
              + '<div class="col-md-12" >Nombre corto: <span class="red" >' + data.data.nombreCorto + '</span></div>'
              + '<div class="col-md-12" >Nivel: <span class="red" >' + data.data.nivel + '</span></div>'
              + '<div class="col-md-12" >Padres: <span class="red" >' + padres + '</span></div>'
              + '</div>';
            instance.content(content);
          })
        }
      }
    });
  }
  getTreeFamilia(familia) {
    let text = "<ul>" + "<li class='litree'>" + familia.nombre + "</li>";
    if ((familia.familia != undefined) && (familia.familia != null)) {
      text = text + "<li class='litreenone' >" + this.getTreeFamilia(familia.familia) + "</li>";
    }
    return text + "</ul>";
  }


}
