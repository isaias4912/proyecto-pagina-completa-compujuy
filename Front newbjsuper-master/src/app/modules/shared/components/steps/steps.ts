import { Component, Input, Output, EventEmitter } from '@angular/core';
import { MenuItem } from '../../models/menu-item';

@Component({
    selector: 'steps',
    template: `
        <div [ngClass]="{'ui-steps ui-widget ui-helper-clearfix mb-0':true,'ui-steps-readonly':readonly}" [ngStyle]="style" [class]="styleClass">
            <ul role="tablist">
                <li *ngFor="let item of model; let i = index" class="ui-steps-item" #menuitem [ngStyle]="item.style" [class]="item.styleClass" role="tab" [attr.aria-selected]="i === activeIndex" [attr.aria-expanded]="i === activeIndex"
                    [ngClass]="{'ui-state-highlight ui-steps-current':(i === activeIndex),
                        'ui-state-default':(i !== activeIndex),
                        'ui-state-complete':(i < activeIndex),
                        'ui-state-disabled ui-steps-incomplete':item.disabled||(i !== activeIndex && readonly)}">
                    <a *ngIf="!item.routerLink" [attr.href]="item.url" class="ui-menuitem-link" role="presentation" (click)="itemClick($event, item, i)" [attr.target]="item.target" [attr.id]="item.id" 
                        [attr.tabindex]="item.disabled||(i !== activeIndex && readonly) ? null : (item.tabindex ? item.tabindex : '0')">
                        <span class="ui-steps-number">{{i + 1}}</span>
                        <span class="ui-steps-title">{{item.label}}</span>
                    </a>
                    <a *ngIf="item.routerLink" [routerLink]="item.routerLink" [queryParams]="item.queryParams" role="presentation" [routerLinkActive]="'ui-state-active'" [routerLinkActiveOptions]="item.routerLinkActiveOptions||{exact:false}" class="ui-menuitem-link" 
                        (click)="itemClick($event, item, i)" [attr.target]="item.target" [attr.id]="item.id" [attr.tabindex]="item.disabled||(i !== activeIndex && readonly) ? null : (item.tabindex ? item.tabindex : '0')">
                        <span class="ui-steps-number">{{i + 1}}</span>
                        <span class="ui-steps-title">{{item.label}}</span>
                    </a>
                </li>
            </ul>
        </div>
    `,
    styleUrls: ['./steps.css']
})
export class StepsComponent {

    @Input() activeIndex: number = 0;

    @Input() model: MenuItem[];

    @Input() readonly: boolean = true;

    @Input() style: any;

    @Input() styleClass: string;

    @Output() activeIndexChange: EventEmitter<any> = new EventEmitter();

    itemClick(event: Event, item: MenuItem, i: number) {
        if (this.readonly || item.disabled) {
            event.preventDefault();
            return;
        }
        let previusIndex= this.activeIndex;
        this.activeIndexChange.emit(i);

        if (!item.url) {
            event.preventDefault();
        }

        if (item.command) {
            item.command({
                originalEvent: event,
                item: item,
                index: i,
                indexPrevius:previusIndex
            });
        }
    }

}