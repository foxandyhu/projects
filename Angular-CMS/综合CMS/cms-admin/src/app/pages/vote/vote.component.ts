import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../core/service/base-component';
import {VoteService} from './service/vote-service';

@Component({
  selector: 'ngx-vote',
  templateUrl: './vote.component.html',
})
export class VoteComponent extends BaseComponent implements OnInit {

  constructor(private voteService: VoteService, protected injector: Injector) {
    super(voteService, injector);
  }

  status = '';
  statuss = [{id: 1, name: '未开始'}, {id: 2, name: '进行中'}, {id: 3, name: '已结束'}];

  ngOnInit() {
    this.changeStatus();
  }

  changeStatus() {
    this.setQueryParams('status', this.status);
    this.getPager(1);
  }

  /**
   * 修改启用状态
   */
  editEnabled(voteId, enabled) {
    const str = enabled ? '开启' : '暂停';
    this.modalUtil.confirm('提示', '您确定要' + str + '该调查问卷吗?').then(result => {
      if (result) {
        this.voteService.editVoteEnabled(voteId, enabled).then(() => {
          this.toastUtil.showSuccess('修改成功!');
          this.changeStatus();
        });
      }
    });
  }
}
