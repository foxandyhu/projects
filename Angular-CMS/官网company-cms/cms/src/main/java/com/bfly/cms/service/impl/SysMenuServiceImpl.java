package com.bfly.cms.service.impl;

import com.bfly.cms.entity.SysMenu;
import com.bfly.cms.service.ISysMenuService;
import com.bfly.cms.entity.UserRole;
import com.bfly.cms.service.IUserRoleService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2019/7/12 18:38
 */
@Service
@Transactional(propagation= Propagation.SUPPORTS, rollbackFor = Exception.class)
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenu, Integer> implements ISysMenuService {

    @Autowired
    private IUserRoleService userRoleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int remove(Integer... integers) {
        for (int id : integers) {
            SysMenu menu = get(id);
            List<SysMenu> children = menu.getChildren();
            if (children != null) {
                Integer[] childrenIds = new Integer[children.size()];
                for (int i = 0; i < children.size(); i++) {
                    childrenIds[i] = children.get(i).getId();
                }
                remove(childrenIds);
            }

            //解绑系统菜单和角色的关系
            List<UserRole> roles = menu.getRoles();
            if (roles != null) {
                for (int i = 0; i < roles.size(); i++) {
                    userRoleService.recycleRoleMenu(roles.get(i).getId(), id);
                }
            }
        }
        getCache().clear();
        return super.remove(integers);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(SysMenu menu) {
        SysMenu sysMenu = get(menu.getId());
        Assert.notNull(sysMenu, "菜单信息不存在!");

        sysMenu.setName(menu.getName());
        sysMenu.setUrl(menu.getUrl());
        sysMenu.setSeq(menu.getSeq());
        sysMenu.setIcon(menu.getIcon());
        sysMenu.setAction(menu.isAction());
        return super.edit(sysMenu);
    }
}
