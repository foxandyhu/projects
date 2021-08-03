import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {FriendLinkRoutingModule} from './friend-link-routing.module';
import {FriendLinkComponent} from './friend-link.component';
import {ThemeModule} from '../../@theme/theme.module';
import {FriendLinkService} from './service/friendlink-service';
import {FriendLinkTypeService} from './service/friendlink-type-service';
import {FriendLinkAddComponent} from './friend-link-add.component';
import {FriendLinkDetailComponent} from './friend-link-detail.component';
import {NbDialogModule} from '@nebular/theme';
import {FriendLinkTypeComponent} from './friend-link-type.component';
import {FriendLinkTypeAddComponent} from './friend-link-type-add.component';
import {FriendLinkTypeDetailComponent} from './friend-link-type-detail.component';

@NgModule({
  declarations: [FriendLinkComponent, FriendLinkAddComponent, FriendLinkDetailComponent,
    FriendLinkTypeComponent, FriendLinkTypeAddComponent, FriendLinkTypeDetailComponent],
  imports: [
    CommonModule,
    ThemeModule,
    FriendLinkRoutingModule,
    NbDialogModule.forChild(),
  ],
  entryComponents: [FriendLinkAddComponent, FriendLinkDetailComponent,
    FriendLinkTypeAddComponent, FriendLinkTypeDetailComponent],
  providers: [FriendLinkService, FriendLinkTypeService],
})
export class FriendLinkModule {
}
