import {Component, OnInit} from '@angular/core';
import {LoadingBarService} from './loading-bar.service';

@Component({
  selector: 'ngx-loading-bar',
  templateUrl: './loading-bar.component.html',
  styleUrls: ['./loading-bar.component.scss'],
})
export class LoadingBarComponent implements OnInit {
  show: boolean = false;

  constructor() {
    LoadingBarService.loading = this;
  }

  ngOnInit() {
  }

  open() {
    this.show = true;
  }

  close() {
    this.show = false;
  }
}
