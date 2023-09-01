import { Component, OnInit, ViewChild, ChangeDetectionStrategy, HostListener } from '@angular/core';
import { IDataForm } from '../../../../../../core/models/idata-form';
import { ComponentPage } from '../../../../../../core/component-page';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from 'src/app/modules/shared/services/confirmation-service.service';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';
import { FieldsPresupuestoVenComponent } from '../fields/fields-presupuesto-ven.component';
import { PresupuestoHTTPService } from 'src/app/core/services/http/presupuesto-http.service';
import { DetailPresupuestoModal } from '../../../shared-presupuesto/modals/detail/detail-presupuesto.modal';

@Component({
  selector: 'new-presupuesto-ven',
  templateUrl: './new-presupuesto-ven.component.html',
  styleUrls: ['./new-presupuesto-ven.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NewPresupuestoVenComponent extends ComponentPage implements OnInit {

  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsPresupuestoVenComponent, { static: false })
  fieldsPresupuestoVenComponent: FieldsPresupuestoVenComponent;
  constructor(
    private presupuestoHTTPService: PresupuestoHTTPService,
    private modalService: NgbModal,
    protected confirmationService: ConfirmationServiceService,
  ) {
    super();
  }

  ngOnInit() {
    // let modalSendMail = this.modalService.open(MessageVenOkModalComponent, { size: 'xl', backdrop: 'static' });
  }

  public reset() {
    this.fieldsPresupuestoVenComponent.reset();
  }
  @HostListener('keydown.control.q', ['$event'])
  public save(option: number) {
    this.dataForm = { ...this.dataForm, submitted: true };
    if (this.fieldsPresupuestoVenComponent.isValid()) {
      // console.log('this.fieldsFactVenComponent.getData()', this.fieldsPresupuestoVenComponent.getData())
      this.presupuestoHTTPService.savePresupuesto(this.fieldsPresupuestoVenComponent.getData()).pipe(catchError((error: HttpErrorResponse) => {
        this.navigate(option);
        return throwError(error);
      })).subscribe(resp => {
        this.messageToast.success('Presupuesto correctamente cargada.');
        let modalDetail = this.modalService.open(DetailPresupuestoModal, { size: 'xl', backdrop: 'static' });
        modalDetail.componentInstance.idEntity = resp.data.id;
        modalDetail.componentInstance.data = resp.data;
        modalDetail.componentInstance.title = 'Detalle del presupuesto | ' + resp.data.id;
        modalDetail.result.then((entidad) => {
          this.navigate(option);
        }, (reason) => {
          this.navigate(option);
        });
      })
    }
  }
  private navigate(option: number) {
    if (option == 1) {
      this.fieldsPresupuestoVenComponent.reset();
    }
    if (option == 2) {
      this.location.back();
    }
  }
}

