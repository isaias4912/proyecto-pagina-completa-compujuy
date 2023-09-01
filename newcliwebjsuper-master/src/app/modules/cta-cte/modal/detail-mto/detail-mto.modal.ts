import { Component, ChangeDetectionStrategy, AfterViewInit, ViewChild, Input } from '@angular/core';
import { Dialog } from '../../../../core/dialog';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../shared/services/confirmation-service.service';

declare var $: any;

@Component({
  selector: 'detail-mto-modal',
  templateUrl: './detail-mto.modal.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DetailMtoModal extends Dialog implements AfterViewInit {

  @Input()
  public mto: any = {};
  @Input()
  public type: 'CTA_CTE_CLI' | 'CTA_CTE_PROV';
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
}

