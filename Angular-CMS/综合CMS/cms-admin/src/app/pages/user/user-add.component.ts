import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../core/service/base-component';
import {UserService} from './service/users.service';
import {Router} from '@angular/router';
import {RoleService} from './service/roles.service';

@Component({
  selector: 'ngx-user-add',
  templateUrl: './user-add.component.html',
  styleUrls: ['./user-add.component.scss'],
})
export class UserAddComponent extends BaseComponent implements OnInit {

  constructor(private userService: UserService, protected injector: Injector,
              private router: Router, private roleService: RoleService) {
    super(userService, injector);
  }

  user = {userName: '', password: '', confirmPassword: '', email: '', roles: [], face: '', superAdmin: false}; //  用户对象
  roles: Array<any>;                                  // 系统所有角色集合
  currentRoles: Array<any> = new Array<any>();       //  当前用户拥有的角色集合
  private formId: string = 'userForm';              //  表单ID

  ngOnInit() {
    this.initValidator();
    this.getRoles();
  }

  /**
   * 初始化表单验证
   */
  initValidator() {
    this.initValidateForm(this.formId, {
      userName: {
        validators: {
          notEmpty: {message: '用户名不能为空!'},
          stringLength: {min: 5, max: 15, message: '用户名长度必须在5到15位之间'},
          regexp: {regexp: /^[a-zA-Z0-9_]+$/, message: '用户名只能包含大写、小写、数字和下划线'},
        },
      },
      email: {
        validators: {
          notEmpty: {message: '邮箱地址不能为空!'},
          emailAddress: {message: '邮箱地址格式有误!'},
        },
      },
      password: {
        validators: {
          notEmpty: {message: '请输入密码!'},
          stringLength: {min: 6, max: 50, message: '密码在6到50个字符之间!'},
        },
      },
      confirmPassword: {
        validators: {
          notEmpty: {message: '请输入确认密码!'},
          stringLength: {min: 6, max: 50, message: '密码在6~50个字符之间!'},
          identical: {field: 'password', message: '两次输入的密码不一致!'},
        },
      },
    });
  }

  /**
   * 获得角色集合
   */
  getRoles() {
    return this.roleService.getRoles().then(result => {
      this.roles = result;
      return Promise.resolve(this.roles);
    });
  }

  /**
   * 角色选择
   */
  changeRole(id: number, selected: boolean) {
    this.currentRoles.splice(0, this.currentRoles.length);
    this.roles.forEach((role, index, array) => {
      if (role.id === id) {
        role.selected = selected;
      }
      if (role.selected === true) {    //  选中的角色重新放到当前用户拥有的角色集合中
        this.currentRoles.push({id: role.id, name: role.name});
      }
    });
  }

  /**
   * 保存用户信息
   */
  saveUser() {
    if (this.isValidForm(this.formId)) {
      this.user.roles = this.currentRoles;
      this.userService.saveData(this.user).then(result => {
        if (result === true) {
          this.toastUtil.showSuccess('新增成功!');
          this.router.navigate(['/pages/user/list']);
        }
      });
    }
  }

  /**
   * 头像上传
   * @param event
   */
  faceFileChange(event) {
    this.user.face = event.dest.path;
  }
}
