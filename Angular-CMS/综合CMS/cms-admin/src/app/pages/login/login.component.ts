import {AfterViewInit, Component, Injector, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from '../user/service/users.service';
import {BaseComponent} from '../../core/service/base-component';
import {AppApi} from '../../core/app-api';

@Component({
  selector: 'ngx-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent extends BaseComponent implements OnInit, AfterViewInit {

  constructor(private router: Router, private userService: UserService, protected injector: Injector) {
    super(userService, injector);
  }

  captcha = '';
  msg = '';
  data = {userName: '', password: '', captcha: ''};

  ngOnInit() {
    this.generateNewCaptcha();
  }

  ngAfterViewInit(): void {
    const spinner = document.getElementById('nb-global-spinner');
    if (spinner) {
      spinner.remove();
    }
  }

  submit() {
    if (!this.data.userName) {
      this.msg = '用户名不能为空!';
      return;
    }
    if (!this.data.password) {
      this.msg = '密码不能为空!';
      return;
    }
    if (!this.data.captcha) {
      this.msg = '验证码不能为空!';
      return;
    }
    this.msg = '';
    const result: Promise<boolean> = this.userService.login(this.data);
    result.then(success => {
      if (success) {
        this.router.navigate(['/']);
      }
    });
  }

  /**
   * 生成验证码
   */
  generateNewCaptcha() {
    this.captcha = AppApi.ROOT_URI.concat(AppApi.SYSTEM.captcha).concat('?t=' + new Date().getTime());
  }
}
