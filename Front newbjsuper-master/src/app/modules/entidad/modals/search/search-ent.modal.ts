import { Component, ChangeDetectionStrategy, OnInit, AfterViewInit } from '@angular/core';
import { Dialog } from '../../../../core/dialog';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../shared/services/confirmation-service.service';
import { RespPagination } from '../../../../core/models/resp-pagination';
import { TypeComponent } from 'src/app/core/component-page';
declare var $: any;
@Component({
  selector: 'search-ent-modal',
  templateUrl: './search-ent.modal.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SearchEntModal extends Dialog {
  public data: RespPagination<any>;
  public TypeComponent = TypeComponent;
  constructor(
    public activeModal: NgbActiveModal,
    protected confirmationService: ConfirmationServiceService,
  ) {
    super(activeModal, confirmationService);
  }

  selectEnt(entidad){
    this.activeModal.close(entidad);
  }
}

