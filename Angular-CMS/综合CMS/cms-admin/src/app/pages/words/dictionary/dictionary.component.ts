import {Component, Injector, OnDestroy, OnInit} from '@angular/core';
import {DictionaryService} from '../service/dictionary-service';
import {NbDialogRef, NbDialogService} from '@nebular/theme';
import {DictionaryAddComponent} from './dictionary-add.component';
import {DictionaryDetailComponent} from './dictionary-detail.component';
import {BaseComponent} from '../../../core/service/base-component';

@Component({
  selector: 'ngx-words-dictionary',
  styleUrls: ['./dictionary.component.scss'],
  templateUrl: './dictionary.component.html',
})
export class DictionaryComponent extends BaseComponent implements OnInit, OnDestroy {

  constructor(private dictionaryService: DictionaryService,
              protected injector: Injector, private dialogService: NbDialogService) {
    super(dictionaryService, injector);
  }

  private dialog: NbDialogRef<any>;
  types: Array<string>;  //  类型集合
  contentType: string = '';

  ngOnInit() {
    this.loadData();
  }

  ngOnDestroy(): void {
    if (this.dialog) {
      this.dialog.close();
    }
  }

  /**
   * 初始化加载数据
   */
  private loadData() {
    this.search();
    this.getDictionaryTypes();
  }

  /**
   * 获得所有数据字典类型
   */
  getDictionaryTypes() {
    this.dictionaryService.getDictionaryTypes().then(result => {
      this.types = result;
    });
  }

  /**
   * 类型查找
   */
  search() {
    this.setQueryParams('type', this.contentType);
    const pageNo = this.pager ? this.pager.pageNo : 1;
    this.getPager(pageNo);
  }

  /**
   * 显示添加数据字典弹框
   */
  showAddDictionary() {
    this.dialog = this.dialogService.open(DictionaryAddComponent);
    this.dialog.onClose.subscribe(result => {
      if (result) {
        this.dictionaryService.saveData(result).then(() => {
          this.loadData();
        });
      }
    });
  }

  /**
   * 显示编辑数据字典弹框
   */
  showEditDictionary(id: string) {
    this.dictionaryService.getData(id).then(data => {
      this.dialog = this.dialogService.open(DictionaryDetailComponent);
      this.dialog.componentRef.instance.dictionary = data;
      this.dialog.onClose.subscribe(result => {
        if (result) {
          this.dictionaryService.editData(result).then(() => {
            this.search();
          });
        }
      });
    });
  }

  /**
   * 删除数据
   * @param id 数据ID或数组
   */
  del(id): Promise<boolean> {
    return super.del(id).then(result => {
      if (result === true) {
        this.getDictionaryTypes();
      }
      return Promise.resolve(result);
    });
  }

  /**
   * 批量删除数据
   */
  delMulti(): Promise<boolean> {
    return super.delMulti().then(result => {
      if (result === true) {
        this.getDictionaryTypes();
      }
      return Promise.resolve(result);
    });
  }

  /**
   * 编辑状态
   */
  editEnabled(event) {
    const dictionaryId = event.target.dataset.id;
    const enabled = event.target.dataset.enabled;
    this.dictionaryService.editEnabled(dictionaryId, enabled).then(() => {
      this.search();
    });
  }
}
