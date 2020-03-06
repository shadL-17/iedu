package cn.shadl.ieducommonbeans.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "exam")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eid;//

    @Column
    private String title;//

    @Column
    private Integer chid;//
}
