import {Injectable} from '@angular/core';
import {NbSidebarService} from '@nebular/theme';

@Injectable({providedIn: 'root'})
export class SidebarUtil {

  constructor(private sidebarService: NbSidebarService) {
  }

  toggleSettings() {
    this.sidebarService.toggle(false, 'settings-sidebar');
  }

  expandSettings() {
    this.sidebarService.expand('settings-sidebar');
  }

  collapseSettings() {
    this.sidebarService.collapse('settings-sidebar');
  }
}
