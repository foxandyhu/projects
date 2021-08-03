import {NgModule} from '@angular/core';

import {PagesComponent} from './pages.component';
import {PagesRoutingModule} from './pages-routing.module';
import {ThemeModule} from '../@theme/theme.module';
import {MenuService} from './system/service/menu-service';

const PAGES_COMPONENTS = [
  PagesComponent,
];

@NgModule({
  imports: [
    PagesRoutingModule,
    ThemeModule,
  ],
  declarations: [
    ...PAGES_COMPONENTS,
  ],
  providers: [MenuService],
})
export class PagesModule {
}
