import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'ngx-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss'],
})
export class ModalComponent implements OnInit {

  title: string = '提示';
  content: string;
  showCancelBtn: boolean = true;
  showOkBtn: boolean = true;

  constructor(private activeModal: NgbActiveModal) {
  }

  closeModal() {
    this.activeModal.close(false);
  }

  ok() {
  }

  ngOnInit() {
  }

}
