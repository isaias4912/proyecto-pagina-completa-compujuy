import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SharedUsuModule } from '../usuario/modules/shared-usu/shared-usu.module';
import { SharedProdModule } from '../producto/modules/shared-prod/shared-prod.module';
import { PresupuestoComponent } from './presupuesto.component';
import { PresupuestoRoutingModule } from './presupuesto-routing.module';


@NgModule({
  declarations: [
    PresupuestoComponent,
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    SharedModule,
    NgbModule,
    PresupuestoRoutingModule,
    SharedUsuModule,
    SharedProdModule,
  ],
  exports: [
  ],
  entryComponents: [
  ]
})
export class PresupuestoModule {
}

