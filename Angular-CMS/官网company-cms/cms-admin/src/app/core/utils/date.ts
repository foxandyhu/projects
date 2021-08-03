import * as moment from 'moment';

/**
 * 时间工具类
 */
export class DateUtil {

  /**
   * 时间格式化YYYY-MM-DD字符串
   * @param date
   */
  static formatDate(date: any): string {
    const m = moment(date);
    if (!date) {
      return '';
    }
    return m.format('YYYY-MM-DD');
  }

}
