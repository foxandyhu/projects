import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {ChannelService} from '../service/channel-service';
import '@ztree/ztree_v3';

declare var jQuery: any;

@Component({
  selector: 'ngx-content-article-channel-tree',
  templateUrl: './channel-tree.component.html',
})
export class ChannelTreeComponent implements OnInit {

  constructor(private channelService: ChannelService) {
    ChannelTreeComponent.component = this;
  }

  @Output() private sendData = new EventEmitter(true);
  @Output() private sendTreeData = new EventEmitter(true);
  private channelTree: any;
  private static component: ChannelTreeComponent;
  private rootNode = [{id: 0, isDir: true, open: true, path: '/', name: '根栏目', children: []}];
  private setting = {
    check: {enable: false},
    data: {keep: {parent: true}, simpleData: {idKey: 'id', pIdKey: 'parentId', rootPId: 0, enable: true}},
    callback: {
      onClick: function (event, treeId, treeNode) {
        ChannelTreeComponent.component.eventTreeNode(treeNode);
      },
    },
  };

  ngOnInit() {
    jQuery.fn.zTree.init(jQuery('#channelTree'), this.setting, this.rootNode);
    this.channelTree = jQuery.fn.zTree.getZTreeObj('channelTree');
    this.initChannel();
  }

  /**
   * 初始化栏目
   */
  initChannel() {
    this.channelService.getAllChannels().then(result => {
      const root = this.channelTree.getNodes();
      if (result) {
        result.data.forEach(item => {
          if (item.children.length === 0) {
              delete item.children;
          }
        });
        this.channelTree.addNodes(root[0], result.data);
        this.sendTreeData.emit(result.data);
      }
    });
  }

  /**
   * TreeNode事件
   * @param treeNode
   */
  eventTreeNode(treeNode: any) {
    this.sendData.emit(treeNode);
  }
}
