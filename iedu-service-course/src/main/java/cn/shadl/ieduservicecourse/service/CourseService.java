package cn.shadl.ieduservicecourse.service;

import cn.shadl.ieducommonbeans.domain.Chapter;
import cn.shadl.ieducommonbeans.domain.Course;
import cn.shadl.ieducommonbeans.domain.Exam;
import cn.shadl.ieducommonbeans.domain.StudentCourse;
import cn.shadl.ieduservicecourse.repository.ChapterRepository;
import cn.shadl.ieduservicecourse.repository.CourseRepository;
import cn.shadl.ieduservicecourse.repository.ExamRepository;
import cn.shadl.ieduservicecourse.repository.StudentCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public List<Course> findTopX(int x) {
        return courseRepository.findTopX(x);
    }

    public Course findByCid(Integer cid) {
        List<Course> courses = courseRepository.findByCid(cid);
        return (courses!=null&&!courses.isEmpty()) ? courses.get(0) : null;
    }

    public Integer getNumberOfStudents(Integer cid) {
        return studentCourseRepository.getNumberOfStudents(cid);
    }

    public Integer getNumberOfLessions(Integer cid) {
        int num = 0;
        List<Chapter> chapters = chapterRepository.findByCid(cid);
        for(Chapter chapter : chapters) {
            num += chapterRepository.getNumberOfLessions(chapter.getChid());
        }
        return num;
    }

    public Integer getNumberOfExams(Integer cid) {
        int num = 0;
        List<Chapter> chapters = chapterRepository.findByCid(cid);
        for (Chapter chapter : chapters) {
            List<Exam> exams = examRepository.findByChid(chapter.getChid());
            if (exams == null || exams.isEmpty()) {
                num += 0;
            }
            else {
                num += exams.size();
            }
        }
        return num;
    }

    public Integer getNumberOfCourseStages(Integer cid) {
        return getNumberOfLessions(cid) + getNumberOfExams(cid);
    }

    public Integer getStudentCourseProgress(Integer uid, Integer cid) {
        return studentCourseRepository.getStudentCourseProgress(uid, cid);
    }

    public StudentCourse joinCourse(Integer uid, Integer cid) {
        StudentCourse scData = new StudentCourse();
        scData.setUid(uid);
        scData.setCid(cid);
        scData.setProgress(0);
        scData.setScore(0);
        return studentCourseRepository.save(scData);
    }
}
