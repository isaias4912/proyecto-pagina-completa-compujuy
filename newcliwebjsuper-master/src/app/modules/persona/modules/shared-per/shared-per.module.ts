import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { InfoPerComponent } from './components/info/info-per.component';

@NgModule({
  declarations: [
    InfoPerComponent,
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    RouterModule,
    NgbModule
  ],
  exports: [
    InfoPerComponent,
  ],
  entryComponents: [
  ]
})
export class SharedPerModule {
}

