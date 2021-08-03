import {ModalUtil} from '../../@theme/components';
import {IBaseService} from './ibase.service';
import 'bootstrapvalidator';
import {Injector} from '@angular/core';
import {SidebarUtil} from './sidebar-util.service';
import {Constant} from '../constant';
import {CommonService} from './common-service';
import {ToastUtil} from './toast-util.service';

declare var jQuery: any;

/**
 *基本的component 封装了一些常用的方法
 */
export class BaseComponent {
  get formValid(): any {
    return this._formValid;
  }

  constructor(private baseService: IBaseService, protected injector: Injector) {
    this._modalUtil = injector.get(ModalUtil);
    this._toastUtil = injector.get(ToastUtil);
    this._sidebarUtil = injector.get(SidebarUtil);

    const commonService = injector.get(CommonService);
    this.editParam.images_upload_handler = function (blobInfo, success, failure) {
      const uploadService = commonService;
      const file = blobInfo.blob();
      file.watermark = true;
      uploadService.uploadFile(file).then(result => {
        if (result) {
          success(result.url);
        } else {
          failure('上传失败');
        }
      });
    };
  }

  private readonly _modalUtil: ModalUtil;
  private readonly _toastUtil: ToastUtil;
  private readonly _sidebarUtil: SidebarUtil;

  private _pager: any;       // 分页对象
  private _isSelectAll: boolean = false; // 是否全部选中
  private _selectItems: Array<any> = new Array<any>(); // 选中的ID
  private _queryParams = new Map();   // 查询条件
  private _formValid: any;    // form对象
  private _list: Array<any>;  //  数据集合

  public editParam = Constant.EDITOR;

  /**
   * 侧边显示
   */
  toggleSideBar() {
    this.sidebarUtil.toggleSettings();
  }

  /**
   * 打开侧边
   */
  expandSettings() {
    this.sidebarUtil.expandSettings();
  }

  /**
   * 关闭侧边
   */
  collapseSettings() {
    this.sidebarUtil.collapseSettings();
  }


  get sidebarUtil(): SidebarUtil {
    return this._sidebarUtil;
  }

  get modalUtil(): ModalUtil {
    return this._modalUtil;
  }

  get toastUtil(): ToastUtil {
    return this._toastUtil;
  }

  get list(): Array<any> {
    if (this.pager) {
      return this.pager.data;
    }
    return this._list;
  }

  set list(value: Array<any>) {
    if (this.pager) {
      this.pager.data = value;
    } else {
      this._list = value;
    }
  }

  get pager(): any {
    return this._pager;
  }

  set pager(value: any) {
    this._pager = value;
  }

  get isSelectAll(): boolean {
    return this._isSelectAll;
  }

  set isSelectAll(value: boolean) {
    this._isSelectAll = value;
  }

  get selectItems(): Array<any> {
    return this._selectItems;
  }

  set selectItems(value: Array<any>) {
    this._selectItems = value;
  }

  /**
   * 设置查询参数
   * @param key
   * @param value
   */
  setQueryParams(key: string, value: any) {
    this._queryParams.set(key, value);
  }

  /**
   * 全选事件
   * @param isSelectAll
   */
  changeAllBox(isSelectAll) {
    this._isSelectAll = isSelectAll;
    if (isSelectAll) {
      const items: Array<any> = this.list;
      if (items) {
        items.forEach((item, index, array) => {
          if (this._selectItems.indexOf(item.id) < 0) {
            this._selectItems.push(item.id);
          }
        });
      }
    } else {
      this._selectItems.splice(0, this._selectItems.length);
    }
  }

  /**
   * 单个复选框选择事件
   */
  changeBox(isSelect, id) {
    if (isSelect) {
      if (this._selectItems.indexOf(id) < 0) {
        this._selectItems.push(id);
      }
    } else {
      const index = this._selectItems.indexOf(id);
      if (index >= 0) {
        this._selectItems.splice(index, 1);
      }
    }
  }

  /**
   * 删除数据
   * @param id 数据ID或数组
   */
  del(id): Promise<boolean> {
    const ids = new Array();
    ids.push(id);
    return this.doDel(ids);
  }

  /**
   * 批量删除数据
   */
  delMulti(): Promise<boolean> {
    if (this.selectItems.length > 0) {
      return this.doDel(this.selectItems);
    }
    this.toastUtil.showWarning('请选择要删除的数据!');
    return Promise.resolve(false);
  }

  private doDel(ids: Array<any>): Promise<boolean> {
    return this.modalUtil.confirm('删除提示', '您确认要删除该数据吗?').then(result => {
      if (result) {
        return this.baseService.delData(ids).then(() => {
          this.toastUtil.showSuccess('删除成功!');
          if (this.pager) {
            this.getPager(this.pager.pageNo);
          } else {
            this.isSelectAll = false;
            this.selectItems = new Array<any>();
          }
          return Promise.resolve(true);
        });
      }
      return Promise.resolve(false);
    });
  }

  /**
   * 分页列表
   */
  getPager(pageNo: number): Promise<any> {
    this._queryParams.forEach((value, key, map) => {
      if (value instanceof String && !value) {     // 去掉空值
        map.delete(key);
      }
      if (value === null || value === undefined) {     // 去掉空值
        map.delete(key);
      }
    });
    this.setQueryParams('pageNo', pageNo);
    const result: Promise<any> = this.baseService.getPager(this._queryParams);
    return result.then(data => {
      this.pager = data;
      this.isSelectAll = false;
      this.selectItems = new Array<any>();
      return Promise.resolve(data);
    });
  }

  /**
   * 分页事件
   * @param pageNo
   */
  changePager(pageNo: number) {
    this.getPager(pageNo);
  }

  /**
   * 表单验证
   */
  initValidateForm(formId: string, fields: any) {
    jQuery('#' + formId).bootstrapValidator({
      feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh',
      },
      fields: fields,
    });
    this._formValid = jQuery('#' + formId).data('bootstrapValidator');
  }

  /**
   * 表单是否验证成功
   * @param formId
   */
  isValidForm(formId: string): boolean {
    jQuery('#' + formId).data('bootstrapValidator').validate();
    const flag = jQuery('#' + formId).data('bootstrapValidator').isValid();
    return flag;
  }

  /**
   * 重置Form
   */
  resetForm(formId: string) {
    jQuery('#' + formId).data('bootstrapValidator').resetForm();
  }

  /**
   * 排序 isUp 为true标识上移 false下移
   * @param id
   * @param isUp
   */
  sort(id: string, isUp: boolean) {
    this.list.forEach((item, index, array) => {
      if (item.id === id) {
        let upItemId = null;
        let downItemId = null;
        if (isUp === true) {
          const preItem = array[index - 1];
          if (preItem) {
            upItemId = id;
            downItemId = preItem.id;
          }
        } else {
          const nextItem = array[index + 1];
          if (nextItem) {
            upItemId = nextItem.id;
            downItemId = id;
          }
        }
        if (upItemId && downItemId) {
          this.baseService.sort(upItemId, downItemId).then(() => {
            this.getPager(1);
          });
        }
      }
    });
  }
}
