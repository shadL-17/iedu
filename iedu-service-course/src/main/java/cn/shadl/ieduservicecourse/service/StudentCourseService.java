package cn.shadl.ieduservicecourse.service;

import cn.shadl.ieducommonbeans.domain.StudentCourse;
import cn.shadl.ieduservicecourse.repository.StudentCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCourseService {
    @Autowired
    private StudentCourseRepository studentCourseRepository;

    public StudentCourse findByUidAndCid(Integer uid, Integer cid) {
        List<StudentCourse> records = studentCourseRepository.findByUidAndAndCid(uid, cid);
        return (records!=null&&!records.isEmpty()) ? records.get(0) : null;
    }

    public StudentCourse upgradeProgress(Integer uid, Integer cid) {//进度+1
        StudentCourse record = findByUidAndCid(uid, cid);
        record.setProgress(record.getProgress()+1);
        return studentCourseRepository.saveAndFlush(record);
    }

    public StudentCourse addScore(Integer uid, Integer cid, Integer score) {
        StudentCourse record = findByUidAndCid(uid, cid);
        record.setScore(record.getScore()+score);
        return studentCourseRepository.save(record);
    }

}
