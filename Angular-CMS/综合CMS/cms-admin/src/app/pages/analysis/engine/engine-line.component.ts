import {AfterViewInit, Component, OnDestroy, OnInit} from '@angular/core';
import {NbThemeService} from '@nebular/theme';
import {EChartOption, ECharts} from 'echarts';
import {Subject} from 'rxjs';

@Component({
  selector: 'ngx-analysis-engine-line',
  template: `
    <div style="height:300px" echarts (chartInit)="chartInit($event)" [options]="options" class="echart"></div>
  `,
})
export class EngineLineComponent implements OnInit, AfterViewInit, OnDestroy {

  options: any = {};
  legendData: Array<string>;
  xAxisData: Array<string>;
  private seriesData: Map<string, any>;
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

      const colors: any = config.variables;
      const echarts: any = config.variables.echarts;

      this.options = {
        backgroundColor: echarts.bg,
        color: [colors.warningLight, colors.infoLight, colors.dangerLight, colors.successLight, colors.primaryLight],
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: echarts.tooltipBackgroundColor,
            },
          },
        },
        legend: {
          data: [],
          textStyle: {
            color: echarts.textColor,
          },
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true,
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            data: [],
            axisTick: {
              alignWithLabel: true,
            },
            axisLine: {
              lineStyle: {
                color: echarts.axisLineColor,
              },
            },
            axisLabel: {
              textStyle: {
                color: echarts.textColor,
              },
            },
          },
        ],
        yAxis: [
          {
            name: 'PV',
            type: 'value',
            axisLine: {
              lineStyle: {
                color: echarts.axisLineColor,
              },
            },
            splitLine: {
              lineStyle: {
                color: echarts.splitLineColor,
              },
            },
            axisLabel: {
              textStyle: {
                color: echarts.textColor,
              },
            },
          },
        ],
        series: [],
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
    this.legendData = new Array();
    this.xAxisData = new Array();
    this.seriesData = new Map();
    if (data) {
      for (const item of data) {
        if (this.legendData.indexOf(item.value) < 0) {
          this.legendData.push(item.value);
        }
        if (this.xAxisData.indexOf(item.time) < 0) {
          this.xAxisData.push(item.time);
        }
      }
    }
    this.legendData.forEach((value, index, array) => {
      const items = new Array();
      this.xAxisData.forEach((xData, seq, datas) => {
        let result = 0;
        for (const item of data) {
          if (value === item.value && xData === item.time) {
            result = parseInt(item.pv, 0);
            break;
          }
        }
        items.push(result);
      });
      this.seriesData.set(value, items);
    });

    this.options.legend.data = [];
    this.options.xAxis[0].data = [];
    this.options.series = [];

    this.options.legend.data = this.legendData;
    this.options.xAxis[0].data = this.xAxisData;
    this.seriesData.forEach((value, key, map) => {
      this.options.series.push({
        name: key,
        type: 'line',
        stack: 'Total amount',
        areaStyle: {normal: {opacity: echarts.areaOpacity}},
        data: value,
      });
    });
    this.chart.setOption(this.options);
  }
}
