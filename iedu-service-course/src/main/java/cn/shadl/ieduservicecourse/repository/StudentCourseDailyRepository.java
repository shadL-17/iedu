package cn.shadl.ieduservicecourse.repository;

import cn.shadl.ieducommonbeans.domain.StudentCourseDaily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface StudentCourseDailyRepository extends JpaRepository<StudentCourseDaily, Long> {
    List<StudentCourseDaily> findByUidAndCidAndDate(Integer uid, Integer cid, Date date);

    @Query(nativeQuery = true, value = "select count(uid) from student_course_daily where cid=:#{#cid} and date=:#{#date}")
    Integer getStudentsOnlineNumOfCourseLastDay(Integer cid, Date date);

    @Query(nativeQuery = true, value = "select COUNT(scdid) from student_course_daily where date(NOW())-date=:#{#x}")
    Integer getStudentsOnlineNumOfCourseXDaysAgo(Integer x);

    //获取系统时间x天后的日期，可为负数
    @Query(nativeQuery = true, value = "select DATE_ADD(DATE(NOW()),INTERVAL :#{#x} DAY)")
    Date getDateByPresent(Integer x);
}
