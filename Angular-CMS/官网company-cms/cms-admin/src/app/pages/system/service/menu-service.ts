import {Injectable} from '@angular/core';
import {HttpUtil} from '../../../core/utils/http';
import {AppApi} from '../../../core/app-api';
import {IBaseService} from '../../../core/service/ibase.service';

@Injectable()
export class MenuService implements IBaseService {

  constructor(private httpUtil: HttpUtil) {
  }

  /**
   * 删除菜单
   * @param ids
   */
  delData(ids: Array<number>): Promise<any> {
    return this.httpUtil.post(AppApi.SYSTEM.menu_del, ids).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 菜单列表
   * @param params
   */
  getPager(params: Map<string, string>): Promise<any> {
    const result: Promise<any> = this.httpUtil.get(AppApi.SYSTEM.menu_list).then(response => {
      return Promise.resolve(response);
    });
    return result;
  }


  /**
   * 新增菜单
   * @param role
   */
  saveData(menu: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.SYSTEM.menu_add, menu).then(() => {
      return Promise.resolve(true);
    });
  }

  /**
   * 编辑菜单
   * @param role
   */
  editData(menu: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.SYSTEM.menu_edit, menu).then(() => {
      return Promise.resolve(true);
    });
  }

  getData(id: any): Promise<any> {
    return this.httpUtil.get(AppApi.SYSTEM.menu_detail.replace('{:menuId}', id)).then((result) => {
      return Promise.resolve(result);
    });
  }

  sort(upItemId, downItemId): Promise<boolean> {
    return undefined;
  }

  /**
   * 获得当前用户的菜单
   */
  getCurrentUserMenu(): Promise<any> {
    return this.httpUtil.get(AppApi.SYSTEM.menu_user).then(result => {
      return Promise.resolve(result);
    });
  }
}
