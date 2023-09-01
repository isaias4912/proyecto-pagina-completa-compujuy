import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ItemsCbteComponent } from './components/items/items-cbte.component';
import { ImpuestosComponent } from './components/impuestos/impuestos.component';
import { TributosComponent } from './components/tributos/tributos.component';
import { SharedProdModule } from '../producto/modules/shared-prod/shared-prod.module';
import { SharedCliModule } from '../cliente/modules/shared-cli/shared-cli.module';
import { ComprobanteService } from './services/comprobante.service';
import { SharedModule } from '../shared/shared.module';
import { ItemsPagoComponent } from './components/pago/items/items-pago.component';
import { DetailPagoComponent } from './components/pago/detail/detail-pago.component';
import { CajaModule } from '../caja/caja.module';
import { HeaderCbteComponent } from './components/header/header-cbte.component';
import { DetailCbteComponent } from './components/detail/detail-cbte.component';
import { TributoEventService } from './components/tributos/tributo-event.service';


@NgModule({
  declarations: [
    ItemsCbteComponent,
    ImpuestosComponent,
    TributosComponent,
    ItemsPagoComponent,
    DetailPagoComponent,
    HeaderCbteComponent,
    DetailCbteComponent
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    SharedProdModule,
    SharedModule,
    CajaModule,
    SharedCliModule
  ],
  exports: [
    ItemsPagoComponent,
    ItemsCbteComponent,
    TributosComponent,
    DetailPagoComponent,
    HeaderCbteComponent,
    DetailCbteComponent
  ],
  providers: [
    ComprobanteService,
    TributoEventService
  ],
  entryComponents: [
    DetailCbteComponent
  ]
})
export class ComprobanteModule {
}

