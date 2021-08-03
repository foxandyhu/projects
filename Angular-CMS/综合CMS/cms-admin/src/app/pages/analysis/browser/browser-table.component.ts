import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'ngx-analysis-browser-table',
  templateUrl: './browser-table.component.html',
})
export class BrowserTableComponent implements OnInit {

  data: any;

  constructor() {
  }

  ngOnInit() {
  }

  /**
   * 设置数据
   * @param data
   */
  setData(data: any) {
    this.data = new Array();
    const map = new Map();
    if (data) {
      for (const item of data) {
        const value = map.get(item.value);
        const json = {ip: 0, pv: 0, uv: 0, value: ''};
        if (value) {
          json.ip = value.ip + parseInt(item.ip, 0);
          json.pv = value.pv + parseInt(item.pv, 0);
          json.uv = value.uv + parseInt(item.uv, 0);
          json.value = value.value;
        } else {
          //  拷贝对象的值
          Object.assign(json, item);
        }
        map.set(item.value, json);
      }
    }
    map.forEach((value, key, m) => {
      this.data.push(value);
    });
  }
}
