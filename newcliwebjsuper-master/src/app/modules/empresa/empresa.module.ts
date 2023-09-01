import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { RxReactiveFormsModule } from "@rxweb/reactive-form-validators"
import { ListEmpPage } from "./pages/list-emp.page"
import { UpdateEmpPage } from "./pages/update-emp.page"
import { NewEmpPage } from "./pages/new-emp.page"
import { EmpresaComponent } from "./empresa.component"
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { EmpresaRoutingModule } from './empresa-routing.module';
import { ListEmpComponent } from './components/list/list-emp.component';
import { NewEmpComponent } from './components/new/new-emp.component';
import { UpdateEmpComponent } from './components/update/update-emp.component';
import { FieldsEmpComponent } from './components/fields/fields-emp.component';
import { PersonaModule } from '../persona/persona.module';
import { SharedPerModule } from '../persona/modules/shared-per/shared-per.module';
import { SharedEmpModule } from './modules/shared-prov/shared-emp.module';
import { SharedModule } from '../shared/shared.module';
// import { SharedPerModule } from '../persona/modules/';


@NgModule({
  declarations: [
    EmpresaComponent,
    UpdateEmpPage,
    ListEmpComponent,
    NewEmpComponent,
    ListEmpPage,
    FieldsEmpComponent,
    UpdateEmpComponent,
    NewEmpPage
  ],
  exports: [
  ],
  imports: [
    ReactiveFormsModule,
    NgbModule,
    FormsModule,
    CommonModule,
    SharedModule,
    SharedPerModule,
    SharedEmpModule,
    RxReactiveFormsModule,
    EmpresaRoutingModule,
    PersonaModule
  ],
  entryComponents:[
  ]
})
export class EmpresaModule {
}

