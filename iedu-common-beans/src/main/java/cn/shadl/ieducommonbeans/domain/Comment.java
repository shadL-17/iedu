package cn.shadl.ieducommonbeans.domain;

import lombok.Data;

import java.sql.Date;

@Data
public class Comment {
    private Integer cmid;//
    private String target_type;//
    private Integer target_id;//
    private String content;//
    private Integer creator;//
    private Integer reply_to;//
    private Date create_date;//
}
