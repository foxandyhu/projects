import {IBaseService} from '../../../core/service/ibase.service';
import {Injectable} from '@angular/core';
import {HttpUtil} from '../../../core/utils/http';
import {AppApi} from '../../../core/app-api';
import {HttpParams} from '@angular/common/http';

/**
 * 问卷调查Service
 */
@Injectable()
export class VoteService implements IBaseService {

  constructor(private httpUtil: HttpUtil) {

  }

  delData(ids: Array<number>): Promise<any> {
    return this.httpUtil.post(AppApi.VOTE.vote_del, ids).then(response => {
      return Promise.resolve(response);
    });
  }

  editData(data: any): Promise<boolean> {
    return undefined;
  }

  getData(id: any): Promise<any> {
    const url = AppApi.VOTE.vote_detail.replace('{:voteId}', id);
    return this.httpUtil.get(url).then(response => {
      return Promise.resolve(response);
    });
  }

  getPager(params: Map<string, string>): Promise<any> {
    const httpParams: HttpParams = this.httpUtil.getHttpParams(params);
    return this.httpUtil.get(AppApi.VOTE.vote_list, httpParams).then(response => {
      return Promise.resolve(response);
    });
  }

  saveData(data: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.VOTE.vote_add, data).then(response => {
      return Promise.resolve(true);
    });
  }


  /**
   * 修改问卷调查状态
   * @param type
   */
  editVoteEnabled(voteId: string, enabled: string): Promise<any> {
    const url = AppApi.VOTE.vote_enabled.replace('{:voteId}', voteId).replace('{:enabled}', enabled);
    return this.httpUtil.get(url).then(response => {
      return Promise.resolve(response);
    });
  }

  sort(upItemId, downItemId): Promise<boolean> {
    return undefined;
  }
}
