import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UpdateAppComponent } from './components/app/update/update-app.component';
import { UpdateConfigComponent } from './components/config/update/update-config.component';
import { FieldsAppComponent } from './components/app/fields/fields-app.component';
import { FieldsConfigComponent } from './components/config/fields/fields-config.component';
import { ConfigRoutingModule } from './config-routing.module';
import { ConfigComponent } from './config.component';
import { UpdateAppPage } from './pages/app/update-app.page';
import { UpdateConfigPage } from './pages/config/update-config.page';
import { QuillModule } from 'ngx-quill'

@NgModule({
  declarations: [
    ConfigComponent,
    UpdateAppComponent,
    UpdateAppPage,
    UpdateConfigComponent,
    FieldsAppComponent,
    UpdateConfigPage,
    FieldsConfigComponent
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    SharedModule,
    ConfigRoutingModule,
    QuillModule.forRoot()
  ],
  exports: [
  ],
  entryComponents: [
  ]
})
export class ConfigModule {
}

