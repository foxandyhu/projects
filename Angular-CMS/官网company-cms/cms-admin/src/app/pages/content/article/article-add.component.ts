import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../../core/service/base-component';
import {ArticleService} from '../service/article-service';
import {ActivatedRoute, Router} from '@angular/router';
import {ChannelService} from '../service/channel-service';
import {ModelItemService} from '../service/model-item-service';
import {DateUtil} from '../../../core/utils/date';
import {ModelService} from '../service/model-service';
import {Constant} from '../../../core/constant';
import {CommonService} from '../../../core/service/common-service';
import {DomSanitizer} from '@angular/platform-browser';
import '../../../@theme/components/editor.loader';
import * as moment from 'moment';
import {ValidateUtil} from '../../../core/utils/validate';
import {DictionaryService} from '../../words/service/dictionary-service';
import {ResourceService} from '../../system/service/resource-service';


@Component({
  selector: 'ngx-content-article-add',
  templateUrl: './article-add.component.html',
  styleUrls: ['./article-add.component.scss'],
})
export class ArticleAddComponent extends BaseComponent implements OnInit {

  constructor(private articleService: ArticleService, protected injector: Injector,
              private route: ActivatedRoute, private channelService: ChannelService,
              private commonService: CommonService, private domSanitizer: DomSanitizer,
              private router: Router,
              private modelItemService: ModelItemService, private modelService: ModelService,
               private dictionaryService: DictionaryService,
              private resourceService: ResourceService) {
    super(articleService, injector);
  }

  modelId: any;
  model: any = {name: ''};
  channels: any;
  modelItems: Array<any>;  //  模型项集合
  titleImgPreview: any; //  标题图
  contentImgPreview: any; //  类型图
  scoreGroups: any;    //   评分组
  fileCategory: Array<any> = [{name: '文档', id: Constant.DOC_TYPE}, {name: '多媒体', id: Constant.MEDIA_TYPE}];
  pcTemplates: any;
  mobileTemplates: any;
  color: string;
  showFileResult: any = {show: false, text: ''};
  article: any = {  //  文章
    channelId: '', modelId: '',
    topLevel: 0, topExpired: '', recommend: false, recommendLevel: 0, status: '',
    type: '', share: true, updown: true, comment: true, score: false, scoreGroupId: '', scores: 0,
    articleExt: {
      title: '', shortTitle: '', summary: '', keywords: '', description: '', author: '', origin: '', originUrl: '',
      postDate: '', filePath: '', fileType: '', fileLength: '', titleColor: '', bold: false, titleImg: '',
      contentImg: '', typeImg: '', link: '', tplPc: '', tplMobile: '', tags: '',
    },
    articleTxt: {txt: ''},
    pictures: [],
    attachments: [],
    attr: {},
  };

  ngOnInit() {
    this.getChannels();
    this.route.paramMap.subscribe(params => {
      this.modelId = params.get('modelId');
      this.article.modelId = this.modelId;
      this.getModelItems();
      this.getModel();
    });
  }


  getChannels() {
    this.channelService.getAllChannels().then(result => {
      if (result) {
        this.channels = result.data;
      }
    });
  }

  getModel() {
    this.modelService.getData(this.modelId).then(result => {
      this.model = result;
    });
  }

  /**
   * 加载模型项
   * @param modelId
   */
  getModelItems() {
    this.modelItemService.getModelItems(this.modelId).then(result => {
      this.modelItems = result;
      if (this.modelItems) {
        this.initArticle();
        //  加载模版
        if (this.hasItem('tplpc') || this.hasItem('tplmobile')) {
          this.loadTpl();
        }
      }
    });
  }

  /**
   * 初始化文章信息--字段默认值
   */
  private initArticle() {
    this.article.type = this.getDefValue('type');
    this.article.topLevel = this.getDefValue('top');
    this.article.recommend = this.getDefValue('recommend');
    this.article.articleExt.title = this.getDefValue('title');
    this.article.articleExt.link = this.getDefValue('link');
    this.article.articleExt.tplMobile = this.getDefValue('tplmobile');
    this.article.articleExt.tplPc = this.getDefValue('tplpc');
    this.titleImgPreview = this.getDefValue('titleimg');
    if (!this.titleImgPreview) {
      this.titleImgPreview = Constant.DEFAULT_PIC;
    }
    this.article.articleExt.contentImg = this.getDefValue('contentimg');
    if (!this.contentImgPreview) {
      this.contentImgPreview = Constant.DEFAULT_PIC;
    }
    const typeId = this.getDefValue('type');
    if (typeId) {
      this.article.type = parseInt(typeId, 0);
    }
    const share = this.getDefValue('share');
    if (share) {
      this.article.share = share;
    }
    const updown = this.getDefValue('updown');
    if (updown) {
      this.article.updown = ValidateUtil.isBoolean(updown);
    }
    const comment = this.getDefValue('comment');
    if (comment) {
      this.article.comment = ValidateUtil.isBoolean(comment);
    }
    const score = this.getDefValue('score');
    if (score) {
      this.article.score = ValidateUtil.isBoolean(score);
    }
    this.modelItems.forEach(item => {
      if (item.custom) {
        this.article.attr[item.field] = item.defValue;
      }
    });
  }

  /**
   * 获得字段默认值
   * @param key
   */
  private getDefValue(key: string): any {
    for (const item of this.modelItems) {
      if (item.field === key) {
        return item.defValue;
      }
    }
    return undefined;
  }

  /**
   * 判断是否存在某个字段
   * @param key
   */
  private hasItem(key: string): boolean {
    let exist: boolean = false;
    for (const item of this.modelItems) {
      if (item.field === 'tplpc' || item.field === 'tplmobile') {
        exist = true;
        break;
      }
    }
    return exist;
  }

