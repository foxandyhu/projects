import {AfterViewChecked, Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {CommonService} from '../../../core/service/common-service';
import {LoadingBarService} from '../loading-bar/loading-bar.service';
import {Constant} from '../../../core/constant';
import {ModalUtil} from '../modal/modal-util.service';

@Component({
  selector: 'ngx-uploader-bar',
  templateUrl: './uploader-bar.component.html',
  styleUrls: ['./uploader-bar.component.scss'],
})

export class UploaderBarComponent implements OnInit, OnDestroy, AfterViewChecked {
  @Input() watermark: boolean = false; // 是否水印 只对图片有效
  @Input() text: string = '上传';  // 按钮文字
  @Input() isPicture: boolean = false;   // 默认显示文字按钮
  @Input() multiple: boolean = false;  // 批量上传
  @Input() type: number = 1;     // 文件类型 1 图片 2视频 3其他文件
  @Input() id: string = '';        // file控件ID
  @Output() private complete = new EventEmitter(true);
  showRemovePreview: boolean = true; // 是否显示移除图标 当picture为true时才能生效
  @Input() previewPic: string = Constant.DEFAULT_PIC; // 预览图片
  @Input() showPreview: boolean = true;   // 是否更新预览图片
  @Input() tip: string = '只能上传jpg/png文件，且不超过2M'; // 提示信息
  isComplete: boolean = true; //  单个是否上传完毕
  private title: string;
  showBar: boolean = false; // 进度条显示
  fileName: string = '';   // 上传的文件名
  private timer: any;      //  进度条定时器
  private value: number = 0;
  private files: Array<any>;  // 上传的文件集合
  private fileIndex: number = 0;     //  上传的文件索引
  private fileCount: number = 0;     // 文件总数
  maxPictureSize = 1024 * 1024 * 2;
  maxVideoSize = 1024 * 1024 * 100;
  maxFileSize = 1024 * 1024 * 10;

  constructor(private commonService: CommonService, private loadingBarService: LoadingBarService,
              private modalUtil: ModalUtil) {
  }

  ngOnInit() {
    this.loadingBarService.shutdown();
  }

  ngAfterViewChecked(): void {
    if (!this.previewPic || this.previewPic === Constant.DEFAULT_PIC) {
      this.previewPic = Constant.DEFAULT_PIC;
      this.showRemovePreview = false;
    } else {
      this.showRemovePreview = true;
    }
  }

  ngOnDestroy(): void {
    this.stop();
    this.loadingBarService.start();
  }

  fileClick(event) {
    event.target.previousSibling.click();
  }


  /**
   * 初始化bar
   */
  initProgressBar() {
    this.showBar = true;
    this.value = 0;
  }

  /**
   * 文件上传
   * @param event
   */
  fileChange(event) {
    const map = event.currentTarget.files;
    if (map.length <= 0) {
      return;
    }
    this.initProgressBar();
    this.files = new Array<any>();
    for (let i = 0; i < map.length; i++) {
      const flag = this.checkFileType(map[i]);
      if (flag) {
        this.files.push(map[i]);
      } else {
        event.target.value = '';
        return;
      }
    }
    event.target.value = '';
    this.fileCount = this.files.length;
    this.fileIndex = 0;
    const file = this.files.pop();
    this.start();
    this.doUpload(file);
  }

  /**
   * 检查文件类型
   * @param file
   */
  checkFileType(file) {
    const fileType = file.type;
    const fileSize = file.size;
    let flag = true;
    const type = parseInt(this.type + '', 0);
    switch (type) {
      case 1:
        if (fileType !== 'image/png' && fileType !== 'image/jpeg') {
          this.modalUtil.alert('类型错误', '只能上传jpg/png文件!');
          flag = false;
          break;
        }
        if (fileSize > this.maxPictureSize) {
          this.modalUtil.alert('文件大小', '图片过大超过2M');
          flag = false;
        }
        break;
      case 2:
        if (fileType !== 'video/mp4') {
          this.modalUtil.alert('类型错误', '只能上传MP4文件!');
          flag = false;
          break;
        }
        if (fileSize > this.maxVideoSize) {
          this.modalUtil.alert('文件大小', '视频过大超过100M');
          flag = false;
        }
        break;
      case 3:
        if (fileSize > this.maxFileSize) {
          this.modalUtil.alert('文件大小', '文件过大超过10M');
          flag = false;
        }
        break;
    }
    return flag;
  }

  /**
   * 执行上传
   * @param file
   */
  doUpload(file) {
    file.watermark = this.watermark;
    this.fileIndex += 1;
    this.fileName = file.name;
    this.title = '正在上传'.concat('(' + this.fileIndex + '/' + this.fileCount + '):');
    this.commonService.uploadFile(file).then((result) => {
      this.complete.emit({source: file, dest: result});
      this.title = '上传成功:';

      if (this.isPicture && this.type === 1 && this.showPreview) {
        this.previewPic = result.url;
        this.showRemovePreview = true;
      }
      const nextFile = this.files.pop();
      if (nextFile) {
        this.doUpload(nextFile);
      } else {
        //  上传完毕
        this.value = 100;
        this.stop();
        this.title = '上传完毕'.concat('(' + this.fileIndex + '/' + this.fileCount + '):');
      }
    });
  }

  /**
   * 移除上传的图片封面
   */
  removePreView() {
    this.complete.emit({source: null, dest: {path: '', url: ''}});
    this.previewPic = Constant.DEFAULT_PIC;
    this.showRemovePreview = false;
  }

  /**
   * 更新进度条
   */
  cmProgressbar() {
    this.value += Math.round(Math.random() * 10);
    // 10 秒 100/2*200
    if (this.value >= 100) {
      this.value = 0;
    }
  }


  /**
   * 开启定时器
   */
  start() {
    this.isComplete = false;
    this.timer = setInterval(() => {
      this.cmProgressbar();
    }, 200);
  }

  /**
   * 停止定时器
   */
  stop() {
    this.isComplete = true;
    this.showBar = false;
    if (this.timer) {
      clearInterval(this.timer);
    }
  }

}
