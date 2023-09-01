import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SharedModule } from 'src/app/modules/shared/shared.module';
import { SharedProdModule } from 'src/app/modules/producto/modules/shared-prod/shared-prod.module';
import { DetailPresupuestoModal } from './modals/detail/detail-presupuesto.modal';
import { ComprobanteModule } from 'src/app/modules/comprobante/comprobante.module';


@NgModule({
  declarations: [
    DetailPresupuestoModal
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    RouterModule,
    NgbModule,
    SharedModule,
    SharedProdModule,
    ComprobanteModule
  ],
  exports: [
  ],
  entryComponents: [
    DetailPresupuestoModal,
  ]
})
export class SharedPresupuesoModule {
}

