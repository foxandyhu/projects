import {Injectable} from '@angular/core';
import {IBaseService} from '../../../core/service/ibase.service';
import {HttpUtil} from '../../../core/utils/http';
import {AppApi} from '../../../core/app-api';
import {HttpParams} from '@angular/common/http';

/**
 * 内容模型项Service
 */
@Injectable()
export class ModelItemService implements IBaseService {

  constructor(private httpUtil: HttpUtil) {
  }

  delData(ids: Array<number>): Promise<any> {
    return this.httpUtil.post(AppApi.CONTENT.model_item_del, ids).then(response => {
      return Promise.resolve(response);
    });
  }


  getPager(params: Map<string, string>): Promise<any> {
    return undefined;
  }


  saveData(menu: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.CONTENT.model_item_add, menu).then(() => {
      return Promise.resolve(true);
    });
  }


  editData(menu: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.CONTENT.model_item_edit, menu).then(() => {
      return Promise.resolve(true);
    });
  }

  getData(id: any): Promise<any> {
    const url = AppApi.CONTENT.model_item_detail.replace('{:modelItemId}', id);
    return this.httpUtil.get(url).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 模型项排序
   * @param upItemId
   * @param downItemId
   */
  sort(upItemId: string, downItemId: string): Promise<boolean> {
    const url = AppApi.CONTENT.model_item_sort.replace('{:upItemId}', upItemId).replace('{:downItemId}', downItemId);
    return this.httpUtil.get(url).then(() => {
      return Promise.resolve(true);
    });
  }

  /**
   * 获得系统默认的模型项
   */
  defaultModelItems(): Promise<any> {
    return this.httpUtil.get(AppApi.CONTENT.model_item_default).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 获得模型项
   * @param modelId
   */
  getModelItems(modelId: string): Promise<any> {
    const url = AppApi.CONTENT.model_item_for_model.replace('{:modelId}', modelId);
    return this.httpUtil.get(url).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 添加系统字段模型
   * @param modelId
   */
  bindDefaultModelItem(modelId: string, ids: any) {
    const url = AppApi.CONTENT.model_item_bind.replace('{:modelId}', modelId);
    return this.httpUtil.post(url, ids).then(response => {
      return Promise.resolve(response);
    });
  }
}
