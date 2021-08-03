import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../core/service/base-component';
import {MemberService} from './service/member-service';
import {Router} from '@angular/router';
import {MemberGroupService} from './service/member-group-service';
import {DateUtil} from '../../core/utils/date';
import {Constant} from '../../core/constant';

@Component({
  selector: 'ngx-member-add',
  templateUrl: './member-add.component.html',
  styleUrls: ['./member-add.component.scss'],
})
export class MemberAddComponent extends BaseComponent implements OnInit {

  constructor(private memberService: MemberService, protected injector: Injector,
              private router: Router, private groupService: MemberGroupService) {
    super(memberService, injector);
  }

  private formId: string = 'memberForm';              //  表单ID
  member: any = {   //  会员信息
    userName: '', password: '', status: '', group: {id: ''},
    memberExt: {
      realName: '', girl: 'false', birthday: '', intro: '', comeFrom: '',
      qq: '', weixin: '', phone: '', mobile: '', email: '', face: '', signature: '',
    },
  };
  statuss: any = [{id: 0, name: '待审核'}, {id: 1, name: '正常'}, {id: 2, name: '已禁用'}];
  groups: any;   //  会员组
  preview: any;   //  头像预览

  ngOnInit() {
    this.preview = Constant.DEFAULT_PIC;
    this.initValidator();
    this.groupService.getAllGroup().then(result => {
      this.groups = result;
    });
  }

  /**
   * 初始化表单验证
   */
  initValidator() {
    this.initValidateForm(this.formId, {
      userName: {
        validators: {
          notEmpty: {message: '用户名不能为空!'},
        },
      },
      email: {
        validators: {
          emailAddress: {message: '邮件格式错误!'},
        },
      },
      password: {
        validators: {
          notEmpty: {message: '密码不能为空!'},
        },
      },
      confirmPassword: {
        validators: {
          notEmpty: {message: '请输入确认密码!'},
          stringLength: {min: 6, max: 50, message: '密码在6-50个字符之间!'},
          identical: {field: 'password', message: '两次输入的密码不一致!'},
        },
      },
      groupId: {
        validators: {
          notEmpty: {message: '会员组不能为空!'},
        },
      },
      status: {
        validators: {
          notEmpty: {message: '状态不能为空!'},
        },
      },
    });
  }

  /**
   * 保存会员
   */
  saveMember() {
    if (this.isValidForm(this.formId)) {
      this.memberService.saveData(this.member).then(() => {
        this.toastUtil.showSuccess('新增成功!');
        this.router.navigate(['/pages/member/list']);
      });
    }
  }

  /**
   * 头像上传
   * @param event
   */
  faceFileChange(event) {
    this.member.memberExt.face = event.dest.path;
  }

  /**
   * 日期选择
   * @param date
   */
  dateChange(date) {
    this.member.memberExt.birthday = DateUtil.formatDate(date);
  }
}
