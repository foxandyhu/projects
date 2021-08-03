import {EventEmitter, Injectable, OnInit} from '@angular/core';

/**
 * 右边侧栏显示隐藏发射器
 */
@Injectable({providedIn: 'root'})
export class SidebarEmitService implements OnInit {
  public eventEmit: any;

  constructor() {
    this.eventEmit = new EventEmitter();
  }

  ngOnInit(): void {
  }
}
