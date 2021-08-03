import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {VoteComponent} from './vote.component';
import {VoteAddComponent} from './vote-add.component';
import {VoteDetailComponent} from './vote-detail.component';

const routes: Routes = [
  {path: 'list', component: VoteComponent},
  {path: 'add', component: VoteAddComponent},
  {path: ':voteId', component: VoteDetailComponent},
  {path: '', redirectTo: 'list', pathMatch: 'full'},
  {path: '**', redirectTo: 'list'},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class VoteRoutingModule {
}
