import {Component, OnDestroy} from '@angular/core';
import {delay, takeWhile, withLatestFrom} from 'rxjs/operators';
import {
  NbMediaBreakpoint,
  NbMediaBreakpointsService,
  NbMenuService,
  NbSidebarService,
  NbThemeService,
} from '@nebular/theme';
import {Router} from '@angular/router';
import {SidebarEmitService} from '../../../core/service/sidebar-emit.service';

@Component({
  selector: 'ngx-sample-layout',
  styleUrls: ['./sample.layout.scss'],
  template: `
    <nb-layout [center]="layout.id === 'center-column'" windowMode>
      <nb-layout-header fixed>
        <ngx-header [position]="sidebar.id === 'start' ? 'normal': 'inverse'"></ngx-header>
      </nb-layout-header>

      <nb-sidebar class="menu-sidebar"
                  tag="menu-sidebar"
                  responsive
                  [end]="sidebar.id === 'end'">
        <nb-sidebar-header *ngIf="currentTheme !== 'corporate'">
          <a href="javascript:void(0)" class="btn btn-hero-success main-btn">
            <i class="ion ion-social-github"></i> <span>系统菜单</span>
          </a>
        </nb-sidebar-header>
        <ng-content select="nb-menu"></ng-content>
      </nb-sidebar>

      <nb-layout-column class="main-content">
        <ng-content select="router-outlet"></ng-content>
        <ngx-loading-bar></ngx-loading-bar>
      </nb-layout-column>

      <nb-layout-footer fixed>
        <ngx-footer></ngx-footer>
      </nb-layout-footer>

      <nb-sidebar class="settings-sidebar"
                  tag="settings-sidebar"
                  state="collapsed"
                  fixed
                  containerFixed
                  [end]="sidebar.id !== 'end'">
        <router-outlet name="sidebar"></router-outlet>
      </nb-sidebar>
    </nb-layout>
  `,
})
export class SampleLayoutComponent implements OnDestroy {

  layout: any = {
    name: 'One Column',
    icon: 'nb-layout-default',
    id: 'one-column',
    selected: true,
  };
  sidebar: any = {
    name: 'Sidebar at layout start',
    icon: 'nb-layout-sidebar-left',
    id: 'start',
    selected: true,
  };

  private alive = true;

  currentTheme: string;
  hidden = true;

  constructor(protected menuService: NbMenuService,
              protected themeService: NbThemeService,
              protected bpService: NbMediaBreakpointsService,
              protected sidebarService: NbSidebarService,
              private sidebarEmitService: SidebarEmitService,
              private router: Router) {


    const isBp = this.bpService.getByName('is');
    this.menuService.onItemSelect()
      .pipe(
        takeWhile(() => this.alive),
        withLatestFrom(this.themeService.onMediaQueryChange()),
        delay(20),
      )
      .subscribe(([item, [bpFrom, bpTo]]: [any, [NbMediaBreakpoint, NbMediaBreakpoint]]) => {

        if (bpTo.width <= isBp.width) {
          this.sidebarService.collapse('menu-sidebar');
        }
      });

    this.themeService.getJsTheme()
      .pipe(takeWhile(() => this.alive))
      .subscribe(theme => {
        this.currentTheme = theme.name;
      });

    this.sidebarEmitService.eventEmit.subscribe(body => {
      this.sidebarService.expand('settings-sidebar');
      this.router.navigate(['pages', {outlets: {sidebar: body.path}}], body.extras);
    });
  }

  ngOnDestroy() {
    this.alive = false;
  }
}
