package cn.shadl.ieduservicecourse.service;

import cn.shadl.ieducommonbeans.domain.Chapter;
import cn.shadl.ieducommonbeans.domain.Course;
import cn.shadl.ieduservicecourse.repository.ChapterRepository;
import cn.shadl.ieduservicecourse.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ChapterRepository chapterRepository;

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
        return courseRepository.getNumberOfStudents(cid);
    }

    public Integer getNumberOfLessions(Integer cid) {
        int num = 0;
        List<Chapter> chapters = chapterRepository.findByCid(cid);
        for(Chapter chapter : chapters) {
            num += chapterRepository.getNumberOfLessions(chapter.getChid());
        }
        return num;
    }
}
