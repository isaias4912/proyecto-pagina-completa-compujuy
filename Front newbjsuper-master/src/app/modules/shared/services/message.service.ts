import { Injectable } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { MessageModalComponent } from '../components/message-modal/message.modal';

@Injectable()
export class MessageService {

  constructor(
    private modalService: NgbModal
  ) { }
  message(title: string = 'Atención', message: string = 'Atención', size: 'sm' | 'lg' | 'xl' = 'sm', type: string = 'warning') {
    let modal = this.modalService.open(MessageModalComponent, { size: size, backdrop: 'static' });
    modal.componentInstance.title = title;
    modal.componentInstance.message = message;
    modal.componentInstance.type = type;
    return modal;
  }
  warning(title: string = 'Atención', message: string = 'Atención', size: 'sm' | 'lg' | 'xl' = 'sm') {
    return this.message(title, message, size, 'warning');
  }
}
