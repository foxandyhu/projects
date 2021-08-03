import {IBaseService} from '../../../core/service/ibase.service';
import {Injectable} from '@angular/core';
import {HttpUtil} from '../../../core/utils/http';
import {AppApi} from '../../../core/app-api';

/**
 * 会员配置Service
 */
@Injectable()
export class MemberConfigService implements IBaseService {

  constructor(private httpUtil: HttpUtil) {

  }

  delData(ids: Array<number>): Promise<any> {
    return undefined;
  }

  editData(data: any): Promise<boolean> {
    return undefined;
  }

  getData(id: any): Promise<any> {
    return undefined;
  }

  getPager(params: Map<string, string>): Promise<any> {
    return undefined;
  }

  saveData(data: any): Promise<boolean> {
    return undefined;
  }

  /**
   * 获得所有的邮件提供商
   */
  getAllEmailProvider(): Promise<any> {
    return this.httpUtil.get(AppApi.EMAIL.provider_all).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 编辑登录配置
   * @param data
   */
  editLoginConfig(data: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.MEMBER.member_login_config_edit, data).then((response) => {
      return Promise.resolve(true);
    });
  }

  /**
   * 编辑注册配置
   * @param data
   */
  editRegistConfig(data: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.MEMBER.member_regist_config_edit, data).then((response) => {
      return Promise.resolve(true);
    });
  }

  /**
   * 获得登录配置
   */
  getLoginConfig(): Promise<any> {
    return this.httpUtil.get(AppApi.MEMBER.member_login_config).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 获得注册配置
   */
  getRegistConfig(): Promise<any> {
    return this.httpUtil.get(AppApi.MEMBER.member_regist_config).then(response => {
      return Promise.resolve(response);
    });
  }

  sort(upItemId, downItemId): Promise<boolean> {
    return undefined;
  }
}
