import {Component, Injector, OnInit, ViewChild} from '@angular/core';
import {NbDialogService} from '@nebular/theme';
import {ResDirAddComponent} from './res-dir-add.component';
import {BaseComponent} from '../../../core/service/base-component';
import {ResourceService} from '../service/resource-service';

@Component({
  selector: 'ngx-system-resource',
  templateUrl: './resource.component.html',
  styleUrls: ['./resource.component.scss'],
})
export class ResourceComponent extends BaseComponent implements OnInit {

  constructor(private resourceService: ResourceService, protected injector: Injector,
              private dialogService: NbDialogService) {
    super(resourceService, injector);
  }

  nodes: any;     // tree 数据集合
  currntNode: any;    //  tree 节点对象
  @ViewChild('resTree') resTree: any;

  ngOnInit() {
  }

  getResource(data) {
    this.nodes = data;
    this.selectItems = new Array<any>();
  }

  /**
   * 显示当前路径
   * @param data
   */
  getCurrentPath(data) {
    this.currntNode = data;
  }

  /**
   * 新建目录
   */
  mkdir() {
    this.dialogService.open(ResDirAddComponent).onClose.subscribe(name => {
      if (name) {
        this.resourceService.mkdir(this.currntNode.path, name).then(() => {
          this.toastUtil.showSuccess('新建目录成功!');
          this.resTree.loadData(this.currntNode, this.currntNode.path);
        });
      }
    });
  }

  /**
   * 上传文件
   * @param event
   */
  fileChange(event) {
    const file = event.currentTarget.files[0];
    if (file) {
      this.resourceService.uploadFile(this.currntNode.path, file).then(() => {
        this.toastUtil.showSuccess('上传成功!');
        this.resTree.loadData(this.currntNode, this.currntNode.path);
      });
    }
  }

  /**
   * 进入指定目录
   * @param path
   */
  cdDir(path: string) {
    this.resTree.choosePathToLoadData(path);
  }

  delRes(path) {
    super.del(path).then(result => {
      if (result) {
        this.resTree.loadData(this.currntNode, this.currntNode.path);
      }
    });
  }

  delMutilRes() {
    super.delMulti().then(result => {
      if (result) {
        this.resTree.loadData(this.currntNode, this.currntNode.path);
      }
    });
  }
}
