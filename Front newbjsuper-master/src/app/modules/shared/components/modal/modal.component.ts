import { Component, OnInit, Input, ChangeDetectionStrategy, AfterViewInit, ChangeDetectorRef, Output, EventEmitter } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModalComponent implements OnInit {


  @Input()
  public title: string = "JSuper";
  // @Output()
  // eventCancel = new EventEmitter<null>();
  @Input()
  public showHeader: boolean = true;
  @Input()
  public footer: boolean = false;
  @Input()
  public autoSize: boolean = false;
  @Input()
  public heigthScreen = $(window).height() - 200;

  constructor(
    public activeModal: NgbActiveModal,
  ) {

  }

  ngOnInit() {
    if (this.showHeader === null) {
      throw new Error("Attribute 'idCollapse' is required");
    }
  }

  public setTitle(title: string): void {
    this.title = title;
  }

  // cancel() {
  //   this.eventCancel.emit();
  // }


}
