import {Component, Injector, OnInit} from '@angular/core';
import {AdService} from './service/ad.service';
import {BaseComponent} from '../../core/service/base-component';

@Component({
  selector: 'ngx-ad',
  templateUrl: './ad.component.html',
})
export class AdComponent extends BaseComponent implements OnInit {

  constructor(private adService: AdService, protected injector: Injector) {
    super(adService, injector);
  }

  ngOnInit() {
    this.getPager(1);
  }

}
