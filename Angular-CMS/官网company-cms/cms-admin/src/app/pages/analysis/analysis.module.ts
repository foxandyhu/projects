import {NgModule} from '@angular/core';
import {ThemeModule} from '../../@theme/theme.module';
import {NgxEchartsModule} from 'ngx-echarts';
import {NbMomentDateModule} from '@nebular/moment';

import {AnalysisRoutingModule} from './analysis-routing.module';
import {FlowAnalysisComponent} from './flow/flow-analysis.component';
import {FlowTableComponent} from './flow/table.component';

import {SourceAnalysisComponent} from './source/source-analysis.component';
import {SourcePieComponent} from './source/source-pie.component';
import {SourceLineComponent} from './source/source-line.component';
import {SourceTableComponent} from './source/source-table.component';
import {EngineAnalysisComponent} from './engine/engine-analysis.component';
import {EnginePieComponent} from './engine/engine-pie.component';
import {EngineLineComponent} from './engine/engine-line.component';
import {EngineTableComponent} from './engine/engine-table.component';
import {BrowserAnalysisComponent} from './browser/browser-analysis.component';
import {BrowserTableComponent} from './browser/browser-table.component';
import {BrowserPieComponent} from './browser/browser-pie.component';
import {BrowserLineComponent} from './browser/browser-line.component';
import {SiteAnalysisComponent} from './site/site-analysis.component';
import {SiteTableComponent} from './site/site-table.component';
import {SitePieComponent} from './site/site-pie.component';
import {SiteLineComponent} from './site/site-line.component';
import {AreaAnalysisComponent} from './area/area-analysis.component';
import {AreaTableComponent} from './area/area-table.component';
import {AreaMapComponent} from './area/area-map.component';
import {AnalysisService} from './service/analysis-service';

@NgModule({
  declarations: [
    FlowAnalysisComponent, FlowTableComponent,
    SourceAnalysisComponent, SourcePieComponent, SourceLineComponent, SourceTableComponent,
    EngineAnalysisComponent, EnginePieComponent, EngineLineComponent, EngineTableComponent,
    BrowserAnalysisComponent, BrowserPieComponent, BrowserLineComponent, BrowserTableComponent,
    SiteAnalysisComponent, SitePieComponent, SiteLineComponent, SiteTableComponent,
    AreaAnalysisComponent, AreaTableComponent, AreaMapComponent,
  ],
  imports: [
    ThemeModule,
    NgxEchartsModule,
    NbMomentDateModule,
    AnalysisRoutingModule],
  providers: [AnalysisService],
})
export class AnalysisModule {
}
