import { Component, ChangeDetectionStrategy } from '@angular/core';
@Component({
  selector: 'gen-barcode-page',
  template: '<gen-barcode></gen-barcode>',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class GenerateBarcodePage {

  constructor(
  ) {
  }

}

