package cn.shadl.ieduservicecourse.repository;

import cn.shadl.ieducommonbeans.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query(nativeQuery = true, value = "select * from course where 1=1 limit :#{#x}")
    List<Course> findTopX(@Param("x") int x);
}
