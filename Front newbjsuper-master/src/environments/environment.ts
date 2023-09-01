// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  prefix: 'token_prefix',
  baseURL: "http://localhost:8080/",
  baseAPIURL: "http://localhost:8080/api/v1/",
  baseURLFiles: "http://localhost:8080/files/",
  baseURLImgProducts: "http://localhost:8080/files/images-products/",
  baseURLImgApp: "http://localhost:8080/images-app/",
  baseURLImgConfig: "http://localhost:8080/images-config/",
  baseURLImgUsers: "http://localhost:8080/images-users/",
  clientJWT: "localhost:8080",
  loginURL: "http://localhost:8080/oauth/token",
  captchaKey: "6LfqvzEUAAAAAGLr2EQIjSfrN_fUBPOYaRsq7jCb",
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
