import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { OfertaRoutingModule } from './oferta-routing.module';
import { OfertaComponent } from './oferta.component';
import { ListOferComponent } from './components/list/list-ofer.component';
import { ListOferPage } from './pages/list-ofer.page';
import { NewOferPage } from './pages/new-ofer.page';
import { NewOferComponent } from './components/new/new-ofer.component';
import { FieldsOferComponent } from './components/fields/fields-ofer.component';
import { AsignOferComponent } from './components/asignacion/asignar/asign-ofer.component';
import { ListAsignComponent } from './components/asignacion/list/list-asign.component';
import { AsignOferPage } from './pages/asign-ofer.page';
import { ListAsignPage } from './pages/list-asign.page';
import { SharedProdModule } from '../producto/modules/shared-prod/shared-prod.module';
import { SharedProvModule } from '../proveedor/modules/shared-prov/shared-prov.module';


@NgModule({
  declarations: [
    OfertaComponent,
    ListOferComponent,
    ListOferPage,
    NewOferComponent,
    NewOferPage,
    FieldsOferComponent,
    AsignOferComponent,
    AsignOferPage,
    ListAsignPage,
    ListAsignComponent
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    SharedModule,
    SharedProdModule,
    SharedProvModule,
    NgbModule,
    OfertaRoutingModule,
  ],
  exports: [
  ],
  entryComponents: [
  ]
})
export class OfertaModule {
}

