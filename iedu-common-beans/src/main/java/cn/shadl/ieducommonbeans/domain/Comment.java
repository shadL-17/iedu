package cn.shadl.ieducommonbeans.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name="comment")
@DynamicInsert
@DynamicUpdate
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cmid;//评论id

    @Column
    private String targetType;//对象类型（course/lession）

    @Column
    private Integer targetId;//对象id

    @Column
    private String content;//评论内容

    @Column
    private Integer creator;//发布者

    @Column
    private Integer replyTo;//回复对象（另一评论的id，可为null）

    @Column
    private Date createDate;//评论日期
}
