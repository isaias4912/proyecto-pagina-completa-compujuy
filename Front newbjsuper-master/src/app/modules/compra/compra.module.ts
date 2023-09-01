import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FactCompraModule } from './modules/factura-comp/fact-comp.module';
import { CompraRoutingModule } from './compra-routing.module';
import { CompraComponent } from './compra.component';

@NgModule({
  declarations: [
    CompraComponent
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    SharedModule,
    NgbModule,
    FactCompraModule,
    CompraRoutingModule,
  ],
  exports: [
  ],
  entryComponents: [
  ]
})
export class CompraModule {
}

