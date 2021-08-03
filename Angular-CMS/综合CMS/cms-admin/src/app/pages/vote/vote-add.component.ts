import {Component, Injector, OnDestroy, OnInit} from '@angular/core';
import {BaseComponent} from '../../core/service/base-component';
import {VoteService} from './service/vote-service';
import {NbDialogRef, NbDialogService} from '@nebular/theme';
import {VoteItemComponent} from './vote-item.component';
import {Router} from '@angular/router';

@Component({
  selector: 'ngx-vote-add',
  templateUrl: './vote-add.component.html',
  styleUrls: ['./vote-add.component.scss'],
})
export class VoteAddComponent extends BaseComponent implements OnInit, OnDestroy {

  constructor(private voteService: VoteService, protected injector: Injector, private dialogService: NbDialogService,
              private router: Router) {
    super(voteService, injector);
  }

  formId: string = 'voteForm';
  types: any = [{id: 1, name: '添加单选题'}, {id: 2, name: '添加多选题'}, {id: 3, name: '添加问答题'}];
  vote: any = {
    title: '', remark: '', startTime: '', endTime: '',
    repeatHour: '', needLogin: 'true', subtopics: [],
  };  // 问卷调查
  private dialog: NbDialogRef<any>;

  ngOnInit() {
    this.initValidator();
  }

  ngOnDestroy(): void {
    if (this.dialog) {
      this.dialog.close();
    }
  }

  /**
   * 初始化表单验证
   */
  initValidator() {
    this.initValidateForm(this.formId, {
      title: {
        validators: {
          notEmpty: {message: '标题不能为空!'},
        },
      },
      repeatHour: {
        validators: {
          notEmpty: {message: '重复答卷限制不能为空!'},
          integer: {message: '重复答卷限制不正确!'},
          greaterThan: {value: -1, message: '重复答卷限制最小为-1!'},
        },
      },
      startTime: {
        validators: {
          notEmpty: {message: '开始时间不能为空!'},
          date: {message: '时间格式不正确!', format: 'YYYY-MM-DD'},
        },
      },
      endTime: {
        validators: {
          notEmpty: {message: '开始时间不能为空!'},
          date: {message: '时间格式不正确!', format: 'YYYY-MM-DD'},
        },
      },
    });
  }

  changeDate(target) {
    this.formValid.resetField(target, false);
  }

  showAddItem(type: any) {
    this.dialog = this.dialogService.open(VoteItemComponent);
    this.dialog.componentRef.instance.type = type;
    this.dialog.onClose.subscribe(subTopic => {
      if (subTopic) {
        subTopic.seq = this.vote.subtopics.length + 1;
        this.vote.subtopics.push(subTopic);
      }
    });
  }

  /**
   * 移除子主题
   * @param subId
   */
  removeSubTopic(subId: string) {
    this.vote.subtopics = this.vote.subtopics.filter(value => {
      return subId !== value.seq;
    });
    this.vote.subtopics = this.vote.subtopics.filter((value, index) => {
      value.seq = index + 1;
      return true;
    });
  }

  /**
   * 保存问卷调查
   */
  saveVote() {
    if (this.isValidForm(this.formId)) {
      if (this.vote.subtopics.length === 0) {
        this.toastUtil.showDanger('请添加子主题!');
        return;
      }
      this.voteService.saveData(this.vote).then(() => {
        this.toastUtil.showSuccess('添加成功!');
        this.router.navigate(['/pages/vote/list']);
      });
    }
  }

  /**
   * 上移
   */
  up(seq) {
    let items = this.vote.subtopics;
    items = items.filter((value, index) => {
      if (value.seq === seq) {
        //  上一个
        if (items[index - 1]) {
          const preSeq = items[index - 1].seq;
          items[index - 1].seq = value.seq;
          value.seq = preSeq;
        }
      }
      return true;
    });
    this.vote.subtopics = items.sort((a, b) => {
      return a.seq - b.seq;
    });
  }

  /**
   * 下移
   */
  down(seq) {
    const items = this.vote.subtopics;
    for (let i = 0; i < items.length; i++) {
      const item = items[i];
      if (item.seq === seq) {
        //  下一个
        const next = items[i + 1];
        if (next) {
          const nextSeq = next.seq;
          next.seq = item.seq;
          item.seq = nextSeq;
        }
        break;
      }
    }
    this.vote.subtopics = items.sort((a, b) => {
      return a.seq - b.seq;
    });
  }
}
