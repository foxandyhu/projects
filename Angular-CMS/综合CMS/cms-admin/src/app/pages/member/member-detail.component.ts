import {Component, Injector, OnInit} from '@angular/core';
import {MemberService} from './service/member-service';
import {ActivatedRoute, Router} from '@angular/router';
import {MemberGroupService} from './service/member-group-service';
import {BaseComponent} from '../../core/service/base-component';
import {forkJoin} from 'rxjs';
import {DateUtil} from '../../core/utils/date';
import {Constant} from '../../core/constant';

@Component({
  selector: 'ngx-member-detail',
  templateUrl: './member-detail.component.html',
  styleUrls: ['./member-detail.component.scss'],
})
export class MemberDetailComponent extends BaseComponent implements OnInit {

  constructor(private memberService: MemberService, protected injector: Injector,
              private router: Router, private groupService: MemberGroupService, private route: ActivatedRoute) {
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
    this.route.paramMap.subscribe(params => {
      const memberId = params.get('memberId');
      const arr = [this.getAllGroup(), this.getMember(memberId)];
      forkJoin(arr);
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

  getAllGroup() {
    return this.groupService.getAllGroup().then(result => {
      this.groups = result;
      return Promise.resolve(this.groups);
    });
  }

  getMember(memberId) {
    return this.memberService.getData(memberId).then(result => {
      if (result) {
        if (result.memberExt) {
          if (result.memberExt.face) {
            this.preview = result.memberExt.face;
          }
          if (result.memberExt.birthday) {
            result.memberExt.birthday = DateUtil.formatDate(result.memberExt.birthday);
          } else {
            result.memberExt.birthday = this.member.memberExt.birthday;
          }
          result.memberExt.girl = result.memberExt.girl + '';
        } else {
          result.memberExt = this.member.memberExt;
        }
        if (!result.group) {
          result.group = this.member.group;
        }
      }
      this.member = result;
    });
  }

  /**
   * 编辑会员
   */
  editMember() {
    if (this.isValidForm(this.formId)) {
      this.memberService.editData(this.member).then(() => {
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
