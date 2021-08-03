import {Component, Injector, OnInit} from '@angular/core';
import {SiteConfigService} from '../service/site-config-service';
import {BaseComponent} from '../../../core/service/base-component';
import {ResourceService} from '../service/resource-service';
import {forkJoin} from 'rxjs';

@Component({
  selector: 'ngx-site-config',
  templateUrl: './site-config.component.html',
  styleUrls: ['./site-config.component.scss'],
})
export class SiteConfigComponent extends BaseComponent implements OnInit {

  constructor(private configService: SiteConfigService, protected injector: Injector,
              private resourceService: ResourceService) {
    super(configService, injector);
  }

  siteConfig: any = {
    openSite: '', name: '', shortName: '', webSite: '', keywords: '',
    remark: '', openFlow: '', tplPc: '', tplMobile: '', filingCode: '', copyRight: '',
    copyRightOwner: '', organizer: '', logo: '',
  }; //  站点配置
  formId: string = 'siteConfigForm';
  pcTemplates: any;
  mobileTemplates: any;

  ngOnInit() {
    this.initValidator();
    const array = [this.loadTpl(), this.loadSiteConfig()];
    forkJoin(array);
  }

  loadSiteConfig(): Promise<boolean> {
    return this.configService.getSiteConfig().then(result => {
      if (result) {
        this.siteConfig = result;
        this.siteConfig.openFlow = this.siteConfig.openFlow + '';
        this.siteConfig.openSite = this.siteConfig.openSite + '';
        if (!this.siteConfig.tplPc) {
          this.siteConfig.tplPc = '';
        }
        if (!this.siteConfig.tplMobile) {
          this.siteConfig.tplMobile = '';
        }
      }
      return Promise.resolve(true);
    });
  }

  /**
   * 加载模版
   */
  loadTpl(): Promise<boolean> {
    return this.resourceService.getTemplates().then(result => {
      if (result) {
        this.pcTemplates = result['pc'];
        this.mobileTemplates = result['mobile'];
      }
      return Promise.resolve(true);
    });
  }

  /**
   * 初始化表单验证
   */
  initValidator() {
    this.initValidateForm(this.formId, {
      name: {
        validators: {
          notEmpty: {message: '站点名称不能为空!'},
        },
      },
      webSite: {
        validators: {
          notEmpty: {message: '站点域名不能为空!'},
        },
      },
      openSite: {
        validators: {
          notEmpty: {message: '请选择是否开启站点!'},
        },
      },
      openFlow: {
        validators: {
          notEmpty: {message: '请选择是否开启流量统计!'},
        },
      },
    });
  }

  /**
   * 编辑站点配置
   */
  editSiteConfig() {
    if (this.isValidForm(this.formId)) {
      this.configService.editSiteConfig(this.siteConfig).then(() => {
        this.toastUtil.showSuccess('保存成功!');
      });
    }
  }

  /**
   * 上传logo
   * @param event
   */
  uploadLogo(event) {
    this.siteConfig.logo = event.dest.path;
  }
}
