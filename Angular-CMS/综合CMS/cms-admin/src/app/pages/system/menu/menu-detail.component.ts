import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../../core/service/base-component';
import {NbDialogRef} from '@nebular/theme';
import {MenuService} from '../service/menu-service';
import {Constant} from '../../../core/constant';

@Component({
  selector: 'ngx-menu-detail',
  templateUrl: './menu-detail.component.html',
  styleUrls: ['./menu-detail.component.scss'],
})
export class MenuDetailComponent extends BaseComponent implements OnInit {

  constructor(private menuService: MenuService,
              protected injector: Injector, private ref: NbDialogRef<MenuDetailComponent>) {
    super(null, injector);
  }

  ngOnInit() {
    this.initValidator();
    this.loadMenu();
  }

  menu: any = {name: '', url: '', id: 0, seq: 0, action: false};  //  菜单信息
  private formId: string = 'menuForm';     //   表单ID
  icons = Constant.ION_ICONS;

  /**
   * 加载父菜单信息
   */
  loadMenu() {
    this.menuService.getData(this.menu.id).then(result => {
      this.menu = result;
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
      this.menuService.editData(this.menu).then(() => {
        this.ref.close(true);
      });
    }
  }
}
