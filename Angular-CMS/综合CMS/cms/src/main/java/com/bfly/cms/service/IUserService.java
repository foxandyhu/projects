package com.bfly.cms.service;

import com.bfly.cms.entity.User;
import com.bfly.core.base.service.IBaseService;

/**
 * 用户管理业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/7 10:32
 */
public interface IUserService extends IBaseService<User, Integer> {

    /**
     * 根据用户名查找用户对象
     *
     * @param userName 用户名
     * @return 用户对象
     * @author andy_hulibo@163.com
     * @date 2019/8/1 13:26
     */
    User getUser(String userName);

    /**
     * 用户登录
     *
     * @param password 明文密码
     * @param userName 用户名
     * @return 返回用户对象
     * @author andy_hulibo@163.com
     * @date 2018/12/8 9:05
     */
    User login(String userName, String password);

    /**
     * 用户退出
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/27 17:36
     */
    void logout(String userName);

    /**
     * 管理员角色回收即解除某个角色权限
     *
     * @param roleId 角色Id
     * @param userId 用户ID
     * @author andy_hulibo@163.com
     * @date 2018/12/10 16:02
     */
    void recyclingRole(int userId, int roleId);

    /**
     * 修改用户密码
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/29 14:54
     */
    boolean editPwd(int userId, String oldPwd, String newPwd);

    /**
     * 重置密码
     * @author andy_hulibo@163.com
     * @date 2020/7/1 11:37
     */
    boolean resetPwd(String userName,String password);
}
