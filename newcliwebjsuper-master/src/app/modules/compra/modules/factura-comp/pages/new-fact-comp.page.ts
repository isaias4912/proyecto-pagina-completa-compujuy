import { Component, ChangeDetectionStrategy } from '@angular/core';
@Component({
  selector: 'new-fact-comp-page',
  template: '<new-fact-comp></new-fact-comp>',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class NewFactCompPage {

  constructor(
  ) {
  }

}

