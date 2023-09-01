import { Component, OnInit,  AfterViewInit, ChangeDetectionStrategy, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

declare var $: any;

@Component({
  selector: 'fields-xxx',
  templateUrl: './fields-xxx.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class zzzComponent extends ComponentPage implements OnInit, AfterViewInit, Fields {

  @Input()
  public dataForm: IDataForm = { submitted: false };
  @Input()
  public form: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
  ) {
    super();
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
    });
  }
  ngAfterViewInit(): void {
    this.initFocus();
  }
  reset() {
    this.form.patchValue({
    });
    this.dataForm = { ...this.dataForm, submitted: false };
    this.initFocus();
  }
  initFocus() {
    setTimeout(() => {
      $("#txtNombre").focus();
    });
  }
  get f() {
    return this.form.controls;
  }
}

