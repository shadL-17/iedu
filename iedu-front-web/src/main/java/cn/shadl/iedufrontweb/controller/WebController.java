package cn.shadl.iedufrontweb.controller;

import cn.shadl.ieducommonbeans.domain.Course;
import cn.shadl.ieducommonbeans.domain.User;
import cn.shadl.iedufrontweb.config.HostConfig;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        ResponseEntity<List<Course>> responseEntity = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/findTopXCourses?x=4", HttpMethod.GET, null, new ParameterizedTypeReference<List<Course>>(){});
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

    @GetMapping("/Course")
    public String courseProfile(HttpServletRequest request, @RequestParam("cid") Integer cid) {
        ResponseEntity<Course> responseEntity1 = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/course/findCourseByCid?cid=" + cid, HttpMethod.GET, null, new ParameterizedTypeReference<Course>() {});
        Course course = responseEntity1.getBody();
        request.setAttribute("course", course);
        if(course!=null) {
            ResponseEntity<User> responseEntity2 = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/user/findByUid?uid=" + course.getCreator(), HttpMethod.GET, null, new ParameterizedTypeReference<User>() {});
            User creator = responseEntity2.getBody();
            request.setAttribute("creator", creator);
        }
        return "course-profile";
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
