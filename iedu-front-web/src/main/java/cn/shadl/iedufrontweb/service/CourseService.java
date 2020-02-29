package cn.shadl.iedufrontweb.service;

import cn.shadl.ieducommonbeans.domain.Course;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@FeignClient("iedu-service-course")
public interface CourseService {
    @GetMapping("/course/find-topX")
    List<Course> findTopX(@RequestParam("x") int x);
}
