import {Component, Injector, OnDestroy, OnInit} from '@angular/core';
import {NbDialogRef, NbDialogService} from '@nebular/theme';
import {Constant} from '../../../core/constant';
import {IconsComponent} from '../../icons/icons.component';
import {BaseComponent} from '../../../core/service/base-component';

@Component({
  selector: 'ngx-words-dictionary-detail',
  templateUrl: './dictionary-detail.component.html',
  styleUrls: ['./dictionary-detail.component.scss'],
})
export class DictionaryDetailComponent extends BaseComponent implements OnInit, OnDestroy {

  dictionary: any = {name: '', value: '', type: '', remark: '', icon: '', seq: 0};  //  数据字典类型
  private formId: string = 'dictionaryForm';     //   表单ID
  previewIcon: any;   //  图标预览
  dialog: NbDialogRef<any>;
  isRemove = false;

  constructor(protected injector: Injector, private ref: NbDialogRef<DictionaryDetailComponent>,
              private dialogService: NbDialogService) {
    super(null, injector);
  }

  ngOnDestroy(): void {
    if (this.dialog) {
      this.dialog.close();
    }
  }

  ngOnInit() {
    if (!this.dictionary.icon) {
      this.previewIcon = Constant.DEFAULT_PIC;
    } else {
      this.previewIcon = this.dictionary.url;
      this.isRemove = true;
    }
    this.initValidator();
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
      value: {
        validators: {
          notEmpty: {message: '值不能为空!'},
        },
      },
      type: {
        validators: {
          notEmpty: {message: '类型不能为空!'},
        },
      },
    });
  }

  cancel() {
    this.ref.close();
  }

  submit() {
    if (this.isValidForm(this.formId)) {
      this.ref.close(this.dictionary);
    }
  }

  removeIcon() {
    this.dictionary.icon = '';
    this.previewIcon = Constant.DEFAULT_PIC;
    this.isRemove = false;
  }

  chooseIcon() {
    this.dialog = this.dialogService.open(IconsComponent);
    this.dialog.onClose.subscribe(result => {
      if (result) {
        this.dictionary.icon = result.path;
        this.previewIcon = result.url;
        this.isRemove = true;
      }
    });
  }
}
