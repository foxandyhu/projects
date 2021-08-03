import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../core/service/base-component';
import {NbDialogRef} from '@nebular/theme';
import {MemberService} from './service/member-service';

@Component({
  selector: 'ngx-member-edit-password',
  templateUrl: './edit-password.component.html',
  styleUrls: ['./edit-password.component.scss'],
})
export class MemberEditPasswordComponent extends BaseComponent implements OnInit {

  data: any = {memberId: 0, password: ''};  //  密码
  private formId: string = 'pwdForm'; //   表单ID

  constructor(private memberService: MemberService,
              protected injector: Injector, private ref: NbDialogRef<MemberEditPasswordComponent>) {
    super(null, injector);
  }

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
          notEmpty: {message: '密码不能为空!'},
        },
      },
      confirmPassword: {
        validators: {
          notEmpty: {message: '请输入确认密码!'},
          stringLength: {min: 6, max: 50, message: '密码在6-50个字符之间!'},
          identical: {field: 'password', message: '两次输入的密码不一致!'},
        },
      },
    });
  }

  cancel() {
    this.ref.close();
  }

  submit() {
    if (this.isValidForm(this.formId)) {
      this.memberService.editPassword(this.data).then(() => {
        this.toastUtil.showSuccess('密码修改成功!');
        this.ref.close(true);
      });
    }
  }

}
