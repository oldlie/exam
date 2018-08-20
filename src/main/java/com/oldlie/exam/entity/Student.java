package com.oldlie.exam.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "t_student")
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class Student implements Serializable {

    private final static long serialVersionUID = 1534046967;

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String number;
    private String examNumber;
    @Column(columnDefinition = "int default 0 comment '0:fixed;1:random'")
    private int flag;
    @Column(columnDefinition = "bigint default 0")
    private long paperId;
    private String paperNumber;
    @Column(columnDefinition = "int default 0 comment '0:没有交卷；1:已经交卷了'")
    private int isFinished;
}