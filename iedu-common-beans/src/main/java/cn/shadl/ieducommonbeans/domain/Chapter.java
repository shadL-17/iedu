package cn.shadl.ieducommonbeans.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "chapter")
public class Chapter implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chid;//

    @Column
    private String title;//

    @Column
    private String description;//

    @Column
    private Integer cid;//
}
