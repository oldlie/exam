package com.oldlie.exam.http.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRequest {
    private long id;
    private String name;
    private String number;
    private String examNumber;
    private int flag;
    private long paperId;
    private int isFinished;
}