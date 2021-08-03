import {Component, OnInit, ViewChild} from '@angular/core';
import {AreaMapComponent} from './area-map.component';
import {AreaTableComponent} from './area-table.component';
import {AnalysisService} from '../service/analysis-service';
import {DateUtil} from '../../../core/utils/date';

@Component({
  selector: 'ngx-analysis-area',
  templateUrl: './area-analysis.component.html',
})
export class AreaAnalysisComponent implements OnInit {

  times: any = [{id: 1, name: '今天', checked: true}, {id: 2, name: '昨天'},
    {id: 3, name: '本周'}, {id: 4, name: '本月'}, {id: 5, name: '本年'}]; // 时间类型
  @ViewChild('mapChart') mapChart: AreaMapComponent;
  @ViewChild('tableChart') tableChart: AreaTableComponent;

  constructor(private analysisService: AnalysisService) {
  }

  ngOnInit() {
    this.chooseTime(1);
  }

  /**
   * 时间类型选择
   * @param type
   */
  chooseTime(time, begin?: string, end?: string) {
    this.analysisService.statisticArea(time, begin, end).then(result => {
      this.mapChart.setData(result);
      this.tableChart.setData(result);
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
