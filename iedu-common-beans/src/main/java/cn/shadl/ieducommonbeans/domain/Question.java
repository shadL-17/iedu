package cn.shadl.ieducommonbeans.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "question")
public class Question {

    @Id
    private Integer qid;//

    @Column
    private String question;//

    @Column
    private String answer;//

    @Column
    private String option;//

    @Column
    private Integer value;//

    @Column
    private Integer eid;//
}
