import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { Select2UsuComponent } from './components/select2/select2-usu.component';
import { InfoUsuComponent } from './components/info/info-usu.component';


@NgModule({
  declarations: [
    Select2UsuComponent,
    InfoUsuComponent
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    RouterModule,
    NgbModule
  ],
  exports: [
    Select2UsuComponent,
    InfoUsuComponent
  ],
  entryComponents: [
  ]
})
export class SharedUsuModule {
}

