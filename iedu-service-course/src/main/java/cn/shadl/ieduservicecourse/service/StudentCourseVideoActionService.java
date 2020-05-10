package cn.shadl.ieduservicecourse.service;

import cn.shadl.ieducommonbeans.domain.StudentCourseVideoAction;
import cn.shadl.ieducommonbeans.domain.dto.LessionVideoActionRecordDTO;
import cn.shadl.ieduservicecourse.repository.StudentCourseVideoActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StudentCourseVideoActionService {

    @Autowired
    private StudentCourseVideoActionRepository studentCourseVideoActionRepository;

    @Autowired
    private LessionService lessionService;

    @Autowired
    private StudentCourseService studentCourseService;

    public StudentCourseVideoAction save(StudentCourseVideoAction studentCourseVideoAction) {
        return studentCourseVideoActionRepository.save(studentCourseVideoAction);
    }

    public StudentCourseVideoAction save(Integer uid, Integer lid, String action, String timeBefore, String timeAfter, String actionTime) {
        try{
            Integer cid = lessionService.getCidBelong(lid);
            String role = studentCourseService.checkRole(uid, cid);
            if (!"student".equals(role)) {
                return null;//只记录学生的操作记录，游客和教师/创建者的操作不记录
            }
            StudentCourseVideoAction studentCourseVideoAction = new StudentCourseVideoAction();
            studentCourseVideoAction.setUid(uid);
            studentCourseVideoAction.setLid(lid);
            studentCourseVideoAction.setAction(action);
            studentCourseVideoAction.setTimeBefore(formatTime(timeBefore));
            studentCourseVideoAction.setTimeAfter(formatTime(timeAfter));
            studentCourseVideoAction.setActionTime(formatDateTime(actionTime));
            return save(studentCourseVideoAction);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String formatTime(String original) {
        try{
            Integer summarySec = Integer.valueOf(original.split("\\.")[0]);
            Integer hours = summarySec/3600;
            Integer minutes = (summarySec - (hours*3600)) / 60;
            Integer seconds = summarySec % 60;
            String hrs = (hours<10) ? "0"+hours : hours.toString();
            String min = (minutes<10) ? "0"+minutes : minutes.toString();
            String sec = (seconds<10) ? "0"+seconds : seconds.toString();
            return hrs + ":" + min + ":" + sec;
        } catch (Exception e) {
            return "00:00:00";
        }
    }

    public Timestamp formatDateTime(String original) {
        Timestamp datetime = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            Date dt = format.parse(original);
            datetime = new Timestamp(dt.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return datetime;
    }

    public List<LessionVideoActionRecordDTO> selectTopNLessionsHavingMostActionRecord(Integer cid, String action, Integer n) {
        List<Map<String, Object>> data = studentCourseVideoActionRepository.selectTopNLessionsHavingMostActionRecord(cid, action, n);
        if (data==null || data.isEmpty()) {
            return null;
        }
        List<LessionVideoActionRecordDTO> dtos = new ArrayList<>();
        for (Map<String, Object> row : data) {
            LessionVideoActionRecordDTO dto = new LessionVideoActionRecordDTO();
            dto.setLession(lessionService.findByLid(Integer.valueOf(row.get("lid").toString())));
            dto.setNum(Integer.valueOf(row.get("num").toString()));
            dto.setAction(action);
            dtos.add(dto);
        }
        return dtos;
    }
}
