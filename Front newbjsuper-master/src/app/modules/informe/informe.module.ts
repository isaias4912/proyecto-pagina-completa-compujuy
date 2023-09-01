import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { InformeComponent } from './informe.component';
import { LibroIvaVenComponent } from './ventas/libro/libro-iva-ven.component';
import { InformeRoutingModule } from './informe-routing.module';
import { LibroIvaCompComponent } from './compras/libro/libro-iva-comp.component';
import { SharedFactCompModule } from '../compra/modules/factura-comp/modules/shared-fact-comp/shared-fact-comp.module';


@NgModule({
  declarations: [
    InformeComponent,
    LibroIvaVenComponent,
    LibroIvaCompComponent
  ],
  imports: [
    ReactiveFormsModule,
    InformeRoutingModule,
    FormsModule,
    CommonModule,
    SharedModule,
    SharedFactCompModule,
    NgbModule,
  ],
  exports: [
  ],
  entryComponents: [
  ]
})
export class InformeModule {
}

