package cn.shadl.ieduservicecourse.service;

import cn.shadl.ieducommonbeans.domain.StudentCourseDaily;
import cn.shadl.ieduservicecourse.repository.StudentCourseDailyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentCourseDailyService {

    @Autowired
    private StudentCourseDailyRepository studentCourseDailyRepository;

    public StudentCourseDaily findByUidAndCidAndDate(Integer uid, Integer cid, Date date) {
        List<StudentCourseDaily> studentCourseDailies = studentCourseDailyRepository.findByUidAndCidAndDate(uid, cid, date);
        return (studentCourseDailies==null||studentCourseDailies.isEmpty()) ? null : studentCourseDailies.get(0);
    }

    public StudentCourseDaily save(StudentCourseDaily studentCourseDaily) {
        Optional<StudentCourseDaily> check = studentCourseDailyRepository.findOne(Example.of(studentCourseDaily));
        if (!check.isPresent()) {
            return studentCourseDailyRepository.save(studentCourseDaily);
        }
        else {//该学生该课程本日已有记录
            return null;
        }
    }

    public Integer getStudentsOnlineNumOfCourseLastDay(Integer cid, Date date) {
        return studentCourseDailyRepository.getStudentsOnlineNumOfCourseLastDay(cid, date);
    }

    public Integer[] getStudentsOnlineNumTrendOfCourseInPastXDay(Integer x) {
        if (x<1) {
            return null;
        }
        List<Integer> trend = new ArrayList<>();
        for (int i=1;i<=x;i++) {
            trend.add(studentCourseDailyRepository.getStudentsOnlineNumOfCourseXDaysAgo(i));
        }
        return trend.toArray(new Integer[0]);
    }
}
