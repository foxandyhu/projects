import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ThemeModule} from '../../@theme/theme.module';
import {ContentRoutingModule} from './content-routing.module';
import {ArticleComponent} from './article/article.component';
import {SpecialTopicComponent} from './topic/topic.component';
import {ChannelTreeComponent} from './article/channel-tree.component';
import {ChannelComponent, FsIconComponent} from './channel/channel.component';
import {ModelComponent} from './model/model.component';
import {ModelService} from './service/model-service';
import {ModelAddComponent} from './model/model-add.component';
import {ModelDetailComponent} from './model/model-detail.component';
import {NbDialogModule, NbTreeGridModule} from '@nebular/theme';
import {ModelItemComponent} from './model/model-item.component';
import {ModelItemAddComponent} from './model/model-item-add.component';
import {ModelItemDetailComponent} from './model/model-item-detail.component';
import {ModelItemService} from './service/model-item-service';
import {ChannelService} from './service/channel-service';
import {ChannelAddComponent} from './channel/channel-add.component';
import {ChannelDetailComponent} from './channel/channel-detail.component';
import {SpecialTopicService} from './service/topic-service';
import {SpecialTopicAddComponent} from './topic/topic-add.component';
import {SpecialTopicDetailComponent} from './topic/topic-detail.component';
import {ArticleService} from './service/article-service';
import {ArticleTopComponent} from './article/top.component';
import {ArticleRelatedTopicComponent} from './article/related-topic.component';
import {ArticleTopicComponent} from './article/article-topic.component';
import {ArticleAddComponent} from './article/article-add.component';
import {ArticleDetailComponent} from './article/article-detail.component';
import {ColorPickerModule} from 'ngx-color-picker';
import {MemberGroupService} from '../member/service/member-group-service';
import {EditorModule} from '@tinymce/tinymce-angular';
import {ScoreGroupService} from '../words/service/score-group-service';
import {DictionaryService} from '../words/service/dictionary-service';
import {ResourceService} from '../system/service/resource-service';

@NgModule({
  declarations: [ArticleComponent, SpecialTopicComponent,
    ChannelTreeComponent, ChannelComponent, ModelComponent,
    ModelAddComponent, ModelDetailComponent, ModelItemComponent, ModelItemAddComponent, ModelItemDetailComponent,
    ChannelAddComponent, ChannelDetailComponent, SpecialTopicAddComponent,
    SpecialTopicDetailComponent, ArticleTopComponent, ArticleRelatedTopicComponent,
    ArticleTopicComponent, ArticleAddComponent, ArticleDetailComponent, ModelItemComponent, FsIconComponent],
  imports: [
    ThemeModule,
    CommonModule,
    EditorModule,
    ColorPickerModule, NbTreeGridModule,
    NbDialogModule.forChild(),
    ContentRoutingModule,
  ],
  entryComponents: [ModelAddComponent, ModelDetailComponent,
    ModelItemAddComponent, ModelItemDetailComponent, ArticleTopicComponent,
    ChannelAddComponent, ChannelDetailComponent, ArticleTopComponent, ArticleRelatedTopicComponent],
  providers: [ModelService, ModelItemService, ChannelService, SpecialTopicService,
    ArticleService, MemberGroupService, ScoreGroupService, DictionaryService, ResourceService],
})
export class ContentModule {
}
