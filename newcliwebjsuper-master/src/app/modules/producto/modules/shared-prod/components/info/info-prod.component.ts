import { Component, OnInit, Input, AfterViewChecked, AfterViewInit } from '@angular/core';
import { ProductosHTTPService } from '../../../../../../core/services/http/product-http.service';
import { UtilPage } from '../../../../../../core/util-page';
import { environment } from '../../../../../../../environments/environment';
import { DialogGenComponent } from 'src/app/modules/shared/components/dialog/gen/dialog-gen.component';
import { DetailProductoComponent } from '../detail-product/detail-product.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AdItem } from 'src/app/modules/shared/models/ad-item';

declare var $: any;

@Component({
  selector: 'info-prod',
  templateUrl: './info-prod.component.html',
})
export class InfoProdComponent extends UtilPage implements OnInit, AfterViewInit {

  @Input()
  nombreProducto: string = null;
  @Input()
  idProducto: number;
  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar

  constructor(
    private productosHTTPService: ProductosHTTPService,
    private modalService: NgbModal
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
      delay: 0,
      theme: ['tooltipster-shadow', 'my-custom-theme'],
      minWidth: 250,
      maxWidth: 350,
      functionBefore: (instance, helper) => {
        let $origin = $(helper.origin);
        if ($origin.data('loaded') !== true) {
          this.productosHTTPService.getProductoPreviewById(this.idProducto, true).subscribe((data: any) => {
            let producto = data.data;
            if (!this.nombreProducto) {
              this.nombreProducto = producto.nombreFinal;
            }
            let img = null;
            if (producto.imagenProducto == null || producto.imagenProducto == undefined) {
              img = '<span class="text-48 fa fa-product-hunt blue" style="margin-top:8px;" ></span>';
            } else {
              img = '<img width="42" class="rounded-circle" src="' + environment.baseURLImgProducts + producto.imagenProducto.nombre + '" style="margin-top:8px;" />';
            }
            let content = '<div class="row form-group" >'
              + '<div class="col-md-2">' + img + '</div>'
              + '<div class="col-md-10">'
              + ' <div class="row form-group" >'
              + '  <div class="col-md-12 "><b>' + producto.nombreFinal + ' - ' + producto.id + '</b></div>'
              + '  <div class="col-md-12" >Producto padre: <span class="red" >' + producto.productoPadre.nombre + '</span></div>'
              + '  <div class="col-md-12" >Familia: <span class="red" >' + producto.productoPadre.familia.nombre + ' - ' + producto.productoPadre.familia.nombreCorto + '</span></div>'
              + '  <div class="col-md-12" >Precio Venta: <span class="red" >' + producto.precioVenta + '</span></div>'
              + ' </div>'
              + '</div>'
              + ''
              + '</div>';
            instance.content(content);
            // var padres = null;
            // if ((data.data.familia != undefined) && (data.data.familia != null)) {
            //   padres = this.getTreeFamilia(data.data);
            // } else {
            //   padres = "Sin padres";
            // }
            // let content = '<div class="form-group" >'
            //   + '<div class="col-md-12 text-bold" >' + data.data.nombre + ' - ' + data.data.id + '</div>'
            //   + '<div class="col-md-12" >Nombre corto: <span class="red" >' + data.data.nombreCorto + '</span></div>'
            //   + '<div class="col-md-12" >Nivel: <span class="red" >' + data.data.nivel + '</span></div>'
            //   + '<div class="col-md-12" >Padres: <span class="red" >' + padres + '</span></div>'
            //   + '</div>';
            // instance.content(content);
          })
        }
      }
    });
  }
  showDetailProduct() {
    let modalDetail = this.modalService.open(DialogGenComponent, { size: 'xl', backdrop: 'static' });
    modalDetail.componentInstance.item = new AdItem(DetailProductoComponent, { idEntity: this.idProducto });
    modalDetail.componentInstance.title = 'Detalle del producto ' + this.nombreProducto;
  }
}
