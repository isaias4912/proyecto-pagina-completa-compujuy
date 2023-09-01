import { Component, OnInit, AfterViewInit } from '@angular/core';
import { pluck } from 'rxjs/operators';
import { Chart } from 'chart.js';
import { UtilHTTPService } from 'src/app/core/services/http/util-http.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, AfterViewInit {

  public dataDashboard: any = {};

  constructor(private utilHTTPService: UtilHTTPService) {

  }

  ngOnInit() {
  }
  ngAfterViewInit(): void {
    this.utilHTTPService.getDashBoard().pipe(pluck("data")).subscribe(resp => {
      this.dataDashboard = resp;
      this.updateChart();
    });
  }
  private updateChart() {
    let meses = [];
    let values = [];
    this.dataDashboard.cantidadPorMes.forEach(function (item) {
      meses.push(item.month);
      values.push(item.value);
    });
    new Chart(document.getElementById("canvas1"), {
      type: 'bar',
      data: {
        labels: meses,
        datasets: [
          {
            label: "Cant. del mes",
            backgroundColor: ["#3e95cd", "#81DAF5", "#3cba9f", "#e8c3b9", "#F7D358", "#FE2EF7"],
            data: values
          }
        ]
      },
      options: {
        legend: { display: false },
        scales: {
          xAxes: [{
            display: true,
            scaleLabel: {
              display: true,
              labelString: 'Mes'
            }
          }],
          yAxes: [{
            display: true,
            scaleLabel: {
              display: true,
              labelString: 'Cantidad'
            }
          }]
        }
      }
    });

    meses = [];
    values = [];
    this.dataDashboard.pesosPorMes.forEach(function (item) {
      meses.push(item.month);
      values.push(item.value);
    });
    new Chart(document.getElementById("canvas2"), {
      type: 'line',
      data: {
        labels: meses,
        datasets: [
          {
            label: "Total del mes ($)",
            backgroundColor: 'rgb(255, 99, 132)',
            borderColor: 'rgb(255, 99, 132)',
            fill: false,
            data: values
          }
        ]
      },
      options: {
        legend: { display: false },
        scales: {
          xAxes: [{
            display: true,
            scaleLabel: {
              display: true,
              labelString: 'Mes'
            }
          }],
          yAxes: [{
            display: true,
            scaleLabel: {
              display: true,
              labelString: 'Pesos'
            }
          }]
        }
      }
    });

    let dias = [];
      values = [];
      this.dataDashboard.cantidadPorDia.forEach(function (item) {
      	dias.push(item.day);
				values.push(item.value);
      });
			new Chart(document.getElementById("canvas3"), {
				type: 'bar',
				data: {
					labels: dias,
					datasets: [
						{
							label: "Cant. del dia",
							backgroundColor: ["#F7FE2E", "#BEF781", "#D8D8D8", "#FE642E", "#F7D358", "#58FAAC", "#FE2EF7"],
							data: values
						}
					]
				},
				options: {
					legend: {display: false},
					scales: {
						xAxes: [{
								display: true,
								scaleLabel: {
									display: true,
									labelString: 'Día'
								}
							}],
						yAxes: [{
								display: true,
								scaleLabel: {
									display: true,
									labelString: 'Cantidad'
								}
							}]
					}
				}
      });
      
      dias = [];
      values = [];
      this.dataDashboard.pesosPorDia.forEach(function (item) {
      	dias.push(item.day);
				values.push(item.value);
      });
			new Chart(document.getElementById("canvas4"), {
				type: 'line',
				data: {
					labels: dias,
					datasets: [
						{
							label: "Total del dia ($)",
							backgroundColor: 'rgb(54, 162, 235)',
							borderColor: 'rgb(54, 162, 235)',
							fill: false,
							data: values
						}
					]
				},
				options: {
					legend: {display: false},
					scales: {
						xAxes: [{
								display: true,
								scaleLabel: {
									display: true,
									labelString: 'Día'
								}
							}],
						yAxes: [{
								display: true,
								scaleLabel: {
									display: true,
									labelString: 'Pesos'
								}
							}]
					}
				}
			});
  }
}
