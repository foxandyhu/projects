/**
 * 校验工具类
 */
export class ValidateUtil {

  /**
   * 是否boolean类型
   * @param value
   */
  public static isBoolean(value: any): boolean {
    if (value) {
      if (value === 'false' || value === 0 || value === '0') {
        return false;
      }
      if (value === 'true' || value === 1 || value === '1') {
        return true;
      }
    }
    return false;
  }

}
