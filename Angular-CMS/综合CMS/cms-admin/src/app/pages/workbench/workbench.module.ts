import {NgModule} from '@angular/core';
import {NgxEchartsModule} from 'ngx-echarts';

import {ThemeModule} from '../../@theme/theme.module';
import {WorkbenchComponent} from './workbench.component';
import {ContentsComponent} from './contents.component';

import {VisitorsFlowComponent} from './visitors-flow.component';

import {WorkbenchRoutingModule} from './workbench-routing.module';
import {AnalysisService} from '../analysis/service/analysis-service';

@NgModule({
  imports: [
    ThemeModule,
    NgxEchartsModule,
    WorkbenchRoutingModule,
  ],
  declarations: [
    WorkbenchComponent,
    ContentsComponent,
    VisitorsFlowComponent,
  ],
  providers: [
    AnalysisService,
  ],
})
export class WorkbenchModule {
}
