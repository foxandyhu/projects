import {Component, Injector, OnInit} from '@angular/core';
import {NbDialogRef} from '@nebular/theme';
import {BaseComponent} from '../../../core/service/base-component';
import {DateUtil} from '../../../core/utils/date';

@Component({
  selector: 'ngx-content-article-top',
  templateUrl: './top.component.html',
  styleUrls: ['./top.component.scss'],
})
export class ArticleTopComponent extends BaseComponent implements OnInit {

  constructor(private ref: NbDialogRef<ArticleTopComponent>, protected injector: Injector) {
    super(null, injector);
  }

  top: any = {level: 0, expired: ''};    //  置顶信息
  formId: string = 'topForm';

  ngOnInit() {
    this.initValidator();
  }

  /**
   * 初始化表单验证
   */
  initValidator() {
    this.initValidateForm(this.formId, {
      level: {
        validators: {
          notEmpty: {message: '置顶级别不能为空!'},
          integer: {message: '置顶级别不正确!'},
          greaterThan: {value: 0, message: '置顶级别最小为0!'},
        },
      },
    });
  }

  /**
   * 日期选择
   * @param date
   */
  dateChange(date) {
    this.top.expired = DateUtil.formatDate(date);
  }

  cancel() {
    this.ref.close();
  }

  submit() {
    if (this.isValidForm(this.formId)) {
      this.ref.close(this.top);
    }
  }
}
