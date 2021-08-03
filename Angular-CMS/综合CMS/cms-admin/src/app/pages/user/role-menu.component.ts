import {Component, OnInit} from '@angular/core';
import '@ztree/ztree_v3';
import {NbDialogRef} from '@nebular/theme';

declare var jQuery: any;

@Component({
  selector: 'ngx-role-menu',
  templateUrl: './role-menu.component.html',
  styleUrls: ['./role-menu.component.scss'],
})
export class RoleMenuComponent implements OnInit {

  constructor(private ref: NbDialogRef<RoleMenuComponent>) {
  }

  ngOnInit() {
    jQuery.fn.zTree.init(jQuery('#menuTree'), this.setting, this.zNodes);
  }

  roleName: string;     //  角色名称
  setting = {check: {enable: true}, data: {simpleData: {enable: true}}};

  private zNodes = [];

  /**
   * 设置数据
   * @param data
   */
  setData(data: any) {
    this.zNodes = data;
    jQuery.fn.zTree.getZTreeObj('menuTree').addNodes(null, this.zNodes);
  }

  cancel() {
    this.ref.close();
  }

  submit() {
    const nodes = jQuery.fn.zTree.getZTreeObj('menuTree').getCheckedNodes(true);
    const ids = new Array();
    for (const node of nodes) {
      ids.push({id: node.id});
    }
    this.ref.close(ids);
  }
}
