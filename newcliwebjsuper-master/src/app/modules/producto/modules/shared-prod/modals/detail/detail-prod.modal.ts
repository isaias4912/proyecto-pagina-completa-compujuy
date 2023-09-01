import { Component, ChangeDetectionStrategy, AfterViewInit, Input } from '@angular/core';
import { Dialog } from '../../../../../../core/dialog';
import { VentaHTTPService } from '../../../../../../core/services/http/venta-http.service';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../../../shared/services/confirmation-service.service';
import * as moment from 'moment';
import * as download from 'downloadjs';
import { SendMailModalComponent } from 'src/app/modules/mail/components/send-mail-modal/send-mail-modal.component';
import { ProductosHTTPService } from 'src/app/core/services/http/product-http.service';

@Component({
  selector: 'detail-prod-modal',
  templateUrl: './detail-prod.modal.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DetailProdModal extends Dialog implements AfterViewInit {
  @Input()
  public title = 'Detalle del producto';
  @Input()
  public idEntity: number;
  @Input()
  public data: any = null;
  constructor(
    public activeModal: NgbActiveModal,
    protected confirmationService: ConfirmationServiceService,
    public productosHTTPService: ProductosHTTPService,
  ) {
    super(activeModal, confirmationService);
  }
  ngAfterViewInit(): void {
  }
  ngOnInit(): void {
  }

  close() {
    this.activeModal.dismiss();
  }
  printDetail(producto) {
    this.productosHTTPService.printDetail(this.idEntity.toString()).subscribe((response: any) => {
      var nameFile = producto.nombreFinal.replace(/\s/g, "").substring(0, 10) + '_' + producto.id + "_" + moment().format('DMMYYYYhmmss');
      var blob = new Blob([response], { type: "application/pdf" });
      download(blob, nameFile + ".pdf", "file/pdf");
    });
  };
 
}

