import {Component, Injector, OnInit} from '@angular/core';
import {NbDialogRef} from '@nebular/theme';
import {BaseComponent} from '../../../core/service/base-component';

@Component({
  selector: 'ngx-template-dir-add',
  templateUrl: 'template-dir-add.component.html',
  styleUrls: ['template-dir-add.component.scss'],
})
export class TemplateDirAddComponent extends BaseComponent implements OnInit {

  constructor(protected injector: Injector, protected ref: NbDialogRef<TemplateDirAddComponent>) {
    super(null, injector);
  }

  private formId: string = 'dirForm';
  name: string;

  ngOnInit() {
    this.initValidator();
  }

  /**
   * 初始化表单验证
   */
  initValidator() {
    this.initValidateForm(this.formId, {
      name: {
        validators: {
          notEmpty: {message: '目录名称不能为空!'},
        },
      },
    });
  }

  cancel() {
    this.ref.close();
  }

  submit() {
    if (this.isValidForm(this.formId)) {
      this.ref.close(this.name);
    }
  }
}
