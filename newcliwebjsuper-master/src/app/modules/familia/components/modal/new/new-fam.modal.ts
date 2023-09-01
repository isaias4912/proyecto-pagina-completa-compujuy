import { Component, ChangeDetectionStrategy, AfterViewInit, ViewChild } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Dialog } from 'src/app/core/dialog';
import { IDataForm } from 'src/app/core/models/idata-form';
import { ConfirmationServiceService } from 'src/app/modules/shared/services/confirmation-service.service';
import { NewFamComponent } from '../../new/new-fam.component';
// import { Dialog } from '../../../../../core/dialog';
// import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
// import { ConfirmationServiceService } from '../../../../shared/services/confirmation-service.service';
// import { IDataForm } from 'src/app/core/models/idata-form';
// import { NewFamComponent } from '../../new/new-fam.component';
declare var $: any;

@Component({
  selector: 'new-fam-modal',
  templateUrl: './new-fam.modal.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class NewFamModal extends Dialog implements AfterViewInit {
  @ViewChild(NewFamComponent, { static: false })
  private newFamComponent: NewFamComponent;
  public dataForm: IDataForm = { submitted: false };
  constructor(
    public activeModal: NgbActiveModal,
    protected confirmationService: ConfirmationServiceService
  ) {
    super(activeModal, confirmationService);
    this.key='NewFamModal';
  }
  ngAfterViewInit(): void {
  }
  ngOnInit(): void {
  }
  saveFam() {
    this.dataForm = { ...this.dataForm, submitted: true };
    this.newFamComponent.save(1);
  }
  close() {
    this.activeModal.dismiss();
  }
  saveSucces(data) {
   this.activeModal.close(data.data);
  }
}

