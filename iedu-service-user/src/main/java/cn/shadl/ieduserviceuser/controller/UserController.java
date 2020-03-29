package cn.shadl.ieduserviceuser.controller;

import cn.shadl.ieducommonbeans.domain.User;
import cn.shadl.ieduserviceuser.config.HostConfig;
import cn.shadl.ieduserviceuser.service.UserService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private HostConfig hostConfig;

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response, String username, String password) {
        List<User> users = userService.findByUsernameAndPassword(username, password);
        if(users.isEmpty()) {//用户名密码不匹配
            //临时Cookie: 提示信息
            Cookie cookie_msg = new Cookie("msg","用户名或密码错误。");
            cookie_msg.setPath("/");
            cookie_msg.setMaxAge(1);
            response.addCookie(cookie_msg);
            //临时Cookie: 上次输入的用户名
            Cookie cookie_usr = new Cookie("usr",username);
            cookie_usr.setPath("/");
            cookie_usr.setMaxAge(1);
            response.addCookie(cookie_usr);
            //临时Cookie: 上次输入的密码
            Cookie cookie_pwd = new Cookie("pwd",password);
            cookie_pwd.setPath("/");
            cookie_pwd.setMaxAge(1);
            response.addCookie(cookie_pwd);
            try {
                response.sendRedirect("http://"+hostConfig.getIp()+"/login");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        else {//用户名密码匹配成功
            User user = users.get(0);
            Cookie cookie_user = null;
            try {
                cookie_user = new Cookie("user", URLEncoder.encode(JSON.toJSONString(user),"utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            cookie_user.setPath("/");
            response.addCookie(cookie_user);
            try {
                response.sendRedirect("http://"+hostConfig.getIp());
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
            response.sendRedirect("http://"+hostConfig.getIp());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/loginCheck")
    public User loginCheck(@RequestParam("username") String username, @RequestParam("password") String password) {
        List<User> users = userService.findByUsernameAndPassword(username, password);
        if(users!=null && !users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }

    @PostMapping("/register")
    public User register(HttpServletRequest request, HttpServletResponse response, @RequestParam("username") String username, @RequestParam("password") String password) {
        User checkUsername = userService.findByUsername(username);
        if(checkUsername==null) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setRole("student");
            User returnUser = userService.save(user);
            try {
                response.sendRedirect("http://"+hostConfig.getIp()+"/login");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return returnUser;
        }
        else {
            try {
                response.sendRedirect("http://"+hostConfig.getIp()+"/login");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @GetMapping("/findByUid")
    public User findByUid(@RequestParam("uid") Integer uid) {
        return userService.findByUid(uid);
    }

}
