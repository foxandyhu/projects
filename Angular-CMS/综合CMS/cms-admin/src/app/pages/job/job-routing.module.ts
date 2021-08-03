import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {JobApplyComponent} from './job-apply.component';
import {JobResumeComponent} from './job-resume.component';

const routes: Routes = [
  {path: 'apply', component: JobApplyComponent},
  {path: 'resume/:memberId', component: JobResumeComponent},
  {path: '', redirectTo: 'apply', pathMatch: 'full'},
  {path: '**', redirectTo: 'apply'},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class JobRoutingModule {
}
