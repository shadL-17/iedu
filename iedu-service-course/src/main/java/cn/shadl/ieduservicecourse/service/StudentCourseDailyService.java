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

    public StudentCourseDaily saveCourseDailyRecord(Integer uid, Integer cid, Date date) {
        if (findByUidAndCidAndDate(uid, cid, date) == null) {//无记录
            StudentCourseDaily studentCourseDaily = new StudentCourseDaily();
            studentCourseDaily.setUid(uid);
            studentCourseDaily.setCid(cid);
            studentCourseDaily.setDate(date);
            return studentCourseDailyRepository.save(studentCourseDaily);
        }
        else {
            return null;
        }
    }

    public Integer getStudentsOnlineNumOfCourseLastDay(Integer cid, Date date) {
        return studentCourseDailyRepository.getStudentsOnlineNumOfCourseLastDay(cid, date);
    }

    public List<Integer> getStudentsOnlineNumTrendOfCourseInPastXDay(Integer x) {
        if (x<1) {
            return null;
        }
        List<Integer> trend = new ArrayList<>();
        for (int i=x;i>=1;i--) {
            trend.add(studentCourseDailyRepository.getStudentsOnlineNumOfCourseXDaysAgo(i));
        }
        return trend;
    }

    public List<String> getDatesRecentlyByPresent(Integer x) {//获取1-x天前的每个日期
        if (x<1) return null;
        List<String> strs = new ArrayList<>();
        for (int i=x;i>=1;i--) {
            strs.add(studentCourseDailyRepository.getDateByPresent(i).toString());
        }
        return strs;
    }
}
