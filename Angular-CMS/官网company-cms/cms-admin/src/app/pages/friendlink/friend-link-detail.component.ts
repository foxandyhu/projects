import {Component, Injector, OnInit} from '@angular/core';
import {NbDialogRef} from '@nebular/theme';
import {BaseComponent} from '../../core/service/base-component';
import {FriendLinkService} from './service/friendlink-service';
import {FriendLinkTypeService} from './service/friendlink-type-service';
import {forkJoin} from 'rxjs';

@Component({
  selector: 'ngx-friend-link-detail',
  templateUrl: './friend-link-detail.component.html',
  styleUrls: ['./friend-link-detail.component.scss'],
})
export class FriendLinkDetailComponent extends BaseComponent implements OnInit {

  friendLink: any = {name: '', url: '', logo: '', remark: '', enabled: false, type: {id: ''}};  //  友情链接
  friendLinkId: any;
  private formId: string = 'friendLinkForm';     //   表单ID
  types: any;    //   类型列表

  constructor(protected injector: Injector, private ref: NbDialogRef<FriendLinkDetailComponent>,
              private linkService: FriendLinkService, private typeService: FriendLinkTypeService) {
    super(null, injector);
  }

  ngOnInit() {
    this.initValidator();
    const array = [this.getAllType(), this.getFriendLink()];
    forkJoin(array);
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

  getFriendLink() {
    return this.linkService.getData(this.friendLinkId).then(data => {
      this.friendLink = data;
      return Promise.resolve(true);
    });
  }

  /**
   * 类型列表
   */
  getAllType() {
    return this.typeService.getAllType().then(result => {
      this.types = result;
      return Promise.resolve(true);
    });
  }

  /**
   * LOGO上传
   * @param event
   */
  logoChange(event) {
    this.friendLink.logo = event.dest.path;
  }

  cancel() {
    this.ref.close();
  }

  submit() {
    if (this.isValidForm(this.formId)) {
      this.linkService.editData(this.friendLink).then(() => {
        this.ref.close(true);
      });
    }
  }
}
