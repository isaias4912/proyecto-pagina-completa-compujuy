import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListProductosComponent } from './components/list/list-productos.component';
import { NewProductoComponent } from './components/new/new-producto.component';
import { UpdateProductoComponent } from './components/update/update-producto.component';
import { ProductoRoutingModule } from './producto-routing.module';
import { SharedModule } from 'src/app/modules/shared/shared.module';
import { FormsModule } from '@angular/forms';
import { FieldsProductoComponent } from './components/fields/fields-producto.component';
import {ReactiveFormsModule} from '@angular/forms';
// import { CKEditorModule } from '@ckeditor/ckeditor5-angular';
import {  RxReactiveFormsModule } from "@rxweb/reactive-form-validators"
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ProductoComponent } from './producto.component';
import { ParentChildComponent } from './components/parent-child/parent-child.component';
import { UpdatePriceComponent } from './components/prices/update/update-price.component';
import { ChangePriceComponent } from './components/prices/change/change-price.component';
import { HistoryProdComponent } from './components/history/history-prod.component';
import { FieldsInputProdComponent } from './components/stock/input-prod/fields/fields-input-prod.component';
import { AddStockComponent } from './components/stock/add/add-stock.component';
import { ListPasesStockComponent } from './components/stock/pases/list/list-pases-stock.component';
import { OutStockComponent } from './components/stock/out/out-stock.component';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { FieldsInputFactComponent } from './components/stock/input-fact/fields/fields-input-fact.component';
import { ListMovStockComponent } from './components/stock/list-mov/list-mov-stock.component';
import { StockProdModalComponent } from './components/stock/stock-prod/stock-prod-modal.component';
import { StockNewPaseComponent } from './components/stock/pases/new/new-pase.component';
import { ListMovPriceComponent } from './components/prices/list-mov/list-mov-price.component';
import { ParentsModalComponent } from './components/parent-child/parents/parents-modal.component';
import { BarcodeModalComponent } from './components/barcode/barcode-modal/barcode-modal.component';
import { NgxSliderModule } from '@angular-slider/ngx-slider';
import { RouterModule } from '@angular/router';
import { ListListPricesComponent } from './components/prices/list-prices/list/list-list-prices.component';
import { NewListPricesComponent } from './components/prices/list-prices/new/new-list-prices.component';
import { UpdateListPricesComponent } from './components/prices/list-prices/update/update-list-prices.component';
import { FieldsListPricesComponent } from './components/prices/list-prices/fields/fields-list-prices.component';
import { ListExpirationComponent } from './components/expiration/list/list-expiration.component';
import { FieldsExpirationComponent } from './components/expiration/fields/fields-expiration.component';
import { NewExpirationComponent } from './components/expiration/new/new-expiration.component';
import { GenerateBarcodeComponent } from './components/barcode/generate/gen-barcode.component';
import { GenerateBarcodePage } from './pages/producto/barcode/gen-barcode.page';
import { SharedProvModule } from '../proveedor/modules/shared-prov/shared-prov.module';
import { SharedProdModule } from './modules/shared-prod/shared-prod.module';
import { SharedFactCompModule } from '../compra/modules/factura-comp/modules/shared-fact-comp/shared-fact-comp.module';
import { FactCompraModule } from '../compra/modules/factura-comp/fact-comp.module';
import { SharedFamModule } from '../familia/modules/shared-fam/shared-fam.module';
import { ListProdPage } from './pages/producto/list-prod.page';
import { NewProdPage } from './pages/producto/new-prod.page';
import { NewProdCompPage } from './pages/producto/new-prod-comp.page';
import { SharedVenModule } from '../venta/modules/shared-ven/shared-ven.module';
import { SharedProdPadreModule } from '../prod-padre/modules/shared-prod-padre/shared-prod-padre.module';
import { QuillModule } from 'ngx-quill'
import { UpdateProdPage } from './pages/producto/update-prod.page';
import { UpdateProdCompPage } from './pages/producto/update-prod-comp.page';
import { FamiliaModule } from '../familia/familia.module';


@NgModule({
  declarations: [
    ListProductosComponent,
    NewProductoComponent,
    UpdateProductoComponent,
    FieldsProductoComponent,
    ProductoComponent, 
    UpdatePriceComponent,
    ChangePriceComponent,
    ParentChildComponent,
    HistoryProdComponent,
    FieldsInputProdComponent,
    FieldsInputFactComponent,
    AddStockComponent,
    OutStockComponent,
    ListMovPriceComponent,
    ListMovStockComponent,
    ParentsModalComponent,
    BarcodeModalComponent,
    StockProdModalComponent,
    StockNewPaseComponent,
    ListPasesStockComponent,
    ListListPricesComponent,
    NewListPricesComponent,
    UpdateListPricesComponent,
    FieldsListPricesComponent,
    ListExpirationComponent,
    FieldsExpirationComponent,
    NewExpirationComponent,
    GenerateBarcodeComponent,
    GenerateBarcodePage,
    ListProdPage,
    NewProdPage,
    NewProdCompPage,
    UpdateProdPage,
    UpdateProdCompPage
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    ProductoRoutingModule,
    CommonModule,
    RouterModule,
    SharedModule,
    RxReactiveFormsModule,
    SharedProvModule,
    NgbModule,
    InfiniteScrollModule,
    SharedFactCompModule,
    SharedProdModule,
    SharedProdPadreModule,
    FamiliaModule,
    SharedFamModule,
    SharedVenModule,
    FactCompraModule,
    NgxSliderModule,
    QuillModule.forRoot()
  ],
  exports:[
    // InfoProdComponent
  ],
  entryComponents:[
    ListMovStockComponent,
    ListMovPriceComponent,
    ParentsModalComponent,
    BarcodeModalComponent,
    StockProdModalComponent
  ]
})
export class ProductoModule {
}

