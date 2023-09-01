import { Component, OnInit, Input, Output, EventEmitter, ChangeDetectorRef, AfterViewInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { IDataForm } from '../../../../core/models/idata-form';
import * as moment from 'moment';
declare var $: any;

@Component({
  selector: 'upd-datetime',
  templateUrl: './upd-datetime.component.html',
})
export class UpdDateTimeComponent implements OnInit {

  @Input()
  name: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  @Input()
  data: any;
  @Input()
  value: number;
  @Input()
  min: number = 0;
  @Input()
  max: number = 999999;
  @Input()
  validateNow: boolean = true;
  @Output()
  saveEvent = new EventEmitter<any>();
  update: boolean = false;
  updating: boolean = false;
  public formNewValue: FormGroup;
  public dataForm: IDataForm = { submitted: false };

  constructor(
    private cdr: ChangeDetectorRef,
    private formBuilder: FormBuilder
  ) {
  }

  ngOnInit() {
    this.formNewValue = this.formBuilder.group({
      newValue: [this.value, []]
    });
    if (this.validateNow){
      this.formNewValue.get('newValue').setValidators([Validators.required, this.dateValidator('DD-MM-YYYY HH:mm'), this.dateValidatorNow()]);
    }else{
      this.formNewValue.get('newValue').setValidators([Validators.required, this.dateValidator('DD-MM-YYYY HH:mm')]);
    }
    this.formNewValue.get('newValue').updateValueAndValidity();
  }

  cancel() {
    this.update = false;
    this.updating = false;
    this.formNewValue.patchValue({ newValue: this.value });
    this.dataForm = { ...this.dataForm, submitted: false };
    this.cdr.markForCheck();
  }
  updateValue() {
    this.update = true;
    setTimeout(() => {
      $("#" + this.name).focus()
    });
  }
  save() {
    this.setValue($("#" + this.name).val());
    this.dataForm = { ...this.dataForm, submitted: true };
    if (this.formNewValue.valid) {
      this.updating = true;
      let temp = { data: this.data, newValue: this.formNewValue.get('newValue').value, component: this }
      this.saveEvent.emit(temp)
    }
  }
  confirm() {
    this.update = false;
    this.updating = false;
    this.value = this.formNewValue.get('newValue').value;
    this.cdr.markForCheck();
  }
  get f() {
    return this.formNewValue.controls;
  }

  setValue(value: any) {
    this.formNewValue.patchValue({ newValue: value });
    this.formNewValue.updateValueAndValidity();  
  }
  dateValidator(format = "DD/MM/YYYY"): any {
    return (control: FormControl): { [key: string]: any } => {
      const val = moment(control.value, format, true);
      if (!val.isValid()) {
        return { invalidDate: true };
      }
      return null;
    };
  }
  dateValidatorNow(): any {
    return (control: FormControl): { [key: string]: any } => {
      let fechaSelDesde = moment(control.value, "DD-MM-YYYY HH:mm", true);
      let fechaActual = moment();
      if (fechaSelDesde.isSameOrAfter(fechaActual, "day")) {
        return null;
      }
      return { invalidDateNow: true };
    };
  }
}
