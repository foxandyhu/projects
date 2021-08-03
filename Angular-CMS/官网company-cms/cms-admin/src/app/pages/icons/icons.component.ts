import {Component, OnInit} from '@angular/core';
import {NbDialogRef} from '@nebular/theme';
import {HttpUtil} from '../../core/utils/http';
import {AppApi} from '../../core/app-api';

@Component({
  selector: 'ngx-icons',
  templateUrl: './icons.component.html',
  styleUrls: ['./icons.component.scss'],
})
export class IconsComponent implements OnInit {

  result: any;
  icons: any;

  constructor(private ref: NbDialogRef<IconsComponent>, private httpUtil: HttpUtil) {
  }

  uploadIcon(event) {
    this.result = event.dest;
  }

  loadIcons() {
    this.httpUtil.get(AppApi.WORDS.icons_list).then(result => {
      this.icons = result;
    });
  }

  ok() {
    this.ref.close(this.result);
  }

  cancel() {
    this.ref.close();
  }

  radioChange(event) {
    const index = parseInt(event, 0);
    this.result = this.icons[index];
  }

  ngOnInit() {
    this.loadIcons();
  }
}
