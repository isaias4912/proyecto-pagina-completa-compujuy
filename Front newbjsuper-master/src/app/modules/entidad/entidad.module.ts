import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SelectEntComponent } from './components/select/select-ent.component';
import { ListEntComponent } from './components/list/list-ent.component';
import { EntidadRoutingModule } from './entidad-routing.module';
import { ListEntPage } from './pages/list-ent.page';
import { EntidadComponent } from './entidad.component';
import { SearchEntModal } from './modals/search/search-ent.modal';
import { SharedEmpModule } from '../empresa/modules/shared-prov/shared-emp.module';


@NgModule({
  declarations: [
    EntidadComponent,
    SelectEntComponent,
    ListEntComponent,
    ListEntPage,
    SearchEntModal
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    SharedModule,
    NgbModule,
    SharedEmpModule,
    EntidadRoutingModule,
  ],
  exports: [
    SelectEntComponent
  ],
  entryComponents: [
    SearchEntModal
  ]
})
export class EntidadModule {
}

