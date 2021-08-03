import {Component, Injector, OnDestroy, OnInit} from '@angular/core';
import {BaseComponent} from '../../core/service/base-component';
import {MemberService} from './service/member-service';
import {NbDialogRef, NbDialogService} from '@nebular/theme';
import {MemberEditPasswordComponent} from './edit-password.component';

@Component({
  selector: 'ngx-member',
  templateUrl: './member.component.html',
})
export class MemberComponent extends BaseComponent implements OnInit, OnDestroy {

  constructor(private memberService: MemberService, protected injector: Injector,
              private dialogService: NbDialogService) {
    super(memberService, injector);
  }

  /**
   * 所有状态
   */
  statuss: any = [{id: 0, name: '待审核'}, {id: 1, name: '正常'}, {id: 2, name: '已禁用'}];
  params: any = {status: '', userName: '', email: ''};    //  搜索条件
  private dialog: NbDialogRef<any>;

  ngOnInit() {
    this.getPager(1);
  }

  ngOnDestroy(): void {
    if (this.dialog) {
      this.dialog.close();
    }
  }

  /**
   * 搜索
   */
  search() {
    for (const key in this.params) {
      if (this.params.hasOwnProperty(key)) {
        this.setQueryParams(key, this.params[key]);
      }
    }
    this.getPager(1);
  }

  /**
   * 修改状态
   */
  editStatus(memberId: string, status: string) {
    this.modalUtil.confirm('提示', '您确认要修改该账户状态吗?').then(result => {
      if (result) {
        this.memberService.editStatus(memberId, status).then(() => {
          this.toastUtil.showSuccess('状态修改成功!');
          this.search();
        });
      }
    });
  }

  /**
   * 修改密码
   * @param memberId
   */
  showEditPassword(memberId: string) {
    this.dialog = this.dialogService.open(MemberEditPasswordComponent);
    this.dialog.componentRef.instance.data.memberId = memberId;
    this.dialog.onClose.subscribe(() => {
    });
  }
}

