import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FamiliaRoutingModule } from './familia-routing.module';
import { FamiliaComponent } from './familia.component';
import { ListFamPage } from './pages/list-fam.page';
import { NewFamPage } from './pages/new-fam.page';
import { UpdateFamPage } from './pages/update-fam.page';
import { NewFamComponent } from './components/new/new-fam.component';
import { UpdateFamComponent } from './components/update/update-fam.component';
import { FieldsFamComponent } from './components/fields/fields-fam.component';
import { SharedFamModule } from './modules/shared-fam/shared-fam.module';
import { ListFamComponent } from './components/list/list-fam.component';
import { SearchFamModal } from './components/modal/search/search-fam.modal';
import { SelectFamilyComponent } from './components/select/select-family.component';
import { NewFamModal } from './components/modal/new/new-fam.modal';
// import { BrowserModule } from '@angular/platform-browser';


@NgModule({
  declarations: [
    FamiliaComponent,
    NewFamComponent,
    ListFamComponent,
    FieldsFamComponent,
    ListFamPage,
    NewFamPage,
    UpdateFamPage,
    UpdateFamComponent,
    SearchFamModal,
    SelectFamilyComponent,
    NewFamModal
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    SharedModule,
    NgbModule,
    FamiliaRoutingModule,
    SharedFamModule,
    // BrowserModule
  ],
  exports: [
    SearchFamModal,
    SelectFamilyComponent
  ],
  entryComponents: [
    SearchFamModal,
    NewFamModal
  ]
})
export class FamiliaModule {
}

