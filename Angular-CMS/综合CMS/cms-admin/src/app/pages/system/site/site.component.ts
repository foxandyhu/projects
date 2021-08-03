import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'ngx-system-site',
  templateUrl: './site.component.html',
  styleUrls: ['./site.component.scss'],
})
export class SiteComponent implements OnInit {

  tabs: any[] = [
    {
      title: '站点设置',
      route: '/pages/system/setting/config/site',
    },
    {
      title: '评论设置',
      route: '/pages/system/setting/config/comment',
    },
    {
      title: '留言设置',
      route: '/pages/system/setting/config/guestbook',
    },
  ];

  constructor() {
  }

  ngOnInit() {
  }
}
