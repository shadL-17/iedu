package cn.shadl.iedufrontweb.controller;

import cn.shadl.ieducommonbeans.domain.Course;
import cn.shadl.iedufrontweb.config.HostConfig;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Controller
public class WebController {

    @Autowired
    private HostConfig hostConfig;

    @RequestMapping("/")
    public String home(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("hot_courses")) {
                    try {
                        String jsonStr = URLDecoder.decode(cookie.getValue(),"utf-8");
                        Cookie replacement = new Cookie("hot_courses",null);
                        replacement.setPath("/");
                        replacement.setMaxAge(1);
                        response.addCookie(replacement);
                        List<Course> hot_courses = JSON.parseArray(jsonStr, Course.class);
                        request.setAttribute("hot_courses",hot_courses);
                        return "index";
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        try {
            response.sendRedirect("http://"+hostConfig.getIp()+":8080/course/find-topX?x=4");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

}
