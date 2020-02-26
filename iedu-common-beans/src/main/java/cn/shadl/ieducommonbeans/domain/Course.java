package cn.shadl.ieducommonbeans.domain;

import lombok.Data;

@Data
public class Course {

    private Integer cid;//ID
    private String name;//课程名称
    private String description;//简介
    private String image;//封面图地址
    private String type;//分类(标签，分号分隔)
    private String status;//状态
    private Integer creator;//创建者
}
