import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../../core/service/base-component';
import {GuestBookService} from '../service/guestbook-service';
import {DictionaryService} from '../../words/service/dictionary-service';

@Component({
  selector: 'ngx-guest-book',
  templateUrl: './guest-book.component.html',
  styleUrls: ['./guest-book.component.scss'],
})
export class GuestBookComponent extends BaseComponent implements OnInit {

  constructor(private guestBookService: GuestBookService, private dictionaryService: DictionaryService,
              protected injector: Injector) {
    super(guestBookService, injector);
  }

  type: string = '';
  status: string = '';
  statuss: any = [{id: 0, name: '待审核'}, {id: 1, name: '审核不通过'}, {id: 2, name: '审核通过'}];
  isRecommend: string = '';
  types: any = [];
  typeDic: string = 'guestbook_type';

  ngOnInit() {
    this.search();
    this.loadTypes();
  }

  /**
   * 搜索
   */
  search() {
    this.setQueryParams('type', this.type);
    this.setQueryParams('status', this.status);
    this.setQueryParams('recommend', this.isRecommend);
    this.getPager(1);
  }

  /**
   * 加载留言类型
   */
  loadTypes() {
    this.dictionaryService.getDictionaryByType(this.typeDic).then(result => {
      this.types = result;
    });
  }

  /**
   * 推荐或取消留言
   * @param guestBookId
   * @param recommend
   */
  recommend(guestBookId: string, isRecommend: boolean) {
    const content = isRecommend ? '您确定要推荐该留言吗?' : '您确定要取消推荐该留言吗?';
    this.modalUtil.confirm('提示', content).then(r => {
      if (r) {
        this.guestBookService.recommend(guestBookId, isRecommend).then(() => {
          this.search();
        });
      }
    });
  }

  /**
   * 留言留言
   * @param status
   */
  verify(status: boolean, guestBookId) {
    const content = status ? '您确定要审核通过该留言吗?' : '您确定要审核不通过该留言吗?';
    this.modalUtil.confirm('提示', content).then(r => {
      if (r) {
        this.guestBookService.verify(status, [guestBookId]).then(() => {
          this.search();
        });
      }
    });
  }

  /**
   * 批量审核留言
   * @param status
   */
  verifyMulti(status: boolean) {
    const content = status ? '您确定要审核通过该留言吗?' : '您确定要审核不通过该留言吗?';
    this.modalUtil.confirm('提示', content).then(r => {
      if (r) {
        this.guestBookService.verify(status, this.selectItems).then(() => {
          this.search();
        });
      }
    });
  }

  /**
   * 留言回复
   */
  showReply(item) {
    item.show = true;
  }

  /**
   * 隐藏回复框
   * @param item
   */
  hideReply(item) {
    item.show = false;
  }

  /**
   * 回复
   * @param item
   */
  reply(item) {
    const replyData = {guestBookId: item.id, content: item.replyText};
    this.guestBookService.saveData(replyData).then(() => {
      this.toastUtil.showSuccess('回复成功!');
      this.hideReply(item);
      this.search();
    });
  }
}

