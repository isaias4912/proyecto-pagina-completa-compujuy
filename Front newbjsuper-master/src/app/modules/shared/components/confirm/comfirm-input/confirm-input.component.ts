import { Component, OnInit, Input, EventEmitter } from '@angular/core';
import { Dialog } from '../../../../../core/dialog'
@Component({
  selector: 'app-confirm-input',
  templateUrl: './confirm-input.component.html'
})
export class ConfirmInputComponent extends Dialog implements OnInit {

  @Input()
  public value: any = null;
  @Input()
  public label: string = "Valor";

  ngOnInit() {
    if (!this.title) {
      this.title = "Atencion!!"
    }
  }
  
}
