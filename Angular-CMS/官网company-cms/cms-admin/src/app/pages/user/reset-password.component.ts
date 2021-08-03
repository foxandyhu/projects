import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../core/service/base-component';
import {UserService} from './service/users.service';
import {NbDialogRef} from '@nebular/theme';

@Component({
  selector: 'ngx-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss'],
})
export class ResetPasswordComponent extends BaseComponent implements OnInit {

  constructor(private userService: UserService, protected injector: Injector,
              private ref: NbDialogRef<ResetPasswordComponent>) {
    super(userService, injector);
  }

  private formId: string = 'passwordForm';
  userName: string = '';
  password: string = '';
  confirmPassword: string = '';

  ngOnInit() {
    this.initValidator();
  }

  /**
   * 初始化表单验证
   */
  initValidator() {
    this.initValidateForm(this.formId, {
      password: {
        validators: {
          notEmpty: {message: '请输入新密码!'},
          stringLength: {min: 6, max: 50, message: '密码在6到50个字符之间!'},
        },
      },
      confirmPassword: {
        validators: {
          notEmpty: {message: '请输入确认密码!'},
          stringLength: {min: 6, max: 50, message: '密码在6~50个字符之间!'},
          identical: {field: 'password', message: '两次输入的密码不一致!'},
        },
      },
    });
  }

  cancel() {
    this.ref.close();
  }

  /**
   * 提交
   */
  submit() {
    if (this.isValidForm(this.formId)) {
      this.userService.resetUserPwd(this.userName, this.password).then(() => {
        this.toastUtil.showSuccess('修改成功!');
        this.ref.close(true);
      });
    }
  }
}
