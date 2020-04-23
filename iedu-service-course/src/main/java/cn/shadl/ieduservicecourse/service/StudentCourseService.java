package cn.shadl.ieduservicecourse.service;

import cn.shadl.ieducommonbeans.domain.Course;
import cn.shadl.ieducommonbeans.domain.StudentCourse;
import cn.shadl.ieducommonbeans.domain.User;
import cn.shadl.ieducommonbeans.domain.dto.StudentCourseProgressDTO;
import cn.shadl.ieduservicecourse.repository.StudentCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentCourseService {
    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    public List<StudentCourse> findByCid(Integer cid) {
        return studentCourseRepository.findByCid(cid);
    }

    public StudentCourse findByUidAndCid(Integer uid, Integer cid) {
        List<StudentCourse> records = studentCourseRepository.findByUidAndCid(uid, cid);
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

    public List<StudentCourseProgressDTO> getAllStudentsProgressOfCourse(Integer cid) {
        List<StudentCourse> records = findByCid(cid);
        if (records==null || records.isEmpty()) {
            return null;
        }
        else {
            List<StudentCourseProgressDTO> dtos = new ArrayList<>();
            for (StudentCourse record : records) {
                StudentCourseProgressDTO dto = new StudentCourseProgressDTO();
                dto.setStudent(userService.findByUid(record.getUid()));
                dto.setCourse(courseService.findByCid(record.getCid()));
                dto.setProgress(record.getProgress());
                dto.setScore(record.getScore());
                Integer total = courseService.getNumberOfCourseStages(cid);
                Double percent100x = ((Double.valueOf(dto.getProgress().toString()))/total)*100;
                dto.setPercent(percent100x.intValue()+"%");
                dtos.add(dto);
            }
            return dtos;
        }
    }

    public String checkRole(Integer uid, Integer cid) {
        Course course = courseService.findByCid(cid);
        User user = userService.findByUid(uid);
        if (course.getCreator() == user.getUid()) {
            return "teacher";
        }
        else if (studentCourseRepository.findByUidAndCid(uid, cid) != null) {
            return "student";
        }
        else {
            return "guest";
        }
    }

}
