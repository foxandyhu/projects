import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../core/service/base-component';
import {NbDialogRef} from '@nebular/theme';
import {RoleService} from './service/roles.service';

@Component({
  selector: 'ngx-role-detail',
  templateUrl: './role-detail.component.html',
  styleUrls: ['./role-detail.component.scss'],
})
export class RoleDetailComponent extends BaseComponent implements OnInit {

  constructor(private roleService: RoleService,
              protected injector: Injector, private ref: NbDialogRef<RoleDetailComponent>) {
    super(null, injector);
  }

  role = {name: '', id: 0};
  private formId: string = 'roleForm';              //  表单ID

  ngOnInit() {
    this.initValidator();
  }

  /**
   * 初始化表单验证
   */
  initValidator() {
    this.initValidateForm(this.formId, {
      name: {
        validators: {
          notEmpty: {message: '角色名称不能为空!'},
        },
      },
    });
  }

  cancel() {
    this.ref.close();
  }

  submit() {
    if (this.isValidForm(this.formId)) {
      this.roleService.editData(this.role).then(() => {
        this.toastUtil.showSuccess('编辑成功!');
        this.ref.close(true);
      });
    }
  }
}
