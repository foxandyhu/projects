import {Component, Injector, Input, OnDestroy, OnInit} from '@angular/core';
import {NbDialogRef, NbDialogService, NbTreeGridDataSource, NbTreeGridDataSourceBuilder} from '@nebular/theme';
import {MenuAddComponent} from './menu-add.component';
import {BaseComponent} from '../../../core/service/base-component';
import {MenuService} from '../service/menu-service';
import {MenuDetailComponent} from './menu-detail.component';

interface TreeNode<T> {
  data: T;
  children?: TreeNode<T>[];
  expanded?: boolean;
}

interface FSEntry {
  id: number;
  seq: number;
  name: string;
  url: string;
  icon: string;
  action: boolean;
}

@Component({
  selector: 'ngx-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss'],
})
export class MenuComponent extends BaseComponent implements OnInit, OnDestroy {

  constructor(private menuService: MenuService, protected injector: Injector,
              private dialogService: NbDialogService,
              private dataSourceBuilder: NbTreeGridDataSourceBuilder<FSEntry>) {
    super(menuService, injector);
  }

  private dialog: NbDialogRef<any>;
  customColumn = 'id';
  headerNames = {id: 'ID', name: '菜单名称', url: '访问路径', seq: '排序', icon: '图标', action: '是否是功能菜单', op: '操作'};
  defaultColumns = ['name', 'url', 'seq', 'op'];
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

  getFSEntry(menu: any): FSEntry {
    if (!menu) {
      return;
    }
    const item: FSEntry = {
      id: menu.id,
      name: menu.name,
      url: menu.url,
      seq: menu.seq,
      icon: menu.icon,
      action: menu.action,
    };
    return item;
  }

  getTreeNode(nodes: TreeNode<FSEntry>[], channel: any) {
    if (!channel) {
      return;
    }
    const item: FSEntry = this.getFSEntry(channel);
    const node: TreeNode<FSEntry> = {data: item, children: [], expanded: false};
    if (channel.children) {
      channel.children.forEach(child => {
        this.getTreeNode(node.children, child);
      });
    }
    nodes.push(node);
  }

  getShowOn(index: number) {
    const minWithForMultipleColumns = 200;
    const nextColumnStep = 100;
    return minWithForMultipleColumns + (nextColumnStep * index);
  }

  /**
   * 弹出添加菜单模态框
   */
  showAddNode(parentId: number) {
    this.dialog = this.dialogService.open(MenuAddComponent);
    this.dialog.componentRef.instance.menu.parentId = parentId;
    this.dialog.onClose.subscribe(result => {
      if (result) {
        this.getPager(1);
      }
    });
  }

  /**
   * 弹出编辑菜单模态框
   */
  showEditNode(menuId: any) {
    this.dialog = this.dialogService.open(MenuDetailComponent);
    this.dialog.componentRef.instance.menu.id = menuId;
    this.dialog.onClose.subscribe(result => {
      if (result) {
        this.getPager(1);
      }
    });
  }
}

@Component({
  selector: 'ngx-menu-icon',
  template: `
    <nb-tree-grid-row-toggle [expanded]="expanded" *ngIf="isDir(); else fileIcon">
    </nb-tree-grid-row-toggle>
    <ng-template #fileIcon>
      <em class="{{length>0?(!expanded?'fa fa-folder':'fa fa-folder-open'):''}}"></em>
    </ng-template>
  `,
})
export class MenuIconComponent {
  @Input() length: any;
  @Input() expanded: boolean;

  isDir(): boolean {
    return parseInt(this.length, 0) > 0;
  }
}
