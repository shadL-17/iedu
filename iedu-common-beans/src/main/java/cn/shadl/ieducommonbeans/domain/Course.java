package cn.shadl.ieducommonbeans.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "course")
public class Course {

    @Id
    private Integer cid;//ID

    @Column
    private String name;//课程名称

    @Column
    private String description;//简介

    @Column
    private String image;//封面图地址

    @Column
    private String type;//分类(标签，分号分隔)

    @Column
    private String status;//状态

    @Column
    private Integer creator;//创建者
}
