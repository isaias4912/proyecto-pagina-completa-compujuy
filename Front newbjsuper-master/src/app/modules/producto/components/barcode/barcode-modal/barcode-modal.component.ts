import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy, Input } from '@angular/core';
import { ProductosHTTPService } from '../../../../../core/services/http/product-http.service';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../../shared/services/confirmation-service.service';
import { Observable, BehaviorSubject } from 'rxjs';
import { Response } from '../../../../../core/services/utils/response';
import { Producto } from '../../../../../core/models/producto';
import { Dialog } from '../../../../../core/dialog';
import * as download from 'downloadjs';
declare var $: any;
import { Options } from '@angular-slider/ngx-slider';

@Component({
  selector: 'barcode-modal',
  templateUrl: './barcode-modal.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class BarcodeModalComponent extends Dialog implements OnInit, AfterViewInit {

  @Input()
  public producto: Producto;
  public codigo: string;
  public parents$: Observable<Response<any>>
  public refresh = new BehaviorSubject<void>(null);
  public dataParentChild: any = null;
  public width = 1;
  public height = 42;
  public fontSize = 10;
  public format = "CODE128";
  optionsBarCodeWidth: Options = {
    floor: 1,
    ceil: 12
  };
  optionsBarCodeHeigh: Options = {
    floor: 10,
    ceil: 400
  };
  optionsBarCodeFont: Options = {
    floor: 10,
    ceil: 100
  };
  constructor(
    public activeModal: NgbActiveModal,
    protected confirmationService: ConfirmationServiceService,
    private modalService: NgbModal,

  ) {
    super(activeModal, confirmationService);
  }

  ngOnInit() {

  }
  ngAfterViewInit(): void {
    this.title = 'Codigos de barra | ' + this.producto.nombreFinal;
    if (this.producto.codigoBarras.length <= 0) {
      this.activeModal.close();
      this.confirmation.cancelEvent.emit(1);
      // this.confirmationService.
      // this.messageToast.error('No existe codigos de barra para este producto');
    } else {
      this.codigo = this.producto.codigoBarras[0].codigo;
      this.newBarcode();
    }
  }
  changeBarCode() {
    $("#barcode").JsBarcode(this.codigo, {
      format: this.format,
      width: this.width,
      height: this.height,
      fontSize: this.fontSize
    });
  }
  changeCodigo() {
    this.newBarcode();
  };
  changeType() {
    this.newBarcode();
  };
  newBarcode() {
    try {
      $("#barcode").JsBarcode(this.codigo, {
        format: this.format,
        height: this.height,
        width: this.width,
        fontSize: this.fontSize
      });
    } catch (e) {
      this.messageToast.error('No se puede crear este codigo de barra para ' + this.format + ' - ' + this.codigo);
      this.format = "CODE128";
      this.newBarcode();
    }
  };
  downloadImage() {
    var image = new Image();
    try {
      image.onload = () => {
        var canvas = document.createElement('canvas');
        canvas.width = image.naturalWidth; // or 'width' if you want a special/scaled size
        canvas.height = image.naturalHeight; // or 'height' if you want a special/scaled size
        canvas.getContext('2d').drawImage(image, 0, 0);
        var nombre = this.producto.nombreFinal.replace(/ /g, "_") + "-" + this.codigo;
        var dataIMG = canvas.toDataURL('image/png');
        download(dataIMG, nombre + ".png", "image/png");
      };
      image.src = $("#barcode").attr("src");
    } catch (e) {
      this.messageToast.error('Hubo un error con la descarga de la imagen. ' + this.format + ' - ' + this.codigo);
    }
  };
}

