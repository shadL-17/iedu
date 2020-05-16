package cn.shadl.iedufrontweb.controller;

import cn.shadl.ieducommonbeans.domain.*;
import cn.shadl.ieducommonbeans.domain.dto.*;
import cn.shadl.iedufrontweb.config.HostConfig;
import cn.shadl.iedufrontweb.util.DataUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class WebController {

    @Autowired
    private HostConfig hostConfig;

    @Autowired
    private RestTemplate restTemplate;

    public String setHostIp(HttpServletRequest req) {
        req.setAttribute("host",hostConfig.getIp());
        return hostConfig.getIp();
    }

    public User setUser(HttpServletRequest req) {
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
                    //将cookies中的用户账密和数据库中的用户账密作校对
                    if(user_database.getUsername().equals(user_cookie.getUsername()) && user_database.getPassword().equals(user_cookie.getPassword())) {
                        req.setAttribute("user", user_database);
                        return user_database;
                    }
                    return null;
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    //初始化，读取主机配置和登录状态
    public Map<String, Object> initRequest(HttpServletRequest req) {
        Map<String, Object> info = new HashMap<>();
        info.put("host", setHostIp(req));
        info.put("user", setUser(req));
        return info;
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
    public String courseCenter(HttpServletRequest request, String keyword, String type, String tab) {
        initRequest(request);
        List<Course> courses;
        if (keyword!=null && !"".equals(keyword.trim())) {
            courses = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/findCoursesByNameLike?keyword="+keyword, HttpMethod.GET, null, new ParameterizedTypeReference<List<Course>>(){}).getBody();
        }
        else if (type!=null && !"".equals(type.trim())) {
            courses = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/findCoursesByTypeLike?keyword="+type, HttpMethod.GET, null, new ParameterizedTypeReference<List<Course>>(){}).getBody();
            request.setAttribute("tabFocus", tab);
        }
        else {
            courses = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/findAllCourses", HttpMethod.GET, null, new ParameterizedTypeReference<List<Course>>(){}).getBody();
        }
        request.setAttribute("courses", courses);
        return "course-center";
    }

    @RequestMapping("/Community")
    public String community(HttpServletRequest request) {
        Map<String, Object> info = initRequest(request);
        if (info.get("user")==null) {
            return "login";
        }
        List<Post> topThemes = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/community/post/findTopThemes", HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {}).getBody();
        List<Post> noneTopThemes = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/community/post/findNoneTopThemes", HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {}).getBody();
        request.setAttribute("topThemes", topThemes);
        request.setAttribute("noneTopThemes", noneTopThemes);
        return "community";
    }

    @RequestMapping("/About")
    public String about(HttpServletRequest request) {
        initRequest(request);
        return "about";
    }

    @GetMapping("/Course")
    public String courseProfile(HttpServletRequest request, @RequestParam("cid") Integer cid) {
        Map<String, Object> info = initRequest(request);
        User user = (User) info.get("user");
        Course course = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/findCourseByCid?cid="+cid, HttpMethod.GET, null, new ParameterizedTypeReference<Course>() {}).getBody();
        User creator = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/user/findByUid?uid=" + course.getCreator(), HttpMethod.GET, null, new ParameterizedTypeReference<User>() {}).getBody();
        //检测身份
        if (user==null) {
            request.setAttribute("role", "anonymous");
        }
        else if (user.getUid()==creator.getUid()){
            request.setAttribute("role", "creator");
        }
        else {
            Integer progress = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/course/getStudentCourseProgress?uid="+user.getUid()+"&cid="+cid, HttpMethod.GET, null, new ParameterizedTypeReference<Integer>() {}).getBody();
            if(progress!=null) {
                request.setAttribute("role", "student");
            }
            else {
                request.setAttribute("role", "guest");
            }
        }
        Integer numOfStu = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/getNumberOfStudentsByCid?cid="+cid, HttpMethod.GET, null, new ParameterizedTypeReference<Integer>() {}).getBody();
        Integer numOfLes = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/getNumberOfLessionsByCid?cid="+cid, HttpMethod.GET, null, new ParameterizedTypeReference<Integer>() {}).getBody();
        Map<String, Object> courseInfo = new HashMap<String, Object>();
        courseInfo.put("numberOfStudents", numOfStu);
        courseInfo.put("numberOfLessions", numOfLes);
        request.setAttribute("course", course);
        request.setAttribute("courseInfo", courseInfo);
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

        return "course-profile";
    }

    @GetMapping("/Lession")
    public String lessionPage(HttpServletRequest request, HttpServletResponse response, @RequestParam("lid") Integer lid) {
        //登录检测
        //TODO:应设计为网关拦截
        Map<String, Object> info = initRequest(request);
        User user = (User) info.get("user");
        if (user==null) {
            return "login";
        }
        //获取课程、章节、课目信息
        Lession currentLession = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/course/findLessionByLid?lid=" + lid, HttpMethod.GET, null, new ParameterizedTypeReference<Lession>() {}).getBody();
        Chapter currentChapter = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/course/findChapterByChid?chid=" + currentLession.getChid(), HttpMethod.GET, null, new ParameterizedTypeReference<Chapter>() {}).getBody();
        Integer cid = currentChapter.getCid();
        Course course = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/findCourseByCid?cid="+cid, HttpMethod.GET, null, new ParameterizedTypeReference<Course>() {}).getBody();
        User creator = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/user/findByUid?uid=" + course.getCreator(), HttpMethod.GET, null, new ParameterizedTypeReference<User>() {}).getBody();
        request.setAttribute("creator", creator);
        //检测是否已加入该课程，是则获取进度，否则跳转申请加入页
        Integer progress = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/course/getStudentCourseProgress?uid="+user.getUid()+"&cid="+cid, HttpMethod.GET, null, new ParameterizedTypeReference<Integer>() {}).getBody();
        if(user.getUid()==creator.getUid()) {//是创建者
            request.setAttribute("progress", 99999);
        }
        else if(progress!=null) {//是学员
            request.setAttribute("progress", progress);
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
            restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/saveCourseDailyRecord?uid="+user.getUid()+"&cid="+cid+"&date="+date, HttpMethod.GET, null, new ParameterizedTypeReference<StudentCourseDaily>() {});
        }
        else {//未加入该课程
            try {
                response.sendRedirect("http://" + hostConfig.getIp() + "/Course?cid="+cid);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;//跳转到申请加入
        }
        //加载基本信息并经请求传到网页
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
        Integer lessionNo = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/course/getLessionNumInCourse?lid="+currentLession.getLid()+"&cid="+cid, HttpMethod.GET, null, new ParameterizedTypeReference<Integer>() {}).getBody();
        request.setAttribute("lessionNo", lessionNo);
        List<Annex> annexes = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/course/findLessionAnnexes?lid="+currentLession.getLid(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Annex>>() {}).getBody();
        request.setAttribute("annexes", annexes);
        return "course-lession";
    }

    @GetMapping("/Exam")
    public String examPage(HttpServletRequest request, @RequestParam("eid") Integer eid) {
        //TODO:登录检测，以后需要改为网关拦截
        Map<String, Object> info = initRequest(request);
        if (info.get("user")==null) {
            return "login";
        }
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
        Integer examNo = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/course/getExamNumInCourse?eid="+exam.getEid()+"&cid="+cid, HttpMethod.GET, null, new ParameterizedTypeReference<Integer>() {}).getBody();
        request.setAttribute("examNo", examNo);
        return "course-exam";
    }

    @GetMapping("/JoinCourse")
    public void joinCourse(HttpServletResponse response, @RequestParam("uid") Integer uid, @RequestParam("cid") Integer cid) {
        try{
            StudentCourse sc = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/joinCourse?uid="+uid+"&cid="+cid, HttpMethod.GET, null, new ParameterizedTypeReference<StudentCourse>() {}).getBody();
        } catch (Exception e) {

        }
        try {
            response.sendRedirect("http://"+hostConfig.getIp()+"/Course?cid="+cid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/SubmitAnswer")
    public String submitAnswer(HttpServletRequest request, @RequestParam("uid") Integer uid, @RequestParam("eid") Integer eid) {
        Map<String, Object> info = initRequest(request);
        User user = (User) info.get("user");
        String host = (String) info.get("host");
        if (info.get("user")==null) {
            return "login";
        }
        Exam exam = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/findExamByEid?eid="+eid, HttpMethod.GET, null, new ParameterizedTypeReference<Exam>() {}).getBody();
        request.setAttribute("exam", exam);
        Chapter currentChapter = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/course/findChapterByChid?chid=" + exam.getChid(), HttpMethod.GET, null, new ParameterizedTypeReference<Chapter>() {}).getBody();
        Integer cid = currentChapter.getCid();
        Course course = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/findCourseByCid?cid="+cid, HttpMethod.GET, null, new ParameterizedTypeReference<Course>() {}).getBody();
        Integer examNo = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/course/getExamNumInCourse?eid="+exam.getEid()+"&cid="+cid, HttpMethod.GET, null, new ParameterizedTypeReference<Integer>() {}).getBody();
        request.setAttribute("examNo", examNo);
        request.setAttribute("user", user);
        request.setAttribute("host", host);
        request.setAttribute("course", course);
        Map<String, String[]> answers = request.getParameterMap();
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        params.put("eid", eid);
        params.put("answers", answers);
        String url = "http://" + hostConfig.getIp() + ":8080/course/countScoreByAnswer";
        Integer score = restTemplate.postForObject(url, params, Integer.class);
        request.setAttribute("score", score);
        return "course-exam-result";
    }

    @GetMapping("/Console")
    public String console(HttpServletRequest request, Integer cid) {
        Map<String, Object> info = initRequest(request);
        User user = (User) info.get("user");
        Course course = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/findCourseByCid?cid="+cid, HttpMethod.GET, null, new ParameterizedTypeReference<Course>() {}).getBody();
        if (user==null || user.getUid()!=course.getCreator()) {
            request.setAttribute("msg", "你无权访问此页面。");
            request.setAttribute("link", "http://"+hostConfig.getIp()+"/Course?cid="+cid);
            return "warn";
        }
        List<StudentCourseProgressDTO> studentsProgress = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/getAllStudentsProgressOfCourse?cid="+cid, HttpMethod.GET, null, new ParameterizedTypeReference<List<StudentCourseProgressDTO>>() {}).getBody();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
        Integer studentsOnlineNumLastDay = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/getStudentsOnlineNumOfCourseLastDay?cid="+cid+"&date="+date, HttpMethod.GET, null, new ParameterizedTypeReference<Integer>() {}).getBody();
        List<Integer> onlineTrendLast7Days = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/getStudentsOnlineNumTrendOfCourseInPastXDay?x=7", HttpMethod.GET, null, new ParameterizedTypeReference<List<Integer>>() {}).getBody();
        List<Integer> onlineTrendLast30Days = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/getStudentsOnlineNumTrendOfCourseInPastXDay?x=30", HttpMethod.GET, null, new ParameterizedTypeReference<List<Integer>>() {}).getBody();
        List<LessionVideoActionRecordDTO> lessionsOftenBeingSkip = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/selectTopNLessionsHavingMostActionRecord?cid="+cid+"&action=skip&n=3", HttpMethod.GET, null, new ParameterizedTypeReference<List<LessionVideoActionRecordDTO>>() {}).getBody();
        List<LessionVideoActionRecordDTO> lessionsOftenBeingFallback = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/selectTopNLessionsHavingMostActionRecord?cid="+cid+"&action=fallback&n=3", HttpMethod.GET, null, new ParameterizedTypeReference<List<LessionVideoActionRecordDTO>>() {}).getBody();
        List<LessionVideoActionRecordDTO> lessionsOftenBeingAbandon = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/selectTopNLessionsHavingMostActionRecord?cid="+cid+"&action=abandon&n=3", HttpMethod.GET, null, new ParameterizedTypeReference<List<LessionVideoActionRecordDTO>>() {}).getBody();
        List<LessionVideoActionRecordDTO> lessionsOftenBeingReview = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/selectTopNLessionsHavingMostActionRecord?cid="+cid+"&action=review&n=3", HttpMethod.GET, null, new ParameterizedTypeReference<List<LessionVideoActionRecordDTO>>() {}).getBody();
        request.setAttribute("course", course);
        request.setAttribute("studentsProgress", studentsProgress);
        request.setAttribute("studentsOnlineNumLastDay", studentsOnlineNumLastDay);
        request.setAttribute("onlineTrendLast7Days", onlineTrendLast7Days);
        request.setAttribute("onlineTrendLast30Days", onlineTrendLast30Days);
        request.setAttribute("lessionsOftenBeingSkip", lessionsOftenBeingSkip);
        request.setAttribute("lessionsOftenBeingFallback", lessionsOftenBeingFallback);
        request.setAttribute("lessionsOftenBeingAbandon", lessionsOftenBeingAbandon);
        request.setAttribute("lessionsOftenBeingReview", lessionsOftenBeingReview);
        return "course-console";
    }

    @RequestMapping("/Post")
    public String post(HttpServletRequest request, @RequestParam("pid") Integer pid) {
        Map<String, Object> info = initRequest(request);
        if (info.get("user")==null) {
            return "login";
        }
        Post theme = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/community/post/findByPid?pid="+pid, HttpMethod.GET, null, new ParameterizedTypeReference<Post>() {}).getBody();
        List<PostDTO> floors = restTemplate.exchange("http://" + hostConfig.getIp() + ":8080/community/post/readThemePost?pid="+pid, HttpMethod.GET, null, new ParameterizedTypeReference<List<PostDTO>>() {}).getBody();
        request.setAttribute("floors", floors);
        request.setAttribute("theme", theme);
        return "community-post";
    }

    @PostMapping("/newPost")
    public String post(HttpServletRequest request, HttpServletResponse response, String title, String content, String type, Integer uid) {
        Map<String, Object> info = initRequest(request);
        if (info.get("user")==null) {
            return "login";
        }
        String url = "http://"+hostConfig.getIp()+":8080/community/post/publish";
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
        params.add("title",title);
        params.add("content",content);
        params.add("type",type);
        params.add("uid",uid);
        Post post = restTemplate.postForObject(url, params, Post.class);
        try {
            response.sendRedirect("http://"+hostConfig.getIp()+"/Community");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/UserCenter")
    public String userCenter(HttpServletRequest request) {
        Map<String, Object> info = initRequest(request);
        User user = (User) info.get("user");
        if (user == null) {
            return "login";
        }
        List<Course> createdCourses = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/findUserCreated?uid="+user.getUid(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Course>>(){}).getBody();
        List<Course> joinedCourses = restTemplate.exchange("http://"+hostConfig.getIp()+":8080/course/findUserJoined?uid="+user.getUid(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Course>>(){}).getBody();
        request.setAttribute("createdCourses", createdCourses);
        request.setAttribute("joinedCourses", joinedCourses);
        return "user-center";
    }

    @RequestMapping("/test")
    @ResponseBody
    public Object test(HttpServletRequest request) {
        return request.getParameterMap();
    }

}
