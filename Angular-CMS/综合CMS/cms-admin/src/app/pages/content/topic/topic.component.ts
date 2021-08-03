import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../../core/service/base-component';
import {SpecialTopicService} from '../service/topic-service';

@Component({
  selector: 'ngx-content-topic',
  templateUrl: './topic.component.html',
})
export class SpecialTopicComponent extends BaseComponent implements OnInit {

  constructor(private topicService: SpecialTopicService, protected injector: Injector) {
    super(topicService, injector);
  }

  ngOnInit() {
    this.getPager(1);
  }
}
