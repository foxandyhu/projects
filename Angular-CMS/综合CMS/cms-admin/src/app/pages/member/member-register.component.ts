import {Component, Injector, OnInit} from '@angular/core';
import {MemberConfigService} from './service/member-config-service';
import {BaseComponent} from '../../core/service/base-component';
import {Constant} from '../../core/constant';

@Component({
  selector: 'ngx-member-register-config',
  templateUrl: './member-register.component.html',
  styleUrls: ['./member-register.component.scss'],
})
export class RegisterConfigComponent extends BaseComponent implements OnInit {

  constructor(private configService: MemberConfigService, protected injector: Injector) {
    super(configService, injector);
  }

  registConfig: any = {
    openRegiste: '', needCheckRegisted: '', minLengthUserName: '', minLengthPassword: '',
    emailProvider: {id: ''}, registeTitle: '', registeText: '',
  };                    //  注册配置
  emails: any;         //    邮件服务商
  formId: string = 'registConfigForm';

  ngOnInit() {
    this.initValidator();
    this.configService.getAllEmailProvider().then(result => {
      this.emails = result;
    });
    this.configService.getRegistConfig().then(result => {
      if (result) {
        this.registConfig = result;
        this.registConfig.openRegiste = this.registConfig.openRegiste + '';
        this.registConfig.needCheckRegisted = this.registConfig.needCheckRegisted + '';
        if (!this.registConfig.emailProvider) {
          this.registConfig.emailProvider = {id: ''};
        }
      }
    });
  }

  /**
   * 初始化表单验证
   */
  initValidator() {
    this.initValidateForm(this.formId, {
      openRegiste: {
        validators: {
          notEmpty: {message: '请选择是否开启注册功能!'},
        },
      },
      needCheckRegisted: {
        validators: {
          notEmpty: {message: '请选择是否开启注册审核功能!'},
        },
      },
      minLengthUserName: {
        validators: {
          notEmpty: {message: '用户名最小长度不能为空!'},
          between: {min: 6, max: 30, message: '用户名最小长度最小为6-30！'},
        },
      },
      minLengthPassword: {
        validators: {
          notEmpty: {message: '密码最小长度不能为空!'},
          between: {min: 6, max: 30, message: '密码最小长度最小为6-30!'},
        },
      },
      emailId: {
        validators: {
          notEmpty: {message: '请选中邮件服务器!'},
        },
      },
      registeTitle: {
        validators: {
          notEmpty: {message: '会员注册标题不能为空!'},
        },
      },
    });
  }

  /**
   * 保存注册配置
   */
  saveRegistConfig() {
    if (this.isValidForm(this.formId)) {
      this.configService.editRegistConfig(this.registConfig).then(() => {
        this.toastUtil.showSuccess('保存成功!');
      });
    }
  }

}
