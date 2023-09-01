import { Component, OnInit, Input, forwardRef, ChangeDetectionStrategy, AfterViewInit, ViewChild, OnDestroy, ChangeDetectorRef }
  from '@angular/core';
import { NG_VALUE_ACCESSOR, ControlValueAccessor, FormBuilder, FormGroup } from '@angular/forms';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { CustomDateAdapter } from '../../utils/custom-date-adapter';
import { CustomDateParserFormatter } from '../../utils/custom-date-parse-formatter';
import { Subscription } from 'rxjs';
export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => DateComponent),
  multi: true
};
import * as moment from 'moment';

@Component({
  selector: 'app-date',
  templateUrl: './date.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR,
    { provide: NgbDateAdapter, useClass: CustomDateAdapter },
    { provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter }
  ],
})
export class DateComponent implements OnInit, AfterViewInit, ControlValueAccessor, OnDestroy {

  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Input()
  enter: string = "default";
  public innerValue: any = '';
  propagateChange = (_: any) => { }
  public formDate: FormGroup;
  private subscriberDateTemp: Subscription;
  private subscriberDate: Subscription;
  constructor(
    private ngbCalendar: NgbCalendar,
    private dateAdapter: NgbDateAdapter<string>,
    private formBuilder: FormBuilder,
    private cdr: ChangeDetectorRef
  ) {

  }

  ngOnInit() {
    this.formDate = this.formBuilder.group({
      date: [this.dateAdapter.toModel(this.ngbCalendar.getToday()), []],
      dateTemp: [this.dateAdapter.toModel(this.ngbCalendar.getToday()), []]
    });
    this.subscriberDate = this.formDate.get('date').valueChanges.subscribe(date => {
      this.setData(date);
      this.formDate.patchValue({ dateTemp: date }, { emitEvent: false, onlySelf: true });
    });
    this.subscriberDateTemp = this.formDate.get('dateTemp').valueChanges.subscribe(date => {
      if (date) {
        this.formDate.patchValue({ date: date });
      }
    });
  }
  loadData(data: any) {
    this.setDate(data);
  }

  changeValueDateTemp() {
    let date = this.formDate.get('dateTemp').value;
    if (date) {
      this.formDate.patchValue({ date: date });
    }
  }
  ngAfterViewInit(): void {
  }

  setToday() {
    this.setDate(this.dateAdapter.toModel(this.ngbCalendar.getToday()));
  }

  setDate(date: any) {
    if (date && date != "") {
      let dateMoment = moment(date, 'DD-MM-YYYY');
      let tempDate: NgbDateStruct = { day: null, month: null, year: null };
      let day = dateMoment.date()
      let month = dateMoment.month()  // jan=0, dec=11
      let year = dateMoment.year()
      tempDate.day = day;
      tempDate.month = month;
      tempDate.year = year;
      this.formDate.patchValue({ date: date, dateTemp: date }); /// emite el evento de cambio para setear los datos
    } else {
      this.formDate.patchValue({ date: null, dateTemp: date });
    }
  }

  setData(data: any) {
    this.innerValue = data;
    if (this.innerValue && this.innerValue.replace(/\s/g, "") == "") {
      this.innerValue = null;
    }
    this.cdr.markForCheck();
    this.propagateChange(this.innerValue);
  }
  writeValue(obj: any): void {
    if (obj != undefined) {
      // setTimeout(() => {
        this.innerValue = obj;
        this.loadData(this.innerValue);
        // this.cdr.markForCheck();
      // });
    }
  }
  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }
  registerOnTouched(fn: any): void {
  }
  setDisabledState?(isDisabled: boolean): void {
  }
  ngOnDestroy(): void {
    if (this.subscriberDate) {
      this.subscriberDate.unsubscribe();
    }
    if (this.subscriberDateTemp) {
      this.subscriberDateTemp.unsubscribe();
    }
  }

}
