import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';

/**
 * HTTP工具类
 */
@Injectable({providedIn: 'root'})
export class HttpUtil {

  constructor(private httpClient: HttpClient) {
  }

  /**
   * POST请求 返回Promise对象
   * @param url
   * @param body
   */
  public post(url: string, body: any | null): Promise<any> {
    return this.httpClient.post(url, body, {withCredentials: true}).toPromise().then(response => {
      if (response) {
        return Promise.resolve(response['message']);
      }
      return Promise.resolve();
    });
  }

  /**
   * GET请求 返回Promise对象
   * @param url
   */
  public get(url: string, params?: HttpParams): Promise<any> {
    return this.httpClient.get(url, {params: params, withCredentials: true}).toPromise().then(response => {
      if (response) {
        return Promise.resolve(response['message']);
      }
      return Promise.resolve();
    });
  }

  /**
   *参数转化为HttpParams
   * @param params
   */
  public getHttpParams(params: Map<string, string>): HttpParams {
    let httpParams: HttpParams = new HttpParams();
    if (params) {
      params.forEach((value, key, map) => {
        httpParams = httpParams.set(key, value);
      });
    }
    return httpParams;
  }
}
