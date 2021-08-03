import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../../core/service/base-component';
import {SpecialTopicService} from '../service/topic-service';
import {Router} from '@angular/router';

@Component({
  selector: 'ngx-content-topic-add',
  templateUrl: './topic-add.component.html',
  styleUrls: ['./topic-add.component.scss'],
})
export class SpecialTopicAddComponent extends BaseComponent implements OnInit {

  constructor(private topicService: SpecialTopicService, protected injector: Injector, private router: Router) {
    super(topicService, injector);
  }

  topic: any = {
    name: '', shortName: '', keywords: '', remark: '', titleImg: '', summary: '',
    contentImg: '', tplPc: '', tplMobile: '', recommend: true,
  }; //  专题
  pcTemplates: any;    //  pc模版
  mobileTemplates: any;  //  手机模版
  formId: string = 'topicForm';

  ngOnInit() {
    this.initValidator();
    this.topicService.getSpecialTopicTemplates().then(result => {
      if (result) {
        this.pcTemplates = result['pc'];
        this.mobileTemplates = result['mobile'];
      }
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
  saveTopic() {
    if (this.isValidForm(this.formId)) {
      this.topicService.saveData(this.topic).then(() => {
        this.toastUtil.showSuccess('新增成功!');
        this.router.navigate(['/pages/content/topic']);
      });
    }
  }
}
