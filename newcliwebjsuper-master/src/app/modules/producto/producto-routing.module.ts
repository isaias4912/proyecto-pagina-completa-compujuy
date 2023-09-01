import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProductoComponent } from './producto.component';
import { UpdateProductoComponent } from './components/update/update-producto.component';
import { UpdatePriceComponent } from './components/prices/update/update-price.component';
import { HistoryProdComponent } from './components/history/history-prod.component';
import { AddStockComponent } from './components/stock/add/add-stock.component';
import { OutStockComponent } from './components/stock/out/out-stock.component';
import { StockNewPaseComponent } from './components/stock/pases/new/new-pase.component';
import { ListPasesStockComponent } from './components/stock/pases/list/list-pases-stock.component';
import { ListListPricesComponent } from './components/prices/list-prices/list/list-list-prices.component';
import { NewListPricesComponent } from './components/prices/list-prices/new/new-list-prices.component';
import { UpdateListPricesComponent } from './components/prices/list-prices/update/update-list-prices.component';
import { ListExpirationComponent } from './components/expiration/list/list-expiration.component';
import { NewExpirationComponent } from './components/expiration/new/new-expiration.component';
import { GenerateBarcodePage } from './pages/producto/barcode/gen-barcode.page';
import { ListProdPage } from './pages/producto/list-prod.page';
import { NewProdPage } from './pages/producto/new-prod.page';
import { NewProdCompPage } from './pages/producto/new-prod-comp.page';
import { UpdateProdPage } from './pages/producto/update-prod.page';
import { UpdateProdCompPage } from './pages/producto/update-prod-comp.page';

const routes: Routes = [
  {
    path: '',
    component: ProductoComponent,
    children: [
      {
        path: 'lista',
        component: ListProdPage,
        pathMatch: 'full',
        data: {
          title: 'Lista de productos',
          breadcrumb: [
            {
              label: 'Lista de productos',
              url: null
            }
          ],
          help: {
            url: 'https://newstock.compujuy.com.ar/soporte/gestion-productos?view=article&id=16&catid=15'
          },
          maximizeScreen: true
        }
      },
      {
        path: 'nuevo',
        component: NewProdPage,
        pathMatch: 'full',
        data: {
          title: 'Nuevo producto',
          breadcrumb: [
            {
              label: 'Lista de productos',
              url: '/productos/lista'
            },
            {
              label: 'Nuevo producto',
              url: null
            }
          ],
          help: {
            url: 'https://newstock.compujuy.com.ar/soporte/gestion-productos?view=article&id=15&catid=15'
          }
        }
      },
      {
        path: 'nuevo-producto-compuesto',
        component: NewProdCompPage,
        pathMatch: 'full',
        data: {
          title: 'Nuevo producto compuesto',
          breadcrumb: [
            {
              label: 'Lista de productos',
              url: '/productos/lista'
            },
            {
              label: 'Nuevo producto compuesto',
              url: null
            }
          ],
          help: {
            url: 'https://newstock.compujuy.com.ar/soporte/gestion-productos?view=article&id=15&catid=15'
          }
        }
      },
      {
        path: 'update/:idProducto',
        component: UpdateProdPage,
        data: {
          breadcrumb: [
            {
              label: 'Lista de productos',
              url: '/productos/lista'
            },
            {
              label: 'Editar producto - {{customText}}',// pageTwoID Parameter value will be add 
              url: ''
            }
          ],
          help: {
            url: 'https://newstock.compujuy.com.ar/soporte/gestion-productos?view=article&id=15&catid=15'
          }
        }
      },
      {
        path: 'update-producto-compuesto/:idProducto',
        component: UpdateProdCompPage,
        data: {
          breadcrumb: [
            {
              label: 'Lista de productos',
              url: '/productos/lista'
            },
            {
              label: 'Editar producto - {{customText}}',// pageTwoID Parameter value will be add 
              url: ''
            }
          ],
          help: {
            url: 'https://newstock.compujuy.com.ar/soporte/gestion-productos?view=article&id=15&catid=15'
          }
        }
      },
      {
        path: 'home',
        redirectTo: 'list',
        pathMatch: 'full'
      },
      {
        path: '',
        redirectTo: 'list',
        pathMatch: 'full'

      },
      {
        path: ':idProducto/history',
        component: HistoryProdComponent,
        data: {
          breadcrumb: [
            {
              label: 'Lista de productos',
              url: '/productos/lista'
            },
            {
              label: 'Historial de producto',// pageTwoID Parameter value will be add 
              url: ''
            }
          ]
        }
      }
    ]
  },
  {
    path: 'precios',
    component: ProductoComponent,
    children: [
      {
        path: 'update',
        component: UpdatePriceComponent,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Lista de productos',
              url: '/productos/lista'
            },
            {
              label: 'Actualización de precios',// pageTwoID Parameter value will be add 
              url: ''
            }
          ]
        }
      },
      {
        path: 'update/:idProducto',
        component: UpdatePriceComponent,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Lista de productos',
              url: '/productos/lista'
            },
            {
              label: 'Actualización de precios',// pageTwoID Parameter value will be add 
              url: ''
            }
          ]
        }
      },
      {
        path: 'lista-precios',
        component: ListListPricesComponent,
        pathMatch: 'Lista de precios',
        data: {
          breadcrumb: [
            {
              label: 'Lista de precios',
              url: ''
            }
          ]
        }
      },
      {
        path: 'lista-precios/new',
        component: NewListPricesComponent,
        pathMatch: 'Nueva lista de precios',
        data: {
          breadcrumb: [
            {
              label: 'Lista de precios',
              url: '/productos/precios/lista-precios'
            },
            {
              label: 'Nueva lista de precios',
              url: ''
            }
          ]
        }
      },
      {
        path: 'lista-precios/:idLista',
        component: UpdateListPricesComponent,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Lista de precios',
              url: '/productos/precios/lista-precios'
            },
            {
              label: 'Actualización de lista de precios',
              url: ''
            }
          ]
        }
      }
    ]
  },
  {
    path: 'stock',
    component: ProductoComponent,
    children: [
      {
        path: 'add',
        component: AddStockComponent,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Ingreso de stock',
              url: ''
            },
          ]
        }
      },
      {
        path: 'add/:idProducto',
        component: AddStockComponent,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Ingreso de stock',
              url: ''
            },
          ]
        }
      },
      {
        path: 'salida',
        component: OutStockComponent,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Egreso de stock',
              url: ''
            },
          ]
        }
      },
      {
        path: 'pases',
        component: ListPasesStockComponent,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Pases de stock entre sucursales',
              url: ''
            },
          ]
        }
      },
      {
        path: 'pases/new',
        component: StockNewPaseComponent,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Lista de pases',
              url: '/productos/stock/pases'
            },
            {
              label: 'Nuevo pase',
              url: ''
            }
          ]
        }
      },

    ]
  },
  {
    path: 'varios',
    component: ProductoComponent,
    children: [
      {
        path: 'vencimientos',
        component: ListExpirationComponent,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Lista de vencimientos',
              url: ''
            }
          ]
        }
      },
      {
        path: 'vencimientos/nuevo',
        component: NewExpirationComponent,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Lista de vencimientos',
              url: '/productos/varios/vencimientos'
            },
            {
              label: 'Nuevo vencimiento',
              url: ''
            }
          ]
        }
      },
      {
        path: 'codigos-barra/generar',
        component: GenerateBarcodePage,
        pathMatch: 'full',
        data: {
          breadcrumb: [
            {
              label: 'Generación de codigos de barra',
              url: ''
            }
          ]
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductoRoutingModule { }
