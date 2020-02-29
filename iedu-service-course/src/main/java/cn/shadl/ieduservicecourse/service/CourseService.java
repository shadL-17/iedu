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

    public List<Course> findTopX(int x) {
        return courseRepository.findTopX(x);
    }
}
