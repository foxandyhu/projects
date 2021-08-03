import {IBaseService} from '../../../core/service/ibase.service';
import {Injectable} from '@angular/core';
import {HttpUtil} from '../../../core/utils/http';
import {AppApi} from '../../../core/app-api';
import {HttpParams} from '@angular/common/http';

/**
 * 留言Service
 */
@Injectable()
export class GuestBookService implements IBaseService {

  constructor(private httpUtil: HttpUtil) {
  }

  editData(data: any): Promise<boolean> {
    return undefined;
  }

  getData(id: any): Promise<any> {
    return undefined;
  }

  delData(ids: Array<number>): Promise<any> {
    return this.httpUtil.post(AppApi.MESSAGE.guestbook_del, ids).then(response => {
      return Promise.resolve(response);
    });
  }

  getPager(params: Map<string, string>): Promise<any> {
    const httpParams: HttpParams = this.httpUtil.getHttpParams(params);
    return this.httpUtil.get(AppApi.MESSAGE.guestbook_list, httpParams).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 回复留言
   * @param data
   */
  saveData(data: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.MESSAGE.guestbook_reply, data).then(() => {
      return Promise.resolve(true);
    });
  }

  /**
   * 推荐或取消留言
   * @param commentId
   * @param recommend
   */
  recommend(guestbookId: string, recommend: boolean): Promise<boolean> {
    const url = AppApi.MESSAGE.guestbook_recommend.replace('{:guestbookId}', guestbookId)
      .replace('{:recommend}', recommend + '');
    return this.httpUtil.get(url).then(() => {
      return Promise.resolve(true);
    });
  }

  /**
   * 审核留言
   * @param status
   */
  verify(status: boolean, ids: any) {
    const url = AppApi.MESSAGE.guestbook_verify.replace('{:status}', status + '');
    return this.httpUtil.post(url, ids).then(() => {
      return Promise.resolve(true);
    });
  }

  sort(upItemId, downItemId): Promise<boolean> {
    return undefined;
  }

  /**
   * 得到留言配置
   */
  getGuestBookConfig(): Promise<any> {
    return this.httpUtil.get(AppApi.MESSAGE.guestbook_config).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   *编辑留言配置信息
   */
  editGuestBookConfig(data: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.MESSAGE.guestbook_config_edit, data).then(() => {
      return Promise.resolve(true);
    });
  }
}
