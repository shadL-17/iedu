package cn.shadl.ieducommonbeans.domain.dto;

import cn.shadl.ieducommonbeans.domain.Course;
import cn.shadl.ieducommonbeans.domain.User;
import lombok.Data;

@Data
public class StudentCourseProgressDTO {
    private User student;
    private Course course;
    private Integer progress;
    private String percent;
    private Integer score;
}
