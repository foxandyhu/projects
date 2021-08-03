import {IBaseService} from '../../../core/service/ibase.service';
import {Injectable} from '@angular/core';
import {HttpUtil} from '../../../core/utils/http';
import {AppApi} from '../../../core/app-api';
import {HttpParams} from '@angular/common/http';

/**
 * 会员组Service
 */
@Injectable()
export class MemberGroupService implements IBaseService {

  constructor(private httpUtil: HttpUtil) {

  }

  delData(ids: Array<number>): Promise<any> {
    return this.httpUtil.post(AppApi.MEMBER.group_del, ids).then(response => {
      return Promise.resolve(response);
    });
  }

  editData(data: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.MEMBER.group_edit, data).then(response => {
      return Promise.resolve(true);
    });
  }

  getData(id: any): Promise<any> {
    const url = AppApi.MEMBER.group_detail.replace('{:groupId}', id);
    return this.httpUtil.get(url).then(response => {
      return Promise.resolve(response);
    });
  }

  getPager(params: Map<string, string>): Promise<any> {
    const httpParams: HttpParams = this.httpUtil.getHttpParams(params);
    return this.httpUtil.get(AppApi.MEMBER.group_list, httpParams).then(response => {
      return Promise.resolve(response);
    });
  }

  saveData(data: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.MEMBER.group_add, data).then(() => {
      return Promise.resolve(true);
    });
  }

  /**
   * 获得所有的会员组信息
   */
  getAllGroup(): Promise<any> {
    return this.httpUtil.get(AppApi.MEMBER.group_all).then(response => {
      return Promise.resolve(response);
    });
  }

  sort(upItemId, downItemId): Promise<boolean> {
    return undefined;
  }
}
