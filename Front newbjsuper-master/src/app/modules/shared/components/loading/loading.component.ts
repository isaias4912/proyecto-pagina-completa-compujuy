import { Component, OnInit, Input, Output, EventEmitter, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LoadingComponent {

  constructor() {
  }
}
