package cn.shadl.ieduservicecourse.service;

import cn.shadl.ieducommonbeans.domain.VideoAction;
import cn.shadl.ieduservicecourse.repository.VideoActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class VideoActionService {

    @Autowired
    private VideoActionRepository videoActionRepository;

    @Autowired
    private LessionService lessionService;

    @Autowired
    private StudentCourseService studentCourseService;

    public VideoAction save(VideoAction videoAction) {
        return videoActionRepository.save(videoAction);
    }

    public VideoAction save(Integer uid, Integer lid, String action, String timeBefore, String timeAfter, String actionTime) {
        try{
            Integer cid = lessionService.getCidBelong(lid);
            String role = studentCourseService.checkRole(uid, cid);
            if (!"student".equals(role)) {
                return null;//只记录学生的操作记录，游客和教师/创建者的操作不记录
            }
            VideoAction videoAction = new VideoAction();
            videoAction.setUid(uid);
            videoAction.setLid(lid);
            videoAction.setAction(action);
            videoAction.setTimeBefore(formatTime(timeBefore));
            videoAction.setTimeAfter(formatTime(timeAfter));
            videoAction.setActionTime(formatDateTime(actionTime));
            return save(videoAction);
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
}
