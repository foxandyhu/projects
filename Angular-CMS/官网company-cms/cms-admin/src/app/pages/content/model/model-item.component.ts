import {Component, Injector, OnDestroy, OnInit} from '@angular/core';
import {BaseComponent} from '../../../core/service/base-component';
import {ModelItemService} from '../service/model-item-service';
import {NbDialogRef, NbDialogService} from '@nebular/theme';
import {ModelItemAddComponent} from './model-item-add.component';
import {ModelItemDetailComponent} from './model-item-detail.component';
import {ActivatedRoute} from '@angular/router';
import {forkJoin} from 'rxjs';
import {ModelService} from '../service/model-service';

@Component({
  selector: 'ngx-content-model-item',
  templateUrl: './model-item.component.html',
  styleUrls: ['./model-item.component.scss'],
})
export class ModelItemComponent extends BaseComponent implements OnInit, OnDestroy {

  constructor(private modelItemService: ModelItemService, private modelService: ModelService,
              protected injector: Injector,
              private dialogService: NbDialogService, private rout: ActivatedRoute) {
    super(modelItemService, injector);
  }

  defaultModelItem: Array<any>; //  系统默认模型
  model: any;  //  模型
  modelId: string; //  模型ID
  private dialog: NbDialogRef<any>;

  ngOnInit() {
    this.rout.paramMap.subscribe(params => {
      this.modelId = params.get('modelId');
      this.getModel();
      this.initData();
    });
  }

  ngOnDestroy(): void {
    if (this.dialog) {
      this.dialog.close();
    }
  }


  getPager(pageNo: number): Promise<any> {
    this.initData();
    return null
      ;
  }

  /**
   * 初始化数据
   */
  initData() {
    const arr = [this.loadModelItems(this.modelId), this.loadDefaultModelItem()];
    const observable = forkJoin(arr);
    observable.subscribe(() => {
      this.list.forEach(item => {
        this.defaultModelItem = this.defaultModelItem.filter(defaultItem => {
          return item.field !== defaultItem.field;
        });
      });
    });
  }

  /**
   * 获得模型对象
   */
  getModel() {
    this.modelService.getData(this.modelId).then(result => {
      this.model = result;
    });
  }

  /**
   * 模型项集合
   * @param modelId
   */
  loadModelItems(modelId): Promise<any> {
    return this.modelItemService.getModelItems(modelId).then(result => {
      this.list = result;
      return Promise.resolve(result);
    });
  }

  /**
   * 系统默认模型项
   */
  loadDefaultModelItem(): Promise<any> {
    return this.modelItemService.defaultModelItems().then(result => {
      this.defaultModelItem = result;
      return Promise.resolve(result);
    });
  }

  /**
   * 显示添加模型项框
   */
  showAddModelItem() {
    this.dialog = this.dialogService.open(ModelItemAddComponent);
    this.dialog.onClose.subscribe(result => {
      if (result) {
        result.modelId = this.modelId;
        this.modelItemService.saveData(result).then(() => {
          this.loadModelItems(this.modelId);
        });
      }
    });
  }

  /**
   * 显示编辑模型项框
   * @param id
   */
  showEditModelItem(id: string) {
    this.dialog = this.dialogService.open(ModelItemDetailComponent);
    this.modelItemService.getData(id).then(result => {
      this.dialog.componentRef.instance.setData(result);
    });
    this.dialog.onClose.subscribe(result => {
      if (result) {
        this.modelItemService.editData(result).then(() => {
          this.loadModelItems(this.modelId);
        });
      }
    });
  }

  /**
   * 删除模型项
   * @param id
   */
  delModelItem(id: string) {
    super.del(id).then(() => {
      this.initData();
    });
  }

  /**
   * 批量删除
   */
  delMutilModelItem() {
    super.delMulti().then(() => {
      this.initData();
    });
  }

  /**
   * 绑定系统模型
   * @param defaultModelItemId
   */
  bindModel(defaultModelItemId: string) {
    this.modelItemService.bindDefaultModelItem(this.modelId, [defaultModelItemId]).then(() => {
      this.initData();
    });
  }
}
