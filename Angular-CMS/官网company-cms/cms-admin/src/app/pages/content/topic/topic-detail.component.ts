import {Component, Injector, OnInit} from '@angular/core';
import {SpecialTopicService} from '../service/topic-service';
import {BaseComponent} from '../../../core/service/base-component';
import {ActivatedRoute, Router} from '@angular/router';
import {forkJoin} from 'rxjs';
import {Constant} from '../../../core/constant';

@Component({
  selector: 'ngx-content-topic-detail',
  templateUrl: './topic-detail.component.html',
  styleUrls: ['./topic-detail.component.scss'],
})
export class SpecialTopicDetailComponent extends BaseComponent implements OnInit {

  constructor(private topicService: SpecialTopicService, protected injector: Injector, private router: Router,
              private route: ActivatedRoute) {
    super(topicService, injector);
  }

  topic: any = {
    name: '', shortName: '', keywords: '', remark: '', titleImg: '', summary: '',
    contentImg: '', tplPc: '', tplMobile: '', recommend: true,
  }; //  专题
  previewTitle: any;   //  预览
  previewContent: any;   //  预览
  pcTemplates: any;    //  pc模版
  mobileTemplates: any;  //  手机模版
  formId: string = 'topicForm';

  ngOnInit() {
    this.previewTitle = Constant.DEFAULT_PIC;
    this.previewContent = Constant.DEFAULT_PIC;
    this.initValidator();
    this.route.paramMap.subscribe(params => {
      const topicId = params.get('topicId');
      const arr = [this.initTopicTemplate(), this.getSpecialTopic(topicId)];
      forkJoin(arr);
    });
  }

  /**
   * 得到专题对象
   * @param id
   */
  getSpecialTopic(id: string): Promise<any> {
    return this.topicService.getData(id).then(result => {
      this.topic = result;
      if (result) {
        if (this.topic.titleImg) {
          this.previewTitle = this.topic.titleImg;
        }
        if (this.topic.contentImg) {
          this.previewContent = this.topic.contentImg;
        }
      }
    });
  }

  /**
   * 初始化专题模版列表
   */
  initTopicTemplate(): Promise<any> {
    return this.topicService.getSpecialTopicTemplates().then(result => {
      if (result) {
        this.pcTemplates = result['pc'];
        this.mobileTemplates = result['mobile'];
      }
      return Promise.resolve(result);
    });
  }

  /**
   * 初始化表单验证
   */
  initValidator() {
    this.initValidateForm(this.formId, {
      name: {
        validators: {
          notEmpty: {message: '名称不能为空!'},
        },
      },
      tplPc: {
        validators: {
          notEmpty: {message: '请选择PC模版!'},
        },
      },
      tplMobile: {
        validators: {
          notEmpty: {message: '请选择手机模版!'},
        },
      },
    });
  }

  /**
   * 图片上传
   * @param event
   */
  titleImgChange(event) {
    this.topic.titleImg = event.dest.path;
  }

  contentImgChange(event) {
    this.topic.contentImg = event.dest.path;
  }

  /**
   * 保存主体
   */
  editTopic() {
    if (this.isValidForm(this.formId)) {
      this.topicService.editData(this.topic).then(() => {
        this.toastUtil.showSuccess('保存成功!');
        this.router.navigate(['/pages/content/topic']);
      });
    }
  }
}
