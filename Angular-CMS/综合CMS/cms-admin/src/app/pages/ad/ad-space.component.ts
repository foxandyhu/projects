import {Component, Injector, OnDestroy, OnInit} from '@angular/core';
import {NbDialogRef, NbDialogService} from '@nebular/theme';
import {SpaceAddComponent} from './space-add.component';
import {SpaceDetailComponent} from './space-detail.component';
import {BaseComponent} from '../../core/service/base-component';
import {AdSpaceService} from './service/ad-space.service';

@Component({
  selector: 'ngx-ad-space',
  templateUrl: './ad-space.component.html',
})
export class AdSpaceComponent extends BaseComponent implements OnInit, OnDestroy {

  constructor(private adSpaceService: AdSpaceService, protected injector: Injector,
              private dialogService: NbDialogService) {
    super(adSpaceService, injector);
  }

  private dialog: NbDialogRef<any>;

  ngOnInit() {
    this.getPager(1);
  }

  ngOnDestroy(): void {
    if (this.dialog) {
      this.dialog.close();
    }
  }

  /**
   * 显示添加广告位
   */
  showAddAdSpace() {
    this.dialog = this.dialogService.open(SpaceAddComponent);
    this.dialog.onClose.subscribe(result => {
      if (result) {
        this.getPager(1);
      }
    });
  }

  /**
   * 显示编辑广告位
   */
  showEditAdSpace(id: string) {
    this.dialog = this.dialogService.open(SpaceDetailComponent);
      this.dialog.componentRef.instance.spaceId = id;
      this.dialog.onClose.subscribe(result => {
        if (result) {
          this.getPager(1);
        }
      });
  }

}
