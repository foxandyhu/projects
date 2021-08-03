import {Injectable} from '@angular/core';
import {HttpUtil} from '../../../core/utils/http';
import {AppApi} from '../../../core/app-api';
import {IBaseService} from '../../../core/service/ibase.service';
import {HttpParams} from '@angular/common/http';

/**
 * 短信服务商业务方法
 */
@Injectable()
export class SmsProviderService implements IBaseService {

  constructor(private httpUtil: HttpUtil) {
  }

  delData(ids: Array<number>): Promise<any> {
    return this.httpUtil.post(AppApi.SMS.provider_del, ids).then(response => {
      return Promise.resolve(response);
    });
  }


  getPager(params: Map<string, string>): Promise<any> {
    const httpParams: HttpParams = this.httpUtil.getHttpParams(params);
    const result: Promise<any> = this.httpUtil.get(AppApi.SMS.provider_list, httpParams).then(response => {
      return Promise.resolve(response);
    });
    return result;
  }


  saveData(menu: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.SMS.provider_add, menu).then(() => {
      return Promise.resolve(true);
    });
  }


  editData(menu: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.SMS.provider_edit, menu).then(() => {
      return Promise.resolve(true);
    });
  }

  getData(id: any): Promise<any> {
    const url = AppApi.SMS.provider_detail.replace('{:providerId}', id);
    return this.httpUtil.get(url).then(response => {
      return Promise.resolve(response);
    });
  }


  /**
   * 查询所有的短信服务商集合
   */
  getAllProviders(): Promise<any> {
    return this.httpUtil.get(AppApi.SMS.provider_all).then(response => {
      return Promise.resolve(response);
    });
  }

  sort(upItemId, downItemId): Promise<boolean> {
    return undefined;
  }
}
