import { Component, Injectable } from '@angular/core';
import {
  NgbDateAdapter,
  NgbDateStruct,
} from '@ng-bootstrap/ng-bootstrap';

/**
 * This Service handles how the date is represented in scripts i.e. ngModel.
 */
@Injectable()
export class CustomDateAdapter extends NgbDateAdapter<string> {

  readonly DELIMITER = '-';

  fromModel(value: string): NgbDateStruct {
    let result: NgbDateStruct = null;
    if (value) {
      let date = value.split(this.DELIMITER);
      result = {
        day: parseInt(date[0], 10),
        month: parseInt(date[1], 10),
        year: parseInt(date[2], 10)
      };
    }
    return result;
  }

  toModel(date: NgbDateStruct): string {
    let result: string = null;
    if (date) {
      let day = date.day.toString().length == 1 ? "0" + date.day : date.day;
      let month = date.month.toString().length == 1 ? "0" + date.month : date.month;
      result = day + this.DELIMITER + month + this.DELIMITER + date.year;
    }
    return result;
  }
}
