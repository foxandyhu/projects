import {AfterViewInit, Component, Injector, OnInit} from '@angular/core';
import {SpecialTopicService} from '../service/topic-service';
import {BaseComponent} from '../../../core/service/base-component';
import {ArticleService} from '../service/article-service';
import {NbDialogRef} from '@nebular/theme';

@Component({
  selector: 'ngx-content-article-related-topic',
  templateUrl: './related-topic.component.html',
  styleUrls: ['./related-topic.component.scss'],
})
export class ArticleRelatedTopicComponent extends BaseComponent implements OnInit, AfterViewInit {

  constructor(private topicService: SpecialTopicService, private articleService: ArticleService,
              protected injector: Injector,
              private ref: NbDialogRef<ArticleRelatedTopicComponent>) {
    super(topicService, injector);
  }

  articleIds: Array<any>;    // 选中的文章ID

  ngOnInit() {
  }


  ngAfterViewInit(): void {
    this.getPager(1);
  }

  /**
   * 保存关联
   */
  submit() {
    this.articleService.saveRelatedTopic(this.articleIds, this.selectItems).then(() => {
      this.toastUtil.showSuccess('关联成功!');
      this.ref.close();
    });
  }
}
