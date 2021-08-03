import {Component, Injector, OnInit} from '@angular/core';
import {SysLogsService} from './service/sys-logs.service';
import {BaseComponent} from '../../core/service/base-component';

@Component({
  selector: 'ngx-logs',
  templateUrl: './logs.component.html',
})
export class LogsComponent extends BaseComponent implements OnInit {

  constructor(private sysLogsService: SysLogsService, protected injector: Injector) {
    super(sysLogsService, injector);
  }

  category: string = '';
  types: any = [{id: 1, name: '操作日志'}, {id: 2, name: '登录日志'}, {id: 3, name: '登出日志'}]; // 日志类型

  ngOnInit() {
    this.getPager(1);
  }

  /**
   * 根据不同日志类型查询
   */
  changeCategory() {
    this.setQueryParams('category', this.category);
    this.getPager(1);
  }
}
