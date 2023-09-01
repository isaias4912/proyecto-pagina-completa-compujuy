import { Component, ChangeDetectionStrategy } from '@angular/core';
@Component({
  selector: 'list-cli-page',
  template: '<list-cli></list-cli>',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListCliPage {

  constructor(
  ) {
  }

}

