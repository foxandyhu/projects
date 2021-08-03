import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {UserRoutingModule} from './user-routing.module';
import {UserComponent} from './user.component';
import {RoleComponent} from './role.component';
import {ThemeModule} from '../../@theme/theme.module';
import {PasswordComponent} from './password.component';
import {UserAddComponent} from './user-add.component';
import {UserDetailComponent} from './user-detail.component';
import {UserService} from './service/users.service';
import {RoleService} from './service/roles.service';
import {RoleAddComponent} from './role-add.component';
import {RoleDetailComponent} from './role-detail.component';
import {RoleUserComponent} from './role-user.component';
import {RoleMenuComponent} from './role-menu.component';
import {NbDialogModule} from '@nebular/theme';
import {ResetPasswordComponent} from './reset-password.component';

@NgModule({
  declarations: [UserComponent, RoleComponent, PasswordComponent, ResetPasswordComponent,
    UserAddComponent, UserDetailComponent, RoleAddComponent, RoleDetailComponent, RoleUserComponent, RoleMenuComponent],
  imports: [
    CommonModule,
    ThemeModule,
    UserRoutingModule,
    NbDialogModule.forChild(),
  ], providers: [UserService, RoleService],
  entryComponents: [RoleMenuComponent, RoleAddComponent, RoleDetailComponent, ResetPasswordComponent],
})
export class UserModule {
}
