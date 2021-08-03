import {Component, Injector, OnInit} from '@angular/core';
import {NbDialogRef} from '@nebular/theme';
import {BaseComponent} from '../../core/service/base-component';
import {Constant} from '../../core/constant';
import {FriendLinkService} from './service/friendlink-service';
import {FriendLinkTypeService} from './service/friendlink-type-service';

@Component({
  selector: 'ngx-friend-link-add',
  templateUrl: './friend-link-add.component.html',
  styleUrls: ['./friend-link-add.component.scss'],
})
export class FriendLinkAddComponent extends BaseComponent implements OnInit {

  friendLink: any = {name: '', url: '', logo: '', remark: '', enabled: true, type: {id: ''}};  //  友情链接
  private formId: string = 'friendLinkForm';     //   表单ID
  types: any;    //   类型列表
  preview: any;   //  图像预览

  constructor(protected injector: Injector,
              private ref: NbDialogRef<FriendLinkAddComponent>,
              private linkService: FriendLinkService, private typeService: FriendLinkTypeService) {
    super(null, injector);
  }

  ngOnInit() {
    this.preview = Constant.DEFAULT_PIC;
    this.initValidator();
    this.getAllType();
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
      url: {
        validators: {
          notEmpty: {message: '链接地址不能为空!'},
        },
      },
      type: {
        validators: {
          notEmpty: {message: '类型不能为空!'},
        },
      },
    });
  }

  /**
   * LOGO上传
   * @param event
   */
  logoChange(event) {
    this.friendLink.logo = event.dest.path;
  }

  /**
   * 类型列表
   */
  getAllType() {
    this.typeService.getAllType().then(result => {
      this.types = result;
    });
  }

  cancel() {
    this.ref.close();
  }

  submit() {
    if (this.isValidForm(this.formId)) {
      this.linkService.saveData(this.friendLink).then(() => {
        this.ref.close(true);
      });
    }
  }
}
