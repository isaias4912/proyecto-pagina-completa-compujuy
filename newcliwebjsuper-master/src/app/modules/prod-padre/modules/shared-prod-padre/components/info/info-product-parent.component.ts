import { Component, OnInit, Input, AfterViewChecked, AfterViewInit, ChangeDetectionStrategy } from '@angular/core';
import { ProductoPadreHTTPService } from '../../../../../../core/services/http/producto-padre-http.service';
import 'select2';
import { ProductoPadre } from '../../../../../../core/models/producto-padre';
import { UtilPage } from '../../../../../../core/util-page';

declare var $: any;

@Component({
  selector: 'info-product-parent',
  templateUrl: './info-product-parent.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class InfoProductParentComponent extends UtilPage implements OnInit, AfterViewInit {
  @Input()
  lnk: number = 1;// 1 enlace, 2 ventana , 3 nada
  @Input()
  productParent: ProductoPadre;
  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  constructor(
    private productParentHTTPService: ProductoPadreHTTPService
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
          this.productParentHTTPService.getProductoPadreMinById(this.productParent.id, true).subscribe((data: any) => {
            let content = '<div class="form-group" >'
              + '<div class="col-md-12 text-bold" >' + data.nombre + ' - ' + data.id + '</div>'
              + '<div class="col-md-12" >Nombre corto: <span class="red" >' + data.nombreCorto + '</span></div>'
              + '<div class="col-md-12" >Familia: <span class="red" >' + data.familia.nombre + ' - ' + data.familia.nombreCorto + '</span></div>'
              + '</div>';
            instance.content(content);
          });
        }
      }
    });
  }
}
