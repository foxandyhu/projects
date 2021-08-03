import {Component, Injector, OnInit} from '@angular/core';
import {NbDialogRef} from '@nebular/theme';
import {BaseComponent} from '../../../core/service/base-component';
import {SmsProviderService} from '../service/sms-provider-service';

@Component({
  selector: 'ngx-sms-provider-detail',
  templateUrl: './provider-detail.component.html',
  styleUrls: ['./provider-detail.component.scss'],
})
export class SmsProviderDetailComponent extends BaseComponent implements OnInit {

  constructor(private smsProviderService: SmsProviderService,
              protected injector: Injector, private ref: NbDialogRef<SmsProviderDetailComponent>) {
    super(null, injector);
  }

  smsProvider: any = {name: '', userName: '', password: '', url: '', enable: false, remark: ''};   // 短信服务商
  providerId: any;
  private formId: string = 'smsProviderForm';     //   表单ID

  ngOnInit() {
    this.initValidator();
    this.loadSmsProvider();
  }

  loadSmsProvider() {
    this.smsProviderService.getData(this.providerId).then(data => {
      this.smsProvider = data;
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
    });
  }

  cancel() {
    this.ref.close();
  }

  submit() {
    if (this.isValidForm(this.formId)) {
      this.smsProviderService.editData(this.smsProvider).then(() => {
        this.ref.close(true);
      });
    }
  }

}
