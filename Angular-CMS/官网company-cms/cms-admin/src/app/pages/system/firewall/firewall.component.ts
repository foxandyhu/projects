import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../../core/service/base-component';
import {FirewallConfigService} from '../service/firewall-config-service';

@Component({
  selector: 'ngx-system-firewall',
  templateUrl: './firewall.component.html',
  styleUrls: ['./firewall.component.scss'],
})
export class FirewallSettingComponent extends BaseComponent implements OnInit {

  constructor(private firewallService: FirewallConfigService, protected injector: Injector) {
    super(firewallService, injector);
  }

  firewallConfig: any = {openFirewall: '', denyIps: ''};  //  防火墙配置

  ngOnInit() {
    this.firewallService.getSysFirewall().then(result => {
      if (result) {
        this.firewallConfig = result;
        this.firewallConfig.openFirewall = this.firewallConfig.openFirewall + '';
      }
    });
  }

  editFirewall() {
    this.firewallService.editSysFirewall(this.firewallConfig).then(() => {
      this.toastUtil.showSuccess('保存成功!');
    });
  }
}
