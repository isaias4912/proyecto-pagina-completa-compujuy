import { Component, ChangeDetectionStrategy, AfterViewInit, Input } from '@angular/core';
import { Dialog } from '../../../../core/dialog';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../shared/services/confirmation-service.service';

@Component({
  selector: 'message-modal',
  templateUrl: './message.modal.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class MessageModalComponent extends Dialog implements AfterViewInit {
  @Input()
  public title = 'Atención';
  @Input()
  public message: string = 'Atención';
  @Input()
  public type: 'warning' | 'success' | 'danger' = 'success';
  constructor(
    public activeModal: NgbActiveModal,
    protected confirmationService: ConfirmationServiceService,
  ) {
    super(activeModal, confirmationService);
    this.key = 'DetailVenModal';
  }
  ngAfterViewInit(): void {
  }
  ngOnInit(): void {
  }

  close() {
    this.activeModal.dismiss();
  }

}

