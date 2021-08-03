import {Component, Injector, OnInit} from '@angular/core';
import {ArticleService} from '../service/article-service';
import {ActivatedRoute, Router} from '@angular/router';
import {ChannelService} from '../service/channel-service';
import {CommonService} from '../../../core/service/common-service';
import {DomSanitizer} from '@angular/platform-browser';
import {MemberGroupService} from '../../member/service/member-group-service';
import {ModelItemService} from '../service/model-item-service';
import {ModelService} from '../service/model-service';
import {Constant} from '../../../core/constant';
import {DateUtil} from '../../../core/utils/date';
import {BaseComponent} from '../../../core/service/base-component';
import * as moment from 'moment';
import {ScoreGroupService} from '../../words/service/score-group-service';
import {DictionaryService} from '../../words/service/dictionary-service';
import {ResourceService} from '../../system/service/resource-service';

@Component({
  selector: 'ngx-content-article-detail',
  templateUrl: './article-detail.component.html',
  styleUrls: ['./article-detail.component.scss'],
})
export class ArticleDetailComponent extends BaseComponent implements OnInit {

  constructor(private articleService: ArticleService, protected injector: Injector,
              private route: ActivatedRoute, private channelService: ChannelService,
              private commonService: CommonService, private domSanitizer: DomSanitizer,
              private groupService: MemberGroupService, private router: Router,
              private modelItemService: ModelItemService, private modelService: ModelService,
              private scoreGroupService: ScoreGroupService, private dictionaryService: DictionaryService,
              private resourceService: ResourceService) {
    super(articleService, injector);
  }

  modelItems: Array<any>;  //  模型项集合
  typeImgPreview: any = Constant.DEFAULT_PIC; //  类型图
  titleImgPreview: any; //  标题图
  contentImgPreview: any; //  类型图
  scoreGroups: any;    //   评分组
  fileCategory: Array<any> = [{name: '文档', id: Constant.DOC_TYPE}, {name: '多媒体', id: Constant.MEDIA_TYPE}];
  pcTemplates: any;
  mobileTemplates: any;
  color: string;
  showFileResult: any = {show: false, text: ''};
  article: any = {  //  文章
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
    this.route.paramMap.subscribe(params => {
      const articleId = params.get('articleId');
      this.articleService.getData(articleId).then(result => {
        if (result) {
          if (!result.articleTxt) {
            result.articleTxt = this.article.articleTxt;
          }
          if (!result.articleExt) {
            result.articleExt = this.article.articleExt;
          }
        }
        this.article = result;
        this.getModelItems();
      });
    });
  }

  /**
   * 加载模型项
   * @param modelId
   */
  getModelItems() {
    this.modelItemService.getModelItems(this.article.modelId).then(result => {
      this.modelItems = result;
      if (this.modelItems) {
        this.initArticle();
        //  加载模版
        if (this.hasItem('tplpc') || this.hasItem('tplmobile')) {
          this.loadTpl();
        }
        //  加载评分组
        if (this.hasItem('score')) {
          this.loadScoreGroup();
        }
      }
    });
  }

  /**
   * 初始化文章信息
   */
  private initArticle() {
    if (!this.article.articleExt.typeImg) {
      this.typeImgPreview = Constant.DEFAULT_PIC;
    } else {
      this.typeImgPreview = this.article.articleExt.typeImg;
    }
    if (!this.article.articleExt.titleImg) {
      this.titleImgPreview = Constant.DEFAULT_PIC;
    } else {
      this.titleImgPreview = this.article.articleExt.titleImg;
    }
    if (!this.article.articleExt.contentImg) {
      this.contentImgPreview = Constant.DEFAULT_PIC;
    } else {
      this.contentImgPreview = this.article.articleExt.contentImg;
    }
    if (this.article.articleExt.postDate) {
      this.article.articleExt.postDate = DateUtil.formatDate(this.article.articleExt.postDate);
    }
    if (this.article.topExpired) {
      this.article.topExpired = DateUtil.formatDate(this.article.topExpired);
    }
    if (this.article.articleExt.filePath) {
      this.showFileResult.show = true;
      this.showFileResult.text = this.article.articleExt.filePath;
    }
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
            const flag = picture.imgPath !== name;
            if (!flag) {
              this.deletePicture(picture);
            }
            return flag;
          });
        }
        break;
      case 'pictures_attr':
        if (name) {
          this.article.attr[field] = this.article.attr[field].filter(picture => {
            const flag = picture.imgPath !== name;
            if (!flag) {
              this.deletePicture(picture);
            }
            return flag;
          });
        }
        break;
      case 'attachments':
        if (name) {
          this.article.attachments = this.article.attachments.filter(attachment => {
            const flag = attachment.path !== name;
            if (!flag) {
              this.deleteAttachment(attachment);
            }
            return flag;
          });
        }
        break;
      case 'attachments_attr':
        this.article.attr[field] = this.article.attr[field].filter(attachment => {
          const flag = attachment.path !== name;
          if (!flag) {
            this.deleteAttachment(attachment);
          }
          return flag;
        });
        break;
    }
  }

  /**
   * 数据库删除图片集
   */
  deletePicture(picture: any) {
    if (picture.id && picture.id > 0) {
      this.articleService.delPicture(picture.id).then(result => {
        this.toastUtil.showSuccess('删除成功!');
      });
    }
  }

  /**
   * 删除附件集
   * @param picture
   */
  deleteAttachment(attachment: any) {
    if (attachment.id && attachment.id > 0) {
      this.articleService.delAttachment(attachment.id).then(result => {
        this.toastUtil.showSuccess('删除成功!');
      });
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
   * 设置默认图片集预览
   * @param pic
   */
  initImgPreView(pic) {
    pic.preview = pic.preview ? pic.preview : (pic.imgPath ? pic.imgPath : Constant.DEFAULT_PIC);
    return pic.preview;
  }

  /**
   * 设置默认选中的复选框
   */
  initMutilCheck(field, value) {
    const items = this.article.attr[field];
    if (items) {
      for (const item of items) {
        if (item === value) {
          return true;
        }
      }
    }
    return false;
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
   * 加载评分组
   */
  loadScoreGroup() {
    this.scoreGroupService.getAllGroup().then(result => {
      this.scoreGroups = result;
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
  editArticle() {
    //  复制对象
    const data = JSON.parse(JSON.stringify(this.article));
    if (data.pictures) {
      data.pictures.forEach(picture => {
        //  删除图片预览
        delete picture.preview;
      });
    }
    this.articleService.editData(data).then(() => {
      this.toastUtil.showSuccess('保存成功!');
      this.router.navigate(['/pages/content/article']);
    });
  }
}
