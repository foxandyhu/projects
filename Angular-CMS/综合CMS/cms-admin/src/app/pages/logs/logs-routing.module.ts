import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LogsComponent} from './logs.component';
import {FileComponent} from './file.component';

const routes: Routes = [
  {path: 'sys', component: LogsComponent},
  {path: 'file', component: FileComponent},
  {path: '', redirectTo: 'sys', pathMatch: 'full'},
  {path: '**', redirectTo: 'sys'},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class LogsRoutingModule {
}
