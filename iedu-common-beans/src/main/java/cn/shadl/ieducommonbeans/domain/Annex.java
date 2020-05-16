package cn.shadl.ieducommonbeans.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "annex")
public class Annex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer aid;//

    @Column
    private String filename;//

    @Column
    private String url;//

    @Column
    private Integer lid;//
}
