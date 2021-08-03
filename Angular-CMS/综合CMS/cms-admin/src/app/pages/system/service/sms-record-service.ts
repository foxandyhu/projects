import {Injectable} from '@angular/core';
import {HttpUtil} from '../../../core/utils/http';
import {AppApi} from '../../../core/app-api';
import {IBaseService} from '../../../core/service/ibase.service';
import {HttpParams} from '@angular/common/http';

/**
 * 短信发送记录业务方法
 */
@Injectable()
export class SmsRecordService implements IBaseService {

  constructor(private httpUtil: HttpUtil) {
  }

  delData(ids: Array<number>): Promise<any> {
    return undefined;
  }


  getPager(params: Map<string, string>): Promise<any> {
    const httpParams: HttpParams = this.httpUtil.getHttpParams(params);
    const result: Promise<any> = this.httpUtil.get(AppApi.SMS.record_list, httpParams).then(response => {
      return Promise.resolve(response);
    });
    return result;
  }


  saveData(menu: any): Promise<boolean> {
    return undefined;
  }


  editData(menu: any): Promise<boolean> {
    return undefined;
  }

  getData(id: any): Promise<any> {
    return undefined;
  }

  /**
   * 短信重发
   */
  resend(id): Promise<boolean> {
    const url = AppApi.SMS.record_resend.replace('{:recordId}', id);
    return this.httpUtil.get(url).then(() => {
      return Promise.resolve(true);
    });
  }

  sort(upItemId, downItemId): Promise<boolean> {
    return undefined;
  }
}
