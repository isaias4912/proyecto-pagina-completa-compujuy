import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { DetailVenComponent } from './components/detail/detail-ven.component';
import { SharedModule } from 'src/app/modules/shared/shared.module';
import { SharedProdModule } from 'src/app/modules/producto/modules/shared-prod/shared-prod.module';
import { DetailVenModal } from './modals/detail/detail-ven.modal';


@NgModule({
  declarations: [
    DetailVenComponent,
    DetailVenModal
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    RouterModule,
    NgbModule,
    SharedModule,
    SharedProdModule,
  ],
  exports: [
    DetailVenComponent
  ],
  entryComponents: [
    DetailVenModal,
    DetailVenComponent,
  ]
})
export class SharedVenModule {
}

