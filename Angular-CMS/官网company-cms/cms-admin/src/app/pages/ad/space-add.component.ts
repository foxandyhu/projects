import {Component, Injector, OnInit} from '@angular/core';
import {NbDialogRef} from '@nebular/theme';
import {BaseComponent} from '../../core/service/base-component';
import {AdSpaceService} from './service/ad-space.service';

@Component({
  selector: 'ngx-ad-space-add',
  templateUrl: './space-add.component.html',
  styleUrls: ['./space-add.component.scss'],
})
export class SpaceAddComponent extends BaseComponent implements OnInit {

  adSpace: any = {name: '', remark: '', enabled: false};  //  广告位
  private formId: string = 'adSpaceForm'; //   表单ID

  constructor(private adSpaceService: AdSpaceService,
              private ref: NbDialogRef<SpaceAddComponent>, protected injector: Injector) {
    super(null, injector);
  }

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
          notEmpty: {message: '广告位名称不能为空!'},
        },
      },
    });
  }

  cancel() {
    this.ref.close();
  }

  submit() {
    if (this.isValidForm(this.formId)) {
      this.adSpaceService.saveData(this.adSpace).then(() => {
        this.ref.close(true);
      });
    }
  }
}
