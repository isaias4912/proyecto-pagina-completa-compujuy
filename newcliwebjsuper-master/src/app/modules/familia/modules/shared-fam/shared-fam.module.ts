import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SharedModule } from 'src/app/modules/shared/shared.module';
import { InfoFamilyComponent } from './components/info/info-family.component';
import { ThreeFamModalComponent } from './components/modal/three-mod/three-fam-mod.component';
import { ThreeFamComponent } from './components/three/three-fam.component';

@NgModule({
  declarations: [
    InfoFamilyComponent,
    ThreeFamModalComponent,
    ThreeFamComponent
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    CommonModule,
    RouterModule,
    NgbModule,
    SharedModule
  ],
  exports: [
    InfoFamilyComponent,
    ThreeFamModalComponent,
    ThreeFamComponent,
  ],
  entryComponents: [
    ThreeFamModalComponent
  ]
})
export class SharedFamModule {
}

