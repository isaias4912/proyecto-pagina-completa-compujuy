import { Component, OnInit, Input, AfterViewInit, ChangeDetectionStrategy, Output, EventEmitter } from '@angular/core';
import { Network, DataSet, Node, Edge, IdType } from 'vis';

declare var $: any;
declare var vis: any;
@Component({
  selector: 'parent-child',
  templateUrl: './parent-child.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class ParentChildComponent implements OnInit, AfterViewInit {

  _data: any;
  nodesParents: any;
  edgesParents: any;
  nodesChilds: any;
  edgesChilds: any;
  network: any;
  @Input()
  collapse = true;
  @Input()
  tooltipPadre = 'Si el producto tiene padres, osea es parte de un producto compuesto, apareceran aqui en un gráfico.';
  @Input()
  tooltipHijo = 'Si el producto tiene hijos, osea es un producto compuesto, apareceran aqui en un gráfico';
  @Output()
  doubleClickEvent = new EventEmitter<any>();
  constructor(
  ) {
  }
  @Input()
  set data(data: any) {
    this._data = data;
    this.reviewNetwork();
  }
  get data(): any { return this._data; }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
    this.reviewNetwork();

  }

  reviewNetwork() {
    if (this._data) {
      if (this.hasParentsOrChilds(this._data.parents)) {
        let optionsPadre = { options: {}, textEdge: "Forma parte de ", click: 2 };
        this.createChartParents(this._data.parents, 'divPadres', optionsPadre);
      } else {
        $("#divPadres").html("Sin padres");
      }
      if (this.hasParentsOrChilds(this._data.childs)) {
        var optionsHijo = { options: {}, textEdge: "Contiene", click: 2 };
        this.createChartChilds(this._data.childs, 'divHijos', optionsHijo);
      } else {
        $("#divHijos").html("Sin hijos");
      }
    } else {
      $("#divPadres").html("Sin padres");
      $("#divHijos").html("Sin hijos");
    }
  }

  hasParentsOrChilds(producto) {
    if (producto && producto.productos) {
      if (producto.productos.length > 0) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  };
  getDataNodes(nodes, producto) {
    if (producto.productos) {
      if (producto.productos.length > 0) {
        producto.productos.forEach((item) => {
          nodes = this.getDataNodes(nodes, item);
        });
        let node = { id: producto.id, label: producto.nombreFinal };
        nodes.push(node);
      } else {
        let node = { id: producto.id, label: producto.nombreFinal };
        nodes.push(node);
      }
    } else {
      let node = { id: producto.id, label: producto.nombreFinal };
      nodes.push(node);
    }
    return nodes;
  }
  getDataEdges(edges, productoPadre, text) {
    if (productoPadre.productos) {
      if (productoPadre.productos.length > 0) {
        productoPadre.productos.forEach((item) => {
          let edge = { from: productoPadre.id, to: item.id, arrows: 'to', color: { inherit: 'to' }, label: text + '(' + item.cantidad + ')' };
          edges.push(edge);
          edges = this.getDataEdges(edges, item, text);
        });
      }
    }
    return edges;
  }
  createChartParents(producto, object, options) {
    this.nodesParents = [];
    this.edgesParents = [];
    let optionsNetwork: any = null;

    this.nodesParents = this.getDataNodes(this.nodesParents, producto);
    this.edgesParents = this.getDataEdges(this.edgesParents, producto, options.textEdge);
    this.nodesParents[this.nodesParents.length - 1].shape = 'box';
    this.nodesParents[this.nodesParents.length - 1].color = {
      border: '#FF0000',
      highlight: {
        border: '#FF0000',
        background: '#D2E5FF'
      }
    };
    this.nodesParents = new DataSet(this.nodesParents);
    // create a network
    var container = document.getElementById(object);
    var data = {
      nodes: this.nodesParents,
      edges: this.edgesParents
    };
    if (!options.options) {
      optionsNetwork = {
        interaction: {
          navigationButtons: true,
          keyboard: true
        }
      };
    } else {
      optionsNetwork = options.options;
    }
    // $timeout(function () {
    this.network = new Network(container, data, optionsNetwork);
    this.network.on("doubleClick", (params) => {
      if ((params.nodes.length != 0) && (params.edges.length != 0)) {
        var idNode = params.nodes[0];
        var clickedNode = this.nodesParents.get(idNode);
        if (options.click == 1) {
          // UtilService.openDialogo("Detalle del Producto", { type: 'producto', id: idNode }, this, "pages/productos/detailProducto.html", "DetailCtrl", "xlg");
        }
        if (options.click == 2) {
          this.doubleClickEvent.emit(clickedNode);
          // this.$emit('doubleClickParentChart', clickedNode);
        }
      }
    });
    // });
  }
  createChartChilds(producto, object, options) {
    this.nodesChilds = [];
    this.edgesChilds = [];
    let optionsNetwork: any = null;
    this.nodesChilds = this.getDataNodes(this.nodesChilds, producto);
    this.edgesChilds = this.getDataEdges(this.edgesChilds, producto, options.textEdge);
    this.nodesChilds[this.nodesChilds.length - 1].shape = 'box';
    this.nodesChilds[this.nodesChilds.length - 1].color = {
      border: '#FF0000',
      highlight: {
        border: '#FF0000',
        background: '#D2E5FF'
      }
    };
    this.nodesChilds = new DataSet(this.nodesChilds);
    // create a network
    var container = document.getElementById(object);
    var data = {
      nodes: this.nodesChilds,
      edges: this.edgesChilds
    };
    if (!options.options) {
      optionsNetwork = {
        interaction: {
          navigationButtons: true,
          keyboard: true
        }
      };
    } else {
      optionsNetwork = options.options;
    }
    this.network = new Network(container, data, optionsNetwork);
    this.network.on("doubleClick", (params) => {
      if ((params.nodes.length != 0) && (params.edges.length != 0)) {
        var idNode = params.nodes[0];
        var clickedNode = this.nodesChilds.get(idNode);
        if (options.click == 1) {
          // UtilService.openDialogo("Detalle del Producto", {type: 'producto', id: idNode},this, "pages/productos/detailProducto.html", "DetailCtrl", "xlg");
        }
        if (options.click == 2) {
          this.doubleClickEvent.emit(clickedNode);
          // this.$emit('doubleClickParentChart', clickedNode);
        }
      }
    });
    // });
  }
}
