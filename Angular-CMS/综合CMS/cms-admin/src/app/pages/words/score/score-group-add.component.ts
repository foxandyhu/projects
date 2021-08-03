import {Component, Injector, OnInit} from '@angular/core';
import {NbDialogRef} from '@nebular/theme';
import {BaseComponent} from '../../../core/service/base-component';
import {ScoreGroupService} from '../service/score-group-service';

@Component({
  selector: 'ngx-score-group-add',
  templateUrl: './score-group-add.component.html',
  styleUrls: ['./score-group-add.component.scss'],
})
export class ScoreGroupAddComponent extends BaseComponent implements OnInit {

  scoreGroup: any = {name: '', remark: ''};  //  评分组
  private formId: string = 'scoreGroupForm'; //   表单ID

  constructor(private scoreGroupService: ScoreGroupService,
              protected injector: Injector, private ref: NbDialogRef<ScoreGroupAddComponent>) {
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
          notEmpty: {message: '评分组名称不能为空!'},
        },
      },
    });
  }

  cancel() {
    this.ref.close();
  }

  submit() {
    if (this.isValidForm(this.formId)) {
      this.scoreGroupService.saveData(this.scoreGroup).then(() => {
        this.ref.close(true);
      });
    }
  }
}
