import {Component, Injector, OnInit} from '@angular/core';

import {BaseComponent} from '../core/service/base-component';
import {MenuService} from './system/service/menu-service';

@Component({
  selector: 'ngx-pages',
  styleUrls: ['pages.component.scss'],
  template: `
    <ngx-sample-layout>
      <nb-menu [items]="menu"></nb-menu>
      <router-outlet></router-outlet>
    </ngx-sample-layout>
  `,
})
export class PagesComponent extends BaseComponent implements OnInit {

  constructor(private menuService: MenuService, protected injector: Injector) {
    super(menuService, injector);
  }

  menu = new Array();

  ngOnInit(): void {
    this.menuService.getCurrentUserMenu().then(result => {
      this.menu = result;
    });
  }
}
