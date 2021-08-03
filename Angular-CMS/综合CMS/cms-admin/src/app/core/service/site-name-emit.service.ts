import {EventEmitter, Injectable, OnInit} from '@angular/core';

/**
 * 系统名称事件
 */
@Injectable({providedIn: 'root'})
export class SiteNameEmitService implements OnInit {
  public eventEmit: any;

  constructor() {
    this.eventEmit = new EventEmitter();
  }

  ngOnInit(): void {
  }
}
