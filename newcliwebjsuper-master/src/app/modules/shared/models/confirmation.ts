import { EventEmitter } from '@angular/core';

export class Confirmation {
    message?: string;
    key?: string;
    icon?: string;
    header?: string;
    accept?: Function;
    reject?: Function;
    update?: Function;
    select?: Function;
    save?: Function;
    cancel?: Function;
    acceptLabel?: string;
    rejectLabel?: string;
    acceptVisible?: boolean;
    rejectVisible?: boolean;
    blockScroll?: boolean;
    acceptEvent?: EventEmitter<any>;
    rejectEvent?: EventEmitter<any>;
    updateEvent?: EventEmitter<any>;
    saveEvent?: EventEmitter<any>;
    selectEvent?: EventEmitter<any>;
    cancelEvent?: EventEmitter<any>;
}
