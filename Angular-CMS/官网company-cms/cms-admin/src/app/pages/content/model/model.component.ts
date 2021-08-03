import {Component, Injector, OnDestroy, OnInit} from '@angular/core';
import {BaseComponent} from '../../../core/service/base-component';
import {ModelService} from '../service/model-service';
import {NbDialogRef, NbDialogService} from '@nebular/theme';
import {ModelAddComponent} from './model-add.component';
import {ModelDetailComponent} from './model-detail.component';

@Component({
  selector: 'ngx-content-model',
  templateUrl: './model.component.html',
  styleUrls: ['./model.component.scss'],
})
export class ModelComponent extends BaseComponent implements OnInit, OnDestroy {

  constructor(private modelService: ModelService, protected injector: Injector,
              private dialogService: NbDialogService) {
    super(modelService, injector);
  }

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
   * 更新状态
   * @param id
   * @param enabled
   */
  editEnabled(id: string, enabled: boolean) {
    const msg = '您确认要进行'.concat(enabled === true ? '启用' : '禁用', '该操作吗?');
    this.modalUtil.confirm('提示', msg).then(r => {
      if (r) {
        this.modelService.editEnabled(id, enabled + '').then(() => {
          this.toastUtil.showSuccess('操作成功!');
          this.getPager(1);
        });
      }
    });
  }

  /**
   * 显示添加模型框
   */
  showAddModel() {
    this.dialog = this.dialogService.open(ModelAddComponent);
    this.dialog.onClose.subscribe(result => {
      if (result) {
        this.getPager(1);
      }
    });
  }

  /**
   * 显示编辑模型框
   * @param id
   */
  showEditModel(id: string) {
    this.dialog = this.dialogService.open(ModelDetailComponent);
    this.dialog.componentRef.instance.modelId = id;
    this.dialog.onClose.subscribe(result => {
      if (result) {
        this.getPager(1);
      }
    });
  }
}
