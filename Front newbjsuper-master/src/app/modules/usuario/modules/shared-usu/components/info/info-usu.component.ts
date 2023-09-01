import { Component, OnInit, Input, AfterViewChecked, AfterViewInit } from '@angular/core';
import { UsuarioHTTPService } from '../../../../../../core/services/http/usuario-http.service';
import { UtilPage } from '../../../../../../core/util-page';
import { environment } from '../../../../../../../environments/environment';
import { Usuario } from 'src/app/core/models/usuario';

declare var $: any;

@Component({
  selector: 'info-usu',
  templateUrl: './info-usu.component.html',
})
export class InfoUsuComponent extends UtilPage implements OnInit, AfterViewInit {

  @Input()
  nombreProducto: string = null;
  @Input()
  usuario: Usuario;
  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar

  constructor(
    private usuarioHTTPService: UsuarioHTTPService
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
          this.usuarioHTTPService.getUsuarioMin(this.usuario.id, true).subscribe((data: any) => {
            let user = data.data;
            let img = null;
            if (user.tipoLogo == 1) {
              img = '<img width="42" class="rounded-circle" src="' + environment.baseURLImgUsers + user.logo + '" style="margin-top:8px;" />';

              // img = '<img class="user-photo" src="' + environment.baseURLImgUsers + user.logo + '" style="margin-top:8px;" />';
            }
            if (user.tipoLogo == 0 || user.tipoLogo == null) {
              img = '<img width="42" class="rounded-circle" src="https://www.gravatar.com/avatar/' + user.keyGravatar + '?s=52&d=identicon" style="margin-top:8px;" />';
            }

            // if (user.imagenProducto == null || user.imagenProducto == undefined) {
            //   img = '<span class="text-48 fa fa-product-hunt blue" style="margin-top:8px;" ></span>';
            // } else {
            //   img = '<img width="42" class="rounded-circle" src="' + environment.baseURLImgProducts + user.imagenProducto.nombre + '" style="margin-top:8px;" />';
            // }
            let content = '<div class="row form-group" >'
										+ '<div class="col-md-2">' + img + '</div>'
										+ '<div class="col-md-10">'
										+ ' <div class="form-group" >'
										+ '  <div class="col-md-12"><b>' + user.persona.apellido + ', ' + user.persona.nombre + ' - ' + user.id + '</b></div>'
										+ '  <div class="col-md-12" >Alias: <span class="red" >' + user.usuario + '</span></div>'
										+ '  <div class="col-md-12" >DNI: <span class="red" >' + user.persona.dni + '</span></div>'
										+ '  <div class="col-md-12" >Mail: <span class="red" >' + user.mail + '</span></div>'
										+ ' </div>'
										+ '</div>'
										+ ''
										+ '</div>';
            // let content = '<div class="row form-group" >'
            //   + '<div class="col-md-2">' + img + '</div>'
            //   + '<div class="col-md-10">'
            //   + ' <div class="row form-group" >'
            //   + '  <div class="col-md-12 text-bold">' + user.nombreFinal + ' - ' + user.id + '</div>'
            //   + '  <div class="col-md-12" >Producto padre: <span class="red" >' + user.productoPadre.nombre + '</span></div>'
            //   + '  <div class="col-md-12" >Familia: <span class="red" >' + user.productoPadre.familia.nombre + ' - ' + user.productoPadre.familia.nombreCorto + '</span></div>'
            //   + '  <div class="col-md-12" >Precio Venta: <span class="red" >' + user.precioVenta + '</span></div>'
            //   + ' </div>'
            //   + '</div>'
            //   + ''
            //   + '</div>';
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

}
