import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../../core/service/base-component';
import {LetterService} from '../service/letter-service';
import {MemberGroupService} from '../../member/service/member-group-service';
import '../../../@theme/components/editor.loader';
import {Router} from '@angular/router';
import {Constant} from '../../../core/constant';

@Component({
  selector: 'ngx-message-letter-add',
  templateUrl: './letter-add.component.html',
  styleUrls: ['./letter-add.component.scss'],
})
export class LetterAddComponent extends BaseComponent implements OnInit {

  constructor(private letterService: LetterService, private groupService: MemberGroupService,
              protected injector: Injector, private router: Router) {
    super(letterService, injector);
  }

  formId: string = 'letterForm';
  letter: any = {receiver: '', letterTxt: {title: '', content: '', groupId: ''}};  //  站内信
  groups: Array<any>; //  会员组
  receiverReadOnly: boolean = false;

  ngOnInit() {
    this.initValidator();
    this.loadMemberGroups();
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
      receiver: {
        validators: {
          notEmpty: {message: '接收人不能为空!'},
        },
      },
    });
  }


  /**
   * 加载会员组
   */
  loadMemberGroups() {
    this.groupService.getAllGroup().then(result => {
      this.groups = result;
    });
  }

  /**
   * 接收人改变事件
   */
  changeReceiver() {
    this.letter.letterTxt.groupId = '';
    if (this.letter.receiver.trim().length > 0) {
      this.receiverReadOnly = false;
    } else {
      this.receiverReadOnly = true;
    }
  }

  /**
   * 会员组选择事件
   */
  changeGroup() {
    if (this.letter.letterTxt.groupId) {
      this.receiverReadOnly = true;
      this.groups.forEach(group => {
        if (group.id === parseInt(this.letter.letterTxt.groupId, 0)) {
          this.formValid.resetField('receiver', false);
          this.letter.receiver = group.name;
        }
      });
    } else {
      this.receiverReadOnly = false;
      this.letter.receiver = '';
    }
  }


  /**
   * 保存站内信
   */
  saveLetter() {
    if (this.isValidForm(this.formId)) {
      this.letterService.saveData(this.letter).then(() => {
        this.toastUtil.showSuccess('添加成功!');
        this.router.navigate(['/pages/message/letter']);
      });
    }
  }
}
