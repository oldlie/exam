package com.oldlie.exam.http.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExamRequest {
    private long id;
    private String number;
    private Date start;
    private int minus;
}