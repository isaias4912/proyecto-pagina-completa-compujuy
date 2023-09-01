import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ClienteRoutingModule } from './cliente-routing.module';
// import { ListCliComponent } from './components/list/list-cli.component';
import { NewCliComponent } from './components/new/new-cli.component';
import { UpdateCliComponent } from './components/update/update-cli.component';
import { FieldsCliComponent } from './components/fields/fields-cli.component';
import { ListCliPage } from './pages/list-cli.page';
import { UpdateCliPage } from './pages/update-cli.page';
import { NewCliPage } from './pages/new-cli.page';
import { ClienteComponent } from './cliente.component';
import { PersonaModule } from '../persona/persona.module';
import { CtaCteCliModule } from '../cta-cte-cli/cta-cte-cli.module';
import { SharedCliModule } from './modules/shared-cli/shared-cli.module';
import { SharedEntModule } from '../entidad/modules/shared-ent/shared-ent.module';
import { EntidadModule } from '../entidad/entidad.module';


@NgModule({
  declarations: [
    ClienteComponent,
    ListCliPage,
    NewCliPage,
    UpdateCliPage,
    // ListCliComponent, 
    NewCliComponent,
    FieldsCliComponent,
    UpdateCliComponent,
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    SharedModule,
    SharedCliModule,
    NgbModule,
    ClienteRoutingModule,
    PersonaModule,
    CtaCteCliModule,
    EntidadModule
  ],
  exports: [
  ],
  entryComponents: [
  ]
})
export class ClienteModule {
}

