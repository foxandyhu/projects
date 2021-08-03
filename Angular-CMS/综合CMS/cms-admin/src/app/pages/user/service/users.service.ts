import {of as observableOf, Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {HttpUtil} from '../../../core/utils/http';
import {AppApi} from '../../../core/app-api';
import {HttpParams} from '@angular/common/http';
import {IBaseService} from '../../../core/service/ibase.service';
import {ContextUtil} from '../../../core/utils/context';

@Injectable()
export class UserService implements IBaseService {

  private users = {userName: ' ', face: 'assets/images/nick.png'};

  constructor(private httpUtil: HttpUtil) {
  }

  /**
   * 修改当前登录用户的密码
   */
  editCurrentPwd(oldPwd, newPwd): Promise<boolean> {
    return this.httpUtil.post(AppApi.USERS.edit_pwd, {oldPwd: oldPwd, newPwd: newPwd}).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 重置密码
   * @param userName
   * @param password
   */
  resetUserPwd(userName, password): Promise<boolean> {
    let httpParams: HttpParams = new HttpParams();
    httpParams = httpParams.set('userName', userName).set('password', password);
    return this.httpUtil.get(AppApi.USERS.reset_pwd, httpParams).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 获得当前登录用户信息
   */
  getCurrentUser(): Observable<any> {
    const user = ContextUtil.getLocalUser();
    if (user) {
      this.users = JSON.parse(user);
    }
    return observableOf(this.users);
  }

  /**
   * 用户登录
   * @param userName
   * @param password
   */
  login(data: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.USERS.login, data).then(response => {
      if (!response) {
        return Promise.resolve(false);
      }
      ContextUtil.setLocalUser(JSON.stringify(response));
      return Promise.resolve(true);
    });
  }

  /**
   * 用户退出
   */
  logout(): Promise<boolean> {
    return this.httpUtil.get(AppApi.USERS.logout).then(() => {
      ContextUtil.clear();
      return Promise.resolve(true);
    });
  }

  /**
   * 用户列表
   */
  getPager(params: Map<string, string>): Promise<any> {
    const httpParams: HttpParams = this.httpUtil.getHttpParams(params);
    const result: Promise<any> = this.httpUtil.get(AppApi.USERS.user_list, httpParams).then(response => {
      return Promise.resolve(response);
    });
    return result;
  }

  /**
   * 删除系统用户
   */
  delData(ids: Array<number>): Promise<any> {
    return this.httpUtil.post(AppApi.USERS.user_del, ids).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 保存用户
   */
  saveData(user: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.USERS.user_add, user).then(() => {
      return Promise.resolve(true);
    });
  }

  /**
   * 编辑用户
   * @param user
   */
  editData(user: any): Promise<boolean> {
    return this.httpUtil.post(AppApi.USERS.user_edit, user).then(() => {
      return Promise.resolve(true);
    });
  }

  /**
   * 获得用户信息
   * @param userId
   */
  getData(userId: any): Promise<any> {
    const url = AppApi.USERS.user_detail.replace('{:userId}', userId);
    return this.httpUtil.get(url).then(response => {
      return Promise.resolve(response);
    });
  }

  /**
   * 回收用户角色
   */
  recycleUserRole(userId: string, roleId: string): Promise<any> {
    const url = AppApi.USERS.role_recycle.replace('{:userId}', userId).replace('{:roleId}', roleId);
    return this.httpUtil.get(url).then(response => {
      return Promise.resolve(response);
    });
  }

  sort(upItemId, downItemId): Promise<boolean> {
    return undefined;
  }
}
