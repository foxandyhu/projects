import {Component, Injector, OnDestroy, OnInit} from '@angular/core';
import {UserService} from './service/users.service';
import {BaseComponent} from '../../core/service/base-component';
import {NbDialogRef, NbDialogService} from '@nebular/theme';
import {ResetPasswordComponent} from './reset-password.component';

@Component({
  selector: 'ngx-user',
  templateUrl: './user.component.html',
})
export class UserComponent extends BaseComponent implements OnInit, OnDestroy {

  constructor(private userService: UserService, protected injector: Injector, private dialogService: NbDialogService) {
    super(userService, injector);
  }

  private dialog: NbDialogRef<any>;

  ngOnInit() {
    this.getPager(1);
  }

  ngOnDestroy(): void {
    if (this.dialog) {
      this.dialog.close();
    }
  }

  /**
   * 根据不同状态类型查询
   */
  changeStatus(status: string) {
    this.setQueryParams('status', status);
    this.getPager(1);
  }

  /**
   * 弹框重置密码
   */
  showResetPwd(event) {
    const userName = event.target.dataset.userName;
    this.dialog = this.dialogService.open(ResetPasswordComponent);
    this.dialog.componentRef.instance.userName = userName;
    this.dialog.onClose.subscribe(result => {});
  }
}
