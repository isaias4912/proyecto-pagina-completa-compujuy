import { Component, AfterViewInit, OnDestroy, ViewChildren, QueryList, ChangeDetectorRef, OnInit } from '@angular/core';
import { AuthService } from '../../../../core/services/auth/auth.service';
import { UserLogged } from '../../../../core/models/user-logged';
import { iif, Observable, of, Subscription } from 'rxjs';
import { filter, delay, switchMap, pluck } from 'rxjs/operators';
import { NavigationEnd, Router } from '@angular/router';
import { UserHTTPService } from 'src/app/core/services/user/user-http.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements AfterViewInit, OnDestroy {
  userLogged: UserLogged = new UserLogged();
  logged$: Observable<boolean>;
  loggedSubs: Subscription;
  private urlWithoutParams: string = null;
  public menuItems: any;
  private menuItemsTemp: any;
  @ViewChildren('allTheseThings') things: QueryList<any>;
  private menuSelected: any;
  private init: boolean = false;;

  constructor(
    private authService: AuthService,
    private router: Router,
    private userHTTPService: UserHTTPService,
    private cdr: ChangeDetectorRef
  ) {
    this.logged$ = this.authService.logged$;
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
          if (!this.init) {
            this.menuItems = resp.menuPrincipal.menuItems;
            this.menuItemsTemp = [...resp.menuPrincipal.menuItems];
            localStorage.setItem('menuJSuper', JSON.stringify(resp));
          }
          this.check();
          this.things.changes.subscribe(t => {
            this.finishRenderedMenu();
            this.init = true;
          })
        }

      });

  }

  ngAfterViewInit(): void {
    this.loggedSubs = this.logged$.pipe(filter(resp => resp == true), delay(0)).subscribe(resp => {
      this.userLogged = this.authService.getUserLogged();
    });




  }
  public filterBy(menu: any, prop: string) {
    return menu.sort((a, b) => a[prop] > b[prop] ? 1 : a[prop] === b[prop] ? 0 : -1);
  }
  finishRenderedMenu() {
    if (!this.init) {
      this.loadMegmenu();
    }
  }
  private loadMegmenu() {
    $(document).ready(function () {

      // Get elements from the DOM
      const menuBtn = document.querySelector('.menu-btn');
      const menu = document.querySelector('.menu-container');
      const dropdowns = document.querySelectorAll('.dropdown > div');
      const subDropdowns = document.querySelectorAll('.sub-dropdown > div');

      // Toggle variable
      let menuOpen = false;

      // Set click event to menu button
      menuBtn.addEventListener('click', () => {
        // Toggle mega menu show class
        menu.classList.toggle('mega-menu-show');
        // If the menu open variable is false
        if (menuOpen === false) {
          // Set the close icon to the menu button
          menuBtn.innerHTML = `<span class="fa fa-times text-20"></span>
        `;
          // Set menu open to true
          menuOpen = true;
        }
        else {
          // Set the menu icon to the menu button
          menuBtn.innerHTML = `<span class="fa fa-bars text-20"></span>
        `;
          // Set menu open to false
          menuOpen = false;
        }
      });

      // Select all dropdowns
      dropdowns.forEach(dropdown => {
        // Add click event
        dropdown.addEventListener("click", (e) => {
          // Toggle dropdown menu show class
          dropdown.nextElementSibling.classList.toggle('menu-show');
          // Toggle icon rotated class
          dropdown.lastElementChild.classList.toggle('icon-rotated');
        });
      });

      // Select all sub dropdowns
      subDropdowns.forEach(subDropdown => {
        // Add click event
        subDropdown.addEventListener('click', (e) => {
          // Toggle sub dropdown menu show class
          subDropdown.nextElementSibling.classList.toggle('sub-menu-show');
          // Toggle icon rotated class
          subDropdown.lastElementChild.classList.toggle('icon-rotated');
        });
      });


      // disabling inspect element
      document.addEventListener("contextmenu", function (e) {
        e.preventDefault(); //this prevents right click
      });
      // document.onkeydown = function (e) {
      //   if (event.keycode == 123) {
      //     return false;
      //   }
      //   if (e.ctrlKey && e.shiftKey && e.keyCode == "I".charCodeAt(0)) {
      //     return false;
      //   }
      //   if (e.ctrlKey && e.shiftKey && e.keyCode == "C".charCodeAt(0)) {
      //     return false;
      //   }
      //   if (e.ctrlKey && e.shiftKey && e.keyCode == "J".charCodeAt(0)) {
      //     return false;
      //   }
      //   if (e.ctrlKey && e.keyCode == "U".charCodeAt(0)) {
      //     return false;
      //   }
      //   if (e.ctrlKey && e.keyCode == "S".charCodeAt(0)) {
      //     return false;
      //   }
      // };
    });
  }
  check() {
    this.rebuildMenu(this.urlWithoutParams);
    if (this.menuSelected) {
      this.refreshActiveMenu(this.menuSelected.id, this.menuItems);
    }
  }
  public refreshActiveMenu(id: number, menuItems: Array<any>) {
    let found = false;
    menuItems.forEach(menu => {
      if (id == menu.id) {
        found = true;
        menu.activeMenuUrl = true;
        setTimeout(() => {
          let parents = $("#menu" + menu.id).attr("data-parents");
          let res = parents.toString().split("-");
          $("#menu" + menu.id).addClass('m-active');
          if (res.length > 0) {
            res.forEach(x => {
              $("#menu" + x).addClass('m-active');
            })
          }
        });
      } else {
        // esto se pone porq habia un bug, se seleccionar por eje list prod, luego list ventas, y list prod sigue en negrita seleccionado
        // $("#liMenu" + menu.id).removeClass('m-active');
        $("#menu" + menu.id).parent().removeClass('m-active');
        $("#menu" + menu.id).removeClass('m-active');
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
  public getActiveRoute(): string {
    const urlTree = this.router.parseUrl(this.router.url);
    const urlWithoutParams = urlTree.root.children['primary'].segments.map(it => it.path).join('/')
    return urlWithoutParams;
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
  public logout() {
    this.authService.logout().subscribe(rexp => {
      window.location.href = '/auth/login'; // with this evict issue get menu
    });
  }

  ngOnDestroy(): void {
    this.loggedSubs.unsubscribe();
  }
  public getFormatRole(rol: any) {
    let replaced = rol.replace(/ROLE_/g, '').toLowerCase();
    replaced = replaced.charAt(0).toUpperCase() + replaced.slice(1)
    return replaced;
  }
}
