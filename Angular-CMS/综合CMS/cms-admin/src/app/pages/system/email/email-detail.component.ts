import {Component, Injector, OnInit} from '@angular/core';
import {NbDialogRef} from '@nebular/theme';
import {BaseComponent} from '../../../core/service/base-component';
import {Constant} from '../../../core/constant';
import {EmailProviderService} from '../service/email-provider-service';

@Component({
  selector: 'ngx-email-provider-detail',
  templateUrl: './email-detail.component.html',
  styleUrls: ['./email-detail.component.scss'],
})
export class EmailProviderDetailComponent extends BaseComponent implements OnInit {

  constructor(private emailProviderService: EmailProviderService,
              protected injector: Injector, private ref: NbDialogRef<EmailProviderDetailComponent>) {
    super(null, injector);
  }

  emailProvider: any = {// 短信服务商
    name: '', host: '', port: 25, encoding: '', protocol: '',
    userName: '', password: '', personal: '', enable: false, remark: '',
  };
  providerId: any;
  protocols: any = Constant.PROTOCOLS;
  charsets: any = Constant.CHARSETS;
  private formId: string = 'emailProviderForm';     //   表单ID

  ngOnInit() {
    this.initValidator();
    this.loadEmailProvider();
  }

  loadEmailProvider() {
    this.emailProviderService.getData(this.providerId).then(data => {
      this.emailProvider = data;
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
      host: {
        validators: {
          notEmpty: {message: '邮件服务器地址不能为空!'},
        },
      },
      encoding: {
        validators: {
          notEmpty: {message: '编码不能为空!'},
        },
      },
      protocol: {
        validators: {
          notEmpty: {message: '请选择服务协议'},
        },
      },
      userName: {
        validators: {
          notEmpty: {message: '用户名不能为空!'},
        },
      },
      password: {
        validators: {
          notEmpty: {message: '密码不能为空!'},
        },
      },
    });
  }

  cancel() {
    this.ref.close();
  }

  submit() {
    if (this.isValidForm(this.formId)) {
      this.emailProviderService.editData(this.emailProvider).then(() => {
        this.ref.close(true);
      });
    }
  }

}
