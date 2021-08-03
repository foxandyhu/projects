import {Injectable} from '@angular/core';
import {HttpUtil} from '../../../core/utils/http';
import {AppApi} from '../../../core/app-api';
import {IBaseService} from '../../../core/service/ibase.service';

/**
 * 水印配置Service
 */
@Injectable()
export class WatermarkConfigService implements IBaseService {

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
   * 得到水印配置
   */
  getWatermarkConfig(): Promise<any> {
    return this.httpUtil.get(AppApi.SYSTEM.watermark_config).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 编辑水印配置
   * @param data
   */
  editWatermarkConfig(data: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.SYSTEM.watermark_config_edit, data).then(() => {
      return Promise.resolve(true);
    });
  }

  sort(upItemId, downItemId): Promise<boolean> {
    return undefined;
  }
}
