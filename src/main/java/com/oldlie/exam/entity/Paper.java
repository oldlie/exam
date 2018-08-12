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
@Table(name = "t_paper")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class Paper implements Serializable {
    private final static long serialVersionUID = 153406729296L;

    @Id
    @GeneratedValue
    private long id;
    private String number;
    @Column(columnDefinition = "text")
    private String content;
}