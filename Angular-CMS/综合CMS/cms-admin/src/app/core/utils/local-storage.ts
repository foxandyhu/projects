/**
 * 本地存储工具类
 */
export class LocalStorageUtil {

  /**
   * 添加数据
   * @param key
   * @param data
   */
  public static put(key: string, data: any) {
    localStorage.setItem(key, data);
  }

  /**
   * 获得数据
   * @param key
   */
  public static get(key: string): any {
    return localStorage.getItem(key);
  }

  /**
   * 清空本地存储
   */
  public static clear() {
    localStorage.clear();
  }
}
