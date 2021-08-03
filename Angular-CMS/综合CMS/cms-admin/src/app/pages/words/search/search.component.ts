import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../../core/service/base-component';
import {SearchWordService} from '../service/searchword-service';
import {NbDialogRef, NbDialogService} from '@nebular/theme';
import {SearchAddComponent} from './search-add.component';
import {SearchDetailComponent} from './search-detail.component';

@Component({
  selector: 'ngx-words-search',
  templateUrl: './search.component.html',
})
export class SearchComponent extends BaseComponent implements OnInit {

  constructor(private searchWordService: SearchWordService, protected injector: Injector,
              private dialogService: NbDialogService) {
    super(searchWordService, injector);
  }

  searchWord: string;  //  搜索词
  recommend: string;   //   是否推荐
  private dialog: NbDialogRef<any>;

  ngOnInit() {
    this.getPager(1);
  }

  /**
   * 搜索
   */
  search() {
    this.setQueryParams('recommend', this.recommend);
    this.setQueryParams('search', this.searchWord);
    this.getPager(1);
  }

  /**
   * 类型搜索
   * @param type
   */
  changeType(type: string) {
    this.recommend = type;
    this.search();
  }

  /**
   * 改变推荐状态
   */
  changeRecommend(id: string, status: string) {
    this.searchWordService.changeRecommend(id, status).then(() => {
      this.toastUtil.showSuccess('推荐成功!');
      this.search();
    });
  }

  /**
   * 显示添加搜索词弹框
   */
  showAddSearch() {
    this.dialog = this.dialogService.open(SearchAddComponent);
    this.dialog.onClose.subscribe(result => {
      if (result) {
        this.getPager(1);
      }
    });
  }

  /**
   * 显示编辑敏感词弹框
   */
  showEditSearch(id: string) {
    this.searchWordService.getData(id).then(data => {
      this.dialog = this.dialogService.open(SearchDetailComponent);
      this.dialog.componentRef.instance.search = data;
      this.dialog.onClose.subscribe(result => {
        if (result) {
          this.getPager(1);
        }
      });
    });
  }
}
