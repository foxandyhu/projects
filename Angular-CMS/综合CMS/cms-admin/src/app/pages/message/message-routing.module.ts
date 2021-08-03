import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LetterComponent} from './letter/letter.component';
import {CommentComponent} from './comment/comment.component';
import {GuestBookComponent} from './guestbook/guest-book.component';
import {LetterAddComponent} from './letter/letter-add.component';
import {LetterDetailComponent} from './letter/letter-detail.component';

const routes: Routes = [
  {path: 'letter', component: LetterComponent},
  {path: 'letter/add', component: LetterAddComponent},
  {path: 'letter/:letterId', component: LetterDetailComponent},
  {path: 'comment', component: CommentComponent},
  {path: 'guestbook', component: GuestBookComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MessageRoutingModule {
}
