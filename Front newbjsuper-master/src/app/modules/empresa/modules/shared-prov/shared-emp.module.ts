import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../../../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { DetailEmpComponent } from './components/detail/detail-emp.component';
@NgModule({
  declarations: [
    DetailEmpComponent
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    SharedModule,
    RouterModule,
    NgbModule
  ],
  providers:[
  ],
  exports: [
  ],
  entryComponents: [
    DetailEmpComponent
  ]
})
export class SharedEmpModule {
}

