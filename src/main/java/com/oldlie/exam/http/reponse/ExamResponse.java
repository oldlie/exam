package com.oldlie.exam.http.reponse;

import java.util.List;

import com.oldlie.exam.entity.Exam;
import com.oldlie.exam.entity.Student;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExamResponse extends BaseResponse {
    private Exam exam;
}