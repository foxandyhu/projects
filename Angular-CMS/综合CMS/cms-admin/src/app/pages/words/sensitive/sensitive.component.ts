import {Component, Injector, OnDestroy, OnInit} from '@angular/core';
import {BaseComponent} from '../../../core/service/base-component';
import {NbDialogRef, NbDialogService} from '@nebular/theme';
import {SensitiveDetailComponent} from './sensitive-detail.component';
import {SensitiveAddComponent} from './sensitive-add.component';
import {SensitiveWordService} from '../service/sensitiveword-service';

@Component({
  selector: 'ngx-words-sensitive',
  templateUrl: './sensitive.component.html',
})
export class SensitiveComponent extends BaseComponent implements OnInit, OnDestroy {

  constructor(private sensitiveService: SensitiveWordService,
              protected injector: Injector, private dialogService: NbDialogService) {
    super(sensitiveService, injector);
  }

  private dialog: NbDialogRef<any>;
  searchWord: string;  //  搜索词

  ngOnInit() {
    this.loadData();
  }

  ngOnDestroy(): void {
    if (this.dialog) {
      this.dialog.close();
    }
  }

  /**
   * 加载数据
   */
  loadData() {
    this.setQueryParams('search', null);
    this.getPager(1);
  }

  /**
   * 显示添加敏感词弹框
   */
  showAddSensitive() {
    this.dialog = this.dialogService.open(SensitiveAddComponent);
    this.dialog.onClose.subscribe(result => {
      if (result) {
        this.loadData();
      }
    });
  }

  /**
   * 显示编辑敏感词弹框
   */
  showEditSensitive(id: string) {
    this.sensitiveService.getData(id).then(data => {
      this.dialog = this.dialogService.open(SensitiveDetailComponent);
      this.dialog.componentRef.instance.sensitive = data;
      this.dialog.onClose.subscribe(result => {
        if (result) {
          this.loadData();
        }
      });
    });
  }

  /**
   * 搜索
   */
  search() {
    this.setQueryParams('search', this.searchWord);
    this.getPager(1);
  }
}
