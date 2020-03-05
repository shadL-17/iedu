package cn.shadl.ieducommonbeans.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "exam")
public class Exam {

    @Id
    private Integer eid;//

    @Column
    private String title;//

    @Column
    private Integer chid;//
}
