import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {SystemRoutingModule} from './system-routing.module';
import {ThemeModule} from '../../@theme/theme.module';
import {ResourceComponent} from './resource/resource.component';
import {ResTreeComponent} from './resource/res-tree.component';
import {WatermarkConfigComponent} from './watermark/watermark.component';
import {FirewallSettingComponent} from './firewall/firewall.component';
import {CompanyComponent} from './company/company.component';
import {SiteComponent} from './site/site.component';
import {TaskComponent} from './task/task.component';
import {MenuComponent, MenuIconComponent} from './menu/menu.component';
import {MenuAddComponent} from './menu/menu-add.component';
import {NbDialogModule, NbTreeGridModule} from '@nebular/theme';
import {MenuService} from './service/menu-service';
import {MenuDetailComponent} from './menu/menu-detail.component';
import {NoRightComponent} from './noright/noright.component';
import {EditorModule} from '@tinymce/tinymce-angular';
import {SiteConfigService} from './service/site-config-service';
import {WatermarkConfigService} from './service/watermark-config-service';
import {ColorPickerModule} from 'ngx-color-picker';
import {FirewallConfigService} from './service/firewall-config-service';
import {CompanyService} from './service/company-service';
import {DictionaryService} from '../words/service/dictionary-service';
import {TaskService} from './service/task-service';
import {ResourceService} from './service/resource-service';
import {ResDirAddComponent} from './resource/res-dir-add.component';
import {TemplateComponent} from './template/template.component';
import {TemplateService} from './service/template-service';
import {TemplateDirAddComponent} from './template/template-dir-add.component';
import {TemplateTreeComponent} from './template/template-tree.component';
import {TemplateDetailComponent} from './template/template-detail.component';
import {SiteConfigComponent} from './site/site-config.component';
import {GuestBookConfigComponent} from '../message/guestbook/guest-book-config.component';
import {GuestBookService} from '../message/service/guestbook-service';

@NgModule({
  declarations: [ResourceComponent, ResTreeComponent, WatermarkConfigComponent,
    FirewallSettingComponent, CompanyComponent, SiteComponent, TaskComponent, ResDirAddComponent,
    MenuComponent, MenuAddComponent, MenuDetailComponent, NoRightComponent, TemplateComponent,
    TemplateDirAddComponent, TemplateTreeComponent, TemplateDetailComponent, SiteConfigComponent,
    GuestBookConfigComponent, MenuIconComponent],
  imports: [
    CommonModule,
    ThemeModule,
    SystemRoutingModule,
    ColorPickerModule, NbTreeGridModule,
    EditorModule,
    NbDialogModule.forChild(),
  ],
  entryComponents: [MenuAddComponent, MenuDetailComponent,
    ResDirAddComponent, TemplateDirAddComponent, TemplateDetailComponent],
  providers: [MenuService, SiteConfigService,
    WatermarkConfigService, FirewallConfigService, CompanyService, DictionaryService, TaskService,
    ResourceService, TemplateService, GuestBookService],
})
export class SystemModule {
}
