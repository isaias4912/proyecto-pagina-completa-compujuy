import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
declare var $: any;

@Injectable()
export class LoaderService {
  // isLoading = new Subject<boolean>();
  show() {
    $('.modal .modal-content').addClass('working');
    // this.isLoading.next(true);
  }

  hide() {
    $('.modal .modal-content').removeClass('working');
    // this.isLoading.next(false);
  }
}