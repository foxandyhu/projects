import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../core/service/base-component';
import {UserService} from './service/users.service';

@Component({
  selector: 'ngx-edit-password',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.scss'],
})
export class PasswordComponent extends BaseComponent implements OnInit {

  constructor(private userService: UserService, protected injector: Injector) {
    super(userService, injector);
  }

  private formId: string = 'passwordForm';
  oldPassword: string = '';
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
      oldPassword: {
        validators: {
          notEmpty: {message: '请输入原密码!'},
        },
      },
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

  /**
   * 提交
   */
  submit() {
    if (this.isValidForm(this.formId)) {
      this.userService.editCurrentPwd(this.oldPassword, this.password).then(() => {
        this.toastUtil.showSuccess('修改成功!');
        this.reset();
      });
    }
  }

  /**
   * 重置
   */
  reset() {
    this.password = '';
    this.oldPassword = '';
    this.confirmPassword = '';
    this.resetForm(this.formId);
  }
}
