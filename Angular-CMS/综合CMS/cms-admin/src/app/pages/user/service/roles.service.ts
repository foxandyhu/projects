import {Injectable} from '@angular/core';
import {HttpUtil} from '../../../core/utils/http';
import {AppApi} from '../../../core/app-api';
import {IBaseService} from '../../../core/service/ibase.service';

@Injectable()
export class RoleService implements IBaseService {

  constructor(private httpUtil: HttpUtil) {
  }

  /**
   * 删除角色
   * @param ids
   */
  delData(ids: Array<number>): Promise<any> {
    return this.httpUtil.post(AppApi.USERS.role_del, ids).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 角色列表
   * @param params
   */
  getPager(params: Map<string, string>): Promise<any> {
    const result: Promise<any> = this.httpUtil.get(AppApi.USERS.role_list).then(response => {
      return Promise.resolve(response);
    });
    return result;
  }

  /**
   * 获得所有角色集合
   */
  getRoles(): Promise<Array<any>> {
    return this.httpUtil.get(AppApi.USERS.role_all).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 新增角色
   * @param role
   */
  saveData(role: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.USERS.role_add, role).then(() => {
      return Promise.resolve(true);
    });
  }

  /**
   * 编辑角色
   * @param role
   */
  editData(role: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.USERS.role_edit, role).then(() => {
      return Promise.resolve(true);
    });
  }

  /**
   * 获得系统角色信息
   * @param roleId
   */
  getData(roleId: any): Promise<any> {
    const url = AppApi.USERS.role_detail.replace('{:roleId}', roleId);
    return this.httpUtil.get(url).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 获得角色系统菜单
   * @param roleId
   */
  getRoleMenu(roleId: string): Promise<any> {
    const url = AppApi.USERS.role_menu.replace('{:roleId}', roleId);
    return this.httpUtil.get(url).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 角色授权
   */
  grantRoleMenu(userRole: any): Promise<any> {
    return this.httpUtil.post(AppApi.USERS.role_grant, userRole).then(response => {
      return Promise.resolve(response);
    });
  }

  sort(upItemId, downItemId): Promise<boolean> {
    return undefined;
  }
}
