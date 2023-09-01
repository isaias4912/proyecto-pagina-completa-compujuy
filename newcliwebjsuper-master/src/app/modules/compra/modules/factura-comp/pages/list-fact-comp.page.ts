import { Component, ChangeDetectionStrategy } from '@angular/core';
@Component({
  selector: 'list-fact-comp-page',
  template: '<list-fact-comp></list-fact-comp>',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListFactCompPage {

  constructor(
  ) {
  }

}

