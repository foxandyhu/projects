import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../../core/service/base-component';
import {LetterService} from '../service/letter-service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'ngx-message-letter-detail',
  templateUrl: './letter-detail.component.html',
  styleUrls: ['./letter-detail.component.scss'],
})
export class LetterDetailComponent extends BaseComponent implements OnInit {

  constructor(private letterService: LetterService, protected injector: Injector,
              private route: ActivatedRoute) {
    super(letterService, injector);
  }

  letter: any; //  站内信对象

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const letterId = params.get('letterId');
      this.letterService.getData(letterId).then(result => {
        this.letter = result;
      });
    });
  }

}
