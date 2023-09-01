import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ListPerPage } from './pages/list-per.page';
import { ListPerComponent } from './components/list/list-per.component';
import { PersonaRoutingModule } from './persona-routing.module';
import { PersonaComponent } from './persona.component';
import { NewPerPage } from './pages/new-per.page';
import { UpdatePerPage } from './pages/update-per.page';
import { NewPerComponent } from './components/new/new-per.component';
import { UpdatePerComponent } from './components/update/update-per.component';
import { FieldsPerComponent } from './components/fields/fields-per.component';
import { SelectPerComponent } from './components/select/select-per.component';
import { SearchPerModal } from './components/modal/search/search-per.modal';
import { NewPerModal } from './components/modal/new/new-per.modal';


@NgModule({
  declarations: [
    PersonaComponent,
    ListPerPage,
    NewPerPage,
    UpdatePerPage,
    ListPerComponent,
    NewPerComponent,
    UpdatePerComponent,
    FieldsPerComponent,
    SelectPerComponent,
    SearchPerModal,
    NewPerModal
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    SharedModule,
    PersonaRoutingModule,
    NgbModule,
  ],
  exports: [
    SelectPerComponent
  ],
  entryComponents: [
    SearchPerModal,
    NewPerModal
  ]
})
export class PersonaModule {
}

