import { Component, OnInit, Input, AfterViewInit, ChangeDetectionStrategy, Output, EventEmitter } from '@angular/core';
import { Network } from 'vis';
declare var $: any;
declare var vis: any;

@Component({
  selector: 'three-fam',
  templateUrl: './three-fam.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class ThreeFamComponent implements OnInit, AfterViewInit {

  _familias: any;
  _familia: any;
  nodesParents: any;
  edgesParents: any;
  nodesChilds: any;
  edgesChilds: any;
  network: any;
  @Output()
  doubleClickEvent = new EventEmitter<any>();
  constructor(
  ) {
  }
  @Input()
  set familias(data: any) {
    this._familias = data;
  }
  get familias(): any { return this._familias; }
  @Input()
  set familia(data: any) {
    this._familia = data;
  }
  get familia(): any { return this._familia; }
  ngOnInit() {
  }

  ngAfterViewInit(): void {
    this.getDataFamily();
  }

  getDataNodes(nodes, familias: []) {
    if (familias) {
      familias.forEach((familia: any) => {
        if (familia.nodes) {
          nodes = this.getDataNodes(nodes, familia.nodes);
        }
        // let node = { id: familia.id, label: familia.text, nivel: familia.nivel, title: familia.text + ' - ' + familia.id, color: '#51FAE8' };
        let node = { id: familia.id, label: familia.text, nivel: familia.nivel, title: familia.text + ' - ' + familia.id };
        nodes.push(node);
      });
    }
    return nodes;
  }
  getDataEdges(edges, familia, text) {
    if (familia.nodes) {
      familia.nodes.forEach((item) => {
        let edge = { from: familia.id, to: item.id, arrows: 'to', color: { inherit: 'to' } };
        edges.push(edge);
        if (item.nodes) {
          if (item.nodes.length > 0) {
            edges = this.getDataEdges(edges, item, text);
          }
        }
      });
    }
    return edges;
  }

  getDataFamily() {
    let nodesParents = [];
    let edgesParents = [];
    let optionsNetwork = {
      interaction: {
        navigationButtons: true,
        keyboard: true
      }
    };
    this.familias.forEach(familia => {
      if (familia.nodes) {
        this.getDataEdges(edgesParents, familia, '');
      }
    });
    this.createChartFamily(this.getDataNodes(nodesParents, this.familias),
      this.getDataEdges(edgesParents, this.familias, ''), "divChartFamily", optionsNetwork);
  };

  createChartFamily(nodes, edges, cont, opt) {
    let container = document.getElementById(cont);
    let data = {
      nodes: nodes,
      edges: edges
    };
    let options = {
      physics: {
        "enabled": true
      },
      layout: {
        hierarchical: {
          direction: "UD"
        }
      }
    };
    var dataNodes = new vis.DataSet(nodes);
    this.network = new Network(container, data, options);
    this.network.on("doubleClick", params => {
      let ids = params.nodes;
      let clickedNodes = dataNodes.get(ids)[0];
      if (clickedNodes){
        this.doubleClickEvent.emit(clickedNodes);
      }
    });
    if (this.familia){
      this.network.selectNodes([this.familia.id]);
      setTimeout(() => {
        this.network.fit({
          nodes: [this.familia.id]
        });
      });
    }

  }
}