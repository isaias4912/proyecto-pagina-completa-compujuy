import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SharedModule } from '../../../shared/shared.module';
import { ListProdPadreComponent } from './components/list/list-prod-padre.component';
import { SearchProdPadreModal } from './modal/search/search-prod-padre.modal';
import { SharedFamModule } from 'src/app/modules/familia/modules/shared-fam/shared-fam.module';
import { InfoProductParentComponent } from './components/info/info-product-parent.component';
import { SelectProductParentComponent } from './components/select/select-product-parent.component';
import { FamiliaModule } from 'src/app/modules/familia/familia.module';


@NgModule({
  declarations: [
    ListProdPadreComponent,
    SearchProdPadreModal,
    InfoProductParentComponent,
    SelectProductParentComponent,
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    SharedModule,
    SharedFamModule,
    FamiliaModule,
    RouterModule,
    NgbModule
  ],
  providers:[
  ],
  exports: [
    ListProdPadreComponent,
    InfoProductParentComponent,
    SelectProductParentComponent
  ],
  entryComponents: [
    SearchProdPadreModal
  ]
})
export class SharedProdPadreModule {
}

