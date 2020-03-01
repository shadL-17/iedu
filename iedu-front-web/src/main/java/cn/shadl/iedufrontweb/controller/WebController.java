package cn.shadl.iedufrontweb.controller;

import cn.shadl.ieducommonbeans.domain.Course;
import cn.shadl.iedufrontweb.config.HostConfig;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/")
    public String home(HttpServletRequest request) {
        ResponseEntity<List<Course>> responseEntity = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/find-topX?x=5", HttpMethod.GET, null, new ParameterizedTypeReference<List<Course>>(){});
        List<Course> courses = responseEntity.getBody();
        request.setAttribute("hot_courses", courses);
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/CourseCenter")
    public String courseCenter(HttpServletRequest request) {
        ResponseEntity<List<Course>> responseEntity = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/findAllCourses", HttpMethod.GET, null, new ParameterizedTypeReference<List<Course>>(){});
        List<Course> courses = responseEntity.getBody();
        request.setAttribute("courses", courses);
        return "course-center";
    }

    @RequestMapping("/Community")
    public String community() {
        return "community";
    }

    @RequestMapping("/About")
    public String about() {
        return "about";
    }

}
