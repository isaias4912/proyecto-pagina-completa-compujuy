import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy } from '@angular/core';
import { ProductosHTTPService } from '../../../../../core/services/http/product-http.service';
import { ConfirmationServiceService } from '../../../../shared/services/confirmation-service.service';
import { Observable, BehaviorSubject } from 'rxjs';
import { pluck, tap, switchMap } from 'rxjs/operators';
import { ComponentPage } from '../../../../../core/component-page';
import { ConfirmComponent } from '../../../../shared/components/confirm/confirm.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UtilHTTPService } from 'src/app/core/services/http/util-http.service';


@Component({
  selector: 'list-expiration',
  templateUrl: './list-expiration.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ListExpirationComponent extends ComponentPage implements OnInit, AfterViewInit {
  public filterVenc: any = {
    activo: 1,
    valido: 1,
    alerta: 2,
    producto: null
  };
  public listaVenc$: Observable<any>
  public refresh = new BehaviorSubject<void>(null);
  public dataOptions = {
    filterActivo: [{ value: 0, name: "Inactivo" }, { value: 1, name: "Activo" }, { value: 2, name: "Todos" }],
    filterValido: [{ value: 0, name: "Invalidos" }, { value: 1, name: "Validos" }, { value: 2, name: "Todos" }],
    filterAlerta: [{ value: 0, name: "No" }, { value: 1, name: "Si" }, { value: 2, name: "Todos" }]
  };
  constructor(
    private productosHTTPService: ProductosHTTPService,
    private utilHTTPService: UtilHTTPService,
    private modalService: NgbModal,
    protected confirmationService: ConfirmationServiceService,
  ) {
    super();
  }

  ngOnInit() {
    this.listaVenc$ = this.refresh.asObservable().pipe(switchMap(() => this.productosHTTPService.getVencimientosAllPagination(this.paramPagination, this.filterVenc).pipe(
      tap(resp => {
        this.setValuePagination(resp);
      }), pluck('data'), tap(resp => { })
    )));

  }
  ngAfterViewInit(): void {
    this.title = "Lista de precios";
    this.reset();
  }

  enabledOrDisabled(value) {
    this.productosHTTPService.enableOrDisableVencimientos(this.listChecked, value).subscribe(resp => {
      if (value) {
        this.messageToast.success("Se activarón correctamente el/los vencimiento/s seleccionado/s");
      } else {
        this.messageToast.success("Se desactivarón correctamente el/los vencimiento/s seleccionado/s");
      }
      this.pageChanged();
    });
  };
  enabledOrDisabledAlerta(vencimiento: any, value: boolean) {
    this.utilHTTPService.enableOrDisableAlertaVencimientos(vencimiento, value).subscribe(resp => {
      if (value) {
        this.messageToast.success("Se activarón correctamente el/los vencimiento/s seleccionado/s");
      } else {
        this.messageToast.success("Se desactivarón correctamente el/los vencimiento/s seleccionado/s");
      }
      this.pageChanged();
    });
  };

  removeVencimiento(vencimiento: any) {
    let modalConfirmRemove = this.modalService.open(ConfirmComponent);
    modalConfirmRemove.componentInstance.title = "Confirmación";
    this.confirmationService.confirm({
      message: 'Confirma eliminación del vencimiento - ' + vencimiento.id,
      accept: () => {
        this.productosHTTPService.deleteVencimiento(vencimiento.id.toString()).subscribe(resp => {
          this.messageToast.success('Se elimino correctamente el vencimiento - ' + vencimiento.id);
          this.pageChanged();
        });
      },
      cancel: () => {
      }
    });
  }
  pageChanged() {
    this.refresh.next();
  }
  reset() {
    this.filterVenc = {
      activo: 1,
      valido: 1,
      alerta: 2,
      producto: null
    };
    setTimeout(() => {
      $("#txtNombre").focus();
    });
  }

}

