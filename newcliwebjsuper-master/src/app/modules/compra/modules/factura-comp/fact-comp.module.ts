import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../../../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FactCompraRoutingModule } from './fact-comp-routing.module';
import { ListFactCompPage } from './pages/list-fact-comp.page';
import { ListFactCompComponent } from './components/list/list-fact-comp.component';
import { FactCompraComponent } from './fact-comp.component';
import { ComprobanteModule } from 'src/app/modules/comprobante/comprobante.module';
import { SharedProdModule } from 'src/app/modules/producto/modules/shared-prod/shared-prod.module';
import { SharedFactCompModule } from './modules/shared-fact-comp/shared-fact-comp.module';
import { SharedProvModule } from 'src/app/modules/proveedor/modules/shared-prov/shared-prov.module';
import { SelectFactCompComponent } from './components/select/select-fact-comp.component';
import { SelectFactCompProdComponent } from './components/select-prod/select-fact-comp-prod.component';
import { ListItemsFactCompComponent } from './components/list-items/list-items-fact-comp.component';
import { NewFactCompComponent } from './components/new/new-fact-comp.component';
import { FieldsFactCompComponent } from './components/fields/fields-fact-comp.component';
import { SearchFactCompModal } from './modals/search/search-fact-comp.modal';
import { SearchFactCompProdModal } from './modals/search-prod/search-fact-comp-prod.modal';
import { NewFactCompPage } from './pages/new-fact-comp.page';
import { NgxMaskModule, IConfig } from 'ngx-mask'

export const options: Partial<IConfig> | (() => Partial<IConfig>)={};

@NgModule({
  declarations: [
    SelectFactCompComponent,
    SelectFactCompProdComponent,
    FactCompraComponent,
    ListFactCompPage,
    ListFactCompComponent,
    ListItemsFactCompComponent ,
    SearchFactCompModal,
    SearchFactCompProdModal,
    NewFactCompPage,
    NewFactCompComponent,
    FieldsFactCompComponent
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    SharedModule,
    NgbModule,
    SharedProdModule,
    SharedFactCompModule,
    SharedProvModule,
    FactCompraRoutingModule,
    ComprobanteModule,
    NgxMaskModule.forRoot()
  ],
  exports: [
    SelectFactCompComponent,
    SelectFactCompProdComponent,
    SearchFactCompModal,
    SearchFactCompProdModal,
    ListItemsFactCompComponent
  ],
  entryComponents: [
    SearchFactCompModal,
    SearchFactCompProdModal
  ]
})
export class FactCompraModule {
}

