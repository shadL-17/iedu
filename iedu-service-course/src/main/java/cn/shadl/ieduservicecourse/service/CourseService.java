package cn.shadl.ieduservicecourse.service;

import cn.shadl.ieducommonbeans.domain.Course;
import cn.shadl.ieduservicecourse.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

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
}
