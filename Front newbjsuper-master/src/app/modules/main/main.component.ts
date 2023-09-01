import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/services/auth/auth.service';
import { ConfigurationService } from '../shared/services/configuration.service';

@Component({
  selector: 'main',
  templateUrl: './main.component.html',
})
export class MainComponent implements OnInit {

  logged$: Observable<boolean>;
  title = 'NewCliWebJSuper';
  public paddingTopAppMain: string;
  public paddingTopAppMainOuter: string;

  constructor(
    private authService: AuthService,
    private configurationService: ConfigurationService

  ) {
    this.logged$ = this.authService.logged$;
  }
  ngOnInit() {
    this.checkConfiguration();
  }
  public fullScreen() {
    this.paddingTopAppMain = $('.app-main').css('padding-top');
    $('.app-main').css('padding-top', '0px');
    $('.app-main').css('margin-bottom', '10px');
    this.paddingTopAppMainOuter = $('.app-main__outer').css('padding-left');
    $('.app-wrapper-footer').hide();
    $('.app-header').hide();
  }
  public resetScreen() {
    $('.app-main').css('padding-top', this.paddingTopAppMain);
    $('.app-main').css('margin-bottom', '0px');
    $('.app-wrapper-footer').show();
    $('.app-header').show();
  }
  public eventScreen(event: string) {
    if (event == "fullScreen") {
      this.fullScreen();
    }
    if (event == "resetScreen") {
      this.resetScreen();
    }
    console.log('event', event)

  }
  /**
   * Verficamos la configuracion cada vez q inicie la app root
   */
  private checkConfiguration() {
    // si viene de configuracion no lo cargamos ya que desde la propia vista trae ese dato
    this.configurationService.checkConfiguration();
  };

}