  /**
   * 标题图，内容图上传
   * @param event
   */
  imgFileChange(event, target) {
    const path = event.dest.path;
    switch (target) {
      case 'titleImg':
        this.article.articleExt.titleImg = path;
        break;
      case 'contentImg':
        this.article.articleExt.contentImg = path;
        break;
    }
  }

  /**
   * 删除上传的文件
   * @param event
   */
  removeUploadFile(target, name, field?) {
    switch (target) {
      case 'file':
        this.article.articleExt.fileType = '';
        this.article.articleExt.filePath = '';
        this.article.articleExt.fileLength = '';
        this.showFileResult.show = false;
        break;
      case 'pictures':
        if (name) {
          this.article.pictures = this.article.pictures.filter(picture => {
            return picture.imgPath !== name;
          });
        }
        break;
      case 'pictures_attr':
        if (name) {
          this.article.attr[field] = this.article.attr[field].filter(picture => {
            return picture.imgPath !== name;
          });
        }
        break;
      case 'attachments':
        if (name) {
          this.article.attachments = this.article.attachments.filter(attachment => {
            return attachment.path !== name;
          });
        }
        break;
      case 'attachments_attr':
        this.article.attr[field] = this.article.attr[field].filter(attachment => {
          return attachment.path !== name;
        });
        break;
    }
  }

  /**
   * 上传文档和多媒体
   * @param result
   * @param target
   */
  uploadFileChange(result, target, field?: string) {
    switch (target) {
      case 'file':
        this.article.articleExt.filePath = result.dest.path;
        this.article.articleExt.fileLength = result.dest.size;
        this.showFileResult.show = true;
        this.showFileResult.text = result.source.name;
        break;
      case 'pictures':
        const maxSeq = this.article.pictures.length;
        const file = result.source;
        const preview = this.domSanitizer.bypassSecurityTrustUrl(window.URL.createObjectURL(file));
        this.article.pictures.push({
          preview: preview, seq: maxSeq + 1, imgPath: result.dest.path,
          name: file.name, remark: '', field: '',
        });
        break;
      case 'attachments':
        this.article.attachments.push({field: '', path: result.dest.path, name: result.source.name, remark: ''});
        break;
    }
  }

  /**
   * 排序图片 isUp 为true标识上移 false下移
   * @param seq
   * @param isUp
   */
  sortPic(seq: number, isUp: boolean, field?: string) {
    let array = new Array();
    if (field) {
      array = this.article.attr[field];
    } else {
      array = this.article.pictures;
    }
    const length = array.length;
    for (let index = 0; index < length; index++) {
      const item = array[index];
      if (item.seq === seq) {
        if (isUp === true) {
          const preItem = array[index - 1];
          if (preItem) {
            const downItemId = preItem.seq;
            preItem.seq = seq;
            item.seq = downItemId;
          }
        } else {
          const nextItem = array[index + 1];
          if (nextItem) {
            const downItemId = nextItem.seq;
            nextItem.seq = seq;
            item.seq = downItemId;
          }
        }
        break;
      }
    }
    array.sort((pic1, pic2) => {
      return pic1.seq - pic2.seq;
    });
  }

  /**
   * 加载模版
   */
  loadTpl() {
    this.resourceService.getTemplates().then(result => {
      if (result) {
        this.pcTemplates = result['pc'];
        this.mobileTemplates = result['mobile'];
      }
    });
  }


  /**
   * 颜色选择
   * @param color
   */
  pickColor(color) {
    this.article.articleExt.titleColor = color;
  }

  /**
   * 外部连接
   */
  isOutLink() {
    this.article.articleExt.link = '';
  }

  /**
   * 置顶
   */
  isTop() {
    this.article.topLevel = 0;
    this.article.topExpired = '';
  }

  /**
   * 日期期限
   * @param date
   */
  dateChange(date, target) {
    switch (target) {
      case 'top':
        this.article.topExpired = DateUtil.formatDate(date);
        break;
      case 'postdate':
        this.article.articleExt.postDate = DateUtil.formatDate(date);
        break;
    }
  }

  /**
   * 是否推荐
   */
  isRecommend() {
    this.article.recommendLevel = 0;
  }

  /**
   * 设置其他自定义字段值
   */
  setAttr(field: string, value: any) {
    if (value instanceof moment) {
      value = DateUtil.formatDate(value);
    }
    this.article.attr[field] = value;
  }

  /**
   * 设置多选框自定义字段值
   * @param field 字段名
   * @param value 值
   * @param isChk 是否选中
   */
  setAttrChk(field: string, value: any, isChk) {
    let data: string = this.article.attr[field];
    if (!data) {
      data = value;
    }
    const array: Array<any> = data.split(',');
    //  选中
    if (isChk) {
      if (array.indexOf(value) < 0) {
        //  不存在 则添加
        array.push(value);
      }
    } else {
      // 取消
      const index = array.indexOf(value);
      if (index > -1) {
        //  存在 则删除
        array.splice(index, 1);
      }
    }
    this.article.attr[field] = array.join(',');
  }

  /**
   * 保存文章
   */
  saveArticle() {
    //  复制对象
    const data = JSON.parse(JSON.stringify(this.article));
    if (data.pictures) {
      data.pictures.forEach(picture => {
        //  删除图片预览
        delete picture.preview;
      });
    }
    data.status = Constant.ARTICLE_STATUS[2].id;
    this.articleService.saveData(data).then(() => {
      this.toastUtil.showSuccess('保存成功!');
      this.router.navigate(['/pages/content/article']);
    });
  }
}
