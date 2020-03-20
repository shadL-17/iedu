package cn.shadl.ieducommonbeans.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Data
@Entity
@Table(name="comment")
public class Comment {

    @Id
    private Integer cmid;//评论id

    @Column(name = "target_type")
    private String targetType;//对象类型（course/lession）

    @Column(name = "target_id")
    private Integer targetId;//对象id

    @Column
    private String content;//评论内容

    @Column
    private Integer creator;//发布者

    @Column(name = "reply_to")
    private Integer replyTo;//回复对象（另一评论的id，可为null）

    @Column(name = "create_date")
    private Date createDate;//评论日期
}
