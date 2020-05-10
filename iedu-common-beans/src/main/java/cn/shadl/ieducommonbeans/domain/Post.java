package cn.shadl.ieducommonbeans.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "post")
@DynamicInsert
@DynamicUpdate
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pid;//帖子ID

    @Column
    private String title;//帖子标题

    @Column
    private String content;//帖子正文

    @Column
    private Date publishDate;//发表日期

    @Column
    private Integer creator;//发布者

    @Column
    private String type;//帖子类型（公告：public，求助：help，讨论：discussion，交易：trade，灌水：chat）

    @Column
    private String status;//帖子状态（正常：normal，删除：deleted，锁定：locked，置顶：top，精品：recommended，置顶精品：top-recommended）

    @Column
    private Integer parent;//所在主题

    @Column
    private Integer floor;//楼序
}
