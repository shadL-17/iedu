package cn.shadl.iedufrontweb.controller;

import cn.shadl.ieducommonbeans.domain.*;
import cn.shadl.ieducommonbeans.domain.dto.CommentFloorDTO;
import cn.shadl.ieducommonbeans.domain.dto.ExamQuestionDTO;
import cn.shadl.iedufrontweb.config.HostConfig;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //初始化，读取主机配置和登录状态
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

    @GetMapping("/Course")
    public String courseProfile(HttpServletRequest request, @RequestParam("cid") Integer cid) {
        initRequest(request);
        Course course = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/findCourseByCid?cid="+cid, HttpMethod.GET, null, new ParameterizedTypeReference<Course>() {}).getBody();
        Integer numOfStu = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/getNumberOfStudentsByCid?cid="+cid, HttpMethod.GET, null, new ParameterizedTypeReference<Integer>() {}).getBody();
        Integer numOfLes = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/getNumberOfLessionsByCid?cid="+cid, HttpMethod.GET, null, new ParameterizedTypeReference<Integer>() {}).getBody();
        Map<String, Object> courseInfo = new HashMap<String, Object>();
        courseInfo.put("numberOfStudents", numOfStu);
        courseInfo.put("numberOfLessions", numOfLes);
        request.setAttribute("course", course);
        request.setAttribute("courseInfo", courseInfo);
        if(course!=null) {
            User creator = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/user/findByUid?uid=" + course.getCreator(), HttpMethod.GET, null, new ParameterizedTypeReference<User>() {}).getBody();
            request.setAttribute("creator", creator);
            List<Chapter> chapters = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/course/findChaptersByCid?cid=" + course.getCid(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Chapter>>() {}).getBody();
            request.setAttribute("chapters", chapters);
            if(chapters!=null && !chapters.isEmpty()) {
                for(Chapter chapter : chapters) {
                    List<Lession> lessions = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/course/findLessionsByChid?chid=" + chapter.getChid(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Lession>>(){}).getBody();
                    request.setAttribute("chapterLessions"+chapter.getChid(), lessions);
                    List<Exam> exams = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/course/findExamsByChid?chid=" + chapter.getChid(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Exam>>() {}).getBody();
                    request.setAttribute("chapterExams"+chapter.getChid(), exams);
                }
            }
        }
        return "course-profile";
    }

    @GetMapping("/Lession")
    public String lessionPage(HttpServletRequest request, @RequestParam("lid") Integer lid) {
        initRequest(request);
        Lession currentLession = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/course/findLessionByLid?lid=" + lid, HttpMethod.GET, null, new ParameterizedTypeReference<Lession>() {}).getBody();
        Chapter currentChapter = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/course/findChapterByChid?chid=" + currentLession.getChid(), HttpMethod.GET, null, new ParameterizedTypeReference<Chapter>() {}).getBody();
        Integer cid = currentChapter.getCid();
        Course course = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/findCourseByCid?cid="+cid, HttpMethod.GET, null, new ParameterizedTypeReference<Course>() {}).getBody();
        Integer numOfStu = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/getNumberOfStudentsByCid?cid="+cid, HttpMethod.GET, null, new ParameterizedTypeReference<Integer>() {}).getBody();
        Integer numOfLes = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/getNumberOfLessionsByCid?cid="+cid, HttpMethod.GET, null, new ParameterizedTypeReference<Integer>() {}).getBody();
        Map<String, Object> courseInfo = new HashMap<String, Object>();
        courseInfo.put("numberOfStudents", numOfStu);
        courseInfo.put("numberOfLessions", numOfLes);
        request.setAttribute("course", course);
        request.setAttribute("courseInfo", courseInfo);
        request.setAttribute("chapter", currentChapter);
        request.setAttribute("lession", currentLession);
        if(course!=null) {
            User creator = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/user/findByUid?uid=" + course.getCreator(), HttpMethod.GET, null, new ParameterizedTypeReference<User>() {}).getBody();
            request.setAttribute("creator", creator);
            List<Chapter> chapters = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/course/findChaptersByCid?cid=" + course.getCid(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Chapter>>() {}).getBody();
            request.setAttribute("chapters", chapters);
            if(chapters!=null && !chapters.isEmpty()) {
                for(Chapter chapter : chapters) {
                    List<Lession> lessions = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/course/findLessionsByChid?chid=" + chapter.getChid(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Lession>>(){}).getBody();
                    request.setAttribute("chapterLessions"+chapter.getChid(), lessions);
                    List<Exam> exams = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/course/findExamsByChid?chid=" + chapter.getChid(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Exam>>() {}).getBody();
                    request.setAttribute("chapterExams"+chapter.getChid(), exams);
                }
            }
        }
        List<CommentFloorDTO> comments = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/course/findCommentsByTargetTypeAndTargetId?targetType=lession&targetId=" + currentLession.getLid(), HttpMethod.GET, null, new ParameterizedTypeReference<List<CommentFloorDTO>>() {}).getBody();
        request.setAttribute("comments", comments);
        return "course-lession";
    }

    @GetMapping("/Exam")
    public String examPage(HttpServletRequest request, @RequestParam("eid") Integer eid) {
        initRequest(request);
        Exam exam = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/findExamByEid?eid="+eid, HttpMethod.GET, null, new ParameterizedTypeReference<Exam>() {}).getBody();
        List<ExamQuestionDTO> questions = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/findQuestionByEid?eid="+eid, HttpMethod.GET, null, new ParameterizedTypeReference<List<ExamQuestionDTO>>() {}).getBody();
        request.setAttribute("exam", exam);
        request.setAttribute("questions", questions);
        Chapter currentChapter = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/course/findChapterByChid?chid=" + exam.getChid(), HttpMethod.GET, null, new ParameterizedTypeReference<Chapter>() {}).getBody();
        Integer cid = currentChapter.getCid();
        Course course = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/findCourseByCid?cid="+cid, HttpMethod.GET, null, new ParameterizedTypeReference<Course>() {}).getBody();
        Integer numOfStu = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/getNumberOfStudentsByCid?cid="+cid, HttpMethod.GET, null, new ParameterizedTypeReference<Integer>() {}).getBody();
        Integer numOfLes = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/getNumberOfLessionsByCid?cid="+cid, HttpMethod.GET, null, new ParameterizedTypeReference<Integer>() {}).getBody();
        Map<String, Object> courseInfo = new HashMap<String, Object>();
        courseInfo.put("numberOfStudents", numOfStu);
        courseInfo.put("numberOfLessions", numOfLes);
        request.setAttribute("course", course);
        request.setAttribute("courseInfo", courseInfo);
        return "course-exam";
    }

}
