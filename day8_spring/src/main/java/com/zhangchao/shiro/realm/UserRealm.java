package com.zhangchao.shiro.realm;

import com.zhangchao.shiro.dao.IPermissionDAO;
import com.zhangchao.shiro.dao.IRoleDAO;
import com.zhangchao.shiro.dao.IUserDAO;
import com.zhangchao.shiro.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


public class UserRealm extends AuthorizingRealm {

    @Resource
    private IUserDAO userDAO;
    @Resource
    private IRoleDAO roleDAO;
    @Resource
    private IPermissionDAO permissionDAO;

    private static final Logger logger = LoggerFactory.getLogger(UserRealm.class);
    //认证操作
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        logger.info("=================认证操作===================");
        //从token中获取登录的用户名， 查询数据库返回用户信息
        String username = (String) token.getPrincipal();
        User user = userDAO.getUserByUsername(username);
        System.out.println("realm"+user);
        if(user == null){
            return null;
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(),
                ByteSource.Util.bytes(user.getUsername()),
                getName());
        return info;
    }



    @Override
    public String getName() {
        return "UserRealm";
    }

    //授权操作
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("=================授权操作===================");
        User user = (User) principals.getPrimaryPrincipal();

        List<String> permissions = new ArrayList<>();
        List<String> roles = new ArrayList<>();

        if("admin".equals(user.getUsername())){
            //拥有所有权限
            permissions.add("*:*");
            //查询所有角色
            roles = roleDAO.getAllRoleSn();
        }else{
            //根据用户id查询该用户所具有的角色
            roles = roleDAO.getRoleSnByUserId(user.getId());
            //根据用户id查询该用户所具有的权限
            permissions = permissionDAO.getPermissionResourceByUserId(user.getId());
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissions);
        info.addRoles(roles);
        return info;
    }

    //清除缓存
    public void clearCached() {
        //获取当前等的用户凭证，然后清除
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }


}
