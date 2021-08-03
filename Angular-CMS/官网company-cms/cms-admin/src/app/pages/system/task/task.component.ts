import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../../core/service/base-component';
import {TaskService} from '../service/task-service';

@Component({
  selector: 'ngx-system-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.scss'],
})
export class TaskComponent extends BaseComponent implements OnInit {

  constructor(private taskService: TaskService, protected injector: Injector) {
    super(taskService, injector);
  }

  ngOnInit() {
    this.getPager(1);
  }

  /**
   * 开启任务
   * @param name
   */
  startTask(name: string) {
    this.modalUtil.confirm('提示', '您确认要开启该计划任务吗?').then(result => {
      if (result) {
        this.taskService.startTask(name).then(() => {
          this.toastUtil.showSuccess('启动成功!');
          this.getPager(1);
        });
      }
    });
  }

  /**
   * 停止任务
   * @param name
   */
  stopTask(name: string) {
    this.modalUtil.confirm('提示', '您确认要停止该计划任务吗?').then(result => {
      if (result) {
        this.taskService.stopTask(name).then(() => {
          this.toastUtil.showSuccess('停止成功!');
          this.getPager(1);
        });
      }
    });
  }
}
