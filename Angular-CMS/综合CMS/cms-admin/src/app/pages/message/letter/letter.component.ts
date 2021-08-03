import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../../core/service/base-component';
import {LetterService} from '../service/letter-service';
import {DateUtil} from '../../../core/utils/date';

@Component({
  selector: 'ngx-message-letter',
  templateUrl: './letter.component.html',
  styleUrls: ['./letter.component.scss'],
})
export class LetterComponent extends BaseComponent implements OnInit {

  constructor(private letterService: LetterService, protected injector: Injector) {
    super(letterService, injector);
  }

  boxes: any = [{id: 1, name: '收件箱'}, {id: 2, name: '发件箱'}, {id: 3, name: '草稿'}, {id: 4, name: '垃圾箱'}];
  box: string = '';
  isRead: string = '';
  beginSendTime: string;
  endSendTime: string;
  dateStr: string = '';

  ngOnInit() {
    this.search();
  }

  /**
   * 搜索
   */
  search() {
    this.setQueryParams('box', this.box);
    this.setQueryParams('read', this.isRead);
    this.setQueryParams('beginSendTime', this.beginSendTime);
    this.setQueryParams('endSendTime', this.endSendTime);
    this.getPager(1);
  }

  /**
   * 日期改变
   * @param event
   */
  changeDate(event) {
    if (event.start && event.end) {
      this.beginSendTime = DateUtil.formatDate(event.start);
      this.endSendTime = DateUtil.formatDate(event.end);
      this.search();
    }
  }

  /**
   * 重置表单
   */
  resetForm() {
    this.beginSendTime = '';
    this.endSendTime = '';
    this.box = '';
    this.isRead = '';
    this.dateStr = '';
    this.search();
  }
}
