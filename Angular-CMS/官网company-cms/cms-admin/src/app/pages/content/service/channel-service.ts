import {Injectable} from '@angular/core';
import {IBaseService} from '../../../core/service/ibase.service';
import {HttpUtil} from '../../../core/utils/http';
import {AppApi} from '../../../core/app-api';
import {HttpParams} from '@angular/common/http';

/**
 * 栏目Service
 */
@Injectable()
export class ChannelService implements IBaseService {

  constructor(private httpUtil: HttpUtil) {
  }

  delData(ids: Array<number>): Promise<any> {
    return this.httpUtil.post(AppApi.CONTENT.channel_del, ids).then(response => {
      return Promise.resolve(response);
    });
  }


  getPager(params: Map<string, string>): Promise<any> {
    const httpParams: HttpParams = this.httpUtil.getHttpParams(params);
    return this.httpUtil.get(AppApi.CONTENT.channel_list, httpParams).then(response => {
      return Promise.resolve(response);
    });
  }


  saveData(menu: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.CONTENT.channel_add, menu).then(() => {
      return Promise.resolve(true);
    });
  }


  editData(menu: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.CONTENT.channel_edit, menu).then(() => {
      return Promise.resolve(true);
    });
  }

  getData(id: any): Promise<any> {
    const url = AppApi.CONTENT.channel_detail.replace('{:channelId}', id);
    return this.httpUtil.get(url).then(response => {
      return Promise.resolve(response);
    });
  }


  /**
   * 栏目项排序
   * @param upItemId
   * @param downItemId
   */
  sort(upItemId: string, downItemId: string): Promise<boolean> {
    const url = AppApi.CONTENT.channel_sort.replace('{:upItemId}', upItemId).replace('{:downItemId}', downItemId);
    return this.httpUtil.get(url).then(() => {
      return Promise.resolve(true);
    });
  }

  /**
   * 获得所有的栏目信息
   */
  getAllChannels(): Promise<any> {
    return this.httpUtil.get(AppApi.CONTENT.channel_list).then(response => {
      return Promise.resolve(response);
    });
  }
}
