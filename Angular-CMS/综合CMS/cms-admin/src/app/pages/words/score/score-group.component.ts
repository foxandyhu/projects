import {Component, Injector, OnDestroy, OnInit} from '@angular/core';
import {BaseComponent} from '../../../core/service/base-component';
import {ScoreGroupAddComponent} from './score-group-add.component';
import {ScoreGroupDetailComponent} from './score-group-detail.component';
import {NbDialogRef, NbDialogService} from '@nebular/theme';
import {ScoreGroupService} from '../service/score-group-service';

@Component({
  selector: 'ngx-score-group',
  templateUrl: './score-group.component.html',
  styleUrls: ['./score-group.component.scss'],
})
export class ScoreGroupComponent extends BaseComponent implements OnInit, OnDestroy {

  constructor(private scoreGroupService: ScoreGroupService,
              protected injector: Injector, private dialogService: NbDialogService) {
    super(scoreGroupService, injector);
  }

  private dialog: NbDialogRef<any>;

  ngOnInit() {
    this.getPager(1);
  }

  ngOnDestroy(): void {
    if (this.dialog) {
      this.dialog.close();
    }
  }

  /**
   * 显示添加评分组
   */
  showAddScoreGroup() {
    this.dialog = this.dialogService.open(ScoreGroupAddComponent);
    this.dialog.onClose.subscribe(result => {
      if (result) {
        this.getPager(1);
      }
    });
  }

  /**
   * 显示编辑评分组
   */
  showEditScoreGroup(id: string) {
    this.scoreGroupService.getData(id).then(data => {
      this.dialog = this.dialogService.open(ScoreGroupDetailComponent);
      this.dialog.componentRef.instance.scoreGroup = data;
      this.dialog.onClose.subscribe(result => {
        if (result) {
          this.getPager(1);
        }
      });
    });
  }
}
