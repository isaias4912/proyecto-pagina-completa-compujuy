import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router, NavigationEnd, Event } from '@angular/router';
import { filter, distinctUntilChanged } from 'rxjs/operators';
import { IBreadCrumb } from './ibreadcrumb';
import { BreadcrumbService } from './breadcrumb.service';
declare var $: any;

@Component({
  selector: 'app-breadcrumb',
  templateUrl: './breadcrumb.component.html',
  styleUrls: ['./breadcrumb.component.css']
})
export class BreadcrumbComponent implements OnInit {

  public fullScreenMode: boolean = false;
  public breadcrumbs: IBreadCrumb[]
  public help: any = {};
  public extraData: any = {};
  @Output()
  eventScreen = new EventEmitter<string>();

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private breadcrumbService: BreadcrumbService
  ) {
    this.breadcrumbs = [...this.buildBreadCrumb(this.activatedRoute.root)];
    this.help = { ...this.buildHelp(this.activatedRoute.root) };
    this.extraData = { ...this.buildExtraData(this.activatedRoute.root) };
  }

  ngOnInit() {
    this.router.events.pipe(
      filter((event: Event) => event instanceof NavigationEnd),
      distinctUntilChanged(),
    ).subscribe(() => {
      this.breadcrumbs = [...this.buildBreadCrumb(this.activatedRoute.root)];
      this.help = { ...this.buildHelp(this.activatedRoute.root) };
    });
    this.breadcrumbService.customText$.subscribe((resp: string) => {
      if (this.breadcrumbs) {
        let index = 0;
        this.breadcrumbs.forEach(item => {
          let replaced = $("#labelCustomText" + index).html().replace(/{{customText}}/g, resp);
          $("#labelCustomText" + index).html(replaced);
          index++;
        });
      }
    });
  }

  /**
   * Recursively build breadcrumb according to activated route.
   * @param route
   * @param url
   * @param breadcrumbs
   */
  buildBreadCrumb(route: ActivatedRoute, url: string = ''): IBreadCrumb[] {
    let breadcrumbs = route.routeConfig && route.routeConfig.data ? route.routeConfig.data.breadcrumb : '';
    if (route.firstChild) {
      return this.buildBreadCrumb(route.firstChild);
    }
    return breadcrumbs;
  }
  buildHelp(route: ActivatedRoute, url: string = ''): IBreadCrumb[] {
    let help = route.routeConfig && route.routeConfig.data ? route.routeConfig.data.help : '';
    // let maximize = route.routeConfig && route.routeConfig.data ? route.routeConfig.data.maximizeScreen : false;
    if (route.firstChild) {
      return this.buildHelp(route.firstChild);
    }
    return help;
  }
  buildExtraData(route: ActivatedRoute, url: string = ''): {} {
    let maximize = route.routeConfig && route.routeConfig.data ? route.routeConfig.data.maximizeScreen : false;
    if (route.firstChild) {
      return this.buildExtraData(route.firstChild);
    }
    return { maximize: maximize };
  }
  public fullScreen() {
    this.fullScreenMode = true;
    this.eventScreen.emit("fullScreen");
  }
  public resetScreen() {
    this.fullScreenMode = false;
    this.eventScreen.emit("resetScreen");
  }
  // public requestFullscreen(){
  //   document.getElementById('mainAppNewstock').requestFullscreen();
  //   // $("#mainAppNewstock")
  // }
}