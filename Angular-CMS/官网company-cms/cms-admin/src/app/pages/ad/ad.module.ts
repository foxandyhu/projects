import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AdRoutingModule} from './ad-routing.module';
import {AdComponent} from './ad.component';
import {ThemeModule} from '../../@theme/theme.module';
import {AdService} from './service/ad.service';
import {AdSpaceService} from './service/ad-space.service';
import {AdAddComponent} from './ad-add.component';
import {AdDetailComponent} from './ad-detail.component';
import {NbDialogModule} from '@nebular/theme';
import {ColorPickerModule} from 'ngx-color-picker';
import {SpaceDetailComponent} from './space-detail.component';
import {SpaceAddComponent} from './space-add.component';
import {AdSpaceComponent} from './ad-space.component';

@NgModule({
  declarations: [AdComponent, AdAddComponent, AdDetailComponent,
    AdSpaceComponent, SpaceDetailComponent, SpaceAddComponent],
  imports: [
    CommonModule,
    ThemeModule,
    AdRoutingModule,
    ColorPickerModule,
    NbDialogModule.forChild(),
  ],
  entryComponents: [SpaceDetailComponent, SpaceAddComponent],
  providers: [AdService, AdSpaceService],
})
export class AdModule {
}
