import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DictionaryComponent} from './dictionary/dictionary.component';

const routes: Routes = [
  {path: '', redirectTo: 'dictionary', pathMatch: 'full'},
  {path: 'dictionary', component: DictionaryComponent},
  {path: '**', redirectTo: 'dictionary'},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class WordsRoutingModule {
}
