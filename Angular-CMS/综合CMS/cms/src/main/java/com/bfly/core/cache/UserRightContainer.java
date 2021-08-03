package com.bfly.core.cache;

import com.bfly.cms.entity.SysMenu;
import com.bfly.cms.entity.User;
import com.bfly.cms.entity.UserRole;
import com.bfly.cms.service.ISysMenuService;
import com.bfly.core.context.UserThreadLocal;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户权限容器
 *
 * @author 胡礼波-Andy
 * @2015年12月14日下午5:48:46
 */
@Component("UsersRightContainer")
public class UserRightContainer implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(UserRightContainer.class);

    private static List<SysMenu> NO_NEED_AUTH_URLS = new ArrayList<>();

    static {
        // 这些url都是不需要授权的 任何登录角色都可以访问
        String urls[] = {"/manage/user/menus.html", "/manage/dictionary/type/{type}.*", "/manage/dictionary/types.html", "/manage/model/all.html",
                "/manage/template/path.html", "/manage/model/item/default.html", "/manage/ad/space/all.html", "/manage/file/upload.html",
                "/manage/friendlink/type/all.html", "/manage/member/check.html", "/manage/member/group/all.html", "/manage/user/role/all.html",
                "/manage/resource/fonts.html", "/manage/score/group/all.html", "/manage/sms/provider/all.html", "/manage/email/provider/all.html",
                "/manage/user/edit/pwd.html","/manage/site/copyright.html"};
        for (String url : urls) {
            NO_NEED_AUTH_URLS.add(new SysMenu(url));
        }
    }

    /**
     * 系统菜单缓存Key
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/13 18:51
     */
    public static final String SYS_ROLE_MENU_KEY = "sys_role_menu_key_";

    /**
     * 系统功能菜单缓存Key
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/30 10:27
     */
    public static final String SYS_ROLE_FUN_MENU_KEY = "sys_role_fun_menu_key_";

    /**
     * @author 胡礼波-Andy
     * @2015年12月14日下午5:48:57
     */
    private static final long serialVersionUID = -3929888818854769210L;

    /**
     * 缓存名称
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/13 18:45
     */
    private static final String USER_RIGHT_CACHE = "userRightCache";

    private static Cache userRightsCache;
    private static ISysMenuService menuService;

    @Autowired
    public void setMenuService(ISysMenuService menuService) {
        UserRightContainer.menuService = menuService;
    }

    @Autowired
    public void setCacheManager(CacheManager cacheManager) {
        userRightsCache = cacheManager.getCache(USER_RIGHT_CACHE);
    }

    /**
     * 加载指定用户权限到系统缓存中
     *
     * @author 胡礼波-Andy
     * @2015年12月14日下午5:49:46
     */
    public static void loadUserRight(User user) {
        try {
            List<SysMenu> menus;
            if (user.isSuperAdmin()) {
                // 超级管理员所有功能菜单都能访问
                menus = menuService.getList();
            } else {
                List<UserRole> roles = user.getRoles();
                menus = new ArrayList<>();
                if (roles != null) {
                    for (UserRole role : roles) {
                        List<SysMenu> list = role.getMenus();
                        if (list != null) {
                            menus.addAll(role.getMenus());
                        }
                    }
                }
            }

            userRightsCache.put(SYS_ROLE_FUN_MENU_KEY + user.getId(), getFunMenus(menus));
            List<SysMenu> sysMenus = getSysMenus(user, menus.stream().filter(sysMenu -> sysMenu.getParentId() == 0).collect(Collectors.toList()));
            userRightsCache.put(SYS_ROLE_MENU_KEY + user.getId(), sysMenus);
            logger.info("【" + user.getUserName() + "】用户菜单权限加载缓存完毕!");
        } catch (Exception e) {
            logger.error("加载用户菜单权限异常," + e.getMessage());
            throw new RuntimeException("加载用户菜单权限异常," + e.getMessage());
        }
    }

    private static List<SysMenu> getSysMenus(User user, List<SysMenu> menus) {
        if (CollectionUtils.isNotEmpty(menus)) {
            Iterator<SysMenu> it = menus.iterator();
            while (it.hasNext()) {
                SysMenu menu = it.next();
                if (menu.isAction()) {
                    it.remove();
                    continue;
                }
                List<UserRole> roles = menu.getRoles();
                if (!user.isSuperAdmin()) {
                    boolean isOwner = false;
                    for (UserRole role : roles) {
                        Set<User> users = role.getUsers();
                        for (User u : users) {
                            if (u.getId() == user.getId()) {
                                isOwner = true;
                            }
                        }
                    }
                    if (!isOwner) {
                        it.remove();
                        continue;
                    }
                }
                List<SysMenu> sysMenus = getSysMenus(user, menu.getChildren());
                if (CollectionUtils.isEmpty(sysMenus)) {
                    menu.setChildren(null);
                }
            }
        }
        return menus;
    }

    private static List<SysMenu> getFunMenus(List<SysMenu> menus) {
        List<SysMenu> sysFunMenus = new ArrayList<>();
        if (menus != null) {
            for (SysMenu menu : menus) {
                if (menu.isAction()) {
                    sysFunMenus.add(menu);
                }
            }
        }
        sysFunMenus.addAll(NO_NEED_AUTH_URLS);
        return sysFunMenus;
    }


    /**
     * 获得当前用户的系统菜单
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/30 14:44
     */
    public static List<SysMenu> getCurrentUserSysMenus() {
        User user = UserThreadLocal.get();
        List list = userRightsCache.get(SYS_ROLE_MENU_KEY + user.getId(), List.class);
        if (list == null) {
            // 如果缓存失效则重新加载权限
            loadUserRight(user);
            list = userRightsCache.get(SYS_ROLE_MENU_KEY + user.getId(), List.class);
        }
        return list;
    }

    /**
     * 获得当前用户的功能菜单o
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/30 14:44
     */
    public static List<SysMenu> getCurrentUserFunMenus() {
        User user = UserThreadLocal.get();
        List list = userRightsCache.get(SYS_ROLE_FUN_MENU_KEY + user.getId(), List.class);
        return list;
    }

    /**
     * 清除登录用户的权限缓存
     *
     * @param user
     * @author 胡礼波-Andy
     * @2015年12月14日下午6:02:00
     */
    public static void clear(User user) {
        userRightsCache.evict(SYS_ROLE_MENU_KEY + user.getId());
        logger.info("【" + user.getUserName() + "】用户菜单权限清除完毕!");
    }

    /**
     * 判断权限是否存在
     *
     * @param user
     * @param url
     * @return
     * @author 胡礼波-Andy
     * @2015年12月14日下午6:04:02
     */
    public static boolean exist(User user, String url) {
        List<SysMenu> menus = userRightsCache.get(SYS_ROLE_FUN_MENU_KEY + user.getId(), List.class);
        if (menus == null) {
            return false;
        }
        //得到缓存的资源权限
        if (menus == null) {
            return false;
        }
        for (SysMenu m : menus) {
            if (m.getUrl().equals(url)) {
                return true;
            }
        }
        return false;
    }
}
