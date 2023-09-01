import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MainRoutingModule } from './main-routing.module';
import { MainComponent } from './main.component';
import { HomeComponent } from './components/home/home.component';
import { Home2Component } from './components/home2/home2.component';
import { MenuComponent } from './components/menu/menu.component';
import { NavComponent } from './components/nav/nav.component';
import { LoadingBarModule } from '@ngx-loading-bar/core';
import { CoreModule } from 'src/app/core/core.module';
import { SharedModule } from '../shared/shared.module';
import { MegaMenuModule } from '../primeng/megamenu/megamenu';

@NgModule({
  declarations: [
    MainComponent,
    HomeComponent,
    Home2Component,
    MenuComponent,
    NavComponent,
  ],
  imports: [
    MainRoutingModule,
    MegaMenuModule,
    CommonModule,
    RouterModule,
    LoadingBarModule,
    CoreModule,
    SharedModule
  ]
})
export class MainModule {
}

