package cn.shadl.ieduservicecourse.controller;

import cn.shadl.ieducommonbeans.domain.Course;
import cn.shadl.ieduservicecourse.config.HostConfig;
import cn.shadl.ieduservicecourse.service.CourseService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RestController
public class CourseController {

    @Autowired
    private HostConfig hostConfig;

    @Autowired
    private CourseService courseService;

    @GetMapping("/findAllCourses")
    public List<Course> findAllCourses() {
        return courseService.findAll();
    }

    @GetMapping("/find-topX")
    public List<Course> findTopXCourses(@RequestParam("x") int x) {
        return courseService.findTopX(x);
    }

}
