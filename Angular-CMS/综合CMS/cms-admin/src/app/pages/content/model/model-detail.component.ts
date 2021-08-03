import {Component, Injector, OnInit} from '@angular/core';
import {NbDialogRef} from '@nebular/theme';
import {BaseComponent} from '../../../core/service/base-component';
import {ModelService} from '../service/model-service';

@Component({
  selector: 'ngx-content-model-detail',
  templateUrl: './model-detail.component.html',
  styleUrls: ['./model-detail.component.scss'],
})
export class ModelDetailComponent extends BaseComponent implements OnInit {

  constructor(private modelService: ModelService,
              protected injector: Injector, private ref: NbDialogRef<ModelDetailComponent>) {
    super(null, injector);
  }

  model: any = {name: '',  enabled: true, hasContent: true, remark: ''}; //  模型
  modelId: any;
  formId: string = 'modelForm';

  ngOnInit() {
    this.initValidator();
    this.loadModel();
  }

  loadModel() {
    this.modelService.getData(this.modelId).then(result => {
      this.model = result;
    });
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
      tplDir: {
        validators: {
          notEmpty: {message: '模版文件夹名称不能为空!'},
        },
      },
    });
  }

  cancel() {
    this.ref.close();
  }

  submit() {
    if (this.isValidForm(this.formId)) {
      this.modelService.editData(this.model).then(() => {
        this.ref.close(true);
      });
    }
  }
}
