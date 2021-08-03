import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {PagesComponent} from './pages.component';

const routes: Routes = [
  {
    path: 'pages',
    component: PagesComponent,
    children: [
      {path: 'workbench', loadChildren: 'app/pages/workbench/workbench.module#WorkbenchModule'},
      {path: 'analysis', loadChildren: 'app/pages/analysis/analysis.module#AnalysisModule'},
      {path: 'content', loadChildren: 'app/pages/content/content.module#ContentModule'},
      {path: 'user', loadChildren: 'app/pages/user/user.module#UserModule'},
      {path: 'logs', loadChildren: 'app/pages/logs/logs.module#LogsModule'},
      {path: 'words', loadChildren: 'app/pages/words/words.module#WordsModule'},
      {path: 'friendlink', loadChildren: 'app/pages/friendlink/friend-link.module#FriendLinkModule'},
      {path: 'message', loadChildren: 'app/pages/message/message.module#MessageModule'},
      {path: 'ad', loadChildren: 'app/pages/ad/ad.module#AdModule'},
      {path: 'system', loadChildren: 'app/pages/system/system.module#SystemModule'},
      {path: '', redirectTo: 'workbench', pathMatch: 'full'},
      {path: '**', redirectTo: 'workbench'}],
  },
  {path: '', redirectTo: 'pages', pathMatch: 'full'},
  {path: '**', redirectTo: 'pages'},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})

export class PagesRoutingModule {
}
