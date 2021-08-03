import {Injectable} from '@angular/core';
import {HttpUtil} from '../../../core/utils/http';
import {AppApi} from '../../../core/app-api';
import {HttpParams} from '@angular/common/http';
import {IBaseService} from '../../../core/service/ibase.service';

@Injectable()
export class SysLogsService implements IBaseService {

  constructor(private httpUtil: HttpUtil) {
  }

  /**
   * 日志列表
   */
  getPager(params: Map<string, string>): Promise<any> {
    const httpParams: HttpParams = this.httpUtil.getHttpParams(params);
    const result: Promise<any> = this.httpUtil.get(AppApi.LOGS.sys_logs_list, httpParams).then(response => {
      return Promise.resolve(response);
    });
    return result;
  }

  /**
   * 删除系统日志
   */
  delData(ids: Array<number>): Promise<any> {
    return this.httpUtil.post(AppApi.LOGS.sys_logs_del, ids).then(response => {
      return Promise.resolve(response);
    });
  }

  editData(data: any): Promise<boolean> {
    return undefined;
  }

  saveData(data: any): Promise<boolean> {
    return undefined;
  }

  getData(id: any): Promise<any> {
    return undefined;
  }

  sort(upItemId, downItemId): Promise<boolean> {
    return undefined;
  }
}
