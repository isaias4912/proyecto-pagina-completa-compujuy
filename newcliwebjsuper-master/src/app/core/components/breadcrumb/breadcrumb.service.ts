import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable()
export class BreadcrumbService {
    private customText = new Subject<String>();
    customText$ = this.customText.asObservable();
    constructor() { }

    changeCustomText(customText) {
        this.customText.next(customText);
    }
}