import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {MemberComponent} from './member.component';
import {MemberGroupComponent} from './member-group.component';
import {MemberAddComponent} from './member-add.component';
import {MemberDetailComponent} from './member-detail.component';
import {MemberConfigComponent} from './member-config.component';
import {LoginConfigComponent} from './member-login.component';
import {RegisterConfigComponent} from './member-register.component';

const routes: Routes = [
  {path: 'list', component: MemberComponent},
  {path: 'add', component: MemberAddComponent},
  {path: 'group', component: MemberGroupComponent},
  {path: 'config', component: MemberConfigComponent, children: [
      {path: '', redirectTo: 'login', pathMatch: 'full'},
      {path: 'login', component: LoginConfigComponent},
      {path: 'register', component: RegisterConfigComponent},
    ],
  },
  {path: ':memberId', component: MemberDetailComponent},
  {path: '', redirectTo: 'list', pathMatch: 'full'},
  {path: '**', redirectTo: 'list'},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MemberRoutingModule {
}
