import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
// import { SharedCliModule } from '../cliente/modules/shared-cli/shared-cli.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { PayModal } from './modal/pay/pay.modal';
import { DetailMtoModal } from './modal/detail-mto/detail-mto.modal';
import { PayComponent } from './components/pay/pay.component';
import { DetailMtoComponent } from './components/detail-mto/detail-mto.component';
import { CustomFormsModule } from 'ng2-validation'
// import { SharedVenModule } from '../venta/modules/shared-ven/shared-ven.module';
import { ComprobanteModule } from '../comprobante/comprobante.module';
import { SharedFactCompModule } from '../compra/modules/factura-comp/modules/shared-fact-comp/shared-fact-comp.module';


@NgModule({
  declarations: [
    PayComponent,
    PayModal,
    DetailMtoComponent,
    DetailMtoModal
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CustomFormsModule,
    CommonModule,
    NgbModule,
    SharedModule,
    SharedFactCompModule,
    ComprobanteModule
  ],
  exports: [
    PayModal,
    DetailMtoModal
  ],
  entryComponents: [
    PayComponent,
    PayModal,
    DetailMtoModal
  ]
})
export class CtaCteModule {
}

