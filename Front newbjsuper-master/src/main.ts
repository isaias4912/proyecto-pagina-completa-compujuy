import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import { environment } from './environments/environment';
import { AppInjector } from './app/core/services/utils/app-injector';

if (environment.production) {
  enableProdMode();
}

platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));
// agregado para poder usar servicios mediante la herencia
// platformBrowserDynamic().bootstrapModule(AppModule).then((moduleRef) => {
//   console.log("swassssssssssssssss");
//   AppInjector.setInjector(moduleRef.injector);
// }); 