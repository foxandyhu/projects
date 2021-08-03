import {Injectable} from '@angular/core';
import {HttpUtil} from '../../../core/utils/http';
import {AppApi} from '../../../core/app-api';
import {HttpParams} from '@angular/common/http';
import {IBaseService} from '../../../core/service/ibase.service';

/**
 * 数据字典Service
 */
@Injectable()
export class DictionaryService implements IBaseService {

  constructor(private httpUtil: HttpUtil) {

  }

  delData(ids: Array<number>): Promise<any> {
    return this.httpUtil.post(AppApi.WORDS.dictionary_del, ids).then(response => {
      return Promise.resolve(response);
    });
  }

  editData(data: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.WORDS.dictionary_edit, data).then(response => {
      return Promise.resolve(true);
    });
  }

  getData(id: any): Promise<any> {
    const url = AppApi.WORDS.dictionary_detail.replace('{:dictionaryId}', id);
    return this.httpUtil.get(url).then(response => {
      return Promise.resolve(response);
    });
  }

  getPager(params: Map<string, string>): Promise<any> {
    const httpParams: HttpParams = this.httpUtil.getHttpParams(params);
    return this.httpUtil.get(AppApi.WORDS.dictionary_list, httpParams).then(response => {
      return Promise.resolve(response);
    });
  }

  saveData(data: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.WORDS.dictionary_add, data).then(response => {
      return Promise.resolve(true);
    });
  }

  /**
   * 获得数据字典类型集合
   */
  getDictionaryTypes(): Promise<any> {
    return this.httpUtil.get(AppApi.WORDS.dictionary_types).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 根据类型获得数据字典
   * @param type
   */
  getDictionaryByType(type: string): Promise<any> {
    const url = AppApi.WORDS.dictionary_type_by.replace('{:type}', type);
    return this.httpUtil.get(url).then(response => {
      return Promise.resolve(response);
    });
  }

  editEnabled(dictionaryId, enabled): Promise<boolean> {
    let httpParams: HttpParams = new HttpParams();
    httpParams = httpParams.set('dictionaryId', dictionaryId).set('enabled', enabled);
    return this.httpUtil.get(AppApi.WORDS.dictionary_enabled, httpParams).then(() => {
      return Promise.resolve(true);
    });
  }

  sort(upItemId, downItemId): Promise<boolean> {
    return undefined;
  }
}
