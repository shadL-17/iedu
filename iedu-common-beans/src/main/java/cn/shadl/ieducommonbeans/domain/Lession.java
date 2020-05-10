package cn.shadl.ieducommonbeans.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "lession")
public class Lession implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lid;//

    @Column
    private String title;//

    @Column
    private String description;//

    @Column
    private String videoUrl;//

    @Column
    private Integer chid;//
}
