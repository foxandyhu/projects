/**
 * 常量
 */
export class Constant {

  /**
   * 内容类型
   */
  static CONTENT_TYPES: Array<any> = [{id: 1, name: '普通'}, {id: 2, name: '图文'},
    {id: 3, name: '焦点'}, {id: 4, name: '头条'}]; //  内容类型

  /**
   * 默认图片
   */
  static DEFAULT_PIC: any = '/assets/images/add_img.png';   //  图像预览

  /**
   * 文档类型
   */
  static DOC_TYPE: number = 3;   // 文档类型

  /**
   * 多媒体类型
   */
  static MEDIA_TYPE: number = 2;  //  多媒体类型

  /**
   * 数据类型
   */
  static DATA_TYPES: any = [{id: 1, name: '文本'}, {id: 2, name: '文本域'}, {id: 3, name: '日期'},
    {id: 4, name: '下拉列表'}, {id: 5, name: '多选框'}, {id: 6, name: '单选框'},
    {id: 9, name: '富文本'}];  //  数据类型

  /**
   * 文章状态
   */
  static ARTICLE_STATUS: Array<any> = [{id: 0, name: '草稿'}, {id: 1, name: '待审核'},
    {id: 2, name: '审核通过'}, {id: 3, name: '审核未通过'}];

  /**
   * 邮件服务协议
   */
  static PROTOCOLS: Array<string> = ['SMTP', 'POP3', 'IMAP'];

  /**
   * 编码
   */
  static CHARSETS: Array<string> = ['UTF-8', 'GBK', 'GB2312', 'GB18030', 'iso-8859-1'];

  static ION_ICONS = ['ion-ionic', 'ion-arrow-right-b', 'ion-arrow-down-b', 'ion-arrow-left-b',
    'ion-arrow-up-c', 'ion-arrow-right-c',
    'ion-arrow-down-c', 'ion-arrow-left-c', 'ion-arrow-return-right', 'ion-arrow-return-left', 'ion-arrow-swap',
    'ion-arrow-shrink', 'ion-arrow-expand', 'ion-arrow-move', 'ion-arrow-resize', 'ion-chevron-up',
    'ion-chevron-right', 'ion-chevron-down', 'ion-chevron-left', 'ion-navicon-round', 'ion-navicon',
    'ion-drag', 'ion-log-in', 'ion-log-out', 'ion-checkmark-round', 'ion-checkmark', 'ion-checkmark-circled',
    'ion-close-round', 'ion-plus-round', 'ion-minus-round', 'ion-information', 'ion-help',
    'ion-backspace-outline', 'ion-help-buoy', 'ion-asterisk', 'ion-alert', 'ion-alert-circled',
    'ion-refresh', 'ion-loop', 'ion-shuffle', 'ion-home', 'ion-search', 'ion-flag', 'ion-star',
    'ion-heart', 'ion-heart-broken', 'ion-gear-a', 'ion-gear-b', 'ion-toggle-filled', 'ion-toggle',
    'ion-settings', 'ion-wrench', 'ion-hammer', 'ion-edit', 'ion-trash-a', 'ion-trash-b', 'ion-ios-cog',
    'ion-document', 'ion-document-text', 'ion-clipboard', 'ion-scissors', 'ion-funnel', 'ion-ios-book',
    'ion-bookmark', 'ion-email', 'ion-email-unread', 'ion-folder', 'ion-filing', 'ion-archive',
    'ion-reply', 'ion-reply-all', 'ion-forward', 'ion-person', 'ion-ios-analytics',
    'ion-images', 'ion-cube', 'ion-ios-flower'];

  static EDITOR: any = {
    height: 200, language: 'zh_CN', image_caption: true, paste_data_images: true,
    plugins: `link lists image code table colorpicker fullscreen help textcolor ` +
      `wordcount contextmenu codesample importcss media preview print textpattern tabfocus ` +
      `hr directionality imagetools autosave paste`,
    toolbar: 'codesample | bold italic underline strikethrough | fontsizeselect | forecolor backcolor | alignleft'
      + ' aligncenter alignright alignjustify | bullist numlist | outdent indent blockquote | undo redo '
      + '| link unlink image code | removeformat | h2 h4 | fullscreen preview paste',
    codesample_languages: [
      {text: 'JavaScript', value: 'javascript'},
      {text: 'HTML/XML', value: 'markup'},
      {text: 'CSS', value: 'css'},
      {text: 'TypeScript', value: 'typescript'},
      {text: 'Java', value: 'java'},
    ],
    imagetools_toolbar: 'rotateleft rotateright | flipv fliph | editimage imageoptions',
    images_upload_handler: function (blobInfo, success, failure) {
    },
  };
}
