import {Injectable} from '@angular/core';
import {HttpUtil} from '../../../core/utils/http';
import {AppApi} from '../../../core/app-api';
import {HttpParams} from '@angular/common/http';
import {IBaseService} from '../../../core/service/ibase.service';

@Injectable()
export class AdSpaceService implements IBaseService {

  constructor(private httpUtil: HttpUtil) {
  }

  getPager(params: Map<string, string>): Promise<any> {
    const httpParams: HttpParams = this.httpUtil.getHttpParams(params);
    const result: Promise<any> = this.httpUtil.get(AppApi.AD.space_list, httpParams).then(response => {
      return Promise.resolve(response);
    });
    return result;
  }

  delData(ids: Array<number>): Promise<any> {
    return this.httpUtil.post(AppApi.AD.space_del, ids).then(response => {
      return Promise.resolve(response);
    });
  }

  editData(data: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.AD.space_edit, data).then(response => {
      return Promise.resolve(response);
    });
  }

  saveData(data: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.AD.space_add, data).then(response => {
      return Promise.resolve(response);
    });
  }

  getData(id: any): Promise<any> {
    const url = AppApi.AD.space_detail.replace('{:spaceId}', id);
    return this.httpUtil.get(url).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 获得所有广告位
   */
  getAllSpaces(): Promise<any> {
    return this.httpUtil.get(AppApi.AD.space_all).then(response => {
      return Promise.resolve(response);
    });
  }

  sort(upItemId, downItemId): Promise<boolean> {
    return undefined;
  }
}
