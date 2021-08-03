import {Component, OnDestroy, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import * as echarts from 'echarts';
import {EChartOption, ECharts} from 'echarts';
import {Observable} from 'rxjs';

@Component({
  selector: 'ngx-analysis-area-map',
  template: `
    <div echarts (chartInit)="chartInit($event)" [options]="options"></div>
  `,
})
export class AreaMapComponent implements OnDestroy, OnInit {

  constructor(private http: HttpClient) {
  }

  options: any = {};
  private chartObservable: Observable<Object>;
  private chart: ECharts;

  ngOnDestroy(): void {

  }

  /**
   * 获得echart实例
   * @param chart
   */
  chartInit(chart) {
    this.chart = chart;
    this.chart.showLoading();
  }

  ngOnInit() {
    this.chartObservable = this.http.get('assets/map/china.json');
    this.options = {
      tooltip: {
        trigger: 'item',
        formatter: '{b}<br/>{c} (UV)',
      },
      toolbox: {
        show: true,
        orient: 'vertical',
        left: 'right',
        top: 'center',
        feature: {
          dataView: {readOnly: false},
          restore : {show: true},
          saveAsImage : {show: true},
        },
      },
      visualMap: {
        min: 1,
        max: 300,
        text: ['高', '低'],
        realtime: false,
        calculable: true,
        inRange: {
          color: ['lightskyblue', 'yellow', 'orangered'],
        },
      },
      series: [
        {
          type: 'map',
          mapType: 'china',
          roam: true,
          itemStyle: {
            normal: {label: {show: true}},
            emphasis: {label: {show: true}},
          },
          data: [],
        },
      ],
    };
  }


  /**
   * 设置数据
   * @param data
   */
  setData(data: any) {
    this.chartObservable.subscribe(geoJson => {
      echarts.registerMap('china', geoJson);
      this.renderChart(data);
    });
  }

  /**
   * 渲染图形
   * @param data
   */
  renderChart(data: any) {
    const map = new Map();
    if (data) {
      for (const item of data) {
        const json = map.get(item.value);
        let areaValue = 0;
        if (json) {
          areaValue = json.value + parseInt(item.uv, 0);
        } else {
          areaValue = item.uv;
        }
        map.set(item.value, areaValue);
      }
    }
    this.options.series[0].data = [];
    map.forEach((value, key, m) => {
      this.options.series[0].data.push({name: key, value: value});
    });
    this.chart.setOption(this.options);
    this.chart.hideLoading();
  }
}

