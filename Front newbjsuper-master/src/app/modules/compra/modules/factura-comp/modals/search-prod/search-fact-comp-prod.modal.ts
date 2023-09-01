import { Component, ChangeDetectionStrategy, OnInit, AfterViewInit } from '@angular/core';
import { ConfirmationServiceService } from '../../../../../shared/services/confirmation-service.service';
import { Dialog } from '../../../../../../core/dialog';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CompraHTTPService } from 'src/app/core/services/http/compra-http.service';
import { TypeComponent } from 'src/app/core/component-page';
declare var $: any;
@Component({
  selector: 'search-fact-comp-prod-modal',
  templateUrl: './search-fact-comp-prod.modal.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SearchFactCompProdModal extends Dialog implements AfterViewInit {

  public factura = null;
  public TypeComponent = TypeComponent;
  constructor(
    private compraHTTPService: CompraHTTPService,
    public activeModal: NgbActiveModal,
    protected confirmationService: ConfirmationServiceService,
  ) {
    super(activeModal, confirmationService);
  }
  ngAfterViewInit(): void {
  }
  selectInvoice(factura: any) {
    this.factura = factura;
  }
  selectItemInvoice(item: any) {
    item.$$facturaNumero = this.factura.numero;
    item.$$facturaId = this.factura.id;
    this.confirmation.selectEvent.emit(item);
    this.activeModal.close();
  }
  goBack() {
    this.factura = null;
  }

}

