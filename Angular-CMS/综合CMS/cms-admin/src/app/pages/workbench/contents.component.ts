import {Component,  OnInit} from '@angular/core';
import {AnalysisService} from '../analysis/service/analysis-service';

@Component({
  selector: 'ngx-contents-statistics',
  styleUrls: ['./contents.component.scss'],
  templateUrl: './contents.component.html',
})
export class ContentsComponent implements OnInit {

  constructor(private analysisService: AnalysisService) {
  }

  progressInfoData: Array<any> = [
    {title: '内容发布数', today: 0, total: 0, iconColor: 'primary', icon: 'nb-list'},
    {title: '评论数', today: 0, total: 0, iconColor: 'success', icon: 'nb-compose'},
    {title: '留言数', today: 0, total: 0, iconColor: 'info', icon: 'nb-email'},
    {title: '会员注册数', today: 0, total: 0, iconColor: 'warning', icon: 'nb-person'},
  ];

  ngOnInit(): void {
    this.initStatisticContent();
  }

  /**
   * 内容统计
   */
  initStatisticContent() {
    this.analysisService.getStatisticContent().then(result => {
      if (result) {
        let item = null;
        if (result['article']) {
          item = result['article'];
          this.progressInfoData[0]['today'] = item.today;
          this.progressInfoData[0]['total'] = item.total;
        }
        if (result['comment']) {
          item = result['comment'];
          this.progressInfoData[1]['today'] = item.today;
          this.progressInfoData[1]['total'] = item.total;
        }
        if (result['guestBook']) {
          item = result['guestBook'];
          this.progressInfoData[2]['today'] = item.today;
          this.progressInfoData[2]['total'] = item.total;
        }
        if (result['member']) {
          item = result['member'];
          this.progressInfoData[3]['today'] = item.today;
          this.progressInfoData[3]['total'] = item.total;
        }
      }
    });
  }
}
