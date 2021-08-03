import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {
  HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor,
  HttpRequest, HttpResponse,
} from '@angular/common/http';
import {Observable, of, throwError} from 'rxjs';
import {tap} from 'rxjs/operators';
import {ModalUtil} from '../../@theme/components';
import {LoadingBarService} from '../../@theme/components/loading-bar/loading-bar.service';

/**
 * HTTP请求拦截器用于拦截接口状态响应
 */
@Injectable({providedIn: 'root'})
export class RequestInterceptor implements HttpInterceptor {

  private LOGIN_ROUTER: string = 'login';
  private NO_RIGHT: string = '/pages/system/noright';

  constructor(private router: Router, private modalUtil: ModalUtil, private loadingBarService: LoadingBarService) {
  }

  /**
   * HTTP 接口拦截校验是否登录
   * @param req
   * @param next
   */
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(tap(() => {
      this.loadingBarService.open();
    }, (err) => {
      this.loadingBarService.close();
      this.handleError(err);
    }, () => {
      this.loadingBarService.close();
    }));
  }

  private handleError(event: HttpResponse<any> | HttpErrorResponse): Observable<any> {
    switch (event.status) {
      case 401:
        this.modalUtil.alert('', '未登录');
        this.router.navigate([this.LOGIN_ROUTER]);
        return of(event);
      case 403:
        this.router.navigate([this.NO_RIGHT]);
        return of(event);
      case 0:
        this.modalUtil.alert('', '请求被取消,请刷新浏览器重试!');
        return of(event);
      default:
        this.modalUtil.alert('', event['error']['message']);
    }
    return throwError(event);
  }
}
