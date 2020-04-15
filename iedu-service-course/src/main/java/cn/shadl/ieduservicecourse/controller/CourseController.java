package cn.shadl.ieduservicecourse.controller;

import cn.shadl.ieducommonbeans.domain.*;
import cn.shadl.ieducommonbeans.domain.dto.CommentFloorDTO;
import cn.shadl.ieducommonbeans.domain.dto.ExamQuestionDTO;
import cn.shadl.ieduservicecourse.config.HostConfig;
import cn.shadl.ieduservicecourse.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class CourseController {

    @Autowired
    private HostConfig hostConfig;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private LessionService lessionService;

    @Autowired
    private ExamService examService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private StudentCourseService studentCourseService;

    @GetMapping("/findAllCourses")
    public List<Course> findAllCourses() {
        return courseService.findAll();
    }

    @GetMapping("/findTopXCourses")
    public List<Course> findTopXCourses(@RequestParam("x") int x) {
        return courseService.findTopX(x);
    }

    @GetMapping("/findCourseByCid")
    public Course findCourseByCid(@RequestParam("cid") Integer cid) {
        return courseService.findByCid(cid);
    }

    @GetMapping("/getNumberOfStudentsByCid")
    public Integer getNumberOfStudentsByCid(@RequestParam("cid") Integer cid) {
        return courseService.getNumberOfStudents(cid);
    }

    @GetMapping("/getStudentCourseProgress")
    public Integer getStudentCourseProgress(Integer uid, Integer cid) {
        return courseService.getStudentCourseProgress(uid, cid);
    }

    @GetMapping("/getNumberOfLessionsByCid")
    public Integer getNumberOfLessionsByCid(@RequestParam("cid") Integer cid) {
        return courseService.getNumberOfLessions(cid);
    }

    @GetMapping("/joinCourse")
    public StudentCourse joinCourse(@RequestParam("uid") Integer uid, @RequestParam("cid") Integer cid, HttpServletResponse response) {
        StudentCourse sc = courseService.joinCourse(uid, cid);
        try {
            response.sendRedirect("http://"+hostConfig.getIp()+"/Course?cid="+cid);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sc;
    }

    @GetMapping("/findChaptersByCid")
    public List<Chapter> findChaptersByCid(@RequestParam("cid") Integer cid) {
        return chapterService.findByCid(cid);
    }

    @GetMapping("/findChapterByChid")
    public Chapter findChapterByChid(@RequestParam("chid") Integer chid) {
        return chapterService.findByChid(chid);
    }

    @GetMapping("/findLessionByLid")
    public Lession findLessionByLid(@RequestParam("lid") Integer lid) {
        return lessionService.findByLid(lid);
    }

    @GetMapping("/findLessionsByChid")
    public List<Lession> findLessionsByChid(@RequestParam("chid") Integer chid) {
        return lessionService.findByChid(chid);
    }

    @GetMapping("/findExamByEid")
    public Exam findExamByEid(@RequestParam("eid") Integer eid) {
        return examService.findByEid(eid);
    }

    @GetMapping("/findExamsByChid")
    public List<Exam> findExamsByChid(@RequestParam("chid") Integer chid) {
        return examService.findByChid(chid);
    }

    @GetMapping("/findCommentsByTargetTypeAndTargetId")
    public List<CommentFloorDTO> findCommentsByTargetTypeAndTargetId(String targetType, Integer targetId) {
            return commentService.getCommentFloor(commentService.findByTargetTypeAndTargetId(targetType, targetId));
    }

    @GetMapping("/findQuestionByEid")
    public List<ExamQuestionDTO> findQuestionByEid(Integer eid) {
        return questionService.findByEid(eid);
    }

    @GetMapping("/getLessionNumInCourse")
    public Integer getLessionNumInCourse(Integer lid, Integer cid) {
        return lessionService.getLessionNumInCourse(lid, cid);
    }

    @GetMapping("/getExamNumInCourse")
    public Integer getExamNumInCourse(Integer eid, Integer cid) {
        return examService.getExamNumInCourse(eid, cid);
    }

    @GetMapping("/upgradeProgress")
    public StudentCourse upgradeProgress(Integer uid, Integer cid) {
        return studentCourseService.upgradeProgress(uid, cid);
    }

    @PostMapping("/countScoreByAnswer")
    public Integer countScoreByAnswer(@RequestBody Map<String, Object> params) {
        Integer uid = Integer.valueOf(params.get("uid").toString());
        Integer eid = Integer.valueOf(params.get("eid").toString());
        Map<String, String[]> answers = (Map<String, String[]>) params.get("answers");
        return examService.countScoreByAnswer(uid, eid, answers);
    }
}
