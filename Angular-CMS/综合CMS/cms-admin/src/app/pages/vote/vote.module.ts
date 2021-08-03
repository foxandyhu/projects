import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {VoteRoutingModule} from './vote-routing.module';
import {VoteComponent} from './vote.component';
import {ThemeModule} from '../../@theme/theme.module';
import {VoteService} from './service/vote-service';
import {VoteAddComponent} from './vote-add.component';
import {VoteItemComponent} from './vote-item.component';
import {NbDialogModule} from '@nebular/theme';
import {VoteDetailComponent} from './vote-detail.component';

@NgModule({
  declarations: [VoteComponent, VoteAddComponent, VoteItemComponent, VoteDetailComponent],
  imports: [
    CommonModule,
    ThemeModule,
    VoteRoutingModule,
    NbDialogModule.forChild(),
  ],
  providers: [VoteService],
  entryComponents: [VoteItemComponent],
})
export class VoteModule {
}
