import { Component, OnInit, Input, EventEmitter } from '@angular/core';
import { Dialog } from '../../../../core/dialog'
@Component({
  selector: 'app-confirm',
  templateUrl: './confirm.component.html'
})
export class ConfirmComponent extends Dialog implements OnInit {

  ngOnInit() {
    if (!this.title){
      this.title="Atencion!!"
    }
  }
}
