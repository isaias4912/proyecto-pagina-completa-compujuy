import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SendMailModalComponent } from './components/send-mail-modal/send-mail-modal.component';


@NgModule({
  declarations: [
    SendMailModalComponent
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    SharedModule,
    NgbModule,
  ],
  exports: [
    SendMailModalComponent
  ],
  entryComponents: [
    SendMailModalComponent
  ]
})
export class MailModule {
}

