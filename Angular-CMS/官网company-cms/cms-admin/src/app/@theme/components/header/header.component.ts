import {Component, Input, OnDestroy, OnInit} from '@angular/core';

import {NbMenuService, NbSidebarService} from '@nebular/theme';
import {LayoutService} from '../../../core/service/layout.service';
import {Subscription} from 'rxjs';
import {Router} from '@angular/router';
import {UserService} from '../../../pages/user/service/users.service';
import {SiteNameEmitService} from '../../../core/service/site-name-emit.service';

@Component({
  selector: 'ngx-header',
  styleUrls: ['./header.component.scss'],
  templateUrl: './header.component.html',
})
export class HeaderComponent implements OnInit, OnDestroy {

  @Input() position = 'normal';

  user: any;
  siteName = '管理系统';
  private menuSubscription: Subscription;

  userMenu = [{title: '修改密码', link: '/pages/user/password', data: '1'}, {title: '退出系统', data: '2'}];

  constructor(private sidebarService: NbSidebarService,
              private menuService: NbMenuService,
              private userService: UserService,
              private layoutService: LayoutService,
              private router: Router, private siteNameEmitService: SiteNameEmitService) {
  }

  ngOnInit() {
    this.userService.getCurrentUser().subscribe((users: any) => this.user = users);
    this.bindMenu();
    this.siteNameEmitService.eventEmit.subscribe(value => {
      this.siteName = value;
    });
  }

  ngOnDestroy(): void {
    this.menuSubscription.unsubscribe();
  }

  /**
   * 左侧菜单收缩
   */
  toggleSidebar(): boolean {
    this.sidebarService.toggle(true, 'menu-sidebar');
    this.layoutService.changeLayoutSize();
    return false;
  }

  /**
   * 登出事件绑定
   */
  private bindMenu() {
    const observable = this.menuService.onItemClick();
    this.menuSubscription = observable.subscribe(bag => {
      if (bag['item']['data'] === '2') {
        const result: Promise<boolean> = this.userService.logout();
        result.then(flag => {
          if (flag) {
            this.router.navigate(['login']);
          }
        });
      }
    });
  }
}
