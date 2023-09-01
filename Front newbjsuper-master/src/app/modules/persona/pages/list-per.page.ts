import { Component, ChangeDetectionStrategy } from '@angular/core';
@Component({
  selector: 'list-per-page',
  template: '<list-per></list-per>',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListPerPage {

  constructor(
  ) {
  }

}

