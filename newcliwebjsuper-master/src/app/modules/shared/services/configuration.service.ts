import { Injectable } from '@angular/core';
import { AuthService } from 'src/app/core/services/auth/auth.service';
import { StorageService } from 'src/app/core/services/utils/storage.service';

@Injectable()
export class ConfigurationService {
  constructor(
    private authService: AuthService,
    private storageService: StorageService
  ) {

  }

  public clearConfig(){
    this.authService.clearConfig();
  }
  /**
  * Verficamos la configuracion cada vez q inicie la app root
  */
  public checkConfiguration() {
    // si viene de configuracion no lo cargamos ya que desde la propia vista trae ese dato
    if (this.authService.getConfiguracion() == null || this.authService.getConfiguracion() == {} || Object.keys(this.authService.getConfiguracion()).length == 0) {
      let config: any = this.storageService.getMainData("dataconfigjs");
      if (config == "" || config == null || config == undefined) {
        this.authService.getConfiguracionGral().subscribe(
          (resp: any) => {
            this.authService.setConfiguracion(resp.data.configuracion);
            this.authService.setSucursales(resp.data.sucursales);
            this.storageService.setMainData('dataconfigjs', resp.data);
          }
        );
      } else {
        this.authService.setConfiguracion(config.configuracion);
        this.authService.setSucursales(config.sucursales);
      }

    }
  };
}
