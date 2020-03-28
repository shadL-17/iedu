package cn.shadl.ieduservicecourse.controller;

import cn.shadl.ieducommonbeans.domain.*;
import cn.shadl.ieducommonbeans.domain.dto.CommentFloorDTO;
import cn.shadl.ieducommonbeans.domain.dto.ExamQuestionDTO;
import cn.shadl.ieduservicecourse.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class CourseController {

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

}
