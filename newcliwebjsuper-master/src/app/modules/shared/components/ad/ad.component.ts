import { Component, Input, OnInit, ViewChild, ComponentFactoryResolver, OnDestroy } from '@angular/core';
import { AdDirective } from '../../directives/ad.directive';
import { AdItem } from '../../models/ad-item';
export interface AdComponent {
    data: any;
    idEntity: any;
}

@Component({
    selector: 'ad',
    template: `
              <div>
                <ng-template ad-host></ng-template>
              </div>
            `
})
export class AdComponent implements OnInit, OnDestroy {
    @Input()
    public ad: AdItem;
    currentAdIndex = -1;
    @ViewChild(AdDirective, { static: true }) adHost: AdDirective;

    constructor(private componentFactoryResolver: ComponentFactoryResolver) { }

    ngOnInit() {
        this.loadComponent();
    }

    ngOnDestroy() {
    }

    public loadComponent() {
        if (this.ad) {
            const componentFactory = this.componentFactoryResolver.resolveComponentFactory(this.ad.component);
            const viewContainerRef = this.adHost.viewContainerRef;
            viewContainerRef.clear();
            const componentRef = viewContainerRef.createComponent(componentFactory);
            (<AdComponent>componentRef.instance).idEntity = this.ad.data.idEntity;
            (<AdComponent>componentRef.instance).data = this.ad.data;
        }
    }

}