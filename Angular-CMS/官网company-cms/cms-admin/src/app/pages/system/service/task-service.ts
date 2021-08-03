import {Injectable} from '@angular/core';
import {HttpUtil} from '../../../core/utils/http';
import {AppApi} from '../../../core/app-api';
import {IBaseService} from '../../../core/service/ibase.service';

/**
 * 系统计划任务Service
 */
@Injectable()
export class TaskService implements IBaseService {

  constructor(private httpUtil: HttpUtil) {
  }

  delData(ids: Array<number>): Promise<any> {
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

  getPager(params: Map<string, string>): Promise<any> {
    const result: Promise<any> = this.httpUtil.get(AppApi.SYSTEM.task_list).then(response => {
      return Promise.resolve(response);
    });
    return result;
  }

  /**
   * 启动计划任务
   * @param name
   */
  startTask(name: string): Promise<boolean> {
    const url = AppApi.SYSTEM.task_start.replace('{:name}', name);
    return this.httpUtil.get(url).then(() => {
      return Promise.resolve(true);
    });
  }

  /**
   * 暂停计划任务
   * @param name
   */
  stopTask(name: string): Promise<boolean> {
    const url = AppApi.SYSTEM.task_stop.replace('{:name}', name);
    return this.httpUtil.get(url).then(() => {
      return Promise.resolve(true);
    });
  }

  sort(upItemId, downItemId): Promise<boolean> {
    return undefined;
  }
}
