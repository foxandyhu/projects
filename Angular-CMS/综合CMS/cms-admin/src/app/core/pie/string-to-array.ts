import {Pipe, PipeTransform} from '@angular/core';

/**
 * 字符串转数组管道
 */
@Pipe({name: 'stringToArray'})
export class StringToArray implements PipeTransform {

  //  默认','号分隔
  transform(value: any, ...args: any[]): any {
    if (!value) {
      return '';
    }
    const data: string = value.toString();
    if (!args[0]) {
      args[0] = ',';
    }
    return data.split(args[0]);
  }

}
