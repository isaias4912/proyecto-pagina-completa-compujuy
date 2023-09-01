import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../../../../modules/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SelectProvComponent } from './components/select/select-prov.component';
import { SearchProvComponent } from './components/search/search-prov.component';
import { SearchProvModal } from './modals/search/search-prov.modal';
import { SelectProvService } from './components/select/select-prov.service';
import { DetailProvComponent } from './components/detail/detail-prov.component';
import { ListProvComponent } from './components/list/list-prov.component';
@NgModule({
  declarations: [
    SelectProvComponent,
    SearchProvComponent,
    DetailProvComponent,
    ListProvComponent,
    SearchProvModal
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    SharedModule,
    RouterModule,
    NgbModule
  ],
  providers:[
    SelectProvService
  ],
  exports: [
    SelectProvComponent,
    SearchProvComponent,
    ListProvComponent
  ],
  entryComponents: [
    SearchProvComponent,
    SearchProvModal,
    DetailProvComponent
  ]
})
export class SharedProvModule {
}

