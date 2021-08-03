import {AfterViewInit, Component, OnDestroy, OnInit} from '@angular/core';
import {NbThemeService} from '@nebular/theme';
import {EChartOption, ECharts} from 'echarts';
import {Subject} from 'rxjs';

@Component({
  selector: 'ngx-analysis-site-pie',
  template: `
    <div style="height:300px" echarts (chartInit)="chartInit($event)" [options]="options" class="echart"></div>
  `,
})
export class SitePieComponent implements OnInit, AfterViewInit, OnDestroy {

  private map: Map<string, any>;
  options: any = {};
  themeSubscription: any;
  private chart: ECharts;
  private chartSubject: Subject<any> = new Subject<any>();

  constructor(private theme: NbThemeService) {
  }

  /**
   * 获得echart实例
   * @param chart
   */
  chartInit(chart) {
    this.chart = chart;
    this.chartSubject.asObservable().subscribe(data => {
      this.renderChart(data);
    });
  }

  ngAfterViewInit() {
    this.themeSubscription = this.theme.getJsTheme().subscribe(config => {

      const colors = config.variables;
      const echarts: any = config.variables.echarts;

      this.options = {
        backgroundColor: echarts.bg,
        color: [colors.warningLight, colors.infoLight, colors.dangerLight, colors.successLight, colors.primaryLight],
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)',
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          data: [],
          textStyle: {
            color: echarts.textColor,
          },
        },
        series: [
          {
            name: '访问来源(PV)',
            type: 'pie',
            radius: '80%',
            center: ['50%', '50%'],
            data: [],
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: echarts.itemHoverShadowColor,
              },
            },
            label: {
              normal: {
                textStyle: {
                  color: echarts.textColor,
                },
              },
            },
            labelLine: {
              normal: {
                lineStyle: {
                  color: echarts.axisLineColor,
                },
              },
            },
          },
        ],
      };
    });
  }

  ngOnDestroy(): void {
    this.chartSubject.unsubscribe();
    this.themeSubscription.unsubscribe();
  }

  ngOnInit(): void {
  }

  /**
   * 设置数据
   * @param data
   */
  setData(data: any) {
    this.chartSubject.next(data);
  }

  /**
   * 渲染图形
   * @param data
   */
  renderChart(data: any) {
    this.map = new Map();
    if (data) {
      for (const item of data) {
        const value = this.map.get(item.value);
        const json = {ip: 0, pv: 0, uv: 0, value: ''};
        if (value) {
          json.ip = value.ip + parseInt(item.ip, 0);
          json.pv = value.pv + parseInt(item.pv, 0);
          json.uv = value.uv + parseInt(item.uv, 0);
          json.value = value.value;
        } else {
          //  拷贝对象的值
          Object.assign(json, item);
        }
        this.map.set(item.value, json);
      }
    }
    this.options.legend.data = [];
    this.options.series[0].data = [];
    this.map.forEach((value, key, map) => {
      this.options.legend.data.push(key);
      this.options.series[0].data.push({value: value.pv, name: key});
    });
    this.chart.setOption(this.options);
  }
}
