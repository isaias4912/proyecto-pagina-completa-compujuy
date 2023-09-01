import { Component, OnInit, ViewChild, AfterViewInit, ElementRef, ChangeDetectionStrategy } from '@angular/core';
import { IDataForm } from '../../../../../../core/models/idata-form';
import { ProductosHTTPService } from '../../../../../../core/services/http/product-http.service';
import { ComponentPage } from '../../../../../../core/component-page';
import { FieldsListPricesComponent } from '../fields/fields-list-prices.component';
import { Observable } from 'rxjs';
import { Response } from '../../../../../../core/services/utils/response';
import { ParamMap, ActivatedRoute } from '@angular/router';
import { pluck, tap } from 'rxjs/operators';

declare var $: any;

@Component({
  selector: 'update-list-prices',
  templateUrl: './update-list-prices.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UpdateListPricesComponent extends ComponentPage implements OnInit, AfterViewInit {

  public dataForm: IDataForm = { submitted: false };
  @ViewChild(FieldsListPricesComponent, { static: false })
  private fields: FieldsListPricesComponent;
  public lista$: Observable<Response<any>>
  private idLista: string;
  constructor(
    private productosHTTPService: ProductosHTTPService,
    private activatedRoute: ActivatedRoute,
  ) {
    super();
  }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(
      (params: ParamMap) => {
        this.idLista = params.get("idLista")
        if (this.idLista) {
          this.lista$ = this.productosHTTPService.getLista(this.idLista).pipe(pluck('data'));
        }
      });
  }
  ngAfterViewInit(): void {
  }
  updateListPrice(option: number = 0) {
    this.dataForm = { ...this.dataForm, submitted: true }
    if (this.fields.formLista.valid) {
      this.productosHTTPService.updateListaPrecio(this.idLista, this.fields.formLista.value).subscribe(resp => {
        this.messageToast.success("Se modificó correctamente la lista de precios " + resp.data.nombre);
        if (option == 1) {
          this.location.back();
        }
      });
    }
  }

}

