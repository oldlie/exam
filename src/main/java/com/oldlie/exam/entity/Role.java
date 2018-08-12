package com.oldlie.exam.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "t_role")
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class Role implements Serializable {
    private static final long serialVersionUID = 3126635799736922270L;

    @Id
    @GeneratedValue
    private long id;
    private String role;
}
