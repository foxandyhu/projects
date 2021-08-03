import {HttpUtil} from '../utils/http';
import {AppApi} from '../app-api';
import {Injectable} from '@angular/core';

/**
 * 公共的Service主要是一些通用的方法
 */
@Injectable({providedIn: 'root'})
export class CommonService {

  constructor(private httpUtil: HttpUtil) {
  }

  /**
   * 文件上传
   */
  uploadFile(file: any): Promise<any> {
    if (file) {
      const formData = new FormData();
      formData.append('file', file);
      formData.append('watermark', file.watermark);
      return this.httpUtil.post(AppApi.FILES.file_upload, formData).then(response => {
        return Promise.resolve(response);
      });
    }
  }
}
