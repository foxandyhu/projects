import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AppApi} from '../app-api';
import {ContextUtil} from '../utils/context';

/**
 * 为每个HTTP头部信息添加自定义头信息
 */
@Injectable({providedIn: 'root'})
export class HeaderInterceptor implements HttpInterceptor {


  /**
   * 为HTTP头赋值
   * @param req
   * @param next
   */
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    req = this.setApiUrl(req);
    req = this.setHeader(req);
    return next.handle(req);
  }

  /**
   * 设置后台接口完整地址
   * @param req
   */
  private setApiUrl(req: HttpRequest<any>): HttpRequest<any> {
    let url = req.url;
    if (url.startsWith(AppApi.API_FLAG)) {  // 后台接口
      url = AppApi.ROOT_URI.concat(url);
      req = req.clone({url: url});
    }
    return req;
  }

  /**
   * 设置头信息
   * @param req
   */
  private setHeader(req: HttpRequest<any>): HttpRequest<any> {
    req = req.clone({headers: req.headers.set('X-Requested-With', 'XMLHttpRequest')});
    return req;
  }
}
