package com.oldlie.exam.http.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyStudentRequest {
    private String examNumber;
    private String name;
    private String number;
}