import { Component, OnInit, ViewChild, AfterViewInit, ElementRef, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { ComponentPage } from '../../../../../core/component-page';
import { FieldsInputProdComponent } from '../input-prod/fields/fields-input-prod.component';
import { IDataForm } from 'src/app/core/models/idata-form';
import { ProductosHTTPService } from 'src/app/core/services/http/product-http.service';
import { FieldsInputFactComponent } from '../input-fact/fields/fields-input-fact.component';
import { CompraHTTPService } from 'src/app/core/services/http/compra-http.service';


@Component({
  selector: 'add-stock',
  templateUrl: './add-stock.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AddStockComponent extends ComponentPage implements OnInit, AfterViewInit {
  @ViewChild(FieldsInputProdComponent, { static: false })
  public fieldsInputProdComponent: FieldsInputProdComponent;
  @ViewChild(FieldsInputFactComponent, { static: false })
  public fieldsInputFactComponent: FieldsInputFactComponent;
  public dataForm: IDataForm = { submitted: false };
  public control: any = {
    tipo: 1
  }
  constructor(
    private productosHTTPService: ProductosHTTPService,
    private compraHTTPService: CompraHTTPService
  ) {
    super();
  }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
    this.loadHelp();
  }

  saveOtros(option = 0) {
    this.dataForm = { ...this.dataForm, submitted: true }
    if (this.fieldsInputProdComponent.getFormFactura().valid) {
      let data = this.fieldsInputProdComponent.getFactura();
      this.productosHTTPService.addExistenciasStock(data).subscribe(resp => {
        this.messageToast.success('Se guardo correctamente el movimiento sobre el producto ' + data.movimientos[0].producto.nombreFinal);
        if (option == 1) {
          this.fieldsInputProdComponent.reset();
        }
        if (option == 2) {
          this.location.back();
        }
      });
    }
  }
  saveNuevaFactura(option=0) {
    this.dataForm = { ...this.dataForm, submitted: true }
    if (this.fieldsInputFactComponent.getFormFactura().valid) {
      if (this.fieldsInputFactComponent.dataFacturaAddStock.completa != 2) {
        let factura = this.fieldsInputFactComponent.getFactura();
        this.compraHTTPService.insertAddStock(factura).subscribe((response) => {
          this.messageToast.success("Se agrego el stock correctamente.");
          if (option == 0) {
            this.fieldsInputFactComponent.renewItems();
          }
          if (option == 1) {
            this.fieldsInputFactComponent.reset();
          }
          if (option == 2) {
            this.location.back();
          }
        });
      } else {
        this.messageToast.error('La factura ' + this.fieldsInputFactComponent.dataFacturaAddStock.numero + ' ya tiene todos sus items cargados');
      }
    }
  }
}

