import {Component, Injector, OnInit} from '@angular/core';
import {BaseComponent} from '../../../core/service/base-component';
import {WatermarkConfigService} from '../service/watermark-config-service';
import {Constant} from '../../../core/constant';
import {ResourceService} from '../service/resource-service';
import {forkJoin} from 'rxjs';

@Component({
  selector: 'ngx-system-watermark-config',
  templateUrl: './watermark.component.html',
  styleUrls: ['./watermark.component.scss'],
})
export class WatermarkConfigComponent extends BaseComponent implements OnInit {

  constructor(private watermarkService: WatermarkConfigService, protected injector: Injector,
              private resourceService: ResourceService) {
    super(watermarkService, injector);
  }

  formId: string = 'watermarkConfigForm';
  preview: any;   //  预览
  color: string;
  fonts: any;
  watermarkConfig: any = {
    openWaterMark: '', imgWidth: '', imgHeight: '', img: '', url: '', imgWaterMark: true, font: '',
    text: '', size: '', color: '', alpha: '', pos: '0', offsetX: '', offsetY: '',
  };    //  水印配置

  poss: any = [{id: 0, name: '随机'}, {id: 1, name: '左上'}, {id: 2, name: '右上'}, {id: 3, name: '左下'},
    {id: 4, name: '右下'}, {id: 5, name: '居中'}];  //  位置

  ngOnInit() {
    this.preview = Constant.DEFAULT_PIC;
    this.initValidator();

    const arr = [this.loadFonts(), this.getWatermarkConfig()];
    forkJoin(arr);
  }

  /**
   * 初始化表单验证
   */
  initValidator() {
    this.initValidateForm(this.formId, {
      imgWidth: {
        validators: {
          digits: {message: '图片宽度最小为0！'},
        },
      },
      imgHeight: {
        validators: {
          digits: {message: '图片高度最小为0！'},
        },
      },
      size: {
        validators: {
          digits: {message: '文字大小最小为0！'},
        },
      },
      alpha: {
        validators: {
          digits: {message: '透明度最小为0！'},
        },
      },
      offsetX: {
        validators: {
          numeric: {message: 'x偏移量最小为0！'},
        },
      },
      offsetY: {
        validators: {
          numeric: {message: 'y偏移量最小为0！'},
        },
      },
    });
  }

  /**
   * 获取水印配置
   */
  getWatermarkConfig(): Promise<boolean> {
    return this.watermarkService.getWatermarkConfig().then(result => {
      if (result) {
        this.watermarkConfig = result;
        if (this.watermarkConfig.img) {
          this.preview = this.watermarkConfig.img;
        }
      }
      return Promise.resolve(true);
    });
  }

  loadFonts(): Promise<boolean> {
    return this.resourceService.getFonts().then(result => {
      this.fonts = result;
      return Promise.resolve(true);
    });
  }

  /**
   * 编辑水印配置
   */
  editWatermarkConfig() {
    this.watermarkService.editWatermarkConfig(this.watermarkConfig).then(() => {
      this.toastUtil.showSuccess('保存成功!');
    });
  }

  /**
   * 颜色选择
   * @param color
   */
  pickColor(color) {
    this.watermarkConfig.color = color;
  }

  /**
   * 水印图片上传
   * @param event
   */
  fileChange(event) {
    this.watermarkConfig.img = event.dest.path;
  }
}
