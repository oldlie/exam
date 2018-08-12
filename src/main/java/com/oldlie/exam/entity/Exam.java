package com.oldlie.exam.entity;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "t_exam")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Exam implements Serializable {

    private final static long serialVersionUID = 1534046523;

    @Id
    @GeneratedValue
    private long id;
    private String number;
    private Date start;
    private int minus;
}