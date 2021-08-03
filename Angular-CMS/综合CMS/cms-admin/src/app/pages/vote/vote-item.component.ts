import {Component, Injector, OnInit} from '@angular/core';
import {DomSanitizer} from '@angular/platform-browser';
import {CommonService} from '../../core/service/common-service';
import {NbDialogRef} from '@nebular/theme';
import {BaseComponent} from '../../core/service/base-component';
import {Constant} from '../../core/constant';

declare var jQuery: any;

@Component({
  selector: 'ngx-vote-item',
  templateUrl: './vote-item.component.html',
  styleUrls: ['./vote-item.component.scss'],
})
export class VoteItemComponent extends BaseComponent implements OnInit {

  constructor(protected injector: Injector, private domSanitizer: DomSanitizer, private commonService: CommonService,
              private ref: NbDialogRef<VoteItemComponent>) {
    super(null, injector);
  }

  formId: string = 'voteItemForm';
  types: any = [{id: 1, name: '添加单选题'}, {id: 2, name: '添加多选题'}, {id: 3, name: '添加问答题'}];
  type: any;
  subTopic: any = {title: '', type: '', seq: 0, voteItems: []}; //  子项题目
  preview: any;   //  头像预览
  items = [{title: '', seq: 1, picture: '', preview: Constant.DEFAULT_PIC}];

  ngOnInit() {
    this.preview = Constant.DEFAULT_PIC;
    this.subTopic.type = this.type;
    this.initValidator();
  }

  /**
   * 初始化表单验证
   */
  initValidator() {
    this.initValidateForm(this.formId, {
      subTopicTitle: {
        validators: {
          notEmpty: {message: '问题标题不能为空!'},
        },
      },
      itemTitle: {
        validators: {
          notEmpty: {message: '问题答案选项不能为空!'},
        },
      },
    });
  }

  /**
   * 图片上传
   * @param event
   */
  fileChange(event, item) {
    item.preview = event.dest.url;
    item.picture = event.dest.path;
  }

  cancel() {
    this.ref.close();
  }

  /**
   * 确定
   */
  submit() {
    this.formValid.addField('itemTitle', {
      validators: {
        notEmpty: {message: '问题答案选项不能为空!'},
      },
    });
    if (this.isValidForm(this.formId)) {
      if (this.type !== this.types[2].id) {
        if (this.items.length === 0) {
          this.toastUtil.showDanger('请添加答案选项!');
          return;
        }
      }
      this.items.forEach(value => delete value.preview);
      this.subTopic.voteItems = this.items;
      this.ref.close(this.subTopic);
    }
  }

  /**
   * 动态添加元素
   */
  addCount() {
    const seq = this.items.length + 1;
    this.items.push({title: '', seq: seq, picture: '', preview: Constant.DEFAULT_PIC});
    this.formValid.addField('itemTitle', {
      validators: {
        notEmpty: {message: '问题答案选项不能为空!'},
      },
    });
  }

  /**
   * 删除原生
   */
  removeCount(seq) {
    const array = new Array();
    this.items.forEach(value => {
      if (value.seq !== seq) {
        value.seq = array.length + 1;
        array.push(value);
      }
    });
    this.items = array;
  }

  /**
   * 上移
   */
  up(seq) {
    this.items = this.items.filter((value, index) => {
      if (value.seq === seq) {
        //  上一个
        if (this.items[index - 1]) {
          const preSeq = this.items[index - 1].seq;
          this.items[index - 1].seq = value.seq;
          value.seq = preSeq;
        }
      }
      return true;
    });
    this.items = this.items.sort((a, b) => {
      return a.seq - b.seq;
    });
  }

  /**
   * 下移
   */
  down(seq) {
    for (let i = 0; i < this.items.length; i++) {
      const item = this.items[i];
      if (item.seq === seq) {
        //  下一个
        const next = this.items[i + 1];
        if (next) {
          const nextSeq = next.seq;
          next.seq = item.seq;
          item.seq = nextSeq;
        }
        break;
      }
    }
    this.items = this.items.sort((a, b) => {
      return a.seq - b.seq;
    });
  }
}
