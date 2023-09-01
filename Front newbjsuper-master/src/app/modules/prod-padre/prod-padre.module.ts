import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
// import { CKEditorModule } from '@ckeditor/ckeditor5-angular';
import { ProdPadreRoutingModule } from './prod-padre-routing.module';
import { ProdPadreComponent } from './prod-padre.component';
import {NewProdPadrePage } from './pages/new-prod-padre.page';
import {UpdateProdPadrePage } from './pages/update-prod-padre.page';
import {ListProdPadrePage } from './pages/list-prod-padre.page';
import { NewProdPadreComponent } from './components/new/new-prod-padre.component';
import { UpdateProdPadreComponent } from './components/update/update-prod-padre.component';
import { FieldsProdPadreComponent } from './components/fields/fields-prod-padre.component';
import { SharedProdPadreModule } from './modules/shared-prod-padre/shared-prod-padre.module';
import { FamiliaModule } from '../familia/familia.module';


@NgModule({
  declarations: [
    ProdPadreComponent,
    NewProdPadreComponent,
    UpdateProdPadreComponent,
    FieldsProdPadreComponent,
    ListProdPadrePage,
    NewProdPadrePage,
    UpdateProdPadrePage
  ],
  imports: [
    ProdPadreRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    SharedModule,
    FamiliaModule,
    SharedProdPadreModule, 
    RouterModule,
    // CKEditorModule,
    NgbModule,
  ],
  exports: [
    // InfoProdComponent
  ],
  entryComponents: [
  ]
})
export class ProdPadreModule {
}

