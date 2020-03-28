package cn.shadl.ieduservicecourse.repository;

import cn.shadl.ieducommonbeans.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByCid(Integer cid);

    @Query(nativeQuery = true, value = "select * from course where 1=1 limit :#{#x}")
    List<Course> findTopX(@Param("x") int x);

    @Query(nativeQuery = true, value = "select count(*) from student_course where cid=:#{#cid}")
    Integer getNumberOfStudents(Integer cid);

    @Query(nativeQuery = true, value = "select progress from student_course where uid=:#{#uid} and cid=:#{#cid}")
    Integer getStudentCourseProgress(Integer uid, Integer cid);
}
