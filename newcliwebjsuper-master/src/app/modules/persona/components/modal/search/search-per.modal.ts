import { Component, ChangeDetectionStrategy, OnInit, AfterViewInit } from '@angular/core';
import { Dialog } from '../../../../../core/dialog';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../../shared/services/confirmation-service.service';
import { RespPagination } from 'src/app/core/models/resp-pagination';
declare var $: any;
@Component({
  selector: 'search-per-modal',
  templateUrl: './search-per.modal.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SearchPerModal extends Dialog implements AfterViewInit {
  public data: RespPagination<any>;
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
  selectPer(persona){
    this.activeModal.close(persona);
  }
}

