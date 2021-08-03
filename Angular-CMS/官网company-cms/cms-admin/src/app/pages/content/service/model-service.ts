import {Injectable} from '@angular/core';
import {IBaseService} from '../../../core/service/ibase.service';
import {HttpUtil} from '../../../core/utils/http';
import {AppApi} from '../../../core/app-api';
import {HttpParams} from '@angular/common/http';

/**
 * 内容模型Service
 */
@Injectable()
export class ModelService implements IBaseService {

  constructor(private httpUtil: HttpUtil) {
  }

  delData(ids: Array<number>): Promise<any> {
    return this.httpUtil.post(AppApi.CONTENT.model_del, ids).then(response => {
      return Promise.resolve(response);
    });
  }


  getPager(params: Map<string, string>): Promise<any> {
    const httpParams: HttpParams = this.httpUtil.getHttpParams(params);
    const result: Promise<any> = this.httpUtil.get(AppApi.CONTENT.model_list, httpParams).then(response => {
      return Promise.resolve(response);
    });
    return result;
  }


  saveData(menu: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.CONTENT.model_add, menu).then(() => {
      return Promise.resolve(true);
    });
  }


  editData(menu: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.CONTENT.model_edit, menu).then(() => {
      return Promise.resolve(true);
    });
  }

  getData(id: any): Promise<any> {
    const url = AppApi.CONTENT.model_detail.replace('{:modelId}', id);
    return this.httpUtil.get(url).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 更新状态
   * @param id
   * @param enabled
   */
  editEnabled(id: string, enabled: string): Promise<boolean> {
    const url = AppApi.CONTENT.model_enabled.replace('{:modelId}', id).replace('{:enabled}', enabled);
    return this.httpUtil.get(url).then(() => {
      return Promise.resolve(true);
    });
  }

  /**
   * 模型排序
   * @param upItemId
   * @param downItemId
   */
  sort(upItemId: string, downItemId: string): Promise<boolean> {
    const url = AppApi.CONTENT.model_sort.replace('{:upItemId}', upItemId).replace('{:downItemId}', downItemId);
    return this.httpUtil.get(url).then(() => {
      return Promise.resolve(true);
    });
  }

  /**
   * 获得所有的模型
   */
  getAllModels(): Promise<any> {
    return this.httpUtil.get(AppApi.CONTENT.model_all).then(response => {
      return Promise.resolve(response);
    });
  }
}
