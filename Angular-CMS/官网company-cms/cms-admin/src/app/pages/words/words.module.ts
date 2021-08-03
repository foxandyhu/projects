import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DictionaryComponent} from './dictionary/dictionary.component';
import {WordsRoutingModule} from './words-routing.module';
import {ThemeModule} from '../../@theme/theme.module';
import {DictionaryService} from './service/dictionary-service';
import {DictionaryAddComponent} from './dictionary/dictionary-add.component';
import {DictionaryDetailComponent} from './dictionary/dictionary-detail.component';
import {NbDialogModule} from '@nebular/theme';
import {IconsModule} from '../icons/icons.module';

@NgModule({
  declarations: [DictionaryComponent,  DictionaryAddComponent, DictionaryDetailComponent],
  imports: [
    CommonModule,
    ThemeModule,
    WordsRoutingModule, IconsModule,
    NbDialogModule.forChild(),
  ], providers: [DictionaryService],
  entryComponents: [DictionaryAddComponent, DictionaryDetailComponent],
})
export class WordsModule {
}
