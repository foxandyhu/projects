import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../../core/service/base-component';
import {SpecialTopicService} from '../service/topic-service';
import {ArticleService} from '../service/article-service';
import {NbDialogRef} from '@nebular/theme';

@Component({
  selector: 'ngx-content-article-topic',
  templateUrl: './article-topic.component.html',
  styleUrls: ['./article-topic.component.scss'],
})
export class ArticleTopicComponent extends BaseComponent implements OnInit {

  constructor(private topicService: SpecialTopicService, private articleService: ArticleService,
              protected injector: Injector, private ref: NbDialogRef<ArticleTopicComponent>) {
    super(topicService, injector);
  }

  articleId: string; //  文章ID

  ngOnInit() {
    this.initArticleTopic();
  }

  /**
   * 初始化专题列表
   */
  initArticleTopic() {
    if (this.articleId) {
      this.topicService.getSpecialTopicForArticle(this.articleId).then(result => {
        this.list = result;
      });
    }
  }

  /**
   * 移除关系
   * @param topicId
   */
  removeShip(topicId: string) {
    if (this.articleId) {
      this.articleService.delRelatedTopic(this.articleId, topicId).then(() => {
        this.initArticleTopic();
      });
    }
  }

  /**
   * 跳转关联专题页面
   */
  toRelated() {
    this.ref.close(this.articleId);
  }
}
