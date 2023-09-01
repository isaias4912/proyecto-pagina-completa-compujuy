import { Component, ChangeDetectionStrategy, OnInit, AfterViewInit } from '@angular/core';
import { Dialog } from '../../../../../../core/dialog';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../../../shared/services/confirmation-service.service';
import { TypeComponent } from 'src/app/core/component-page';
declare var $: any;
@Component({
  selector: 'search-fact-comp-modal',
  templateUrl: './search-fact-comp.modal.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SearchFactCompModal extends Dialog implements AfterViewInit {
  public TypeComponent = TypeComponent;
  constructor(
    public activeModal: NgbActiveModal,
    protected confirmationService: ConfirmationServiceService,
  ) {
    super(activeModal, confirmationService);
  }
  ngAfterViewInit(): void {
  }
  ngOnInit(): void {
  }
  selectInvoice(factura){
    this.confirmation.selectEvent.emit(factura);
    this.activeModal.close();
  }
}

