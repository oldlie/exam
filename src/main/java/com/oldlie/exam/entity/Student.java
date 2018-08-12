package com.oldlie.exam.entity;

import java.io.Serializable;

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
    private int flag;
}