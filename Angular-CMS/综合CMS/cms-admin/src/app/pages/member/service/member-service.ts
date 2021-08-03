import {IBaseService} from '../../../core/service/ibase.service';
import {Injectable} from '@angular/core';
import {HttpUtil} from '../../../core/utils/http';
import {AppApi} from '../../../core/app-api';
import {HttpParams} from '@angular/common/http';

/**
 * 会员Service
 */
@Injectable()
export class MemberService implements IBaseService {

  constructor(private httpUtil: HttpUtil) {

  }

  delData(ids: Array<number>): Promise<any> {
    return this.httpUtil.post(AppApi.MEMBER.member_del, ids).then(response => {
      return Promise.resolve(response);
    });
  }

  editData(data: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.MEMBER.member_edit, data).then(response => {
      return Promise.resolve(true);
    });
  }

  getData(id: any): Promise<any> {
    const url = AppApi.MEMBER.member_detail.replace('{:memberId}', id);
    return this.httpUtil.get(url).then(response => {
      return Promise.resolve(response);
    });
  }

  getPager(params: Map<string, string>): Promise<any> {
    const httpParams: HttpParams = this.httpUtil.getHttpParams(params);
    return this.httpUtil.get(AppApi.MEMBER.member_list, httpParams).then(response => {
      return Promise.resolve(response);
    });
  }

  saveData(data: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.MEMBER.member_add, data).then(() => {
      return Promise.resolve(true);
    });
  }

  /**
   * 修改状态
   * @param memberId
   * @param status
   */
  editStatus(memberId: string, status: string): Promise<boolean> {
    const url = AppApi.MEMBER.member_edit_status.replace('{:memberId}', memberId).replace('{:status}', status);
    return this.httpUtil.get(url).then(() => {
      return Promise.resolve(true);
    });
  }

  /**
   * 修改密码
   * @param memberId
   * @param password
   */
  editPassword(data: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.MEMBER.member_edit_password, data).then(() => {
      return Promise.resolve(true);
    });
  }

  sort(upItemId, downItemId): Promise<boolean> {
    return undefined;
  }
}
