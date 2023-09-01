import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { Select2ProdComponent } from './components/select2/select2-prod.component';
import { InfoProdComponent } from './components/info/info-prod.component';
import { SelectProdComponent } from './components/select/select-prod.component';
import { DetailProductoComponent } from './components/detail-product/detail-product.component';
import { SearchProdComponent } from './components/search/search-prod.component';
import { SharedModule } from 'src/app/modules/shared/shared.module';
import { SharedFamModule } from 'src/app/modules/familia/modules/shared-fam/shared-fam.module';
import { SharedProdPadreModule } from 'src/app/modules/prod-padre/modules/shared-prod-padre/shared-prod-padre.module';
import { DetailProdModal } from './modals/detail/detail-prod.modal';


@NgModule({
  declarations: [
    Select2ProdComponent,
    InfoProdComponent,
    SearchProdComponent,
    DetailProductoComponent,
    SelectProdComponent,
    DetailProdModal
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    RouterModule,
    NgbModule,
    SharedModule, 
    SharedFamModule,
    SharedProdPadreModule
  ],
  exports: [
    Select2ProdComponent,
    InfoProdComponent,
    SelectProdComponent
  ],
  entryComponents: [
    SearchProdComponent,
    DetailProductoComponent,
    DetailProdModal
  ]
})
export class SharedProdModule {
}

