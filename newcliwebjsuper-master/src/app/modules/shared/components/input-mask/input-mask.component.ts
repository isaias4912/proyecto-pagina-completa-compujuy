import { Component, OnInit, Input, forwardRef, ChangeDetectionStrategy, AfterViewInit, OnDestroy, ChangeDetectorRef }
  from '@angular/core';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';
export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => InputMaskComponent),
  multi: true
};
declare var $: any;

@Component({
  selector: 'input-mask',
  templateUrl: './input-mask.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR],
})
export class InputMaskComponent implements OnInit, AfterViewInit, ControlValueAccessor, OnDestroy {

  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Input()
  enter: string = 'default';
  @Input()
  mask: string = '99999999';
  @Input()
  placeholder: string = '';
  // @Input()
  // data: any = null;
  public innerValue: any = '';
  propagateChange = (_: string) => { }
  public inputValue = null;
  constructor(
    private cdr: ChangeDetectorRef
  ) {

  }

  ngOnInit() {

  }

  ngAfterViewInit(): void {
    this.loadComponents();
    // if (this.data) {
    //   $("#" + this.name).val(this.data);
    // }
  }
  changeValue() {
    this.setValue(this.inputValue);
  }
  loadData() {
    if (this.innerValue) {
      this.inputValue = this.innerValue;
      this.setValue(this.innerValue)
      this.cdr.markForCheck();
    } else {
      this.setValue('')
    }
  }
  loadComponents() {
    // setTimeout(() => {
    //   if (this.placeholder && this.placeholder != '') {
    //     $("#" + this.name).inputmask({
    //       "mask": this.mask, placeholder: this.placeholder,
    //       "onKeyValidation": (e) => {
    //         this.setValue($("#" + this.name).val());
    //         this.cdr.markForCheck();
    //       },
    //       "oncleared": (e) => {
    //         this.setValue(null);
    //         this.cdr.markForCheck();
    //       },
    //       "oncomplete": (e) => {
    //         this.setValue($("#" + this.name).val());
    //         this.cdr.markForCheck();
    //       },
    //       "onincomplete": (e) => {
    //         this.setValue($("#" + this.name).val());
    //         this.cdr.markForCheck();
    //       }
    //     });  //static mask
    //   } else {
    //     $("#" + this.name).inputmask({
    //       "mask": this.mask,
    //       "onKeyValidation": (e) => {
    //         this.setValue($("#" + this.name).val());
    //         this.cdr.markForCheck();
    //       },
    //       "oncleared": (e) => {
    //         this.setValue(null);
    //         this.cdr.markForCheck();
    //       },
    //       "oncomplete": (e) => {
    //         this.setValue($("#" + this.name).val());
    //         this.cdr.markForCheck();
    //       },
    //       "onincomplete": (e) => {
    //         this.setValue($("#" + this.name).val());
    //         this.cdr.markForCheck();
    //       }
    //     });  //static mask
    //   }
    // });
  }

  // setData(data: any) {
  //   this.data = data;
  //   $("#" + this.name).val(this.data);
  //   this.setValue(data);
  // }
  setValue(data: any) {
    this.innerValue = data;
    this.propagateChange(this.innerValue);
  }
  writeValue(obj: any): void {
    setTimeout(() => {
      this.innerValue = obj;
      this.loadData();
    });
  }
  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }
  registerOnTouched(fn: any): void {
  }
  setDisabledState?(isDisabled: boolean): void {
  }
  ngOnDestroy(): void {
    // if (this.subscriberDate) {
    //   this.subscriberDate.unsubscribe();
    // }
  }

}
