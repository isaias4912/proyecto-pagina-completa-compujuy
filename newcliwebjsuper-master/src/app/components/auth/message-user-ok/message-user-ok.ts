import { Component, ChangeDetectionStrategy, AfterViewInit, Input, ChangeDetectorRef } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import ConfettiGenerator from "confetti-js";
import { Router } from '@angular/router';

@Component({
  selector: 'message-user-ok',
  templateUrl: './message-user-ok.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class MessageUserOkComponent implements AfterViewInit {
  @Input()
  public title = 'Atención';
  @Input()
  public message: string = ' Felicitaciones, el usuario se creo correctamente, ahora puede ingresar desde el login del sistema y empezar a usarlo!!';
  @Input()
  public type: 'warning' | 'success' | 'danger' = 'success';
  public counter: number = 5;
  constructor(
    public activeModal: NgbActiveModal,
    private cdr: ChangeDetectorRef,
    public router: Router
  ) {
    // super(activeModal, confirmationService);
    // this.key = 'DetailVenModal';
  }
  ngAfterViewInit(): void {
    let confettiSettings = { target: 'my-canvas', width: '800' };
    let confetti = new ConfettiGenerator(confettiSettings);
    confetti.render();
    let intervalId = setInterval(() => {
      this.counter = this.counter - 1;
      this.cdr.markForCheck();
      if (this.counter === 0) {
        clearInterval(intervalId);
        this.close()
        this.router.navigate(['auth/login']);
      }
    }, 1000)
  }
  ngOnInit(): void {
  }

  close() {
    this.activeModal.dismiss();
  }

}

