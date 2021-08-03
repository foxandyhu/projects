import {Component, Injector, OnDestroy, OnInit} from '@angular/core';
import {BaseComponent} from '../../../core/service/base-component';
import {NbDialogRef, NbDialogService} from '@nebular/theme';
import {ScoreItemService} from '../service/score-item-service';
import {ScoreItemAddComponent} from './score-item-add.component';
import {ScoreItemDetailComponent} from './score-item-detail.component';
import {ActivatedRoute} from '@angular/router';
import {ScoreGroupService} from '../service/score-group-service';
import {Constant} from '../../../core/constant';

@Component({
  selector: 'ngx-score-item',
  templateUrl: './score-item.component.html',
  styleUrls: ['./score-item.component.scss'],
})
export class ScoreItemComponent extends BaseComponent implements OnInit, OnDestroy {

  constructor(private scoreGroupService: ScoreGroupService, private scoreItemService: ScoreItemService,
              protected injector: Injector, private dialogService: NbDialogService, private route: ActivatedRoute) {
    super(scoreItemService, injector);
  }

  private dialog: NbDialogRef<any>;
  groupId: string;    //  评分组ID
  scoreGroup: any = {name: '', id: 0};    //   评分组对象
  items: any;

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const groupId = params.get('groupId');
      this.groupId = groupId;
      this.getScoreGroup();
      this.loadData();
    });
  }

  ngOnDestroy(): void {
    if (this.dialog) {
      this.dialog.close();
    }
  }

  /**
   * 得到评分组对象
   */
  private getScoreGroup() {
    this.scoreGroupService.getData(this.groupId).then(result => {
      this.scoreGroup = result;
      this.scoreGroup.id = this.groupId;
    });
  }

  /**
   * 加载数据
   */
  private loadData() {
    this.setQueryParams('groupId', this.groupId);
    this.getPager(1);
  }

  getPager(pageNo: number): Promise<any> {
    return super.getPager(pageNo).then(result => {
      if (result) {
        this.items = result.data;
        this.items.forEach(item => {
          if (!item.pic) {
            item.url = Constant.DEFAULT_PIC;
          }
        });
      }
    });
  }

  /**
   * 显示添加评分项
   */
  showAddScoreItem() {
    this.dialog = this.dialogService.open(ScoreItemAddComponent);
    this.dialog.componentRef.instance.scoreGroup = this.scoreGroup;
    this.dialog.onClose.subscribe(result => {
      if (result) {
        this.loadData();
      }
    });
  }

  /**
   * 显示编辑评分项
   */
  showEditScoreItem(id: string) {
    this.scoreItemService.getData(id).then(data => {
      this.dialog = this.dialogService.open(ScoreItemDetailComponent);
      this.dialog.componentRef.instance.scoreItem = data;
      this.dialog.componentRef.instance.scoreGroup = this.scoreGroup;
      this.dialog.onClose.subscribe(result => {
        if (result) {
          this.loadData();
        }
      });
    });
  }
}
