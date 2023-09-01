import { ActivatedRoute, Params, Router } from '@angular/router';
import { AppInjector } from './services/utils/app-injector';
import { ToastrService } from 'ngx-toastr';
import { LoadingBarService } from '@ngx-loading-bar/core';
import { environment } from '../../environments/environment'
import { DatePipe, Location } from '@angular/common';
import { ParamPagination } from './models/param-pagination';
import { Pagination } from '../modules/shared/models/pagination';
import { UtilPage } from './util-page';
import { Input, HostListener, Component, Output, EventEmitter } from '@angular/core';
import { Roles } from './enums';
export enum TypeComponent {
  page = 1,
  modal = 2
};
@Component({
  template: ''
})
export abstract class ComponentPage extends UtilPage {
  private _activatedRoute: ActivatedRoute;
  private _router: Router
  protected loadingBarService: LoadingBarService;
  protected messageToast: ToastrService;
  protected location: Location
  public checkAll: boolean = false;
  public showFilter: boolean = false;
  public disabledButtonsTable: boolean = false;
  public listChecked: Array<any>;
  public baseURLImg: string = environment.baseURLFiles;
  public baseURLFiles: string = environment.baseURLFiles;
  public heigthScreen = $(window).height() - 240;
  protected config: Pagination;
  protected id: any;
  protected firstExecute: boolean = true;
  public title: string;
  @Input()
  public typeComponent: TypeComponent = TypeComponent.page;
  public internalId: string = Math.random().toString(36).substring(5);// si no viene el nombre le mandamos uno al azar
  public roles={
    level1:[Roles.ROLE_ADMIN],
    level2:[Roles.ROLE_ADMIN, Roles.ROLE_GERENTE],
    level3:[Roles.ROLE_ADMIN, Roles.ROLE_GERENTE, Roles.ROLE_CAJERO],
    level4:[Roles.ROLE_ADMIN, Roles.ROLE_GERENTE, Roles.ROLE_CAJERO, Roles.ROLE_VENDEDOR],
  }
  public paramPagination: ParamPagination = new ParamPagination();
  constructor(
  ) {
    super();
    const injector = AppInjector.getInjector();
    this._activatedRoute = injector.get(ActivatedRoute);
    this._router = injector.get(Router);
    this.location = injector.get(Location);
    this.messageToast = injector.get(ToastrService);
    this.loadingBarService = injector.get(LoadingBarService);
    this.config = new Pagination();
  }
  @HostListener('window:popstate', ['$event'])
  onPopState(event) {
    this.firstExecute = true;
  }
  protected setParamURL(params: any): Promise<Boolean> {
    Object.keys(params).forEach(function (k) {
      if (params[k] == "null" || params[k] == "") {
        params[k] = null;
      }
    });
    const queryParams: Params = params;
    return this._router.navigate([],
      {
        relativeTo: this._activatedRoute,
        queryParams,
        queryParamsHandling: 'merge', // remove to replace all query params by provided
      });
  }
  protected getFilterFromUrl(
    filter: any,
    params: any,
    decripts: any = [],
    parseInteger: any = [],
    parseDecimals: any = [],
    arrays: any = [],
    arraysObjects: any = [],
    arraysString: any = []): any {
    let dataTemp: any = {};
    Object.keys(filter).forEach((k) => {
      if (params[k]) {
        let array = false;
        arrays.forEach(key => {
          if (key == k) {//para los array convertimos
            array = true;
          }
        });
        if (!array) { // si no es array
          dataTemp[k] = decodeURI(params[k]);;
        } else { //es array
          let tempArray = [].concat(params[k]);
          let temp = [];
          tempArray.forEach(suc => {
            temp.push({ id: parseInt(suc) })
          });
          dataTemp[k] = temp;
        }
      } else {
        dataTemp[k] = filter[k];
      }
      decripts.forEach(key => {
        if (k == key && dataTemp[k]) {
          dataTemp[k] = this.decrypt(dataTemp[k]);
        }
      })
      parseInteger.forEach(key => {
        if (k == key) {
          if (dataTemp[k]) {
            dataTemp[k] = parseInt(dataTemp[k]);
          }
        }
      })
      arraysObjects.forEach(key => {
        if (k == key) {
          if (dataTemp[k]) {
            dataTemp[k] = { id: parseInt(dataTemp[k]) };
          }
        }
      })
      arraysString.forEach(key => {
        if (k == key) {
          if (dataTemp[k]) {
            let tempArray = [].concat(params[k]);
            // let temp = [];
            // tempArray.forEach(suc => {
            //   temp.push({ id: parseInt(suc) })
            // });
            dataTemp[k] = tempArray;

            // dataTemp[k] = dataTemp[k];
          }
        }
      })
    });
    return dataTemp;
  }
  protected getParamURLFromArray(data: []) {
    let arrayTemp = [];
    if (data) {
      data.forEach((obj: any) => {
        arrayTemp.push(obj.id);
      });
    }
    return arrayTemp;
  }
  protected getParamURLFromArrayId(data: []) {
    let arrayTemp = [];
    if (data) {
      data.forEach((obj: any) => {
        arrayTemp.push(obj);
      });
    }
    return arrayTemp;
  }
  protected getParamURLFromArrayObj(data: []) {
    let arrayTemp = [];
    if (data) {
      data.forEach((obj: any) => {
        arrayTemp.push(obj.id);
      });
    }
    return arrayTemp;
  }
  public checkRow(items) {
    let disabled = true;
    let todosActivos = true;
    items.forEach(item => {
      todosActivos = (todosActivos && item.$$activo);
      if (disabled) {
        item.$$activo ? disabled = false : disabled = true;
      }
    })
    this.checkAll = todosActivos;
    this.disabledButtonsTable = disabled;
    this.setListChequed(items);
  }

