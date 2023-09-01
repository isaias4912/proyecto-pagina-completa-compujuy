import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { Dialog } from '../../../../../core/dialog';
import { ConfirmationServiceService } from '../../../services/confirmation-service.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AdItem } from '../../../models/ad-item';

@Component({
  selector: 'dialog-gen',
  templateUrl: './dialog-gen.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DialogGenComponent extends Dialog implements OnInit {

  public item: AdItem;

  constructor(
    protected confirmationService: ConfirmationServiceService,
    public activeModal: NgbActiveModal,
  ) {
    super(activeModal, confirmationService);
  }

  ngOnInit() {
  }
  
}

