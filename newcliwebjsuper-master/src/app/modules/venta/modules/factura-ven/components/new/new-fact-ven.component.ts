import { Component, OnInit, ViewChild, ChangeDetectionStrategy, HostListener, ChangeDetectorRef, ElementRef } from '@angular/core';
import { IDataForm } from '../../../../../../core/models/idata-form';
import { ComponentPage } from '../../../../../../core/component-page';
import { VentaHTTPService } from '../../../../../../core/services/http/venta-http.service';
import { FieldsFactVenComponent } from '../fields/fields-fact-ven.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationServiceService } from 'src/app/modules/shared/services/confirmation-service.service';
import { DetailVenModal } from '../../../shared-ven/modals/detail/detail-ven.modal';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'new-fact-ven',
  templateUrl: './new-fact-ven.component.html',
  styleUrls: ['./new-fact-ven.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NewFactVenComponent extends ComponentPage implements OnInit {

  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsFactVenComponent, { static: false })
  fieldsFactVenComponent: FieldsFactVenComponent;
  @ViewChild('btnSaveFactVen')
  btnSaveFactVen: ElementRef;
  constructor(
    private ventaHTTPService: VentaHTTPService,
    private modalService: NgbModal,
    protected confirmationService: ConfirmationServiceService,
  ) {
    super();
  }

  ngOnInit() {
  }

  public reset() {
    this.fieldsFactVenComponent.reset();
  }
  @HostListener('window:keydown.control.s', ['$event'])
  public saveAndNew(event: Event) {
    this.fieldsFactVenComponent.markAsTouched(); // hay q marcarlo porq tira exception en el modal (ng-touched)
    this.save(1);
    event.preventDefault();
  }
  @HostListener('document:keydown.control.d', ['$event'])
  public saveAndBack() {
    this.fieldsFactVenComponent.markAsTouched(); // hay q marcarlo porq tira exception en el modal (ng-touched)
    this.save(2);
    event.preventDefault();
  }
  public save(option: number) {
    this.dataForm = { ...this.dataForm, submitted: true };
    if (this.fieldsFactVenComponent.isValid()) {
      this.ventaHTTPService.saveVenta(this.fieldsFactVenComponent.getData()).pipe(catchError((error: HttpErrorResponse) => {
        return throwError(error);
      })).subscribe(resp => {
        this.messageToast.success('Factura de venta correctamente cargada.');
        let modalDetail = this.modalService.open(DetailVenModal, { size: 'xl', backdrop: 'static' });
        modalDetail.componentInstance.idEntity = resp.data.id;
        modalDetail.componentInstance.data = resp.data;
        modalDetail.componentInstance.title = 'Detalle de la venta | ' + resp.data.id;
        modalDetail.result.then((entidad) => {
          this.navigate(option);
        }, (reason) => {
          this.navigate(option);
        });
      }, (error) => {
        this.checkError(error, option);
      })
    }
  }

  private checkError(error: any, option: number = 1) {
    if (error.type && error.type == "ERROR_STOCK") {
      return;
    }
    this.navigate(option);
  }

  private navigate(option: number) {
    if (option == 1) {
      this.fieldsFactVenComponent.reset();
    }
    if (option == 2) {
      this.location.back();
    }
  }
}

