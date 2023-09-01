import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DetailFactCompModalComponent } from './components/detail/detail-fact-comp-modal.component';
import { SharedModule } from '../../../../../shared/shared.module';


@NgModule({
  declarations: [
    DetailFactCompModalComponent
  ],
  exports: [
  ],
  imports: [
    CommonModule,
    SharedModule,
  ],
  entryComponents: [
    DetailFactCompModalComponent
  ]
})
export class SharedFactCompModule {
}

