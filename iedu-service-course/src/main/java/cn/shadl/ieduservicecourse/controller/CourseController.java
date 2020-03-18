package cn.shadl.ieduservicecourse.controller;

import cn.shadl.ieducommonbeans.domain.Chapter;
import cn.shadl.ieducommonbeans.domain.Course;
import cn.shadl.ieducommonbeans.domain.Exam;
import cn.shadl.ieducommonbeans.domain.Lession;
import cn.shadl.ieduservicecourse.service.ChapterService;
import cn.shadl.ieduservicecourse.service.CourseService;
import cn.shadl.ieduservicecourse.service.ExamService;
import cn.shadl.ieduservicecourse.service.LessionService;
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

    @GetMapping("/findExamsByChid")
    public List<Exam> findExamsByChid(@RequestParam("chid") Integer chid) {
        return examService.findByChid(chid);
    }

}
