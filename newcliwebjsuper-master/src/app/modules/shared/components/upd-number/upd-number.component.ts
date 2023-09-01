import { Component, OnInit, Input, Output, EventEmitter, ChangeDetectorRef } from '@angular/core';
import { Observable } from 'rxjs';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IDataForm } from 'src/app/core/models/idata-form';

@Component({
  selector: 'upd-number',
  templateUrl: './upd-number.component.html',
})
export class UpdNumberComponent implements OnInit {

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
  @Output()
  saveEvent = new EventEmitter<any>();
  // newValue: number;
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
      newValue: [this.value, [Validators.required, Validators.min(this.min), Validators.max(this.max)]]
    });
  }

  cancel() {
    this.update = false;
    this.updating = false;
    this.formNewValue.patchValue({newValue:this.value});
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
}
