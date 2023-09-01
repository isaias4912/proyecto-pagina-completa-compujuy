import { Directive, HostListener, Input } from '@angular/core';
declare var $: any;

@Directive({
    selector: '[enter]'
})
export class EnterDirective {

    @Input() enter: string | any;
    constructor() {

    }

    @HostListener('keydown.enter', ['$event'])
    onKeyEnterHandler(event: KeyboardEvent) {
        setTimeout(() => {
            if (typeof this.enter === 'string' || this.enter instanceof String) {
                $("#" + this.enter).focus();
                $("#" + this.enter).select();
            } else {
                if (this.enter.type == 'select2') {
                    $('#' + this.enter.field).select2("open")
                }
            }
        });
    }

}