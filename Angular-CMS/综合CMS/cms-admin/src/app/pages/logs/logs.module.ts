import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {LogsRoutingModule} from './logs-routing.module';
import {LogsComponent} from './logs.component';
import {FileComponent} from './file.component';
import {ThemeModule} from '../../@theme/theme.module';
import {SysLogsService} from './service/sys-logs.service';

@NgModule({
  declarations: [LogsComponent, FileComponent],
  imports: [
    CommonModule,
    ThemeModule,
    LogsRoutingModule,
  ],
  providers: [SysLogsService],
})
export class LogsModule {
}
