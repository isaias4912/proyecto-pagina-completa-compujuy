import { Component, OnInit, AfterViewInit, ViewChildren, QueryList, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import 'perfect-scrollbar';
import { AuthService } from '../../../../core/services/auth/auth.service';
import { filter, pluck, switchMap, tap } from 'rxjs/operators';
import { iif, Observable, of } from 'rxjs';
import { UserHTTPService } from '../../../../core/services/user/user-http.service';
import 'metismenu';
import { MenuPrincipal } from '../../../../core/models/menu-principal';
import { ActivatedRoute, Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class MenuComponent implements OnInit, AfterViewInit {
  menuPrincipal$: Observable<MenuPrincipal>;
  @ViewChildren('allTheseThings') things: QueryList<any>;
  public menuItems: any;
  private menuItemsTemp: any;
  private menuSelected: any;
  private finishEvents: Array<boolean> = new Array<boolean>();
 
  private urlWithoutParams: string = null;
  constructor(
    private authService: AuthService,
    private userHTTPService: UserHTTPService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {
    this.finishEvents[0] = false;
    this.finishEvents[1] = false;
    this.finishEvents[2] = false;
    this.router.events.pipe(switchMap((data: any) => iif(() => {
      if (data instanceof NavigationEnd) {
        const urlTree = this.router.parseUrl(data.url);
        this.urlWithoutParams = urlTree.root.children['primary']?.segments.map(it => it.path).join('/')
        return true;
      }
      return false;
    },
      this.userHTTPService.getMenu().pipe(pluck("data")),
      of(null)))).subscribe((resp: any) => {
        if (resp) {
          this.menuItems = resp.menuPrincipal.menuItems;
          this.menuItemsTemp = [...resp.menuPrincipal.menuItems];
          localStorage.setItem('menuJSuper', JSON.stringify(resp));
          this.check();
          this.things.changes.subscribe(t => {
            this.finishRenderedMenu();
          })
        }

      });
  }

  ngOnInit() {

  }

  check() {
    this.rebuildMenu(this.urlWithoutParams);
    if (this.menuSelected) {
      this.refreshActiveMenu(this.menuSelected.id, this.menuItems);
    }
  }


  ngAfterViewInit(): void {
  }
  finishRenderedMenu() {
    this.loadMegaMenu();
    // $('.mobile-toggle-header-nav').click(function () {
    //   $(this).toggleClass('active');
    //   $('.app-header__content').toggleClass('header-mobile-open');
    // });
    // $('.mobile-toggle-nav').click(function () {
    //   $(this).toggleClass('is-active');
    //   $('.app-container').toggleClass('sidebar-mobile-open');
    // });
    this.initComponentsView();
  }
  private initComponentsView() {
  }
  private loadMegaMenu() {
    /*global $ */
    $(document).ready(function () {

      "use strict";

      $('.menu > ul > li:has( > ul)').addClass('menu-dropdown-icon');
      //Checks if li has sub (ul) and adds class for toggle icon - just an UI


      $('.menu > ul > li > ul:not(:has(ul))').addClass('normal-sub');
      //Checks if drodown menu's li elements have anothere level (ul), if not the dropdown is shown as regular dropdown, not a mega menu (thanks Luka Kladaric)

      // $(".menu > ul").before("<a href=\"#\" class=\"menu-mobile\">Navigation</a>");

      //Adds menu-mobile class (for mobile toggle menu) before the normal menu
      //Mobile menu is hidden if width is more then 959px, but normal menu is displayed
      //Normal menu is hidden if width is below 959px, and jquery adds mobile menu
      //Done this way so it can be used with wordpress without any trouble
      // $("#liMain42").children("ul").fadeIn(150);
      $(".menu > ul > li").hover(
        function (e) {
          if ($(window).width() > 943) {
            var $setter = $("#liMain" + $(this).attr("data-xxx"));
            $("#triangulo" + $(this).attr("data-xxx")).css("width", $setter.width() + "px");
            $("#triangulo" + $(this).attr("data-xxx")).show();
            $(this).children("ul").fadeIn(150);
            e.preventDefault();
          }
        }, function (e) {
          if ($(window).width() > 943) {
            $("#triangulo" + $(this).attr("data-xxx")).hide();
            $(this).children("ul").fadeOut(150);
            console.log('sssssssssssssssssssssssssssssssssssss8888',)
            e.preventDefault();
          }
        }
      );
      //If width is more than 943px dropdowns are displayed on hover


      //the following hides the menu when a click is registered outside
      $(document).on('click', function (e) {
        if ($(e.target).parents('.menu').length === 0)
          $(".menu > ul").removeClass('show-on-mobile');
      });

      $(".menu > ul > li").click(function () {
        //no more overlapping menus
        //hides other children menus when a list item with children menus is clicked
        var thisMenu = $(this).children("ul");
        var prevState = thisMenu.css('display');
        $(".menu > ul > li > ul").fadeOut();
        if ($(window).width() < 943) {
          if (prevState !== 'block')
            thisMenu.fadeIn(150);
        }
      });
      //If width is less or equal to 943px dropdowns are displayed on click (thanks Aman Jain from stackoverflow)

      $(".mobile-toggle-nav").click(function (e) {
        $(".menu > ul").toggleClass('show-on-mobile');
        e.preventDefault();
      });

    });
  }
  private rebuildMenu(urlWithoutParams = null) {
    let menu = null;
    this.setInitMenu(this.menuItems);
    if (urlWithoutParams) {
      this.menuSelected = this.getMenuFromURL(urlWithoutParams, this.menuItems);
    } else {
      this.menuSelected = this.getMenuFromURL(this.getActiveRoute(), this.menuItems);
    }
    this.cdr.markForCheck();
  }
  public filterBy(menu: any, prop: string) {
    return menu.sort((a, b) => a[prop] > b[prop] ? 1 : a[prop] === b[prop] ? 0 : -1);
  }

  public getActiveRoute(): string {
    const urlTree = this.router.parseUrl(this.router.url);
    const urlWithoutParams = urlTree.root.children['primary'].segments.map(it => it.path).join('/')
    return urlWithoutParams;
  }
  public setInitMenu(menuItems: Array<any>) {
    let found = null;
    menuItems.forEach(menu => {
      if (menu) {
        menu.activeMenuUrl = false;
        if (menu && menu.menuItems) {
          if (menu.menuItems && menu.menuItems.length > 0) {
            found = this.setInitMenu(menu.menuItems)
          }
        }
      }
    });
  }
  public refreshActiveMenu(id: number, menuItems: Array<any>) {
    let found = false;
    menuItems.forEach(menu => {
      if (id == menu.id) {
        found = true;
        menu.activeMenuUrl = true;
        setTimeout(() => {
          let parents = $("#aMenu" + menu.id).attr("data-parents");
          let res = parents.toString().split("-");
          $("#aMenu" + menu.id).addClass('m-active');
          if (res.length > 0) {
            res.forEach(x => {
              $("#aMenu" + x).addClass('m-active');
            })
          }
        });
      } else {
        // esto se pone porq habia un bug, se seleccionar por eje list prod, luego list ventas, y list prod sigue en negrita seleccionado
        $("#liMenu" + menu.id).removeClass('mm-active');
        $("#aMenu" + menu.id).parent().removeClass('mm-active');
        $("#aMenu" + menu.id).removeClass('mm-active');
        menu.activeMenuUrl = false;
        if (menu && menu.menuItems) {
          if (menu.menuItems && menu.menuItems.length > 0) {
            found = this.refreshActiveMenu(id, menu.menuItems)
            menu.activeMenuUrl = found;
          }
        }
        // }
      }

    });
    return found;
  }


  public getMenuFromURL(url: string, menuItems: Array<any>) {
    let found = null;
    menuItems.forEach(menu => {
      if (!found && menu) {
        if (this.removeSlashes(url) == this.removeSlashes(menu.routerLink)) {
          found = menu;
        } else {
          if (menu && menu.menuItems) {
            if (menu.menuItems && menu.menuItems.length > 0) {
              found = this.getMenuFromURL(url, menu.menuItems)
            }
          }
        }
      }
    });
    if (found) {
      return found;
    } else {
      return null;
    }
  }

  /**
   * 
   * @param url Quitas todas las barrras  / de la url
   */
  removeSlashes(url) {
    if (url) {
      return url.replace(/\//g, '');
    } else {
      '';
    }
  }
}
