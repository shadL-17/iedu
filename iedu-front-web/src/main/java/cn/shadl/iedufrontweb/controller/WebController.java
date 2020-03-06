package cn.shadl.iedufrontweb.controller;

import cn.shadl.ieducommonbeans.domain.*;
import cn.shadl.iedufrontweb.config.HostConfig;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.List;

@Controller
public class WebController {

    @Autowired
    private HostConfig hostConfig;

    @Autowired
    private RestTemplate restTemplate;

    public void setHostIp(HttpServletRequest req) {
        req.setAttribute("host",hostConfig.getIp());
    }

    public void setUser(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        try{
            for(Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    User user_cookie = JSON.parseObject(URLDecoder.decode(cookie.getValue(),"utf-8"), User.class);
                    String url = "http://"+hostConfig.getIp()+":8080/user/loginCheck";
                    MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
                    params.add("username",user_cookie.getUsername());
                    params.add("password",user_cookie.getPassword());
                    User user_database = restTemplate.postForObject(url, params, User.class);
                    if(user_database.getUsername().equals(user_cookie.getUsername()) && user_database.getPassword().equals(user_cookie.getPassword())) {
                        req.setAttribute("user", user_database);
                    }
                    return;
                }
            }
        } catch (Exception e) {
            return;
        }
    }

    public void initRequest(HttpServletRequest req) {
        setHostIp(req);
        setUser(req);
    }

    @RequestMapping("/")
    public String home(HttpServletRequest request) {
        initRequest(request);
        ResponseEntity<List<Course>> responseEntity = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/findTopXCourses?x=4", HttpMethod.GET, null, new ParameterizedTypeReference<List<Course>>(){});
        List<Course> courses = responseEntity.getBody();
        request.setAttribute("hot_courses", courses);
        return "index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        initRequest(request);
        return "login";
    }

    @RequestMapping("/CourseCenter")
    public String courseCenter(HttpServletRequest request) {
        initRequest(request);
        ResponseEntity<List<Course>> responseEntity = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/findAllCourses", HttpMethod.GET, null, new ParameterizedTypeReference<List<Course>>(){});
        List<Course> courses = responseEntity.getBody();
        request.setAttribute("courses", courses);
        return "course-center";
    }

    @GetMapping("/Course")
    public String courseProfile(HttpServletRequest request, @RequestParam("cid") Integer cid) {
        initRequest(request);
        ResponseEntity<Course> responseEntity1 = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/course/findCourseByCid?cid=" + cid, HttpMethod.GET, null, new ParameterizedTypeReference<Course>() {});
        Course course = responseEntity1.getBody();
        request.setAttribute("course", course);
        if(course!=null) {
            ResponseEntity<User> responseEntity2 = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/user/findByUid?uid=" + course.getCreator(), HttpMethod.GET, null, new ParameterizedTypeReference<User>() {});
            User creator = responseEntity2.getBody();
            request.setAttribute("creator", creator);
            ResponseEntity<List<Chapter>> responseEntity3 = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/course/findChaptersByCid?cid=" + course.getCid(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Chapter>>() {});
            List<Chapter> chapters = responseEntity3.getBody();
            request.setAttribute("chapters", chapters);
            if(chapters!=null && !chapters.isEmpty()) {
                for(Chapter chapter : chapters) {
                    ResponseEntity<List<Lession>> lessionResponseEntity = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/course/findLessionsByChid?chid=" + chapter.getChid(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Lession>>(){});
                    List<Lession> lessions = lessionResponseEntity.getBody();
                    request.setAttribute("chapterLessions"+chapter.getChid(), lessions);
                    ResponseEntity<List<Exam>> examResponseEntity = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/course/findExamsByChid?chid=" + chapter.getChid(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Exam>>() {});
                    List<Exam> exams = examResponseEntity.getBody();
                    request.setAttribute("chapterExams"+chapter.getChid(), exams);
                }
            }
        }
        return "course-profile";
    }

    @RequestMapping("/Community")
    public String community(HttpServletRequest request) {
        initRequest(request);
        return "community";
    }

    @RequestMapping("/About")
    public String about(HttpServletRequest request) {
        initRequest(request);
        return "about";
    }

}
