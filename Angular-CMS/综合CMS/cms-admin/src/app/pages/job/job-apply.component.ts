import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../core/service/base-component';
import {JobService} from './service/job-service';

@Component({
  selector: 'ngx-job-apply',
  templateUrl: './job-apply.component.html',
})
export class JobApplyComponent extends BaseComponent implements OnInit {

  constructor(private jobService: JobService, protected injector: Injector) {
    super(jobService, injector);
  }

  title: string; //  搜索标题

  ngOnInit() {
    this.search();
  }

  /**
   * 搜索
   */
  search() {
    this.setQueryParams('title', this.title);
    this.getPager(1);
  }
}
