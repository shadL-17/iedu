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

    @Query(nativeQuery = true, value = "select * from course where creator=:#{#uid}")
    List<Course> findUserCreated(Integer uid);

    @Query(nativeQuery = true, value = "select * from course where cid in (select cid from student_course where uid=:#{#uid})")
    List<Course> findUserJoined(Integer uid);

    List<Course> findByTypeLike(String type);

    List<Course> findByNameLike(String name);

}
