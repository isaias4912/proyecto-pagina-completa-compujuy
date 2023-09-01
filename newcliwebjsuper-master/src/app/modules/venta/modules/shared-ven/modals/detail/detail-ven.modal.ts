import { Component, ChangeDetectionStrategy, AfterViewInit, Input } from '@angular/core';
import { Dialog } from '../../../../../../core/dialog';
import { VentaHTTPService } from '../../../../../../core/services/http/venta-http.service';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../../../shared/services/confirmation-service.service';
import * as moment from 'moment';
import * as download from 'downloadjs';
import { SendMailModalComponent } from 'src/app/modules/mail/components/send-mail-modal/send-mail-modal.component';
import { AuthService } from 'src/app/core/services/auth/auth.service';

@Component({
  selector: 'detail-ven-modal',
  templateUrl: './detail-ven.modal.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DetailVenModal extends Dialog implements AfterViewInit {
  @Input()
  public title = 'Detalle de la venta';
  @Input()
  public idEntity: number;
  @Input()
  public data: any = null;
  private configuration: any;
  constructor(
    public activeModal: NgbActiveModal,
    protected confirmationService: ConfirmationServiceService,
    private ventaHTTPService: VentaHTTPService,
    private modalService: NgbModal,
    private authService: AuthService
  ) {
    super(activeModal, confirmationService);
    this.key = 'DetailVenModal';
  }
  ngAfterViewInit(): void {
    setTimeout(() => {
      $("#btnCerrarDetailVen").focus();
    }, 500);
    this.configuration = this.authService.getConfiguracion();
    // console.log('configuration', configuration)
  }
  ngOnInit(): void {
  }

  close() {
    this.activeModal.dismiss();
  }
  donwloadCbte(type = 'A4') {
    this.ventaHTTPService.downloadCbte(this.idEntity, type).subscribe((response: any) => {
      var nameFile = this.idEntity + '-' + moment().format('DMMYYYYhmmss');
      var blob = new Blob([response], { type: "application/pdf" });
      download(blob, nameFile + ".pdf", "file/pdf");
    });
  };

  printCbte(type: string) {
    this.ventaHTTPService.downloadCbte(this.idEntity, type).subscribe((response: any) => {
      console.log('response', response)
      var nameFile = this.idEntity + '-' + moment().format('DMMYYYYhmmss');
      var blob = new Blob([response], { type: "application/pdf" });
      // var blob = new Blob([response], { type: 'application/octet-stream' });

      // var binary = '';
      // var bytes = new Uint8Array( blob );
      // var len = bytes.byteLength;
      // for (var i = 0; i < len; i++) {
      //     binary += String.fromCharCode( bytes[ i ] );
      // }
      // var temp= window.btoa( binary );


      // var reader = new FileReader();
      // reader.readAsDataURL(blob);
      // reader.onloadend = ()=> {
      //   var base64data = reader.result;
      //   console.log(base64data);
      let formData: FormData = new FormData();
      formData.append('file', blob);
      this.ventaHTTPService.printCbte(formData, type, this.configuration.printHost, this.configuration.printPort).subscribe((response: any) => {

      });
      // }



    });

  }

  sendEmailCbte(entity: any) {
    if (entity.idCliente && this.isNumber(entity.idCliente)) {
      this.ventaHTTPService.getEmailsCliente(entity.idCliente).subscribe((response: any) => {
        this.openModalEmail(entity, response.data);
      });
    } else {
      this.openModalEmail(entity, []);
    }
  };
  openModalEmail(entity, mails) {
    let modalSendMail = this.modalService.open(SendMailModalComponent, { size: 'lg', backdrop: 'static' });
    modalSendMail.componentInstance.title = "Enviar comprobante de venta por email | " + entity.id;
    modalSendMail.componentInstance.mails = mails;
    this.confirmationService.confirm({
      accept: (data) => {
        this.ventaHTTPService.sendEmailCbte(entity.id, data).subscribe(resp => {
          modalSendMail.close();
          this.messageToast.success("El/Los mail/s fueron enviados correctamente.");
        });
      }
    });
  }
}

