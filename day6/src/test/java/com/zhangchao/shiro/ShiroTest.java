package com.zhangchao.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

/**
 * 〈〉
 *
 * @author 22902
 * @create 2018/11/20
 */
public class ShiroTest {

    @Test
    public void testLogin(){

        //1、创建SecurityManager工厂,加载配置文件，创建工厂对象
        Factory<SecurityManager> managerFactory = new IniSecurityManagerFactory("classpath:shiro-permission-realm.ini");
        //2、通过工厂对象，创建SecurityManager对象
        SecurityManager securityManager = managerFactory.getInstance();

        //3、将securityManager绑定到当前运行环境，让系统随时可以访问securityManager对象
        SecurityUtils.setSecurityManager(securityManager);
        //4、创建当前登陆主体
        Subject subject = SecurityUtils.getSubject();
        //5、收集主体登陆的身份凭证，即账号，密码
        UsernamePasswordToken token = new UsernamePasswordToken("zhangchao", "666");
        //6、主体登陆
        try {
            subject.login(token);
            boolean a = subject.isPermitted("user:create");
            System.out.println("是否拥有权限: "+a);
            boolean b = subject.hasRole("role1");
            System.out.println("是否拥有角色: "+b);

        }catch (UnknownAccountException e){
            e.printStackTrace();
            System.out.println("用户名错误");
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            System.out.println("密码错误");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //验证是否登陆
        System.out.println("验证是否登陆  ： "+ subject.isAuthenticated());
        //7、登出
        subject.logout();
        //验证是否登出
        System.out.println("验证是否登出  ： "+ subject.isAuthenticated());
    }


}
