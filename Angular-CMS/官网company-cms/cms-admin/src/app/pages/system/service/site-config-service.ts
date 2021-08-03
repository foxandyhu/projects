import {Injectable} from '@angular/core';
import {HttpUtil} from '../../../core/utils/http';
import {AppApi} from '../../../core/app-api';
import {IBaseService} from '../../../core/service/ibase.service';

/**
 * 站点配置Service
 */
@Injectable()
export class SiteConfigService implements IBaseService {

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
   * 得到站点版权信息
   */
  getCopyRight(): Promise<any> {
    return this.httpUtil.get(AppApi.SYSTEM.site_copy_right).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 得到站点配置
   */
  getSiteConfig(): Promise<any> {
    return this.httpUtil.get(AppApi.SYSTEM.site_config).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 编辑站点配置
   * @param data
   */
  editSiteConfig(data: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.SYSTEM.site_config_edit, data).then(() => {
      return Promise.resolve(true);
    });
  }

  sort(upItemId, downItemId): Promise<boolean> {
    return undefined;
  }
}
