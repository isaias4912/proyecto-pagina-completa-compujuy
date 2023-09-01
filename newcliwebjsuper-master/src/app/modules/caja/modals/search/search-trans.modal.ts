import { Component, ChangeDetectionStrategy, OnInit, AfterViewInit } from '@angular/core';
import { Dialog } from '../../../../core/dialog';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../shared/services/confirmation-service.service';
import { TypeComponent } from 'src/app/core/component-page';
declare var $: any;
@Component({
  selector: 'search-trans-modal',
  templateUrl: './search-trans.modal.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SearchTransCompModal extends Dialog implements AfterViewInit {
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
  selectTrans(trans){
    this.confirmation.selectEvent.emit(trans);
    this.activeModal.close();
  }
}

