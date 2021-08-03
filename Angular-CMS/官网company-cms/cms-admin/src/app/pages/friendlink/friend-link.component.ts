import {Component, Injector, OnDestroy, OnInit} from '@angular/core';
import {BaseComponent} from '../../core/service/base-component';
import {FriendLinkService} from './service/friendlink-service';
import {FriendLinkTypeService} from './service/friendlink-type-service';
import {NbDialogRef, NbDialogService} from '@nebular/theme';
import {FriendLinkAddComponent} from './friend-link-add.component';
import {FriendLinkDetailComponent} from './friend-link-detail.component';

@Component({
  selector: 'ngx-friend-link',
  templateUrl: './friend-link.component.html',
})
export class FriendLinkComponent extends BaseComponent implements OnInit, OnDestroy {

  constructor(private linkService: FriendLinkService, private typeService: FriendLinkTypeService,
              protected injector: Injector, private dialogService: NbDialogService) {
    super(linkService, injector);
  }

  type: string = '';  //  类型
  types: any;    //   类型列表
  private dialog: NbDialogRef<any>;
  friendLinks: any;

  ngOnInit() {
    this.changeType();
    this.getAllType();
  }

  ngOnDestroy(): void {
    if (this.dialog) {
      this.dialog.close();
    }
  }

  /**
   * 类型选择
   */
  changeType() {
    this.setQueryParams('type', this.type);
    this.getPager(1);
  }

  getPager(pageNo: number): Promise<any> {
    return super.getPager(pageNo).then(data => {
      if (data) {
        this.friendLinks = data.data;
        this.friendLinks.forEach(item => {
          if (!item.logo) {
            item.logoUrl = 'assets/images/no-pic.gif';
          }
        });
      }
    });
  }

  /**
   * 类型列表
   */
  getAllType() {
    this.typeService.getAllType().then(result => {
      this.types = result;
    });
  }

  /**
   * 显示添加友情链接弹框
   */
  showAddFriendLink() {
    this.dialog = this.dialogService.open(FriendLinkAddComponent);
    this.dialog.onClose.subscribe(result => {
      if (result) {
        this.changeType();
      }
    });
  }

  /**
   * 显示编辑友情链接弹框
   */
  showEditFriendLink(id: string) {
    this.dialog = this.dialogService.open(FriendLinkDetailComponent);
    this.dialog.componentRef.instance.friendLinkId = id;
    this.dialog.onClose.subscribe(result => {
      if (result) {
        this.changeType();
      }
    });
  }
}
