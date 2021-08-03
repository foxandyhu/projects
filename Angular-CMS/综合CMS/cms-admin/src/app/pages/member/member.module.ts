import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ThemeModule} from '../../@theme/theme.module';

import {MemberRoutingModule} from './member-routing.module';
import {MemberComponent} from './member.component';
import {MemberGroupComponent} from './member-group.component';
import {MemberService} from './service/member-service';
import {MemberGroupService} from './service/member-group-service';
import {MemberGroupAddComponent} from './member-group-add.component';
import {MemberGroupDetailComponent} from './member-group-detail.component';
import {NbDialogModule} from '@nebular/theme';
import {MemberAddComponent} from './member-add.component';
import {MemberDetailComponent} from './member-detail.component';
import {MemberEditPasswordComponent} from './edit-password.component';
import {MemberConfigComponent} from './member-config.component';
import {LoginConfigComponent} from './member-login.component';
import {RegisterConfigComponent} from './member-register.component';
import {EditorModule} from '@tinymce/tinymce-angular';
import {MemberConfigService} from './service/member-config-service';

@NgModule({
  declarations: [MemberComponent, MemberGroupComponent, MemberGroupAddComponent,
    MemberGroupDetailComponent, MemberAddComponent, MemberDetailComponent, MemberEditPasswordComponent,
    MemberConfigComponent, LoginConfigComponent, RegisterConfigComponent],
  imports: [
    ThemeModule,
    CommonModule,
    MemberRoutingModule,
    EditorModule,
    NbDialogModule.forChild(),
  ],
  entryComponents: [MemberGroupAddComponent, MemberGroupDetailComponent, MemberEditPasswordComponent],
  providers: [MemberService, MemberGroupService, MemberConfigService],
})
export class MemberModule {
}
