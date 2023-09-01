import { Component, ChangeDetectionStrategy } from '@angular/core';
@Component({
  selector: 'new-emp-page',
  template: '<new-emp></new-emp>',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class NewEmpPage {

  constructor(
  ) {
  }

}

