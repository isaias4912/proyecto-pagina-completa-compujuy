import { Component, ChangeDetectionStrategy } from '@angular/core';
@Component({
  selector: 'new-cli-page',
  template: '<new-cli></new-cli>',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class NewCliPage {

  constructor(
  ) {
  }

}

