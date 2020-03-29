package cn.shadl.ieducommonbeans.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@Entity
@Table(name = "student_course")
@DynamicInsert
@DynamicUpdate
public class StudentCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ucid;

    @Column
    private Integer uid;

    @Column
    private Integer cid;

    @Column
    private Integer progress;

    @Column
    private Integer score;
}
