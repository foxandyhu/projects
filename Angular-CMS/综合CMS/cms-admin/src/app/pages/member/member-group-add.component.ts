import {Component, Injector, OnInit} from '@angular/core';
import {NbDialogRef} from '@nebular/theme';
import {BaseComponent} from '../../core/service/base-component';
import {MemberGroupService} from './service/member-group-service';

@Component({
  selector: 'ngx-member-group-add',
  templateUrl: './member-group-add.component.html',
  styleUrls: ['./member-group-add.component.scss'],
})
export class MemberGroupAddComponent extends BaseComponent implements OnInit {

  group: any = {          //  会员组
    name: '', allowUploadPerDay: 0, allowUploadMaxFile: 0, allowUploadSuffix: '',
    seq: 0, commentNeedCheck: false, commentNeedCaptcha: false, defaults: false,
  };
  private formId: string = 'memberGroupForm'; //   表单ID

  constructor(private groupService: MemberGroupService, protected injector: Injector,
              private ref: NbDialogRef<MemberGroupAddComponent>, private memberService: MemberGroupService) {
    super(null, injector);
  }

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
          notEmpty: {message: '组名称不能为空!'},
        },
      },
      allowUploadPerDay: {
        validators: {
          notEmpty: {message: '每日允许上传附件总大小不能为空!'},
          digits: {min: 0, message: '每日允许上传附件最小为0!'},
        },
      },
      allowUploadMaxFile: {
        validators: {
          notEmpty: {message: '最大允许上传附件总大小不能为空!'},
          digits: {min: 0, message: '最大允许上传附件最小为0!'},
        },
      },
    });
  }

  cancel() {
    this.ref.close();
  }

  submit() {
    if (this.isValidForm(this.formId)) {
      this.memberService.saveData(this.group).then(() => {
        this.ref.close(true);
      });
    }
  }
}
