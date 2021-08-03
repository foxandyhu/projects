import {IBaseService} from '../../../core/service/ibase.service';
import {Injectable} from '@angular/core';
import {HttpUtil} from '../../../core/utils/http';
import {AppApi} from '../../../core/app-api';
import {HttpParams} from '@angular/common/http';

/**
 * 评论Service
 */
@Injectable()
export class CommentService implements IBaseService {

  constructor(private httpUtil: HttpUtil) {
  }

  delData(ids: Array<number>): Promise<any> {
    return this.httpUtil.post(AppApi.MESSAGE.comment_del, ids).then(response => {
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
    return this.httpUtil.get(AppApi.MESSAGE.comment_list, httpParams).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 评论回复
   * @param data
   */
  saveData(data: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.MESSAGE.comment_add, data).then(() => {
      return Promise.resolve(true);
    });
  }

  /**
   * 推荐或取消评论
   * @param commentId
   * @param recommend
   */
  recommend(commentId: string, recommend: boolean): Promise<boolean> {
    const url = AppApi.MESSAGE.commend_recommend.replace('{:commentId}', commentId)
      .replace('{:recommend}', recommend + '');
    return this.httpUtil.get(url).then(() => {
      return Promise.resolve(true);
    });
  }

  /**
   * 审核评论
   * @param status
   */
  verify(status: boolean, ids: any) {
    const url = AppApi.MESSAGE.comment_verify.replace('{:status}', status + '');
    return this.httpUtil.post(url, ids).then(() => {
      return Promise.resolve(true);
    });
  }

  sort(upItemId, downItemId): Promise<boolean> {
    return undefined;
  }

  /**
   * 得到评论配置
   */
  getCommentConfig(): Promise<any> {
    return this.httpUtil.get(AppApi.MESSAGE.comment_config).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   *编辑评论配置信息
   */
  editCommentConfig(data: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.MESSAGE.comment_config_edit, data).then(() => {
      return Promise.resolve(true);
    });
  }
}
