import {Component, Injector, OnInit} from '@angular/core';
import {SiteConfigService} from '../../../pages/system/service/site-config-service';
import {SiteNameEmitService} from '../../../core/service/site-name-emit.service';

@Component({
  selector: 'ngx-footer',
  styleUrls: ['./footer.component.scss'],
  template: `
    <span class="created-by">{{copyRight?.copyRight}}&nbsp;&nbsp;<b>
      <a href="{{copyRight?.webSite}}" target="_blank">{{copyRight?.copyRightOwner}}</a>
    </b>&nbsp;&nbsp;{{copyRight?.filingCode}}</span>
  `,
})
export class FooterComponent implements OnInit {

  constructor(private siteConfigService: SiteConfigService, protected injector: Injector,
              private siteNameEmitService: SiteNameEmitService) {
  }

  copyRight: any;

  ngOnInit(): void {
    this.siteConfigService.getCopyRight().then(result => {
      this.copyRight = result;
      this.siteNameEmitService.eventEmit.emit(this.copyRight.shortName);
    });
  }
}
