import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CajaComponent } from './caja.component';
import { CajaRoutingModule } from './caja-routing.module';
import { ListTransComponent } from './components/transaccion/list/list-trans.component';
import { TransComponent } from './components/transaccion/trans.component';
import { SummaryTransComponent } from './components/transaccion/summary/summary-trans.component';
import { ListTransMovComponent } from './components/transaccion/movimiento/list/list-trans-mov.component';
import { TransMovComponent } from './components/transaccion/movimiento/trans-mov.component';
import { DetailTransComponent } from './components/transaccion/movimiento/detail/detail-trans.component';
import { SelectTransComponent } from './components/transaccion/select/select-trans.component';
import { SharedUsuModule } from '../usuario/modules/shared-usu/shared-usu.module';
import { SharedFactCompModule } from '../compra/modules/factura-comp/modules/shared-fact-comp/shared-fact-comp.module';
import { ListCajaComponent } from './components/list/list-caja.component';
import { ListCajaPage } from './pages/list-caja.page';
import { NewCajaPage } from './pages/new-caja.page';
import { UpdateCajaPage } from './pages/update-caja.page';
import { NewCajaComponent } from './components/new/new-caja.component';
import { UpdateCajaComponent } from './components/update/update-caja.component';
import { FieldsCajaComponent } from './components/fields/fields-caja.component';
import { DetailCajaComponent } from './components/detail/detail-caja.component';
import { SearchTransCompModal } from './modals/search/search-trans.modal';


@NgModule({
  declarations: [
    ListCajaPage,
    NewCajaPage,
    UpdateCajaPage,
    CajaComponent,
    TransComponent,
    ListTransComponent,
    SummaryTransComponent,
    TransMovComponent,
    ListTransMovComponent,
    ListCajaComponent,
    NewCajaComponent,
    FieldsCajaComponent,
    UpdateCajaComponent,
    DetailCajaComponent,
    SelectTransComponent,
    SearchTransCompModal,
    DetailTransComponent
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    SharedModule,
    NgbModule,
    CajaRoutingModule,
    SharedUsuModule,
    SharedFactCompModule
  ],
  exports: [
    SelectTransComponent,
    SearchTransCompModal,
    DetailTransComponent
  ],
  entryComponents: [
    SummaryTransComponent,
    DetailCajaComponent,
    SearchTransCompModal,
    DetailTransComponent
  ]
})
export class CajaModule {
}

