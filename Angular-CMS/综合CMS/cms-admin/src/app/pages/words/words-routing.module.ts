import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {DictionaryComponent} from './dictionary/dictionary.component';
import {SensitiveComponent} from './sensitive/sensitive.component';
import {SearchComponent} from './search/search.component';
import {ScoreGroupComponent} from './score/score-group.component';
import {ScoreItemComponent} from './score/score-item.component';

const routes: Routes = [
  {path: '', redirectTo: 'dictionary', pathMatch: 'full'},
  {path: 'dictionary', component: DictionaryComponent},
  {path: 'sensitivewords', component: SensitiveComponent},
  {path: 'searchwords', component: SearchComponent},
  {path: 'score/group', component: ScoreGroupComponent},
  {path: 'score/item/group/:groupId', component: ScoreItemComponent},
  {path: '**', redirectTo: 'dictionary'},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class WordsRoutingModule {
}
