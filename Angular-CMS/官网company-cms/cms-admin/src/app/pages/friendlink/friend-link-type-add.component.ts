import {Component, Injector, OnInit} from '@angular/core';
import {NbDialogRef} from '@nebular/theme';
import {BaseComponent} from '../../core/service/base-component';

@Component({
  selector: 'ngx-friend-link-type-add',
  templateUrl: './friend-link-type-add.component.html',
  styleUrls: ['./friend-link-type-add.component.scss'],
})
export class FriendLinkTypeAddComponent extends BaseComponent implements OnInit {

  friendLinkType: any = {name: ''};  //  友情链接类型
  private formId: string = 'friendLinkTypeForm';     //   表单ID

  constructor(protected injector: Injector, private ref: NbDialogRef<FriendLinkTypeAddComponent>) {
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
          notEmpty: {message: '名称不能为空!'},
        },
      },
    });
  }

  cancel() {
    this.ref.close();
  }

  submit() {
    if (this.isValidForm(this.formId)) {
      this.ref.close(this.friendLinkType);
    }
  }
}
