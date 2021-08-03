package com.bfly.cms.service.impl;

import com.bfly.cms.dao.IUserDao;
import com.bfly.cms.entity.SysLog;
import com.bfly.cms.entity.User;
import com.bfly.cms.entity.UserRole;
import com.bfly.cms.enums.LogsType;
import com.bfly.cms.service.ISysLogService;
import com.bfly.cms.service.IUserRoleService;
import com.bfly.cms.service.IUserService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.context.IpThreadLocal;
import com.bfly.core.context.ServletRequestThreadLocal;
import com.bfly.core.context.UserThreadLocal;
import com.bfly.core.security.LoginToken;
import com.bfly.core.security.Md5PwdEncoder;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/7 10:33
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class UserServiceImpl extends BaseServiceImpl<User, Integer> implements IUserService {

    @Autowired
    private IUserDao userDao;
    @Autowired
    private ISysLogService sysLogService;
    @Autowired
    private IUserRoleService roleService;

    @Override
    public User getUser(String userName) {
        return userDao.getUserByUserName(userName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(User user) {
        Assert.notNull(user, "用户信息为空!");

        User u = get(new HashMap<String, Object>(1) {{
            put("userName", user.getUserName());
        }});
        Assert.isTrue(u == null, "该用户名已存在!");

        user.setRegisterTime(new Date());
        user.setRegisterIp(IpThreadLocal.get());
        //默认账号可用
        user.setStatus(User.AVAILABLE_STATUS);
        user.setPassword(new Md5PwdEncoder().encodePassword(user.getPassword()));

        List<UserRole> userRoles = checkRoles(user);
        user.setRoles(userRoles);

        return super.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(User user) {
        Assert.notNull(user, "用户信息为空!");

        User dbUser = getUser(user.getUserName());
        Assert.notNull(dbUser, "不存在该用户!");

        dbUser.setEmail(user.getEmail());
        dbUser.setStatus(user.getStatus());
        dbUser.setSuperAdmin(user.isSuperAdmin());
        dbUser.setFace(user.getFace());

        List<UserRole> userRoles = checkRoles(user);
        dbUser.setRoles(userRoles);
        return super.edit(dbUser);
    }

    /**
     * 校验用户角色
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/11 12:13
     */
    private List<UserRole> checkRoles(User user) {
        List<UserRole> roles = user.getRoles();
        List<UserRole> userRoles = new ArrayList<>();
        for (UserRole role : roles) {
            role = roleService.get(role.getId());
            Assert.notNull(role, "不存在该角色!");
            userRoles.add(role);
        }
        return userRoles;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User login(String userName, String password) {
        Assert.hasText(userName, "用户名不能为空!");
        Assert.hasText(password, "密码不能为空!");

        Subject subject = SecurityUtils.getSubject();
        LoginToken token = new LoginToken(userName, password, false, true);
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            throw new RuntimeException("用户名或密码错误!");
        } catch (IncorrectCredentialsException e) {
            throw new RuntimeException("用户名或密码错误!");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
        }

        User user = (User) subject.getPrincipal();
        updateLoginInfo(user);
        saveLoginLogs(userName, "用户登录", LogsType.LOGIN_LOG);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void logout(String userName) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        saveLoginLogs(userName, "用户登出", LogsType.LOGOUT_LOG);
    }

    /**
     * 保存登录/登出信息
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/26 18:33
     */
    private void saveLoginLogs(String userName, String title, LogsType type) {
        HttpServletRequest request = ServletRequestThreadLocal.get();
        SysLog log = new SysLog();
        log.setTime(new Date());
        log.setTitle(title);
        log.setUserName(userName);
        log.setIp(IpThreadLocal.get());
        log.setUrl(request.getRequestURL().toString());
        log.setSuccess(true);
        log.setContent(null);
        log.setCategory(type.getId());
        sysLogService.save(log);
    }

    /**
     * 更新登录信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/8 9:33
     */
    protected void updateLoginInfo(User user) {
        user.setLastLoginTime(new Date());
        user.setLastLoginIp(IpThreadLocal.get());
        user.setLoginCount(user.getLoginCount() + 1);
        super.edit(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recyclingRole(int userId, int roleId) {
        User user = get(userId);
        Assert.notNull(user, "用户信息不存在!");
        List<UserRole> roles = user.getRoles();
        if (roles == null) {
            return;
        }
        Iterator<UserRole> it = roles.iterator();
        while (it.hasNext()) {
            //回收指定的角色
            UserRole role = it.next();
            if (roleId == role.getId()) {
                it.remove();
            }
        }
        user.setRoles(roles);
        super.edit(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int remove(Integer... integers) {
        User user = UserThreadLocal.get();
        int count = 0;
        for (int id : integers) {
            if (id == user.getId()) {
                throw new RuntimeException("不能删除当前登录用户!");
            }
            userDao.deleteById(id);
            count++;
        }
        getCache().clear();
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editPwd(int userId, String oldPwd, String newPwd) {
        User user = get(userId);
        boolean flag = new Md5PwdEncoder().isPasswordValid(user.getPassword(), oldPwd);
        Assert.isTrue(flag, "原始密码不正确!");

        newPwd = new Md5PwdEncoder().encodePassword(newPwd);
        user.setPassword(newPwd);
        return userDao.editUserPassword(userId, newPwd) > 0;
    }

    @Override
    @Transactional
    public boolean resetPwd(String userName, String password) {
        Assert.hasLength(password, "新密码不能为空!");

        User user = getUser(userName);
        Assert.isTrue(user != null, "用户不存在!");

        password = new Md5PwdEncoder().encodePassword(password);
        user.setPassword(password);
        return super.edit(user);
    }
}
