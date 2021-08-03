import {Component, Injector, OnInit} from '@angular/core';
import {NbDialogRef} from '@nebular/theme';
import {BaseComponent} from '../../../core/service/base-component';
import {TemplateService} from '../service/template-service';

@Component({
  selector: 'ngx-system-template-detail',
  templateUrl: './template-detail.component.html',
  styleUrls: ['./template-detail.component.scss'],
})
export class TemplateDetailComponent extends BaseComponent implements OnInit {

  constructor(private templateService: TemplateService, protected injector: Injector,
              private ref: NbDialogRef<TemplateDetailComponent>) {
    super(templateService, injector);
  }

  path: any;
  content: string; //  内容
  ngOnInit() {
    this.templateService.getData(this.path).then(result => {
      this.content = result;
    });
  }

  cancel() {
    this.ref.close();
  }

  submit() {
    this.templateService.editData({path: this.path, content: this.content}).then(() => {
      this.toastUtil.showSuccess('保存成功!');
      this.ref.close(this.content);
    });
  }
}
