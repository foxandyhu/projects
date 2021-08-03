import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'ngx-analysis-flow-table',
  templateUrl: './table.component.html',
})
export class FlowTableComponent implements OnInit {

  ngOnInit() {
  }

  data: any;

  setData(data: any) {
    this.data = data;
  }
}
