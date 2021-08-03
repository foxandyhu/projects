import {AfterViewInit, Component, OnDestroy} from '@angular/core';
import {NbThemeService} from '@nebular/theme';
import {EChartOption, ECharts} from 'echarts';
import {AnalysisService} from '../analysis/service/analysis-service';

@Component({
  selector: 'ngx-visitors-flow',
  template: `
    <nb-card>
      <nb-card-header>本周流量统计</nb-card-header>
      <nb-card-body>
        <div echarts [options]="options" (chartInit)="chartInit($event)" class="echart"></div>
      </nb-card-body>
    </nb-card>
  `,
})
export class VisitorsFlowComponent implements AfterViewInit, OnDestroy {
  options: any = {};
  themeSubscription: any;
  private echartInstance: ECharts;

  constructor(private theme: NbThemeService, private flowService: AnalysisService) {
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
            type: 'cross', label: {
              backgroundColor: echarts.tooltipBackgroundColor,
            },
          },
        },
        legend: {
          data: ['PV', 'IP', '独立访客'],
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
        series: [
          {
            name: 'PV',
            type: 'line',
            stack: 'Total amount',
            areaStyle: {normal: {opacity: echarts.areaOpacity}},
            data: [],
          },
          {
            name: 'IP',
            type: 'line',
            stack: 'Total amount',
            areaStyle: {normal: {opacity: echarts.areaOpacity}},
            data: [],
          },
          {
            name: '独立访客',
            type: 'line',
            stack: 'Total amount',
            areaStyle: {normal: {opacity: echarts.areaOpacity}},
            data: [],
          },
        ],
      };
    });
  }

  ngOnDestroy(): void {
    this.themeSubscription.unsubscribe();
  }

  /**
   * 获得echart实例
   * @param chart
   */
  chartInit(chart) {
    this.echartInstance = chart;
    this.initFlow();
  }

  /**
   *初始化流量数据
   * @param time
   * @param begin
   * @param end
   */
  initFlow() {
    this.flowService.statisticFlow('3', null, null).then(result => {
      this.options.xAxis[0].data = [];
      this.options.series[0].data = [];
      this.options.series[1].data = [];
      this.options.series[2].data = [];
      if (!result) {
        return;
      }
      for (const item of result) {
        this.options.xAxis[0].data.push(item.time);
        this.options.series[0].data.push(item.pv);
        this.options.series[1].data.push(item.ip);
        this.options.series[2].data.push(item.uv);
      }
      if (this.echartInstance) {
        this.echartInstance.setOption(this.options);
      }
    });
  }
}
