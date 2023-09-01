import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { SharedCliModule } from '../cliente/modules/shared-cli/shared-cli.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CtaCteProvRoutingModule } from './cta-cte-prov-routing.module';
import { CtaCteProvComponent } from './cta-cte-prov.component';
import { ListCtaCteProvComponent } from './components/list/list-cta-cte-prov.component';
// import { PayModal } from './modal/pay/pay.modal';
// import { DetailMtoModal } from './modal/detail-mto/detail-mto.modal';
// import { PayComponent } from './components/pay/pay.component';
// import { DetailMtoComponent } from './components/detail-mto/detail-mto.component';
import { ListCtaCteProvPage } from './pages/list-cta-cte-prov.page';
import { CustomFormsModule } from 'ng2-validation'
import { ComprobanteModule } from '../comprobante/comprobante.module';
import { SharedProvModule } from '../proveedor/modules/shared-prov/shared-prov.module';
import { CtaCteModule } from '../cta-cte/cta-cte.module';
import { SharedFactCompModule } from '../compra/modules/factura-comp/modules/shared-fact-comp/shared-fact-comp.module';


@NgModule({
  declarations: [
    CtaCteProvComponent,
    ListCtaCteProvPage,
    ListCtaCteProvComponent
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CustomFormsModule,
    CommonModule,
    NgbModule,
    CtaCteProvRoutingModule,
    SharedModule,
    SharedProvModule,
    ComprobanteModule,
    SharedFactCompModule,
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
export class CtaCteProvModule {
}

