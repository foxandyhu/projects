import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../core/service/base-component';
import {ActivatedRoute} from '@angular/router';
import {UserService} from './service/users.service';

@Component({
  selector: 'ngx-role-user',
  templateUrl: './role-user.component.html',
  styleUrls: ['./role-user.component.scss'],
})
export class RoleUserComponent extends BaseComponent implements OnInit {

  constructor(private userService: UserService, protected injector: Injector, private route: ActivatedRoute) {
    super(userService, injector);
  }

  roleId: string;  //  角色ID

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.roleId = params.get('roleId');
      this.setQueryParams('roleId', this.roleId);
      this.getPager(1);
    });
  }

  /**
   * 回收用户角色
   */
  recycleUserRole(userId: string) {
    this.modalUtil.confirm('提示', '您确认要回收该用户的角色权限吗?').then(result => {
      if (result) {
        this.userService.recycleUserRole(userId, this.roleId).then(() => {
          this.getPager(1);
        });
      }
    });
  }
}
