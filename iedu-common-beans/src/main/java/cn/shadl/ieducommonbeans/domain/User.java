package cn.shadl.ieducommonbeans.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    private Integer uid;//ID

    @Column
    private String username;//用户名

    @Column
    private String password;//密码

    @Column
    private String role;//用户类型/角色

    @Column
    private String gender;//性别

    @Column
    private Date birthday;//生日

    @Column
    private String contact;//联系方式
}
