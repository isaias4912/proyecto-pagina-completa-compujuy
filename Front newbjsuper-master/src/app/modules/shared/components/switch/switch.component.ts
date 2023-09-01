import { Component, OnInit, Input, AfterViewInit, Output, EventEmitter, ChangeDetectionStrategy, ElementRef, ViewChild } from '@angular/core';
declare var $: any;

@Component({
  selector: 'switch',
  templateUrl: 'switch.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SwitchComponent implements OnInit, AfterViewInit {

  @ViewChild('buttonClickSelect2', { static: false }) buttonSelect2: ElementRef<HTMLElement>;
  @Input()
  size: string = 'sm';
  @Input()
  textoff: string = 'No';
  @Input()
  texton: string = 'Si';
  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Input()
  checked: any;
  checked1: boolean = false;
  @Output()
  readonly changueState = new EventEmitter<boolean>(false);
  constructor(
  ) { }

  ngOnInit() {
  }
  ngAfterViewInit(): void {
    $('#' + this.name).bootstrapToggle({
      on: this.texton,
      off: this.textoff,
      size: this.size
    });
    $('#' + this.name).change((event, target) => {
      this.buttonSelect2.nativeElement.click();
    })
  }

  changueValue() {
    this.changueState.emit($('#' + this.name).prop('checked'));
  }

}
