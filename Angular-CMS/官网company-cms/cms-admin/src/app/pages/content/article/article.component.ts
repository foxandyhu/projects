import {Component, Injector, OnDestroy, OnInit} from '@angular/core';
import {BaseComponent} from '../../../core/service/base-component';
import {ArticleService} from '../service/article-service';
import {Router} from '@angular/router';
import {NbDialogRef, NbDialogService} from '@nebular/theme';
import {ArticleTopComponent} from './top.component';
import {DateUtil} from '../../../core/utils/date';
import {ArticleRelatedTopicComponent} from './related-topic.component';
import {ArticleTopicComponent} from './article-topic.component';
import {Constant} from '../../../core/constant';
import {ModelService} from '../service/model-service';

@Component({
  selector: 'ngx-normal',
  styleUrls: ['./article.component.scss'],
  templateUrl: './article.component.html',
})
export class ArticleComponent extends BaseComponent implements OnInit, OnDestroy {

  constructor(private articleService: ArticleService, protected injector: Injector, private router: Router,
              private dialogService: NbDialogService, private modelService: ModelService) {
    super(articleService, injector);
  }

  types: Array<any>;  //  内容类型
  statuss: Array<any> = Constant.ARTICLE_STATUS;      //  状态
  channel: any;                //  树状栏目选中
  searchType: string = '';        //  搜索类型
  searchStatus: string = '';      //  搜索状态
  searchChannel: string = '';     //  搜索栏目
  dialog: NbDialogRef<any>;    //  文章置顶框
  models: any;

  ngOnInit() {
    this.types = Constant.CONTENT_TYPES;
    this.search();
    this.loadModels();
  }

  ngOnDestroy(): void {
    if (this.dialog) {
      this.dialog.close();
    }
  }


  loadModels() {
    this.modelService.getAllModels().then(result => {
      this.models = result;
    });
  }

  /**
   * 得到选中的栏目
   * @param channel
   */
  getChannel(channel: string) {
    this.channel = channel;
    const channelId = this.channel ? this.channel['id'] : null;
    this.searchChannel = channelId === 0 ? null : channelId;
    this.search();
  }

  /**
   * 搜索
   */
  search() {
    this.setQueryParams('type', this.searchType);
    this.setQueryParams('status', this.searchStatus);
    this.setQueryParams('channelId', this.searchChannel);
    this.getPager(1);
  }

  /**
   * 推荐或取消评论
   * @param commentId
   * @param recommend
   */
  recommendMulti(isRecommend: boolean) {
    const content = isRecommend ? '您确定要推荐该文章吗?' : '您确定要取消推荐该文章吗?';
    this.modalUtil.confirm('提示', content).then(r => {
      if (r) {
        this.articleService.recommend(isRecommend, this.selectItems).then(() => {
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
    const content = status ? '您确定要审核通过该文章吗?' : '您确定要审核不通过该文章吗?';
    this.modalUtil.confirm('提示', content).then(r => {
      if (r) {
        this.articleService.verify(status, this.selectItems).then(() => {
          this.search();
        });
      }
    });
  }

  /**
   * 显示文章置顶框
   */
  showTop(articleId) {
    const top = {level: 0, expired: ''};    //  置顶信息
    this.list.forEach(item => {
      if (item.id === articleId) {
        top.level = item.topLevel;
        top.expired = DateUtil.formatDate(item.topExpired);
      }
    });
    this.dialog = this.dialogService.open(ArticleTopComponent);
    this.dialog.componentRef.instance.top = top;
    this.dialog.onClose.subscribe(result => {
      if (result) {
        this.articleService.top(articleId, result.level, result.expired).then(() => {
          this.search();
        });
      }
    });
  }

  /**
   * 显示关联专题
   */
  showRelatedTopic() {
    this.dialog = this.dialogService.open(ArticleRelatedTopicComponent);
    this.dialog.componentRef.instance.articleIds = this.selectItems;
  }

  /**
   * 显示文章关联的专题
   * @param articleId
   */
  showArticleRelatedTopic(articleId: string) {
    this.dialog = this.dialogService.open(ArticleTopicComponent);
    this.dialog.componentRef.instance.articleId = articleId;
    this.dialog.onClose.subscribe(result => {
      if (result) {
        this.list.forEach(item => {
          if (item.id === result) {
            item.selected = true;
            this.changeBox(true, result);
            this.showRelatedTopic();
          }
        });
      }
    });
  }

  /**
   * 重构索引库
   */
  resetAllIndex() {
    this.modalUtil.confirm('提示', '重构索引库将会耗费一分钟时间,是否继续?').then(r => {
      if (r) {
        this.articleService.resetIndex().then(() => {
          this.toastUtil.showSuccess('正在重构索引库请稍候...');
        });
      }
    });
  }
}
