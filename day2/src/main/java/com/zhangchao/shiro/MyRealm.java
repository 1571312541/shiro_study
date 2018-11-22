package com.zhangchao.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 〈自定义realm〉
 *
 * @author 22902
 * @create 2018/11/20
 */
public class MyRealm extends AuthorizingRealm {

    @Override
    public String getName() {
        return "myRealm";
    }

    /**
     * 授权操作
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证操作
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
      //token 表示登陆时包装的username和password
        //通过用户名到数据库中查询用户信息，封装成一个authenticationInfo 对象返回，方便认证器进行对比
        //获取token中用户名
       String username = (String) token.getPrincipal();
        System.out.println(username);
        //通过用户名查询数据库，将改用户对应数据返回账号密码
        if (!"zhangchao".equals(username)){
            return null;
        }
        String password = "666";
        //info对象表示realm登陆对比信息，参数1：用户信息（真实登陆中是登陆对象user对象），参数2：密码，参数3：当前realm名
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, getName());

        return info;
    }
}
