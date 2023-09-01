import { Directive, HostListener, Input, ElementRef } from '@angular/core';
declare var $: any;

@Directive({
  selector: '[cantDigits]'
})
export class CantDigitsDirective {

  constructor(private el: ElementRef) { }

  @Input() cantDigits: number;
  private specialKeys: Array<string> = ['Backspace', 'Tab', 'End', 'Home', '-', 'ArrowLeft', 'ArrowRight', 'Del', 'Delete'];

  @HostListener('keydown', ['$event']) onKeyDown(event) {
    let e = <KeyboardEvent>event;
    if (this.specialKeys.indexOf(event.key) !== -1) {
      return;
    }
    if (this.cantDigits) {
      let current: string = this.el.nativeElement.value;
      if (current.toString().length > (this.cantDigits - 1)) {
        e.preventDefault();
      }
    }
  }

}