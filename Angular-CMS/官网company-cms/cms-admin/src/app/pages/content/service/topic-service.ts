import {Injectable} from '@angular/core';
import {IBaseService} from '../../../core/service/ibase.service';
import {HttpUtil} from '../../../core/utils/http';
import {AppApi} from '../../../core/app-api';
import {HttpParams} from '@angular/common/http';

/**
 * 专题Service
 */
@Injectable()
export class SpecialTopicService implements IBaseService {

  constructor(private httpUtil: HttpUtil) {
  }

  delData(ids: Array<number>): Promise<any> {
    return this.httpUtil.post(AppApi.CONTENT.topic_del, ids).then(response => {
      return Promise.resolve(response);
    });
  }


  getPager(params: Map<string, string>): Promise<any> {
    const httpParams: HttpParams = this.httpUtil.getHttpParams(params);
    const result: Promise<any> = this.httpUtil.get(AppApi.CONTENT.topic_list, httpParams).then(response => {
      return Promise.resolve(response);
    });
    return result;
  }


  saveData(menu: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.CONTENT.topic_add, menu).then(() => {
      return Promise.resolve(true);
    });
  }


  editData(menu: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.CONTENT.topic_edit, menu).then(() => {
      return Promise.resolve(true);
    });
  }

  getData(id: any): Promise<any> {
    const url = AppApi.CONTENT.topic_detail.replace('{:topicId}', id);
    return this.httpUtil.get(url).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 模型排序
   * @param upItemId
   * @param downItemId
   */
  sort(upItemId: string, downItemId: string): Promise<boolean> {
    const url = AppApi.CONTENT.topic_sort.replace('{:upItemId}', upItemId).replace('{:downItemId}', downItemId);
    return this.httpUtil.get(url).then(() => {
      return Promise.resolve(true);
    });
  }

  /**
   * 获得专题模版集合
   */
  getSpecialTopicTemplates(): Promise<any> {
    return this.httpUtil.get(AppApi.SYSTEM.template_paths).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 获得文章关联的专题
   * @param articleId
   */
  getSpecialTopicForArticle(articleId: string): Promise<any> {
    return this.httpUtil.get(AppApi.CONTENT.topic_article.replace('{:articleId}', articleId)).then(response => {
      return Promise.resolve(response);
    });
  }
}
