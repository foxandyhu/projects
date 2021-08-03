import {Injectable} from '@angular/core';
import {NbGlobalPhysicalPosition, NbToastrService} from '@nebular/theme';
import {NbToastStatus} from '@nebular/theme/components/toastr/model';

@Injectable({providedIn: 'root'})
export class ToastUtil {

  constructor(private toastService: NbToastrService) {
  }

  private config = {
    status: NbToastStatus.DEFAULT,
    duration: 2000,
    hasIcon: true,
    position: NbGlobalPhysicalPosition.BOTTOM_RIGHT,
    preventDuplicates: false,
    destroyByClick: true,
  };

  showSuccess(content: string) {
    this.config.status = NbToastStatus.SUCCESS;
    this.toastService.show(content, '提示', this.config);
  }

  showWarning(content: string) {
    this.config.status = NbToastStatus.WARNING;
    this.toastService.show(content, '警告', this.config);
  }

  showInfo(content: string) {
    this.config.status = NbToastStatus.INFO;
    this.toastService.show(content, '消息', this.config);
  }

  showDanger(content: string) {
    this.config.status = NbToastStatus.DANGER;
    this.toastService.show(content, '错误', this.config);
  }
}
