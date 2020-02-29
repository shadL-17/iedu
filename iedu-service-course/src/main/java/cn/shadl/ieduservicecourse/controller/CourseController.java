package cn.shadl.ieduservicecourse.controller;

import cn.shadl.ieducommonbeans.domain.Course;
import cn.shadl.ieduservicecourse.config.HostConfig;
import cn.shadl.ieduservicecourse.service.CourseService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class CourseController {

    @Autowired
    private HostConfig hostConfig;

    @Autowired
    private CourseService courseService;

    @GetMapping("/find-topX")
    public void findTopXCourses(HttpServletResponse response, @RequestParam("x") int x) {
        List<Course> courses = courseService.findTopX(x);
        try {
            Cookie cookie_courses = new Cookie("hot_courses", URLEncoder.encode(JSON.toJSONString(courses),"utf-8"));
            cookie_courses.setPath("/");
            response.addCookie(cookie_courses);
            response.sendRedirect("http://"+hostConfig.getIp());
        } catch (UnsupportedEncodingException e) {
            System.out.println("json-URL转码失败");
        } catch (IOException e) {
            System.out.println("重定向回主页失败");
        }
    }

}
