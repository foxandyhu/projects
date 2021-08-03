import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {GuestBookComponent} from './guestbook/guest-book.component';

const routes: Routes = [
  {path: 'guestbook', component: GuestBookComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MessageRoutingModule {
}
