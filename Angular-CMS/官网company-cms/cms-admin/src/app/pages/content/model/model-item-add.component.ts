import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../../core/service/base-component';
import {NbDialogRef} from '@nebular/theme';
import {Constant} from '../../../core/constant';

@Component({
  selector: 'ngx-content-model-item-add',
  templateUrl: './model-item-add.component.html',
  styleUrls: ['./model-item-add.component.scss'],
})
export class ModelItemAddComponent extends BaseComponent implements OnInit {

  constructor(protected injector: Injector, private ref: NbDialogRef<ModelItemAddComponent>) {
    super(null, injector);
  }

  modelItem: any = {
    field: '', dataType: '', name: '',
    defValue: '', optValue: '', remark: '', single: false, required: false,
  };  //  模型项
  formId: string = 'modelItemForm';
  dataTypes: Array<any> = Constant.DATA_TYPES;
  disabledDef: boolean = false;

  ngOnInit() {
    this.initValidator();
  }

  /**
   * 类型选择转换
   */
  dataTypeChange() {
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
      field: {
        validators: {
          notEmpty: {message: '字段名不能为空!'},
        },
      },
      dataType: {
        validators: {
          notEmpty: {message: '请选择数据类型!'},
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
