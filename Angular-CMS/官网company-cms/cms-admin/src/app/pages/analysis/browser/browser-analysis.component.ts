import {Component, OnInit, ViewChild} from '@angular/core';
import {BrowserPieComponent} from './browser-pie.component';
import {BrowserLineComponent} from './browser-line.component';
import {BrowserTableComponent} from './browser-table.component';
import {AnalysisService} from '../service/analysis-service';
import {DateUtil} from '../../../core/utils/date';

@Component({
  selector: 'ngx-analysis-browser',
  templateUrl: './browser-analysis.component.html',
})
export class BrowserAnalysisComponent implements OnInit {

 times: any = [{id: 1, name: '今天', checked: true}, {id: 2, name: '昨天'},
    {id: 3, name: '本周'}, {id: 4, name: '本月'}, {id: 5, name: '本年'}]; // 时间类型

  @ViewChild('pieChart') pieChart: BrowserPieComponent;
  @ViewChild('lineChart') lineChart: BrowserLineComponent;
  @ViewChild('tableChart') tableChart: BrowserTableComponent;


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
    this.analysisService.statisticBrowser(time, begin, end).then(result => {
      this.tableChart.setData(result);
      this.pieChart.setData(result);
      this.lineChart.setData(result);
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
