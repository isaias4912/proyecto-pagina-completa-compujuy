import { Component, ChangeDetectionStrategy, AfterViewInit, Input } from '@angular/core';
import { Dialog } from '../../../../../../core/dialog';
import { VentaHTTPService } from '../../../../../../core/services/http/venta-http.service';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../../../shared/services/confirmation-service.service';
import * as moment from 'moment';
import * as download from 'downloadjs';
import { SendMailModalComponent } from 'src/app/modules/mail/components/send-mail-modal/send-mail-modal.component';
import { AuthService } from 'src/app/core/services/auth/auth.service';
import { TipoCbte } from 'src/app/core/enums';
import { PresupuestoHTTPService } from 'src/app/core/services/http/presupuesto-http.service';

@Component({
  selector: 'detail-presupuesto-modal',
  templateUrl: './detail-presupuesto.modal.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DetailPresupuestoModal extends Dialog implements AfterViewInit {
  TipoCbte = TipoCbte;
  @Input()
  public title = 'Detalle del Presupuesto';
  @Input()
  public idEntity: number;
  @Input()
  public data: any = null;
  private configuration: any;
  constructor(
    public activeModal: NgbActiveModal,
    protected confirmationService: ConfirmationServiceService,
    private presupuestoHTTPService: PresupuestoHTTPService,
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
    this.presupuestoHTTPService.downloadCbte(this.idEntity, type).subscribe((response: any) => {
      var nameFile = this.idEntity + '-' + moment().format('DMMYYYYhmmss');
      var blob = new Blob([response], { type: "application/pdf" });
      download(blob, nameFile + ".pdf", "file/pdf");
    });
  };

  printCbte(type: string) {
    // this.presupuestoHTTPService.downloadCbte(this.idEntity, type).subscribe((response: any) => {
    //   console.log('response', response)
    //   var nameFile = this.idEntity + '-' + moment().format('DMMYYYYhmmss');
    //   var blob = new Blob([response], { type: "application/pdf" });
    //   let formData: FormData = new FormData();
    //   formData.append('file', blob);
    //   this.presupuestoHTTPService.printCbte(formData, type, this.configuration.printHost, this.configuration.printPort).subscribe((response: any) => {
    //   });
    //   // }



    // });

  }

  sendEmailCbte(entity: any) {
    // if (entity.idCliente && this.isNumber(entity.idCliente)) {
    //   this.presupuestoHTTPService.getEmailsCliente(entity.idCliente).subscribe((response: any) => {
    //     this.openModalEmail(entity, response.data);
    //   });
    // } else {
    //   this.openModalEmail(entity, []);
    // }
  };
  openModalEmail(entity, mails) {
    // let modalSendMail = this.modalService.open(SendMailModalComponent, { size: 'lg', backdrop: 'static' });
    // modalSendMail.componentInstance.title = "Enviar comprobante de venta por email | " + entity.id;
    // modalSendMail.componentInstance.mails = mails;
    // this.confirmationService.confirm({
    //   accept: (data) => {
    //     this.presupuestoHTTPService.sendEmailCbte(entity.id, data).subscribe(resp => {
    //       modalSendMail.close();
    //       this.messageToast.success("El/Los mail/s fueron enviados correctamente.");
    //     });
    //   }
    // });
  }
}

