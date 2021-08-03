import {NbMenuItem} from '@nebular/theme';

export const MENU_ITEMS: NbMenuItem[] = [
  {
    title: '首页',
    icon: 'ion-home',
    children: [
      {
        title: '工作台',
        link: '/pages/workbench',
      },
    ],
  },
  {
    title: '内容管理',
    icon: 'ion-ios-book',
    children: [
      {
        title: '栏目管理',
        link: '/pages/content/channel',
      },
      {
        title: '内容管理',
        link: '/pages/content/article',
      },
      {
        title: '专题管理',
        link: '/pages/content/topic',
      }, {
        title: '模型管理',
        link: '/pages/content/model',
      },
    ],
  },
  {
    title: '运营',
    icon: 'ion-ios-cog',
    children: [
      {
        title: '友情链接',
        link: '/pages/friendlink/list',
      },
      {
        title: '广告管理',
        link: '/pages/ad/list',
      },
      {
        title: '问卷调查',
        link: '/pages/vote/list',
      }, {
        title: '评论管理',
        link: '/pages/message/comment',
      }, {
        title: '留言管理',
        link: '/pages/message/guestbook',
      }, {
        title: '站内信管理',
        link: '/pages/message/letter',
      },
    ],
  },
  {
    title: '用户管理',
    icon: 'ion-person',
    children: [
      {
        title: '会员管理',
        link: '/pages/member/list',
      }, {
        title: '会员组管理',
        link: '/pages/member/group',
      },
      {
        title: '菜单管理',
        link: '/pages/system/menu',
      },
      {
        title: '角色管理',
        link: '/pages/user/role',
      },
      {
        title: '系统管理员',
        link: '/pages/user/list',
      },
    ],
  },
  {
    title: '数据分析',
    icon: 'ion-ios-analytics',
    children: [
      {
        title: '趋势分析',
        link: '/pages/analysis/flow',
      }, {
        title: '来源分类',
        link: '/pages/analysis/source',
      }, {
        title: '搜索引擎',
        link: '/pages/analysis/engine',
      }, {
        title: '来访站点',
        link: '/pages/analysis/site',
      }, {
        title: '浏览器',
        link: '/pages/analysis/browser',
      }, {
        title: '来访地区',
        link: '/pages/analysis/area',
      },
    ],
  },
  {
    title: '系统资源',
    icon: 'ion-images',
    children: [
      {
        title: '资源管理',
        link: '/pages/system/resource',
      }, {
        title: '模版管理',
        link: '/pages/system/template',
      },
    ],
  },
  {
    title: '辅助数据',
    icon: 'ion-cube',
    children: [
      {
        title: '数据字典',
        link: '/pages/words/dictionary',
      },
      {
        title: '敏感词管理',
        link: '/pages/words/sensitivewords',
      }, {
        title: '热词管理',
        link: '/pages/words/searchwords',
      }, {
        title: '评分组管理',
        link: '/pages/words/score/group',
      },
    ],
  },
  {
    title: '系统设置',
    icon: 'ion-settings',
    children: [
      {
        title: '站点设置',
        link: '/pages/system/setting/config/site',
      },
      {
        title: '登录注册设置',
        link: '/pages/member/config/login',
      }, {
        title: '水印设置',
        link: '/pages/system/setting/watermark',
      }, {
        title: '防火墙设置',
        link: '/pages/system/setting/firewall',
      }, {
        title: '企业设置',
        link: '/pages/system/setting/company',
      }, {
        title: '定时任务',
        link: '/pages/system/setting/task',
      },
    ],
  },
  {
    title: '第三方服务管理',
    icon: 'ion-ios-flower',
    children: [
      {
        title: '短信服务商管理',
        link: '/pages/system/sms/provider',
      },
      {
        title: '短信记录',
        link: '/pages/system/sms/record',
      },
      {
        title: '邮件服务商管理',
        link: '/pages/system/email/provider',
      },
    ],
  },
  {
    title: '日志管理',
    icon: 'ion-document',
    children: [
      {
        title: '操作日志',
        link: '/pages/logs/sys',
      },
    ],
  },
];
