import {Injectable} from '@angular/core';
import {NavigationStart, Router} from '@angular/router';
import {LocalStorageUtil} from '../../core/utils/local-storage';

/**
 * 页面切换拦截器,防止直接访问某个未授权的页面
 */
@Injectable({providedIn: 'root'})
export class PageInterceptor {

  /**
   * 登录后返回的标识字符串存储于本地浏览器,用来校验是否登录
   */
  private USER_KEY: string = 'user';
  private LOGIN_ROUTER: string = 'login';

  constructor(private router: Router) {
    this.init();
  }

  private init() {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationStart) {
        this.checkLocalAuth(event.url);
      }
    });
  }

  /**
   * 校验本地是否存在登录标识防止直接访问页面
   * @param url
   */
  private checkLocalAuth(url: string): boolean {
    if ('/login' !== url) {
      const key = LocalStorageUtil.get(this.USER_KEY);
      if (!key) {
        this.router.navigate([this.LOGIN_ROUTER]);
        return false;
      }
    }
    return true;
  }
}
