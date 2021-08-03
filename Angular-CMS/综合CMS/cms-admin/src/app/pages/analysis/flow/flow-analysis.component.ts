import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {NbThemeService} from '@nebular/theme';
import {AnalysisService} from '../service/analysis-service';
import {EChartOption, ECharts} from 'echarts';
import {DateUtil} from '../../../core/utils/date';

@Component({
  selector: 'ngx-analysis-flow-analysis',
  templateUrl: './flow-analysis.component.html',
})
export class FlowAnalysisComponent implements OnInit, AfterViewInit, OnDestroy {

  options: any = {};
  themeSubscription: any;
  @ViewChild('flowTable') flowTable;
  private echartInstance: ECharts;
  times: any = [{id: 1, name: '今天', checked: true}, {id: 2, name: '昨天'},
    {id: 3, name: '本周'}, {id: 4, name: '本月'}, {id: 5, name: '本年'}]; // 时间类型

  constructor(private theme: NbThemeService, private flowService: AnalysisService) {
  }

  ngOnInit() {
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
    this.chooseTime(1);
  }

  /**
   * 时间类型选择
   * @param type
   */
  chooseTime(time, begin?: string, end?: string) {
    this.flowService.statisticFlow(time, begin, end).then(result => {
      this.options.xAxis[0].data = [];
      this.options.series[0].data = [];
      this.options.series[1].data = [];
      this.options.series[2].data = [];
      if (!result) {
        return;
      }
      this.flowTable.setData(result);
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

  /**
   * 日期改变
   * @param event
   */
  changeDate(event) {
    if (event.start && event.end) {
      const begin = DateUtil.formatDate(event.start);
      const end = DateUtil.formatDate(event.end);
      this.chooseTime(0, begin, end);
    }
  }
}
