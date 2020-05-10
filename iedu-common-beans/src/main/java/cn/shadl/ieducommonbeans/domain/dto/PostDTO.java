package cn.shadl.ieducommonbeans.domain.dto;

import cn.shadl.ieducommonbeans.domain.User;
import lombok.Data;

import java.sql.Date;

@Data
public class PostDTO {
    private Integer pid;//帖子ID

    private String title;//帖子标题

    private String content;//帖子正文

    private Date publishDate;//发表日期

    private User creator;//发布者

    private String type;//帖子类型（公告：public，求助：help，讨论：discussion，交易：trade，灌水：chat）

    private String status;//帖子状态（正常：normal，删除：deleted，锁定：locked，置顶：top，精品：recommended，置顶精品：top-recommended）

    private Integer parent;//所在主题

    private Integer floor;//楼序

}
