import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../../../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FactVenComponent } from './fact-ven.component';
import { ListFactVenComponent } from './components/list/list-fact-ven.component';
import { NewFactVenComponent } from './components/new/new-fact-ven.component';
import { FieldsFactVenComponent } from './components/fields/fields-fact-ven.component';
import { MessageVenOkModalComponent } from './components/message-ven-ok-modal/message-ven-ok.modal';
import { ListFactVenPage } from './pages/list-fact-ven.page';
import { NewFactVenPage } from './pages/new-fact-ven.page';
import { FactVenRoutingModule } from './fact-ven-routing.module';
import { SharedUsuModule } from 'src/app/modules/usuario/modules/shared-usu/shared-usu.module';
import { SharedProdModule } from 'src/app/modules/producto/modules/shared-prod/shared-prod.module';
import { SharedVenModule } from '../shared-ven/shared-ven.module';
import { ComprobanteModule } from 'src/app/modules/comprobante/comprobante.module';
import { MailModule } from 'src/app/modules/mail/mail.module';
import { NgxMaskModule, IConfig } from 'ngx-mask';
import { SharedCliModule } from 'src/app/modules/cliente/modules/shared-cli/shared-cli.module';



export const options: Partial<IConfig> | (() => Partial<IConfig>)={};


@NgModule({
  declarations: [
    FactVenComponent,
    ListFactVenComponent,
    NewFactVenComponent,
    FieldsFactVenComponent,
    ListFactVenPage,
    NewFactVenPage,
    MessageVenOkModalComponent
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    SharedModule,
    NgbModule,
    FactVenRoutingModule,
    SharedUsuModule,
    SharedProdModule,
    SharedVenModule,
    SharedCliModule,
    ComprobanteModule,
    MailModule ,
    NgxMaskModule.forRoot()
  ],
  exports: [
  ],
  entryComponents: [
    MessageVenOkModalComponent
  ]
})
export class FactVenModule {
}

