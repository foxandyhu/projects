import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ResourceComponent} from './resource/resource.component';
import {WatermarkConfigComponent} from './watermark/watermark.component';
import {FirewallSettingComponent} from './firewall/firewall.component';
import {CompanyComponent} from './company/company.component';
import {SiteComponent} from './site/site.component';
import {TaskComponent} from './task/task.component';
import {MenuComponent} from './menu/menu.component';
import {NoRightComponent} from './noright/noright.component';
import {TemplateComponent} from './template/template.component';
import {GuestBookConfigComponent} from '../message/guestbook/guest-book-config.component';
import {SiteConfigComponent} from './site/site-config.component';

const routes: Routes = [
  {path: 'resource', component: ResourceComponent},
  {path: 'template', component: TemplateComponent},
  {path: 'noright', component: NoRightComponent},
  {path: 'setting/watermark', component: WatermarkConfigComponent},
  {path: 'setting/firewall', component: FirewallSettingComponent},
  {path: 'setting/company', component: CompanyComponent},
  {
    path: 'setting/config', component: SiteComponent, children: [
      {path: 'site', component: SiteConfigComponent},
      {path: 'guestbook', component: GuestBookConfigComponent},
      {path: '', redirectTo: 'site', pathMatch: 'full'},
    ],
  },
  {path: 'setting/task', component: TaskComponent},
  {path: 'menu', component: MenuComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SystemRoutingModule {
}
