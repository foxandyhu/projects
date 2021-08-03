import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../../core/service/base-component';
import {CommentService} from '../service/comment-service';

@Component({
  selector: 'ngx-comment-config',
  templateUrl: './comment-config.component.html',
  styleUrls: ['./comment-config.component.scss'],
})
export class CommentConfigComponent extends BaseComponent implements OnInit {

  commentConfig: any = {openComment: true, needLoginComment: false, maxCommentLimit: ''}; //  评论配置
  formId: string = 'commentConfigForm';

  constructor(private commentService: CommentService, protected injector: Injector) {
    super(commentService, injector);
  }

  ngOnInit() {
    this.initValidator();
    this.getCommentConfig();
  }

  /**
   * 初始化表单验证
   */
  initValidator() {
    this.initValidateForm(this.formId, {
      openComment: {
        validators: {
          notEmpty: {message: '请选择是否开启评论!'},
        },
      },
      needLoginComment: {
        validators: {
          notEmpty: {message: '请选择评论是否登录!'},
        },
      },
      maxGuestBookLimit: {
        validators: {
          notEmpty: {message: '评论日最高限制数不能为空!'},
          digits: {message: '评论日最高限制数最小为0！'},
        },
      },
    });
  }

  /**
   * 得到评论配置
   */
  getCommentConfig() {
    this.commentService.getCommentConfig().then(result => {
      if (result) {
        this.commentConfig = result;
      }
    });
  }

  /**
   * 编辑评论配置
   */
  editCommentConfig() {
    if (this.isValidForm(this.formId)) {
      this.commentService.editCommentConfig(this.commentConfig).then(() => {
        this.toastUtil.showSuccess('保存成功!');
      });
    }
  }

}
