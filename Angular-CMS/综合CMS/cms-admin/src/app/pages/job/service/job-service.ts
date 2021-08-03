import {IBaseService} from '../../../core/service/ibase.service';
import {Injectable} from '@angular/core';
import {HttpUtil} from '../../../core/utils/http';
import {AppApi} from '../../../core/app-api';
import {HttpParams} from '@angular/common/http';

/**
 * 职位简历Service
 */
@Injectable()
export class JobService implements IBaseService {

  constructor(private httpUtil: HttpUtil) {
  }

  delData(ids: Array<number>): Promise<any> {
    return this.httpUtil.post(AppApi.JOB.apply_del, ids).then(response => {
      return Promise.resolve(response);
    });
  }

  editData(data: any): Promise<boolean> {
    return undefined;
  }

  getData(id: any): Promise<any> {
    return undefined;
  }

  getPager(params: Map<string, string>): Promise<any> {
    const httpParams: HttpParams = this.httpUtil.getHttpParams(params);
    const result: Promise<any> = this.httpUtil.get(AppApi.JOB.apply_list, httpParams).then(response => {
      return Promise.resolve(response);
    });
    return result;
  }

  saveData(data: any): Promise<boolean> {
    return undefined;
  }

  /**
   * 获得用户的简历
   */
  getMemberResume(memberId: string): Promise<any> {
    const url = AppApi.JOB.resume_detail.replace(':memberId', memberId);
    return this.httpUtil.get(url).then(response => {
      return Promise.resolve(response);
    });
  }

  sort(upItemId, downItemId): Promise<boolean> {
    return undefined;
  }
}
