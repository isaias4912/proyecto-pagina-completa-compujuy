import { NgModule, Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SortComponent } from './components/sort/sort.component';
import { SelectSucursalComponent } from './components/sucursal/select-multiple/select-scursal.component';
import { SelectDualListComponent } from './components/sucursal/select-dual-list/select-dual-list.component';
import { SelectSucursalSimpleComponent } from './components/sucursal/select-simple/select-scursal-simple.component';
import { SwitchComponent } from './components/switch/switch.component';
import { SwitchService } from './services/switch.service';
import { ImageUploadComponent } from './components/image-upload/image-upload.component';
import { FileUploadComponent } from './components/file-upload/file-upload.component';
import { ImagePreviewComponent } from './components/image-preview/image-preview.component';
import { ConfirmationServiceService } from './services/confirmation-service.service';
import { Scroll } from './components/scroll/scrool';
import { TwoDecimalDirective } from './directives/two-decimal';
import { EnterDirective } from './directives/enter.directive';
import { ArraySortPipe } from './pipes/sort';
import { StepsComponent } from './components/steps/steps';
import { RouterModule } from '@angular/router';
import { PanelComponent } from './components/panel/panel.component';
import { ModalComponent } from './components/modal/modal.component';
import { DateComponent } from './components/date/date.component';
import { CustomDateAdapter } from './utils/custom-date-adapter';
import { CustomDateParserFormatter } from './utils/custom-date-parse-formatter';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ResgistertComponent } from './components/register/resgister.component';
import { ConfirmComponent } from './components/confirm/confirm.component';
import { ConfirmInputComponent } from './components/confirm/comfirm-input/confirm-input.component';
import { SelectService } from './services/select.service';
import { MessageService } from './services/message.service';
import { LoadingComponent } from './components/loading/loading.component';
import { SafePipe } from './pipes/pipes/safe';
import { CantDigitsDirective } from './directives/cant-digits.directive';
import { AdDirective } from './directives/ad.directive';
import { VarDirective } from './directives/var.directive';
import { DomicilioComponent } from './components/domicilio/domicilio.component';
import { TelefonoComponent } from './components/telefono/telefono.component';
import { ContactoComponent } from './components/contacto/contacto.component';
import { DialogGenComponent } from './components/dialog/gen/dialog-gen.component';
import { DetailPersonaComponent } from './components/persona/detail/detail-per-modal.component';
import { AdComponent } from './components/ad/ad.component';
import { InputMaskComponent } from './components/input-mask/input-mask.component';
import { UpdNumberComponent } from './components/upd-number/upd-number.component';
import { UpdDateTimeComponent } from './components/upd-datetime/upd-datetime.component';
import { MessageModalComponent } from './components/message-modal/message.modal';
import { SizeDetectorComponent } from './components/size-detector/size-detector.component';
import { NgxMaskModule, IConfig } from 'ngx-mask'
import { ResizeService } from './components/size-detector/resize.service';
import { AuthRolViewDirective } from './directives/auth-rol-view.directive';
import { ConfigurationService } from './services/configuration.service';
import { ButtonPrintComponent } from './components/button-print/button-print.component';
import { NavigationTableComponent } from './components/navigation-table/navigation-table.component';
import { NavigationTableService } from './components/navigation-table/navigation-table.service';
import { ButtonFooterComponent } from './components/button/button-footer.component';

export const options: Partial<IConfig> | (() => Partial<IConfig>)={};

@Component({
  selector: 'app-footer',
  template: '<ng-content></ng-content>'
})
export class Footer { }
@Component({
  selector: '[app-footer-in]',
  template: '<ng-content></ng-content>'
})
export class FooterIn { }
@NgModule({
  declarations: [
    SortComponent,
    SelectSucursalComponent,
    SwitchComponent,
    Scroll,
    AuthRolViewDirective,
    EnterDirective,
    VarDirective,
    CantDigitsDirective,
    ImageUploadComponent,
    ImagePreviewComponent,
    FileUploadComponent,
    ArraySortPipe,
    TwoDecimalDirective,
    AdDirective,
    AdComponent,
    StepsComponent,
    PanelComponent,
    ModalComponent,
    Footer,
    FooterIn,
    DateComponent,
    ResgistertComponent, 
    ConfirmComponent,
    ConfirmInputComponent,
    SelectSucursalSimpleComponent,
    SafePipe,
    LoadingComponent,
    DomicilioComponent,
    TelefonoComponent,
    ContactoComponent,
    DialogGenComponent,
    DetailPersonaComponent,
    SelectDualListComponent,
    InputMaskComponent,
    UpdNumberComponent,
    UpdDateTimeComponent,
    MessageModalComponent,
    SizeDetectorComponent,
    ButtonPrintComponent,
    NavigationTableComponent,
    ButtonFooterComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    NgbModule,
    ReactiveFormsModule,
    FormsModule,
    NgxMaskModule.forRoot()
  ],
  exports: [
    SelectSucursalComponent,
    SelectSucursalSimpleComponent,
    SortComponent,
    SwitchComponent,
    Scroll,
    EnterDirective,
    VarDirective,
    CantDigitsDirective,
    ImageUploadComponent,
    FileUploadComponent,
    ImagePreviewComponent,
    ArraySortPipe,
    TwoDecimalDirective,
    StepsComponent,
    PanelComponent,
    ModalComponent,
    DateComponent,
    Footer,
    FooterIn,
    ResgistertComponent,
    LoadingComponent,
    DomicilioComponent,
    TelefonoComponent,
    ContactoComponent,
    DialogGenComponent,
    DetailPersonaComponent,
    SelectDualListComponent,
    InputMaskComponent,
    UpdNumberComponent,
    UpdDateTimeComponent,
    SafePipe,
    SizeDetectorComponent,
    AuthRolViewDirective,
    ButtonPrintComponent,
    NavigationTableComponent,
    ButtonFooterComponent
    // NavigationTableService
    ],
  providers: [
    SwitchService,
    ConfirmationServiceService,
    CustomDateAdapter,
    CustomDateParserFormatter,
    SelectService,
    MessageService,
    ResizeService,
    ConfigurationService,
    NavigationTableService
  ],
  entryComponents:[
    ConfirmComponent,
    ConfirmInputComponent,
    DialogGenComponent,
    DetailPersonaComponent,
    MessageModalComponent
  ]
})
export class SharedModule { }
