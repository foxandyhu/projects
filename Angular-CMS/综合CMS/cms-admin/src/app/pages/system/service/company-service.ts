import {Injectable} from '@angular/core';
import {HttpUtil} from '../../../core/utils/http';
import {AppApi} from '../../../core/app-api';
import {IBaseService} from '../../../core/service/ibase.service';

/**
 * 企业信息配置Service
 */
@Injectable()
export class CompanyService implements IBaseService {

  constructor(private httpUtil: HttpUtil) {
  }

  delData(ids: Array<number>): Promise<any> {
    return undefined;
  }


  getPager(params: Map<string, string>): Promise<any> {
    return undefined;
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
   * 得到企业配置
   */
  getCompany(): Promise<any> {
    return this.httpUtil.get(AppApi.SYSTEM.company_info).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 编辑企业配置
   * @param data
   */
  editCompany(data: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.SYSTEM.company_edit, data).then(() => {
      return Promise.resolve(true);
    });
  }

  sort(upItemId, downItemId): Promise<boolean> {
    return undefined;
  }
}
