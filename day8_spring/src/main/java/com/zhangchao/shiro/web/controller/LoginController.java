package com.zhangchao.shiro.web.controller;


import com.zhangchao.shiro.dao.impl.UserDAOImpl;
import com.zhangchao.shiro.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @Resource
    private UserDAOImpl userDAO;
    @RequestMapping("/login")
    public String login(Model model, HttpServletResponse resp, HttpServletRequest req, String username, String password) throws  Exception{
        if (username!=null){
            User user = userDAO.getUserByUsername(username);
            System.out.println("==========="+username);
            System.out.println("==========="+password);
            System.out.println("==========="+user);
            if (user!=null){
                req.getRequestDispatcher("/WEB-INF/views/main.jsp").forward(req, resp);
            }else {
                return "forward:/login.jsp";
            }
        }
        return "forward:/login.jsp";

        /*//如果登陆失败从request中获取认证异常信息，shiroLoginFailure就是shiro异常类的全限定名
        String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");
        //根据shiro返回的异常类路径判断，抛出指定异常信息
        System.out.println(exceptionClassName);
        if(exceptionClassName!=null){
            if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
                //最终会抛给异常处理器
                System.out.println("账号不存在");
                model.addAttribute("errorMsg", "账号不存在");
            } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
                System.out.println("用户名/密码错误");
                model.addAttribute("errorMsg", "用户名/密码错误");
            } else {
                //最终在异常处理器生成未知错误.
                System.out.println("其他异常信息");
                model.addAttribute("errorMsg", "其他异常信息");
            }
        }
        //此方法不处理登陆成功（认证成功），shiro认证成功会自动跳转到上一个请求路径
        //登陆失败还到login页面
        return "forward:/login.jsp";*/
    }

}
