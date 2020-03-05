package cn.shadl.ieducommonbeans.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "chapter")
public class Chapter implements Serializable, Cloneable {

    @Id
    private Integer chid;//

    @Column
    private String title;//

    @Column
    private String description;//

    @Column
    private Integer cid;//
}
