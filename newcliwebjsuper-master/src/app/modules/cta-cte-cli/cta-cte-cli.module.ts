import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { SharedCliModule } from '../cliente/modules/shared-cli/shared-cli.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CtaCteCliRoutingModule } from './cta-cte-cli-routing.module';
import { CtaCteCliComponent } from './cta-cte-cli.component';
import { ListCtaCteComponent } from './components/list/list-cta-cte.component';
// import { PayModal } from './modal/pay/pay.modal';
// import { DetailMtoModal } from './modal/detail-mto/detail-mto.modal';
// import { PayComponent } from './components/pay/pay.component';
// import { DetailMtoComponent } from './components/detail-mto/detail-mto.component';
import { ListCtaCtePage } from './pages/list-cta-cte.page';
import { CustomFormsModule } from 'ng2-validation'
import { SharedVenModule } from '../venta/modules/shared-ven/shared-ven.module';
import { ComprobanteModule } from '../comprobante/comprobante.module';
import { CtaCteModule } from '../cta-cte/cta-cte.module';


@NgModule({
  declarations: [
    CtaCteCliComponent,
    ListCtaCteComponent,
    ListCtaCtePage,
    // PayComponent,
    // PayModal,
    // DetailMtoComponent,
    // DetailMtoModal
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CustomFormsModule,
    CommonModule,
    NgbModule,
    CtaCteCliRoutingModule,
    SharedModule,
    SharedCliModule,
    SharedVenModule,
    ComprobanteModule,
    CtaCteModule
  ],
  exports: [
  ],
  entryComponents: [
    // PayComponent,
    // PayModal,
    // DetailMtoModal
  ]
})
export class CtaCteCliModule {
}

