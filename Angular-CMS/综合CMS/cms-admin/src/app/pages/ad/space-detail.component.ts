import {Component, Injector, OnInit} from '@angular/core';
import {NbDialogRef} from '@nebular/theme';
import {BaseComponent} from '../../core/service/base-component';
import {AdSpaceService} from './service/ad-space.service';

@Component({
  selector: 'ngx-ad-space-detail',
  templateUrl: './space-detail.component.html',
  styleUrls: ['./space-detail.component.scss'],
})
export class SpaceDetailComponent extends BaseComponent implements OnInit {

  adSpace: any = {name: '', remark: '', enabled: false};  //  广告位
  spaceId: any;
  private formId: string = 'adSpaceForm'; //   表单ID

  constructor(private adSpaceService: AdSpaceService,
              private ref: NbDialogRef<SpaceDetailComponent>, protected injector: Injector) {
    super(null, injector);
  }

  ngOnInit() {
    this.initValidator();
    this.getAdSpace();
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

  getAdSpace() {
    this.adSpaceService.getData(this.spaceId).then(data => {
      this.adSpace = data;
    });
  }

  cancel() {
    this.ref.close();
  }

  submit() {
    if (this.isValidForm(this.formId)) {
      this.adSpaceService.editData(this.adSpace).then(() => {
        this.ref.close(true);
      });
    }
  }
}
