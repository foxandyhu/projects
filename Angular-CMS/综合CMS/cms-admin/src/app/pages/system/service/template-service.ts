import {Injectable} from '@angular/core';
import {HttpUtil} from '../../../core/utils/http';
import {AppApi} from '../../../core/app-api';
import {IBaseService} from '../../../core/service/ibase.service';
import {HttpParams} from '@angular/common/http';

/**
 * 系统模版Service
 */
@Injectable()
export class TemplateService implements IBaseService {

  constructor(private httpUtil: HttpUtil) {
  }

  getPager(params: Map<string, string>): Promise<any> {
    return undefined;
  }

  saveData(menu: any): Promise<boolean> {
    return undefined;
  }

  delData(ids: Array<number>): Promise<any> {
    return this.httpUtil.post(AppApi.SYSTEM.template_del, ids).then(response => {
      return Promise.resolve(response);
    });
  }

  editData(data: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.SYSTEM.template_edit, data).then(() => {
      return Promise.resolve(true);
    });
  }

  getData(path: any): Promise<any> {
    const url = AppApi.SYSTEM.template_detail.concat('?path=').concat(path);
    return this.httpUtil.get(url).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 获得模版列表
   */
  getTemplate(path: string): Promise<any> {
    let params: HttpParams = new HttpParams();
    params = params.set('target', path);
    return this.httpUtil.get(AppApi.SYSTEM.template_list, params).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 新建目录
   * @param path
   * @param name
   */
  mkdir(path: string, name: string): Promise<any> {
    let params: HttpParams = new HttpParams();
    params = params.set('path', path).set('name', name);
    return this.httpUtil.post(AppApi.SYSTEM.template_mkdir, params).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 上传文件
   * @param path
   */
  uploadFile(path: string, file: any): Promise<boolean> {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('path', path);
    return this.httpUtil.post(AppApi.SYSTEM.template_upload, formData).then(response => {
      return Promise.resolve(true);
    });
  }

  sort(upItemId, downItemId): Promise<boolean> {
    return undefined;
  }
}
