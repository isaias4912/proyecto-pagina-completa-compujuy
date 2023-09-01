import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { UsuarioRoutingModule } from './usuario-routing.module';
import { UsuarioComponent } from './usuario.component';
import { ListUsuPage } from './pages/list-usu.page';
import { NewUsuPage } from './pages/new-usu.page';
import { ListUsuComponent } from './components/list/list-usu.component';
import { UpdateUsuComponent } from './components/update/update-usu.component';
import { NewUsuComponent } from './components/new/new-usu.component';
import { FieldsUsuComponent } from './components/fields/fields-usu.component';
import { PersonaModule } from '../persona/persona.module';
import { UpdateUsuPage } from './pages/update-usu.page';
import { SharedPerModule } from '../persona/modules/shared-per/shared-per.module';
import { MyUsuComponent } from './components/my/my-usu.component';


@NgModule({
  declarations: [
    UsuarioComponent,
    ListUsuPage,
    NewUsuPage,
    UpdateUsuPage,
    ListUsuComponent,
    NewUsuComponent,
    FieldsUsuComponent,
    UpdateUsuComponent,
    MyUsuComponent
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    SharedModule,
    NgbModule,
    UsuarioRoutingModule,
    PersonaModule,
    SharedPerModule
  ],
  exports: [
  ],
  entryComponents: [
  ]
})
export class UsuarioModule {
}

