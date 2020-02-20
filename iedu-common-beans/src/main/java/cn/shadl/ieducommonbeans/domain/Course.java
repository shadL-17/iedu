package cn.shadl.ieducommonbeans.domain;

import lombok.Data;

@Data
public class Course {

    private Integer cid;//ID
    private String name;//课程名称
    private String description;//简介
    private String type;//分类
    private String status;//状态
    private Integer creator;//创建者
}
