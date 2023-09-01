import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { RxReactiveFormsModule } from "@rxweb/reactive-form-validators"
import { ListProvPage } from "./pages/list-prov.page"
import { UpdateProvPage } from "./pages/update-prov.page"
import { NewProvPage } from "./pages/new-prov.page"
import { ProveedorComponent } from "./proveedor.component"
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ProveedorRoutingModule } from './proveedor-routing.module';
import { NewProvComponent } from './components/new/new-prov.component';
import { UpdateProvComponent } from './components/update/update-prov.component';
import { FieldsProvComponent } from './components/fields/fields-prov.component';
import { PersonaModule } from '../persona/persona.module';
import { SharedPerModule } from '../persona/modules/shared-per/shared-per.module';
import { SharedProvModule } from './modules/shared-prov/shared-prov.module';
import { SharedModule } from '../shared/shared.module';
import { SharedEntModule } from '../entidad/modules/shared-ent/shared-ent.module';
import { EntidadModule } from '../entidad/entidad.module';
// import { SharedPerModule } from '../persona/modules/';


@NgModule({
  declarations: [
    ProveedorComponent,
    UpdateProvPage,
    NewProvComponent,
    ListProvPage,
    FieldsProvComponent,
    UpdateProvComponent,
    NewProvPage
  ],
  exports: [
  ],
  imports: [
    ReactiveFormsModule,
    NgbModule,
    FormsModule,
    CommonModule,
    ProveedorRoutingModule,
    SharedModule,
    EntidadModule,
    SharedProvModule,
    RxReactiveFormsModule,
  ],
  entryComponents:[
  ]
})
export class ProveedorModule {
}

