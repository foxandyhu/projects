import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../../core/service/base-component';
import {GuestBookService} from '../service/guestbook-service';

@Component({
  selector: 'ngx-guest-book-config',
  templateUrl: './guest-book-config.component.html',
  styleUrls: ['./guest-book-config.component.scss'],
})
export class GuestBookConfigComponent extends BaseComponent implements OnInit {

  guestBookConfig: any = {openGuestBook: true, needLoginGuestBook: false, maxGuestBookLimit: ''}; //  留言配置
  formId: string = 'guestBookConfigForm';

  constructor(private guestBookService: GuestBookService, protected injector: Injector) {
    super(guestBookService, injector);
  }

  ngOnInit() {
    this.initValidator();
    this.getGuestBookConfig();
  }

  /**
   * 初始化表单验证
   */
  initValidator() {
    this.initValidateForm(this.formId, {
      openGuestBook: {
        validators: {
          notEmpty: {message: '请选择是否开启留言!'},
        },
      },
      needLoginGuestBook: {
        validators: {
          notEmpty: {message: '请选择留言是否登录!'},
        },
      },
      maxCommentLimit: {
        validators: {
          notEmpty: {message: '留言日最高限制数不能为空!'},
          digits: {message: '留言日最高限制数最小为0！'},
        },
      },
    });
  }

  /**
   * 得到留言配置
   */
  getGuestBookConfig() {
    this.guestBookService.getGuestBookConfig().then(result => {
      if (result) {
        this.guestBookConfig = result;
      }
    });
  }

  /**
   * 编辑留言配置
   */
  editGuestBookConfig() {
    if (this.isValidForm(this.formId)) {
      this.guestBookService.editGuestBookConfig(this.guestBookConfig).then(() => {
        this.toastUtil.showSuccess('保存成功!');
      });
    }
  }

}
