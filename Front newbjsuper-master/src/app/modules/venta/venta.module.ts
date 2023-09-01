import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { VentaComponent } from './venta.component';
import { VentaRoutingModule } from './venta-routing.module';
import { SharedUsuModule } from '../usuario/modules/shared-usu/shared-usu.module';
import { SharedProdModule } from '../producto/modules/shared-prod/shared-prod.module';
import { SharedVenModule } from './modules/shared-ven/shared-ven.module';


@NgModule({
  declarations: [
    VentaComponent,
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    SharedModule,
    NgbModule,
    VentaRoutingModule,
    SharedUsuModule,
    SharedProdModule,
    SharedVenModule,
  ],
  exports: [
  ],
  entryComponents: [
  ]
})
export class VentaModule {
}

