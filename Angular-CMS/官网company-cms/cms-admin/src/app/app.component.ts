import {Component, OnInit} from '@angular/core';
import {PageInterceptor} from './core/interceptor';

@Component({
  selector: 'ngx-app',
  template: `
    <router-outlet></router-outlet>`,
})
export class AppComponent implements OnInit {

  constructor(private authService: PageInterceptor) {
  }

  ngOnInit(): void {
  }
}
