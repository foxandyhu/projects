import {IBaseService} from '../../../core/service/ibase.service';
import {Injectable} from '@angular/core';
import {HttpUtil} from '../../../core/utils/http';
import {AppApi} from '../../../core/app-api';
import {HttpParams} from '@angular/common/http';

/**
 * 敏感词Service
 */
@Injectable()
export class SensitiveWordService implements IBaseService {

  constructor(private httpUtil: HttpUtil) {

  }

  delData(ids: Array<number>): Promise<any> {
    return this.httpUtil.post(AppApi.WORDS.sensitive_del, ids).then(response => {
      return Promise.resolve(response);
    });
  }

  editData(data: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.WORDS.sensitive_edit, data).then(() => {
      return Promise.resolve(true);
    });
  }

  getData(id: any): Promise<any> {
    const url = AppApi.WORDS.sensitive_detail.replace('{:sensitiveId}', id);
    return this.httpUtil.get(url).then(response => {
      return Promise.resolve(response);
    });
  }

  getPager(params: Map<string, string>): Promise<any> {
    const httpParams: HttpParams = this.httpUtil.getHttpParams(params);
    return this.httpUtil.get(AppApi.WORDS.sensitive_list, httpParams).then(response => {
      return Promise.resolve(response);
    });
  }

  saveData(data: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.WORDS.sensitive_add, data).then(response => {
      return Promise.resolve(true);
    });
  }

  sort(upItemId, downItemId): Promise<boolean> {
    return undefined;
  }
}
