import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../../core/service/base-component';
import {CommentService} from '../service/comment-service';

@Component({
  selector: 'ngx-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.scss'],
})
export class CommentComponent extends BaseComponent implements OnInit {

  constructor(private commentService: CommentService, protected injector: Injector) {
    super(commentService, injector);
  }

  status: string = '';
  isRecommend: string = '';
  articleId: string = '';
  statuss: any = [{id: 0, name: '待审核'}, {id: 1, name: '审核不通过'}, {id: 2, name: '审核通过'}];

  ngOnInit() {
    this.search();
  }

  /**
   * 搜索
   */
  search() {
    this.setQueryParams('status', this.status);
    this.setQueryParams('recommend', this.isRecommend);
    this.setQueryParams('articleId', this.articleId);
    this.getPager(1);
  }

  /**
   * 查看该文章所有评论
   */
  viewArticleAll(articleId: string) {
    this.articleId = articleId;
    this.search();
  }

  /**
   * 推荐或取消评论
   * @param commentId
   * @param recommend
   */
  recommend(commentId: string, isRecommend: boolean) {
    const content = isRecommend ? '您确定要推荐该评论吗?' : '您确定要取消推荐该评论吗?';
    this.modalUtil.confirm('提示', content).then(r => {
      if (r) {
        this.commentService.recommend(commentId, isRecommend).then(() => {
          this.search();
        });
      }
    });
  }

  /**
   * 审核评论
   * @param status
   */
  verify(status: boolean, commentId) {
    const content = status ? '您确定要审核通过该评论吗?' : '您确定要审核不通过该评论吗?';
    this.modalUtil.confirm('提示', content).then(r => {
      if (r) {
        this.commentService.verify(status, [commentId]).then(() => {
          this.search();
        });
      }
    });
  }

  /**
   * 批量审核评论
   * @param status
   */
  verifyMulti(status: boolean) {
    const content = status ? '您确定要审核通过该评论吗?' : '您确定要审核不通过该评论吗?';
    this.modalUtil.confirm('提示', content).then(r => {
      if (r) {
        this.commentService.verify(status, this.selectItems).then(() => {
          this.search();
        });
      }
    });
  }

  /**
   * 评论回复
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
    const replyData = {parentId: item.id, articleId: item.articleId, commentTxt: {text: item.replyText}};
    this.commentService.saveData(replyData).then(() => {
      this.toastUtil.showSuccess('回复成功!');
      this.hideReply(item);
      this.search();
    });
  }
}
