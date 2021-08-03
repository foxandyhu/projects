import {Component, Injector, OnDestroy, OnInit} from '@angular/core';
import {BaseComponent} from '../../core/service/base-component';
import {RoleService} from './service/roles.service';
import {NbDialogRef, NbDialogService} from '@nebular/theme';
import {RoleMenuComponent} from './role-menu.component';
import {RoleAddComponent} from './role-add.component';
import {RoleDetailComponent} from './role-detail.component';

@Component({
  selector: 'ngx-role',
  templateUrl: './role.component.html',
})
export class RoleComponent extends BaseComponent implements OnInit, OnDestroy {

  constructor(private roleService: RoleService, protected injector: Injector, private dialogService: NbDialogService) {
    super(roleService, injector);
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
   * 弹出菜单权限选择框
   * @param roleId
   */
  showRoleMenuDialog(roleId: string, roleName: string) {
    this.dialog = this.dialogService.open(RoleMenuComponent);
    this.dialog.componentRef.instance.roleName = roleName;
    this.roleService.getRoleMenu(roleId).then(result => {
      this.dialog.componentRef.instance.setData(result);
    });
    this.dialog.onClose.subscribe(result => {
      if (result === undefined) {
        return;
      }
      const userRole = {id: roleId, menus: result};
      this.roleService.grantRoleMenu(userRole).then(() => {
        this.getPager(1);
        this.toastUtil.showSuccess('保存成功!');
      });
    });
  }

  /**
   * 弹出添加角色模态框
   */
  showAddRole() {
    this.dialog = this.dialogService.open(RoleAddComponent);
    this.dialog.onClose.subscribe(result => {
      if (result) {
        this.getPager(1);
      }
    });
  }

  /**
   * 弹出编辑角色模态框
   */
  showEditRole(item: any) {
    this.dialog = this.dialogService.open(RoleDetailComponent);
    this.dialog.componentRef.instance.role = {name: item.name, id: item.id};
    this.dialog.onClose.subscribe(result => {
      if (result) {
        this.getPager(1);
      }
    });
  }
}
