import {Component, Injector, OnInit} from '@angular/core';
import {NbDialogRef} from '@nebular/theme';
import {BaseComponent} from '../../../core/service/base-component';
import {ScoreItemService} from '../service/score-item-service';
import {Constant} from '../../../core/constant';

@Component({
  selector: 'ngx-score-item-add',
  templateUrl: './score-item-add.component.html',
  styleUrls: ['./score-item-add.component.scss'],
})
export class ScoreItemAddComponent extends BaseComponent implements OnInit {

  scoreItem: any = {name: '', score: 0, pic: '', group: {id: 0}};  //  评分项
  scoreGroup: any = {name: '', id: ''};   //  评分组
  private formId: string = 'scoreItemForm'; //   表单ID
  preview: any;   //  图像预览

  constructor(private scoreItemService: ScoreItemService, protected injector: Injector,
              private ref: NbDialogRef<ScoreItemAddComponent>) {
    super(null, injector);
  }

  ngOnInit() {
    this.preview = Constant.DEFAULT_PIC;
    this.initValidator();
  }

  /**
   * 初始化表单验证
   */
  initValidator() {
    this.initValidateForm(this.formId, {
      name: {
        validators: {
          notEmpty: {message: '评分项名称不能为空!'},
        },
      },
      score: {
        validators: {
          notEmpty: {message: '评分值不能为空!'},
          digits: {min: 0, message: '评分值最小为0!'},
        },
      },
    });
  }

  cancel() {
    this.ref.close();
  }

  submit() {
    if (this.isValidForm(this.formId)) {
      this.scoreItem.group = this.scoreGroup;
      this.scoreItemService.saveData(this.scoreItem).then(() => {
        this.ref.close(true);
      });
    }
  }

  /**
   * 头像上传
   * @param event
   */
  fileChange(event) {
    this.scoreItem.pic = event.dest.path;
  }
}
