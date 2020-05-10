package cn.shadl.ieduservicecourse.repository;

import cn.shadl.ieducommonbeans.domain.StudentCourseVideoAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface StudentCourseVideoActionRepository extends JpaRepository<StudentCourseVideoAction, Long> {

    //查询指定课程中指定类型操作记录最多的N个课目
    @Query(nativeQuery = true, value = "SELECT * FROM (SELECT DISTINCT(lid) as lid, ROUND(SUM(lid)/lid) as num FROM student_course_video_action WHERE ACTION=:#{#action} AND lid IN(SELECT lid FROM lession WHERE chid IN (SELECT chid FROM chapter WHERE cid=:#{#cid})) group BY lid) AS lession_num ORDER BY num DESC LIMIT :#{#n}")
    List<Map<String, Object>> selectTopNLessionsHavingMostActionRecord(Integer cid, String action, Integer n);
}