  public checkAllRow(value, items) {
    items.forEach(item => {
      item.$$activo = value;
    });
    this.disabledButtonsTable = !value;
    this.checkAll = value; // puesto al final revisar
    this.setListChequed(items);
    // this.$emit('checkRowTable');
  }
  private setListChequed(data: any) {
    this.listChecked = [];
    data.forEach((item: any) => {
      if (item.$$activo) {
        this.listChecked.push({ id: item.id });
      }
    });
  };

  protected languaje: any = {
    inputTooShort: () => {
      return 'Ingrese mas de 2 caracteres...';
    },
    searching: () => {
      return 'Buscando…';
    },
    noResults: () => {
      return 'No se encontraron resultados';
    }
  };

  protected getDateFromTimestamp(date: any) {
    const datePipe = new DatePipe('de-at');
    return datePipe.transform(date, 'dd/MM/yyyy');
  }


  setPagination(data: any) {
    this.config.currentPage = data.page;
    this.config.itemsPerPage = data.pageSize;
    this.config.totalItems = data.total;
  }
  setValuePagination(datos) {
    this.paramPagination = {
      ...this.paramPagination,
      totalItems: datos.total,
      itemsPerPage: datos.pageSize,
      page: datos.page,
      maxSize: 5,
      bigTotalItems: 175,
      bigCurrentPage: 1,
      filtroNombre: "",
      totalPages: Math.ceil((datos.total / datos.pageSize)),
      getSize: ()=>datos.total
    }
    this.checkAll = false;
    this.disabledButtonsTable = true;
  }

  cancel() {
    this.location.back();
  }
  isNumber(value: string | number): boolean {
    return ((value != null) &&
      (value !== '') &&
      !isNaN(Number(value.toString())));
  }
  isNumberInt(value: string | number): boolean {
    if (this.isNumber(value)) {
      if (value.toString().includes('.') || value.toString().includes(',')) {
        return false;
      }
      let val;
      try {
        val = parseInt(value.toString(), 10);
        if (typeof parseInt(val) === 'number' && (parseInt(val) % 1) === 0) {
          return true;
        }
      } catch (e) {
        return false;
      }
    }
    return false;
  }
  public isPage(typeComponent: any) {
    return typeComponent == TypeComponent.page;
  }
  public isModal(typeComponent: any) {
    return typeComponent == TypeComponent.modal;
  }
}
