import { Component, OnInit, AfterViewInit, ChangeDetectionStrategy } from '@angular/core';
import { ProductosHTTPService } from '../../../../../../core/services/http/product-http.service';
import { ConfirmationServiceService } from '../../../../../shared/services/confirmation-service.service';
import { Observable, BehaviorSubject } from 'rxjs';
import { pluck, tap, switchMap } from 'rxjs/operators';
import { ComponentPage } from '../../../../../../core/component-page';
import { ConfirmComponent } from 'src/app/modules/shared/components/confirm/confirm.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'list-list-prices',
  templateUrl: './list-list-prices.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ListListPricesComponent extends ComponentPage implements OnInit, AfterViewInit {
  public filterLista: any = { nombre: null, activo: 2 };
  public listaPrecios$: Observable<any>
  public refresh = new BehaviorSubject<void>(null);
  public dataOptions = {
    filterActivo: [{ value: 0, name: "Inactivo" }, { value: 1, name: "Activo" }, { value: 2, name: "Todos" }]
  };
  constructor(
    private productosHTTPService: ProductosHTTPService,
    private modalService: NgbModal,
    protected confirmationService: ConfirmationServiceService,
  ) {
    super();
  }

  ngOnInit() {
    this.listaPrecios$ = this.refresh.asObservable().pipe(switchMap(() => this.productosHTTPService.getListasAllPagination(this.paramPagination, this.filterLista).pipe(
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
    this.productosHTTPService.enableOrDisableListas(this.listChecked, value).subscribe(resp => {
      if (value) {
        this.messageToast.success("Se activarón correctamente la/las lista/s de precios seleccionada/s");
      } else {
        this.messageToast.success("Se desactivarón correctamente la/las lista/s de precios seleccionada/s");
      }
      this.pageChanged();
    });
  };

  removeLista(lista: any) {
    let modalConfirmRemove = this.modalService.open(ConfirmComponent);
    modalConfirmRemove.componentInstance.title = "Confirmación";
    this.confirmationService.confirm({
      message: 'Confirma eliminación de la lista de precio - ' + lista.nombre,
      accept: () => {
        this.productosHTTPService.deleteListaPrecio(lista.id.toString()).subscribe(resp => {
          this.messageToast.success('Se elimino correctamente la lista de precio - ' + lista.nombre,);
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
    this.filterLista = { nombre: null, activo: 2 };
    setTimeout(() => {
      $("#txtNombre").focus();
    });
  }

}

