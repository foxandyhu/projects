import {Injectable} from '@angular/core';
import {LoadingBarComponent} from './loading-bar.component';

@Injectable({providedIn: 'root'})
export class LoadingBarService {
  public static loading: LoadingBarComponent;

  constructor() {
  }

  private on: boolean = true;

  open(): void {
    if (this.on) {
      LoadingBarService.loading.open();
    }
  }

  close(): void {
    if (this.on) {
      LoadingBarService.loading.close();
    }
  }

  /**
   * 模拟卸载服务
   */
  shutdown(): void {
    LoadingBarService.loading.close();
    this.on = false;
  }


  /**
   * 开启
   */
  start(): void {
    this.on = true;
  }
}
