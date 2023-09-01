import { Component, ChangeDetectionStrategy } from '@angular/core';
@Component({
  selector: 'list-emp-page',
  template: '<list-emp></list-emp>',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListEmpPage {

  constructor(
  ) {
  }

}

