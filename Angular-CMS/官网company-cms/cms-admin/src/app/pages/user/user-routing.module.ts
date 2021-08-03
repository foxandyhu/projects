import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {UserComponent} from './user.component';
import {RoleComponent} from './role.component';
import {PasswordComponent} from './password.component';
import {UserAddComponent} from './user-add.component';
import {UserDetailComponent} from './user-detail.component';
import {RoleUserComponent} from './role-user.component';

const routes: Routes = [
  {path: 'list', component: UserComponent},
  {path: 'add', component: UserAddComponent},
  {path: 'detail', component: UserDetailComponent},
  {path: 'role', component: RoleComponent},
  {path: 'role/mu/:roleId', component: RoleUserComponent},
  {path: 'password', component: PasswordComponent},
  {path: ':userId', component: UserDetailComponent},
  {path: '', redirectTo: 'list', pathMatch: 'full'},
  {path: '**', redirectTo: 'list'},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserRoutingModule {
}
