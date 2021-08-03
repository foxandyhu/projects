import {Injectable} from '@angular/core';
import {HttpUtil} from '../../../core/utils/http';
import {AppApi} from '../../../core/app-api';
import {HttpParams} from '@angular/common/http';
import {IBaseService} from '../../../core/service/ibase.service';

/**
 * 流量统计Service
 */
@Injectable()
export class AnalysisService implements IBaseService {

  constructor(private httpUtil: HttpUtil) {
  }

  /**
   * 根据日期类型和时间统计
   * @param time
   * @param begin
   * @param end
   */
  statisticFlow(time: string, begin?: any, end?: any): Promise<any> {
    let httpParam: HttpParams = new HttpParams();
    httpParam = httpParam.set('time', time).set('begin', begin).set('end', end);
    return this.httpUtil.get(AppApi.STATISTIC.flow, httpParam).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 访问来源
   * @param type
   * @param begin
   * @param end
   */
  statisticSource(time: string, begin?: any, end?: any): Promise<any> {
    let httpParam: HttpParams = new HttpParams();
    httpParam = httpParam.set('time', time).set('begin', begin).set('end', end);
    return this.httpUtil.get(AppApi.STATISTIC.source, httpParam).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 搜索引擎
   * @param type
   * @param begin
   * @param end
   */
  statisticEngine(time: string, begin?: any, end?: any): Promise<any> {
    let httpParam: HttpParams = new HttpParams();
    httpParam = httpParam.set('time', time).set('begin', begin).set('end', end);
    return this.httpUtil.get(AppApi.STATISTIC.engine, httpParam).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 来访站点
   * @param time
   * @param begin
   * @param end
   */
  statisticSite(time: string, begin?: any, end?: any): Promise<any> {
    let httpParam: HttpParams = new HttpParams();
    httpParam = httpParam.set('time', time).set('begin', begin).set('end', end);
    return this.httpUtil.get(AppApi.STATISTIC.site, httpParam).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 浏览器
   * @param time
   * @param begin
   * @param end
   */
  statisticBrowser(time: string, begin?: any, end?: any): Promise<any> {
    let httpParam: HttpParams = new HttpParams();
    httpParam = httpParam.set('time', time).set('begin', begin).set('end', end);
    return this.httpUtil.get(AppApi.STATISTIC.browser, httpParam).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 浏览器
   * @param time
   * @param begin
   * @param end
   */
  statisticArea(time: string, begin?: any, end?: any): Promise<any> {
    let httpParam: HttpParams = new HttpParams();
    httpParam = httpParam.set('time', time).set('begin', begin).set('end', end);
    return this.httpUtil.get(AppApi.STATISTIC.area, httpParam).then(response => {
      return Promise.resolve(response);
    });
  }

  sort(upItemId, downItemId): Promise<boolean> {
    return undefined;
  }

  delData(ids: Array<number>): Promise<any> {
    return undefined;
  }

  editData(data: any): Promise<boolean> {
    return undefined;
  }

  getData(id: any): Promise<any> {
    return undefined;
  }

  getPager(params: Map<string, string>): Promise<any> {
    return undefined;
  }

  saveData(data: any): Promise<boolean> {
    return undefined;
  }

  /**
   * 获得内容统计
   */
  getStatisticContent(): Promise<any> {
    return this.httpUtil.get(AppApi.STATISTIC.content).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 统计最新的数据
   */
  getStatisticLatest(): Promise<any> {
    return this.httpUtil.get(AppApi.STATISTIC.latest).then(response => {
      return Promise.resolve(response);
    });
  }
}
