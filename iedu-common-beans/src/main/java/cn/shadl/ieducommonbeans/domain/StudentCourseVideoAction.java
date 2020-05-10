package cn.shadl.ieducommonbeans.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "student_course_video_action")
@DynamicInsert
@DynamicUpdate
public class StudentCourseVideoAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vaid;

    @Column
    private Integer uid;

    @Column
    private Integer lid;

    @Column
    private String action;

    @Column
    private String timeBefore;

    @Column
    private String timeAfter;

    @Column
    private Timestamp actionTime;
}
