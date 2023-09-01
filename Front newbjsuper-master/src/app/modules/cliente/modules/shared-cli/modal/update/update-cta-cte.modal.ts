import { Component, ChangeDetectionStrategy, AfterViewInit, ViewChild } from '@angular/core';
import { Dialog } from '../../../../../../core/dialog';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from '../../../../../shared/services/confirmation-service.service';
import { IDataForm } from '../../../../../../core/models/idata-form';
import { Cliente } from '../../../../../../core/models/cliente';
import { CtaCte } from '../../../../../../core/models/cta-cte';
import { UpdateCtaCteComponent } from '../../components/cta-cte/update/update-cta-cte.component';
import { Entidad } from 'src/app/core/models/entidad';
declare var $: any;

@Component({
  selector: 'update-cta-cte-modal',
  templateUrl: './update-cta-cte.modal.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class UpdateCtaCteModal extends Dialog {
  @ViewChild(UpdateCtaCteComponent, { static: false })
  private updateCtaCteComponent: UpdateCtaCteComponent;
  public dataForm: IDataForm = { submitted: false };
  public cliente: Cliente;
  public ctaCte: CtaCte;
  constructor(
    public activeModal: NgbActiveModal,
    protected confirmationService: ConfirmationServiceService,
  ) {
    super(activeModal, confirmationService);
    this.key = 'UpdateCtaCteModal';
  }

  getEntidadString(entidad: Entidad) {
    if (entidad.tipo == 'PERSONA') {
      return entidad.persona.apellido + ' ' + entidad.persona.nombre;
    }
    if (entidad.tipo == 'EMPRESA') {
      return entidad.empresa.razonSocial;
    }
  }
  saveCtaCte() {
    this.dataForm = { ...this.dataForm, submitted: true };
    this.updateCtaCteComponent.save(1);
  }
  saveSucces(data) {
    this.activeModal.close(data.data);
  }
}

