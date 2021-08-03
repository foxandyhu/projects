import {NgModule} from '@angular/core';
import {IconsComponent} from './icons.component';
import {ThemeModule} from '../../@theme/theme.module';

const PAGES_COMPONENTS = [
  IconsComponent,
];

@NgModule({
  imports: [
    ThemeModule,
  ],
  declarations: [
    ...PAGES_COMPONENTS,
  ], providers: [], entryComponents: [IconsComponent],
})
export class IconsModule {
}
