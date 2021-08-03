import {Component, Injector, OnInit} from '@angular/core';
import {NbDialogRef} from '@nebular/theme';
import {BaseComponent} from '../../../core/service/base-component';
import {MenuService} from '../service/menu-service';
import {Constant} from '../../../core/constant';

@Component({
  selector: 'ngx-menu-add',
  templateUrl: './menu-add.component.html',
  styleUrls: ['./menu-add.component.scss'],
})
export class MenuAddComponent extends BaseComponent implements OnInit {

  constructor(private menuService: MenuService,
              protected injector: Injector, private ref: NbDialogRef<MenuAddComponent>) {
    super(null, injector);
  }

  ngOnInit() {
    this.initValidator();
    this.loadParentMenu();
  }

  menu: any = {name: '', url: '', seq: 0, parentId: 0, icon: '', action: false};  //  菜单信息
  parent = {id: 0, name: '根菜单'};
  private formId: string = 'menuForm';     //   表单ID
  icons = Constant.ION_ICONS;

  /**
   * 加载父菜单信息
   */
  loadParentMenu() {
    this.menuService.getData(this.menu.parentId).then(result => {
      if (result) {
        this.parent = result;
      }
    });
  }

  /**
   * 初始化表单验证
   */
  initValidator() {
    this.initValidateForm(this.formId, {
      name: {
        validators: {
          notEmpty: {message: '菜单名称不能为空!'},
        },
      },
      url: {
        validators: {
          notEmpty: {message: '菜单地址不能为空!'},
        },
      },
      seq: {
        validators: {
          notEmpty: {message: '菜单排序不能为空!'},
        },
      },
    });
  }

  cancel() {
    this.ref.close();
  }

  submit() {
    if (this.isValidForm(this.formId)) {
      this.menuService.saveData(this.menu).then(() => {
        this.ref.close(true);
      });
    }
  }
}
