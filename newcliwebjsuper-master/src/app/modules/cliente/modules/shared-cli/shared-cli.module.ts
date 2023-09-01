import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SelectCliComponent } from './components/select/select-cli.component';
import { SelectCliService } from './components/select/select-cli.service';
import { SharedModule } from 'src/app/modules/shared/shared.module';
import { ListCliComponent } from './components/list/list-cli.component';
import { DetailCliComponent } from './components/detail/detail-cli.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NewCtaCteComponent } from './components/cta-cte/new/new-cta-cte.component';
import { UpdateCtaCteComponent } from './components/cta-cte/update/update-cta-cte.component';
import { FieldsCtaCteComponent } from './components/cta-cte/fields/fields-cta-cte.component';
import { NewCtaCteModal } from './modal/new/new-cta-cte.modal';
import { UpdateCtaCteModal } from './modal/update/update-cta-cte.modal';
import { SearchCliModal } from './modal/search/search-cli.modal';


@NgModule({
  declarations: [
    SelectCliComponent,
    ListCliComponent,
    NewCtaCteComponent,
    UpdateCtaCteComponent,
    FieldsCtaCteComponent,
    NewCtaCteModal,
    UpdateCtaCteModal,
    SearchCliModal,
    DetailCliComponent    
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
    SelectCliService
  ],
  exports: [
    ListCliComponent,
    SelectCliComponent,
  ],
  entryComponents: [
    NewCtaCteModal,
    UpdateCtaCteModal,
    SearchCliModal,
    DetailCliComponent
  ]
})
export class SharedCliModule {
}

