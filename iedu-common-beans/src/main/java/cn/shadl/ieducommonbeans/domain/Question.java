package cn.shadl.ieducommonbeans.domain;

import lombok.Data;

@Data
public class Question {
    private Integer qid;//
    private String question;//
    private String answer;//
    private String option;//
    private Integer value;//
    private Integer eid;//
}
