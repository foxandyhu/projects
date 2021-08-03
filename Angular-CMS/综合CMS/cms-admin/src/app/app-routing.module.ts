import {ExtraOptions, RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {LoginComponent} from './pages/login/login.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: '', loadChildren: 'app/pages/pages.module#PagesModule'},
  {path: '', redirectTo: '', pathMatch: 'full'},
  {path: '**', redirectTo: ''},
];

const config: ExtraOptions = {
  useHash: true,
};

@NgModule({
  imports: [RouterModule.forRoot(routes, config)],
  exports: [RouterModule],
})
export class AppRoutingModule {
}
