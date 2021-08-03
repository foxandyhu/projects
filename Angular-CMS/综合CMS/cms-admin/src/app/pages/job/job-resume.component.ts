import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../core/service/base-component';
import {JobService} from './service/job-service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'ngx-job-resume',
  templateUrl: './job-resume.component.html',
  styleUrls: ['./job-resume.component.scss'],
})
export class JobResumeComponent extends BaseComponent implements OnInit {

  constructor(private jobService: JobService, protected injector: Injector, private route: ActivatedRoute) {
    super(jobService, injector);
  }

  resume: any; //  简历

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const memberId = params.get('memberId');
      this.jobService.getMemberResume(memberId).then(result => {
        this.resume = result;
      });
    });
  }

}
