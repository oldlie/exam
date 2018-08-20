package com.oldlie.exam.controller;

import com.oldlie.exam.entity.Exam;
import com.oldlie.exam.entity.Paper;
import com.oldlie.exam.entity.Student;
import com.oldlie.exam.http.reponse.BaseResponse;
import com.oldlie.exam.http.reponse.ExamResponse;
import com.oldlie.exam.http.reponse.SimpleResponse;
import com.oldlie.exam.http.request.IdRequest;
import com.oldlie.exam.http.request.VerifyStudentRequest;
import com.oldlie.exam.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/exam")
    public SimpleResponse<Exam> exam() {
        return this.studentService.examInfo();
    }

    @PostMapping("/verify")
    public SimpleResponse<Student> verify(@RequestBody VerifyStudentRequest request) {
        return this.studentService.verifyStudent(request);
    }

    @GetMapping("/paper")
    public SimpleResponse<Paper> paper(@RequestParam String examNumber, @RequestParam String name, @RequestParam String number) {
        VerifyStudentRequest request = new VerifyStudentRequest();
        request.setExamNumber(examNumber);
        request.setName(name);
        request.setNumber(number);
        return this.studentService.paper(request);
    }

    @PostMapping("/finish")
    public BaseResponse finish(@RequestBody IdRequest request) {
        return this.studentService.finish(request);
    }
}