import {LocalStorageUtil} from './local-storage';

/**
 * 上下文工具类
 */
export class ContextUtil {

  private static USER_KEY: string = 'user';

  /**
   * 获得本地存储的用户对象
   */
  public static getLocalUser() {
    const user = LocalStorageUtil.get(ContextUtil.USER_KEY);
    return user;
  }

  /**
   * 设置存储本地的用户对象
   * @param {string} appAuth
   */
  public static setLocalUser(user: any) {
    LocalStorageUtil.put(ContextUtil.USER_KEY, user);
  }

  /**
   * 清空上下文
   */
  public static clear() {
    LocalStorageUtil.clear();
  }
}
