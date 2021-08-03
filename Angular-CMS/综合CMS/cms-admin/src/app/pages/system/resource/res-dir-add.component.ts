import {Component, Injector, OnInit} from '@angular/core';
import {NbDialogRef} from '@nebular/theme';
import {BaseComponent} from '../../../core/service/base-component';

@Component({
  selector: 'ngx-resource-dir-add',
  templateUrl: 'res-dir-add.component.html',
  styleUrls: ['res-dir-add.component.scss'],
})
export class ResDirAddComponent extends BaseComponent implements OnInit {

  constructor(protected injector: Injector, protected ref: NbDialogRef<ResDirAddComponent>) {
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
