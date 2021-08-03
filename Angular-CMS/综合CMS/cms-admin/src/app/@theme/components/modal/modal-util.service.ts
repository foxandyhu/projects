import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ModalComponent} from './modal.component';
import {Injectable} from '@angular/core';

@Injectable(({providedIn: 'root'}))
export class ModalUtil {
  constructor(private modalService: NgbModal) {
  }

  alert(title: string, content: string): Promise<boolean> {
    const flag = this.modalService.hasOpenModals();
    if (flag) {
      this.modalService.dismissAll();
    }
    const modal = this.modalService.open(ModalComponent, {size: 'sm', backdrop: 'static'});
    modal.componentInstance.showCancelBtn = false;
    modal.componentInstance.ok = function () {
      modal.close(true);
    };
    if (title) {
      modal.componentInstance.title = title;
    }
    modal.componentInstance.content = content;
    return modal.result;
  }

  confirm(title: string, content: string): Promise<boolean> {
    const modal = this.modalService.open(ModalComponent, {size: 'sm', backdrop: 'static'});
    modal.componentInstance.showCancelBtn = true;
    modal.componentInstance.ok = function () {
      modal.close(true);
    };
    if (title) {
      modal.componentInstance.title = title;
    }
    modal.componentInstance.content = content;
    return modal.result;
  }
}
