import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../core/service/base-component';
import {VoteService} from './service/vote-service';
import {ActivatedRoute} from '@angular/router';
import {DateUtil} from '../../core/utils/date';

@Component({
  selector: 'ngx-vote-detail',
  templateUrl: './vote-detail.component.html',
  styleUrls: ['./vote-detail.component.scss'],
})
export class VoteDetailComponent extends BaseComponent implements OnInit {

  constructor(private voteService: VoteService, protected injector: Injector, private route: ActivatedRoute) {
    super(voteService, injector);
  }

  vote: any; //  调查问卷对象

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const voteId = params.get('voteId');
      this.voteService.getData(voteId).then(result => {
        this.vote = result;
        if (this.vote && this.vote.startTime) {
          this.vote.startTime = DateUtil.formatDate(this.vote.startTime);
        }
        if (this.vote && this.vote.endTime) {
          this.vote.endTime = DateUtil.formatDate(this.vote.endTime);
        }
        this.voteCountPercent();
      });
    });
  }

  /**
   * 投票项百分比计算
   */
  voteCountPercent() {
    const voteSubTopics = this.vote.subtopics;
    if (!voteSubTopics) {
      return;
    }
    voteSubTopics.forEach(voteSub => {
      const voteItems = voteSub.voteItems;
      if (voteItems) {
        let total = 0;
        //  计算该子主题的总投票数
        voteItems.forEach(item => {
          total = total + item.voteCount;
        });
        voteItems.forEach(item => {
          item.percent = (item.voteCount / total * 100).toFixed(0);
        });
      }
    });
  }

  status(percent) {
    if (percent < 10) {
      return 'primary';
    } else if (percent <= 25) {
      return 'info';
    } else if (percent <= 50) {
      return 'warning';
    } else if (percent <= 75) {
      return 'success';
    } else {
      return 'danger';
    }
  }
}
