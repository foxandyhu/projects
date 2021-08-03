import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../../core/service/base-component';
import {SmsRecordService} from '../service/sms-record-service';
import {SmsProviderService} from '../service/sms-provider-service';
import {DateUtil} from '../../../core/utils/date';

@Component({
  selector: 'ngx-sms-record',
  templateUrl: './record.component.html',
  styleUrls: ['./record.component.scss'],
})
export class SmsRecordComponent extends BaseComponent implements OnInit {

  constructor(private smsRecordService: SmsRecordService, private smsProviderService: SmsProviderService,
              protected injector: Injector) {
    super(smsRecordService, injector);
  }

  phone: string;                //  手机号码
  userName: string;           //  用户名
  providerId: string = '';  //  选中的服务商
  type: string = '';       //  选中的类型
  status: string = '';    //  选中的状态
  providers: any;        // 服务商集合
  statuss: any = [{id: 1, name: '待发送'}, {id: 2, name: '发送成功'}, {id: 3, name: '发送失败'}];
  types: any = [{id: 0, name: '未知'}, {id: 1, name: '注册验证'}, {id: 2, name: '找回密码'}];
  date: any;
  beginTime: string;   // 开始时间
  endTime: string;    // 结束时间

  ngOnInit() {
    this.getPager(1);
    this.getAllProviders();
  }

  /**
   * 获得所有的短信服务商集合
   */
  getAllProviders() {
    this.smsProviderService.getAllProviders().then(result => {
      this.providers = result;
    });
  }

  /**
   * 日期改变
   * @param event
   */
  changeDate(event) {
    if (event.start) {
      this.beginTime = DateUtil.formatDate(event.start);
    }
    if (event.end) {
      this.endTime = DateUtil.formatDate(event.end);
    }
  }

  /**
   * 搜索
   */
  search() {
    this.setQueryParams('beginTime', this.beginTime);
    this.setQueryParams('endTime', this.endTime);
    this.setQueryParams('type', this.type);
    this.setQueryParams('status', this.status);
    this.setQueryParams('provider', this.providerId);
    this.setQueryParams('userName', this.userName);
    this.setQueryParams('phone', this.phone);
    this.getPager(1);
  }

  /**
   * 重置表单
   */
  resetForm() {
    this.beginTime = '';
    this.endTime = '';
  }

  /**
   * 短信重发
   * @param id
   */
  resned(id: string) {
    this.modalUtil.confirm('提示', '您确定要重新发送该短信记录吗?').then(flag => {
      if (flag) {
        this.smsRecordService.resend(id).then(result => {
          if (result) {
            this.toastUtil.showSuccess('发送成功!');
            this.search();
          }
        });
      }
    });
  }
}
