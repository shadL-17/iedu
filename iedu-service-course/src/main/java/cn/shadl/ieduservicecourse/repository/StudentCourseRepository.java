package cn.shadl.ieduservicecourse.repository;

import cn.shadl.ieducommonbeans.domain.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {

    @Query(nativeQuery = true, value = "select count(*) from student_course where cid=:#{#cid}")
    Integer getNumberOfStudents(Integer cid);

    @Query(nativeQuery = true, value = "select progress from student_course where uid=:#{#uid} and cid=:#{#cid}")
    Integer getStudentCourseProgress(Integer uid, Integer cid);

    List<StudentCourse> findByUidAndAndCid(Integer uid, Integer cid);

}
