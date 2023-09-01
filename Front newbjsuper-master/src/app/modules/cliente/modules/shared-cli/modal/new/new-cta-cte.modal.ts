import { Component, ChangeDetectionStrategy, AfterViewInit, ViewChild } from '@angular/core';
import { Dialog } from '../../../../../../core/dialog';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../../../shared/services/confirmation-service.service';
// import { NewPerComponent } from '../../new/new-per.component';
import { IDataForm } from '../../../../../../core/models/idata-form';
import { NewCtaCteComponent } from '../../components/cta-cte/new/new-cta-cte.component';
import { Cliente } from 'src/app/core/models/cliente';
declare var $: any;

@Component({
  selector: 'new-cta-cte-modal',
  templateUrl: './new-cta-cte.modal.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class NewCtaCteModal extends Dialog implements AfterViewInit {
  @ViewChild(NewCtaCteComponent, { static: false })
  private newCtaCteComponent: NewCtaCteComponent;
  public dataForm: IDataForm = { submitted: false };
  public cliente: Cliente;
  constructor(
    public activeModal: NgbActiveModal,
    protected confirmationService: ConfirmationServiceService,
  ) {
    super(activeModal, confirmationService);
    this.key = 'NewCtaCteModal';
  }
  ngAfterViewInit(): void {
  }
  ngOnInit(): void {
  }
  saveCtaCte() {
    this.dataForm = { ...this.dataForm, submitted: true };
    this.newCtaCteComponent.save(1);
  }
  saveSucces(data) {
    this.activeModal.close(data.data);
  }
}

