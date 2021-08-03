import {Component, Injector, Input, OnDestroy, OnInit} from '@angular/core';
import {BaseComponent} from '../../../core/service/base-component';
import {ChannelService} from '../service/channel-service';
import {NbDialogRef, NbDialogService, NbTreeGridDataSource, NbTreeGridDataSourceBuilder} from '@nebular/theme';
import {ChannelAddComponent} from './channel-add.component';
import {ChannelDetailComponent} from './channel-detail.component';
import {ModelService} from '../service/model-service';

interface TreeNode<T> {
  data: T;
  children?: TreeNode<T>[];
  expanded?: boolean;
}

interface FSEntry {
  id: string;
  name: string;
  alias: string;
  seq: string;
  path: string;
  link: string;
  display: string;
}

@Component({
  selector: 'ngx-content-channel',
  templateUrl: './channel.component.html',
  styleUrls: ['./channel.component.scss'],
})
export class ChannelComponent extends BaseComponent implements OnInit, OnDestroy {

  constructor(private channelService: ChannelService, protected injector: Injector,
              private modelService: ModelService,
              private dataSourceBuilder: NbTreeGridDataSourceBuilder<FSEntry>,
              private dialogService: NbDialogService) {
    super(channelService, injector);
  }

  private dialog: NbDialogRef<any>;
  customColumn = 'id';
  headerNames = {name: '名称', alias: '别名', id: 'ID', seq: '排序', path: '访问路径', link: '外部连接', display: '是否显示', op: '操作'};
  defaultColumns = ['name', 'alias', 'path', 'link', 'display', 'seq', 'op'];
  allColumns = [this.customColumn, ...this.defaultColumns];
  dataSource: NbTreeGridDataSource<FSEntry>;
  data: TreeNode<FSEntry>[] = [];

  ngOnInit() {
    this.getPager(1);
  }

  ngOnDestroy(): void {
    if (this.dialog) {
      this.dialog.close();
    }
  }

  getPager(pageNo): Promise<boolean> {
    this.data = [];
    return super.getPager(pageNo).then(result => {
      if (result) {
        result.data.forEach(item => {
          this.getTreeNode(this.data, item);
        });
      }
      this.dataSource = this.dataSourceBuilder.create(this.data);
      return Promise.resolve(true);
    });
  }

  /**
   * 显示添加栏目框
   */
  showAddChannel() {
    this.dialog = this.dialogService.open(ChannelAddComponent);
    this.dialog.onClose.subscribe(result => {
      if (result) {
        this.getPager(1);
      }
    });
  }

  /**
   * 显示编辑栏目框
   * @param id
   */
  showEditChannel(id: string) {
    this.dialog = this.dialogService.open(ChannelDetailComponent);
    this.dialog.componentRef.instance.channelId = id;
    this.dialog.onClose.subscribe(result => {
      if (result) {
        this.getPager(1);
      }
    });
  }

  getShowOn(index: number) {
    const minWithForMultipleColumns = 400;
    const nextColumnStep = 100;
    return minWithForMultipleColumns + (nextColumnStep * index);
  }

  getFSEntry(channel: any): FSEntry {
    if (!channel) {
      return;
    }
    const item: FSEntry = {
      id: channel.id, name: channel.name, alias: channel.alias, seq: channel.seq,
      display: (channel.display ? '显示' : '隐藏'), path: channel.path, link: channel.link,
    };
    return item;
  }

  getTreeNode(nodes: TreeNode<FSEntry>[], channel: any) {
    if (!channel) {
      return;
    }
    const item: FSEntry = this.getFSEntry(channel);
    const node: TreeNode<FSEntry> = {data: item, children: [], expanded: true};
    if (channel.children) {
      channel.children.forEach(child => {
        this.getTreeNode(node.children, child);
      });
    }
    nodes.push(node);
  }
}

@Component({
  selector: 'ngx-fs-icon',
  template: `
    <nb-tree-grid-row-toggle [expanded]="expanded" *ngIf="isDir(); else fileIcon">
    </nb-tree-grid-row-toggle>
    <ng-template #fileIcon>
      <em class="{{length>0?(!expanded?'fa fa-angle-up':'fa fa-angle-down'):''}}"></em>
    </ng-template>
  `,
})
export class FsIconComponent {
  @Input() length: any;
  @Input() expanded: boolean;

  isDir(): boolean {
    return parseInt(this.length, 0) > 0;
  }
}
