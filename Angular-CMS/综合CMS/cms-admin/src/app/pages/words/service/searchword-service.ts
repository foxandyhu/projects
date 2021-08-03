import {IBaseService} from '../../../core/service/ibase.service';
import {Injectable} from '@angular/core';
import {HttpUtil} from '../../../core/utils/http';
import {AppApi} from '../../../core/app-api';
import {HttpParams} from '@angular/common/http';

/**
 * 搜索词Service
 */
@Injectable()
export class SearchWordService implements IBaseService {

  constructor(private httpUtil: HttpUtil) {

  }

  delData(ids: Array<number>): Promise<any> {
    return this.httpUtil.post(AppApi.WORDS.searchword_del, ids).then(response => {
      return Promise.resolve(response);
    });
  }

  editData(data: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.WORDS.searchword_edit, data).then(() => {
      return Promise.resolve(true);
    });
  }

  getData(id: any): Promise<any> {
    return this.httpUtil.get(AppApi.WORDS.searchword_detail.replace('{:searchwordId}', id)).then(response => {
      return Promise.resolve(response);
    });
  }

  getPager(params: Map<string, string>): Promise<any> {
    const httpParams: HttpParams = this.httpUtil.getHttpParams(params);
    return this.httpUtil.get(AppApi.WORDS.searchword_list, httpParams).then(response => {
      return Promise.resolve(response);
    });
  }

  saveData(data: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.WORDS.searchword_add, data).then(() => {
      return Promise.resolve(true);
    });
  }

  /**
   * 改变推荐状态
   * @param id
   */
  changeRecommend(id: string, status: string): Promise<boolean> {
    const url = AppApi.WORDS.searchword_recommend.replace('{:searchId}', id).replace('{:type}', status);
    return this.httpUtil.get(url).then(result => {
      return Promise.resolve(true);
    });
  }

  sort(upItemId, downItemId): Promise<boolean> {
    return undefined;
  }
}
