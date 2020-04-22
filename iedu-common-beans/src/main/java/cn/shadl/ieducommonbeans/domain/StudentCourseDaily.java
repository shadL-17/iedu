package cn.shadl.ieducommonbeans.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "student_course_daily")
@DynamicInsert
@DynamicUpdate
public class StudentCourseDaily {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scdid;

    @Column
    private Integer uid;

    @Column
    private Integer cid;

    @Column
    private Date date;
}
