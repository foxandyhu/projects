import {Component, Injector, OnInit, ViewChild} from '@angular/core';
import {BaseComponent} from '../../../core/service/base-component';
import {TemplateService} from '../service/template-service';
import {NbDialogService} from '@nebular/theme';
import {TemplateDirAddComponent} from './template-dir-add.component';
import {TemplateDetailComponent} from './template-detail.component';

@Component({
  selector: 'ngx-system-template',
  templateUrl: './template.component.html',
  styleUrls: ['./template.component.scss'],
})
export class TemplateComponent extends BaseComponent implements OnInit {

  constructor(private templateService: TemplateService, protected injector: Injector,
              private dialogService: NbDialogService) {
    super(templateService, injector);
  }

  nodes: any;     // tree 数据集合
  currentNode: any;    //  tree 节点对象
  @ViewChild('resTree') resTree: any;

  ngOnInit() {
  }

  getTemplate(data) {
    this.selectItems = new Array<any>();
    this.nodes = data;
  }

  /**
   * 显示当前路径
   * @param data
   */
  getCurrentPath(data) {
    this.currentNode = data;
  }

  /**
   * 新建目录
   */
  mkdir() {
    this.dialogService.open(TemplateDirAddComponent).onClose.subscribe(name => {
      if (name) {
        this.templateService.mkdir(this.currentNode.path, name).then(() => {
          this.toastUtil.showSuccess('新建目录成功!');
          this.resTree.loadData(this.currentNode, this.currentNode.path);
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
      this.templateService.uploadFile(this.currentNode.path, file).then(() => {
        this.toastUtil.showSuccess('上传成功!');
        this.resTree.loadData(this.currentNode, this.currentNode.path);
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
        this.resTree.loadData(this.currentNode, this.currentNode.path);
      }
    });
  }

  delMultiRes() {
    super.delMulti().then(result => {
      if (result) {
        this.resTree.loadData(this.currentNode, this.currentNode.path);
      }
    });
  }

  /**
   * 弹出编辑模版页面
   */
  showEditTemplate(path) {
    const ref = this.dialogService.open(TemplateDetailComponent);
    ref.componentRef.instance.path = path;
    ref.onClose.subscribe(result => {});
  }
}
