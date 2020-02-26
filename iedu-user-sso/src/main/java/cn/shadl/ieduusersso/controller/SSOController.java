package cn.shadl.ieduusersso.controller;

import cn.shadl.ieducommonbeans.domain.User;
import cn.shadl.ieduusersso.config.HostConfig;
import cn.shadl.ieduusersso.service.SSOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class SSOController {

    @Autowired
    private HostConfig hostConfig;

    @Autowired
    private SSOService ssoService;

    @RequestMapping("/login")
    public void login(HttpServletResponse response, String username, String password) {
        List<User> users = ssoService.findByUsernameAndPassword(username, password);
        if(users.isEmpty()) {
            Cookie cookie_msg = new Cookie("msg","用户名或密码错误。");
            cookie_msg.setPath("/");
            cookie_msg.setMaxAge(1);
            response.addCookie(cookie_msg);
            try {
                response.sendRedirect("http://"+hostConfig.getIp()+"/login");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        else {
            User user = users.get(0);
            Cookie cookie_user = new Cookie("user",user.getUsername());
            cookie_user.setPath("/");
            response.addCookie(cookie_user);
            try {
                response.sendRedirect("http://"+hostConfig.getIp()+"/index");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
    }

    @RequestMapping("/logout")
    public void logout(HttpServletResponse response) {
        Cookie cookie_user = new Cookie("user",null);
        cookie_user.setPath("/");
        cookie_user.setMaxAge(0);
        response.addCookie(cookie_user);
        try {
            response.sendRedirect("http://"+hostConfig.getIp()+"/index");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
