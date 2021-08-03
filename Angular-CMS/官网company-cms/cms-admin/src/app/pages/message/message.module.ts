import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {MessageRoutingModule} from './message-routing.module';
import {ThemeModule} from '../../@theme/theme.module';
import {GuestBookComponent} from './guestbook/guest-book.component';
import {EditorModule} from '@tinymce/tinymce-angular';
import {DictionaryService} from '../words/service/dictionary-service';
import {GuestBookService} from './service/guestbook-service';

@NgModule({
  declarations: [GuestBookComponent],
  imports: [
    CommonModule,
    ThemeModule,
    EditorModule,
    MessageRoutingModule,
  ],
  providers: [DictionaryService, GuestBookService],
})
export class MessageModule {
}
