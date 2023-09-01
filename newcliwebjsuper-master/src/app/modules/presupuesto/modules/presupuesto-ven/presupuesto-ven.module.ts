import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../../../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SharedUsuModule } from 'src/app/modules/usuario/modules/shared-usu/shared-usu.module';
import { SharedProdModule } from 'src/app/modules/producto/modules/shared-prod/shared-prod.module';
import { ComprobanteModule } from 'src/app/modules/comprobante/comprobante.module';
import { MailModule } from 'src/app/modules/mail/mail.module';
import { NgxMaskModule, IConfig } from 'ngx-mask';
import { SharedCliModule } from 'src/app/modules/cliente/modules/shared-cli/shared-cli.module';
import { PresupuestoVenComponent } from './presupuesto-ven.component';
import { PresupuestoVenRoutingModule } from './presupuesto-ven-routing.module';
import { ListPresupuestoVenComponent } from './components/list/list-presupuesto-ven.component';
import { FieldsPresupuestoVenComponent } from './components/fields/fields-presupuesto-ven.component';
import { NewPresupuestoVenComponent } from './components/new/new-presupuesto-ven.component';
import { ListPresupuestoVenPage } from './pages/list-presupuesto-ven.page';
import { NewPresupuestoVenPage } from './pages/new-presupuesto-ven.page';
import { SharedPresupuesoModule } from '../shared-presupuesto/shared-presupuesto.module';
export const options: Partial<IConfig> | (() => Partial<IConfig>)={};


@NgModule({
  declarations: [
    PresupuestoVenComponent,
    ListPresupuestoVenComponent,
    FieldsPresupuestoVenComponent,
    NewPresupuestoVenComponent,
    ListPresupuestoVenPage,
    NewPresupuestoVenPage
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    SharedModule,
    NgbModule,
    PresupuestoVenRoutingModule,
    SharedUsuModule,
    SharedProdModule,
    SharedCliModule,
    ComprobanteModule,
    MailModule ,
    NgxMaskModule.forRoot(),
    SharedPresupuesoModule
  ],
  exports: [
  ],
  entryComponents: [
  ]
})
export class PresupuestoVenModule {
}

