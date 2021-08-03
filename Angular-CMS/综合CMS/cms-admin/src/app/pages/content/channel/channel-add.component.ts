import {Component, Injector, OnInit} from '@angular/core';
import {NbDialogRef} from '@nebular/theme';
import {BaseComponent} from '../../../core/service/base-component';
import {ChannelService} from '../service/channel-service';
import {ResourceService} from '../../system/service/resource-service';

@Component({
  selector: 'ngx-content-channel-add',
  templateUrl: './channel-add.component.html',
  styleUrls: ['./channel-add.component.scss'],
})
export class ChannelAddComponent extends BaseComponent implements OnInit {

  constructor(private channelService: ChannelService,
              protected injector: Injector, private resourceService: ResourceService,
              private ref: NbDialogRef<ChannelAddComponent>) {
    super(null, injector);
  }

  formId: string = 'channelForm';
  channel: any = {
    name: '', alias: '', path: '', display: true, link: '', tplPcChannel: '',
    tplPcContent: '', tplMobileChannel: '', tplMobileContent: '', target: '', keywords: '',
    summary: '', parentId: '',
  };  //  栏目
  opens: any = ['_blank', '_self', '_parent', '_top'];
  channels: Array<any>;  // 栏目
  private _isOutLink: boolean = false;  //  是否外部连接
  pcTemplates: any;    //  pc模版
  mobileTemplates: any;  //  手机模版

  ngOnInit() {
    this.initValidator();
    this.channelService.getAllChannels().then(result => {
      if (result) {
        this.channels = result.data;
      }
    });
    this.loadTemplates();
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
      alias: {
        validators: {
          notEmpty: {message: '别名不能为空!'},
        },
      },
      path: {
        validators: {
          notEmpty: {message: '访问路径不能为空!'},
        },
      },
      modelId: {
        validators: {
          notEmpty: {message: '请选择模型!'},
        },
      },
    });
  }

  cancel() {
    this.ref.close();
  }

  submit() {
    if (this.isValidForm(this.formId)) {
      this.channelService.saveData(this.channel).then(() => {
        this.ref.close(true);
      });
    }
  }

  /**
   * 加载模板
   */
  loadTemplates() {
    this.resourceService.getTemplates().then(result => {
      if (result) {
        this.pcTemplates = result['pc'];
        this.mobileTemplates = result['mobile'];
      }
    });
  }

  get isOutLink(): boolean {
    return this._isOutLink;
  }

  set isOutLink(value: boolean) {
    this._isOutLink = value;
    if (!value) {
      this.channel.link = '';
    }
  }
}
