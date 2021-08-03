import {AfterViewChecked, Component, Injector, OnInit} from '@angular/core';
import {AdService} from './service/ad.service';
import {ActivatedRoute, Router} from '@angular/router';
import {AdSpaceService} from './service/ad-space.service';
import {DateUtil} from '../../core/utils/date';
import {Constant} from '../../core/constant';
import {BaseComponent} from '../../core/service/base-component';

@Component({
  selector: 'ngx-ad-detail',
  templateUrl: './ad-detail.component.html',
  styleUrls: ['./ad-detail.component.scss'],
})
export class AdDetailComponent extends BaseComponent implements OnInit, AfterViewChecked {

  ad: any = {
    name: '', spaceId: '', type: '', clickCount: 0, displayCount: 0, enabled: false, startTime: '', endTime: '',
    txt: {},
  };  //  广告对象
  types: any = [{id: 1, name: '图片'}, {id: 2, name: '文字'}, {id: 3, name: '代码'}]; // 类型集合
  spaces: any; //  广告位
  private formId: string = 'adForm';     //   表单ID
  preview: any;   //  预览
  date: string = '';    //  展示时间
  color: string;

  constructor(private adService: AdService, protected injector: Injector,
              private router: Router, private spaceService: AdSpaceService, private route: ActivatedRoute) {
    super(adService, injector);
  }

  ngOnInit() {
    this.preview = Constant.DEFAULT_PIC;
    this.loadData();
    this.getAllSpaces();
    this.initValidator();
  }

  ngAfterViewChecked(): void {
    const typeId = parseInt(this.ad.type, 0);
    if (typeId === 1) {
      this.formValid.addField('picUrl', {
        validators: {
          notEmpty: {message: '图片不能为空!'},
        },
      });
    } else if (typeId === 2) {
      this.formValid.addField('textTitle', {
        validators: {
          notEmpty: {message: '文字内容不能为空!'},
        },
      });
    } else if (typeId === 3) {
      this.formValid.addField('codeContent', {
        validators: {
          notEmpty: {message: '代码内容不能为空!'},
        },
      });
    }
  }

  /**
   * 加载数据
   */
  loadData() {
    this.route.paramMap.subscribe(params => {
      const adId = params.get('adId');
      this.adService.getData(adId).then(result => {
        this.ad = result;
        if (this.ad) {
          if (this.ad.txt) {
            const txt = this.ad.txt;
            if (txt.picPath) {
              this.preview = txt.url;
            }
            if (this.ad.startTime) {
              this.date = DateUtil.formatDate(this.ad.startTime);
              if (this.ad.endTime) {
                this.date += ' - ' + DateUtil.formatDate(this.ad.endTime);
              }
            }
          } else {
            this.ad.txt = {};
          }
        }
      });
    });
  }

  /**
   * 初始化表单验证
   */
  initValidator() {
    this.initValidateForm(this.formId, {
      name: {
        validators: {
          notEmpty: {message: '名称不能为空!'},
        },
      },
      spaceId: {
        validators: {
          notEmpty: {message: '请选择广告位!'},
        },
      },
      clickCount: {
        validators: {
          notEmpty: {message: '点击率不能为空!'},
          digits: {min: 0, message: '点击率最小为0!'},
        },
      },
      displayCount: {
        validators: {
          notEmpty: {message: '展示次数不能为空!'},
          digits: {min: 0, message: '展示次数最小为0!'},
        },
      }, showTime: {
        validators: {
          notEmpty: {message: '开始或结束时间不能为空!'},
        },
      },
    });
  }

  /**
   * 广告位
   */
  getAllSpaces() {
    this.spaceService.getAllSpaces().then(result => {
      this.spaces = result;
    });
  }

  /**
   * 保存广告信息
   */
  editAd() {
    if (this.isValidForm(this.formId)) {
      this.adService.editData(this.ad).then(result => {
        if (result === true) {
          this.toastUtil.showSuccess('编辑成功!');
          this.router.navigate(['/pages/ad/list']);
        }
      });
    }
  }

  /**
   * 广告图片上传
   * @param event
   */
  adFileChange(event) {
    this.ad.txt.picPath = event.dest.path;
  }

  /**
   * 日期改变
   * @param event
   */
  changeDate(event) {
    if (event.start) {
      this.ad.startTime = DateUtil.formatDate(event.start);
    }
    if (event.end) {
      this.ad.endTime = DateUtil.formatDate(event.end);
    }
    if (event.start && event.end) {
      this.formValid.updateStatus('showTime', 'VALID');
    } else {
      this.formValid.updateStatus('showTime', 'NOT_VALIDATED');
    }
  }


  /**
   * 颜色选择
   * @param color
   */
  pickColor(color) {
    this.ad.txt.txtColor = color;
  }
}
