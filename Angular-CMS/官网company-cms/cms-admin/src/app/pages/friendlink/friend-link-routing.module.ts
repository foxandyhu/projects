import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {FriendLinkComponent} from './friend-link.component';
import {FriendLinkTypeComponent} from './friend-link-type.component';

const routes: Routes = [
  {path: 'list', component: FriendLinkComponent},
  {path: 'type/list', component: FriendLinkTypeComponent},
  {path: '', redirectTo: 'list', pathMatch: 'full'},
  {path: '**', redirectTo: 'list'},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class FriendLinkRoutingModule {
}
