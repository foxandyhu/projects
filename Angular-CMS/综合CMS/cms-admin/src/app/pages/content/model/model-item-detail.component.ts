import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../../core/service/base-component';
import {NbDialogRef} from '@nebular/theme';
import {Constant} from '../../../core/constant';

@Component({
  selector: 'ngx-content-model-item-detail',
  templateUrl: './model-item-detail.component.html',
  styleUrls: ['./model-item-detail.component.scss'],
})
export class ModelItemDetailComponent extends BaseComponent implements OnInit {

  constructor(protected injector: Injector, private ref: NbDialogRef<ModelItemDetailComponent>) {
    super(null, injector);
  }

  modelItem: any = {
    field: '', dataType: '', name: '',
    defValue: '', optValue: '', remark: '', single: false, required: false,
  };  //  模型项
  formId: string = 'modelItemForm';
  disabledDef: boolean = false;

  ngOnInit() {
    this.initValidator();
  }

  setData(modelItem: any) {
    this.modelItem = modelItem;
    const dateType = parseInt(this.modelItem.dataType, 0);
    if (dateType === Constant.DATA_TYPES[6].id) {
      this.disabledDef = true;
      this.modelItem.defValue = '';
      this.modelItem.optValue = '';
    } else {
      this.disabledDef = false;
    }
  }

  /**
   * 初始化表单验证
   */
  initValidator() {
    this.initValidateForm(this.formId, {
      name: {
        validators: {
          notEmpty: {message: '名称不能为空!'},
        },
      },
    });
  }

  cancel() {
    this.ref.close();
  }

  submit() {
    if (this.isValidForm(this.formId)) {
      this.ref.close(this.modelItem);
    }
  }
}
