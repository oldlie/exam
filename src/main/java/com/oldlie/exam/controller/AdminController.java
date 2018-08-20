package com.oldlie.exam.controller;

import com.oldlie.exam.entity.Exam;
import com.oldlie.exam.entity.Paper;
import com.oldlie.exam.entity.Student;
import com.oldlie.exam.http.reponse.BaseResponse;
import com.oldlie.exam.http.reponse.IdResponse;
import com.oldlie.exam.http.reponse.ListResponse;
import com.oldlie.exam.http.reponse.PageResponse;
import com.oldlie.exam.http.request.ExamRequest;
import com.oldlie.exam.http.request.IdRequest;
import com.oldlie.exam.http.request.PageRequest;
import com.oldlie.exam.http.request.PaperRequest;
import com.oldlie.exam.http.request.PasswordRequest;
import com.oldlie.exam.http.request.StudentRequest;
import com.oldlie.exam.service.ManagerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private ManagerService managerService;

    /**
     * @param managerService the managerService to set
     */
    @Autowired
    public void setManagerService(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/init")
    public BaseResponse init() {
        BaseResponse response = new BaseResponse();
        return response;
    }

    @PostMapping("/paper")
    public BaseResponse storePaper(@RequestBody PaperRequest request) {
        return this.managerService.storePaper(request);
    }

    @DeleteMapping("/paper")
    public BaseResponse deletePaper(@RequestParam long id) {
        return this.managerService.deletePaper(id);
    }

    @GetMapping("/paper")
    public PageResponse<Paper> listPaper(@RequestParam int page, @RequestParam int size, @RequestParam String orderBy,
            @RequestParam int order) {
        PageRequest request = new PageRequest();
        request.setPage(page);
        request.setSize(size);
        request.setOrderBy(orderBy);
        request.setOrder(order);
        return this.managerService.listPaper(request);
    }

    @GetMapping("/exam/paper")
    public ListResponse<Paper> activePaperList() {
        return this.managerService.activePaperList();
    }

    @PostMapping("/exam")
    public IdResponse storeExam(@RequestBody ExamRequest request) {
        return this.managerService.storeExam(request);
    }

    @DeleteMapping("/exam")
    public BaseResponse deleteExam(@RequestParam Long id) {
        return this.managerService.deleteExam(id);
    }

    @GetMapping("/exam")
    public PageResponse<Exam> listExam(@RequestParam int page, @RequestParam int size, @RequestParam String orderBy,
            @RequestParam int order) {
        PageRequest request = new PageRequest();
        request.setPage(page);
        request.setSize(size);
        request.setOrderBy(orderBy);
        request.setOrder(order);
        return this.managerService.listExam(request);
    }

    @PostMapping("/student")
    public IdResponse storeStudent(@RequestBody StudentRequest request) {
        return this.managerService.addStudent(request);
    }

    @DeleteMapping("/student")
    public BaseResponse deleteStudent(@RequestBody IdRequest request) {
        return this.managerService.deleteStudent(request.getId());
    }

    @GetMapping("/student/exam")
    public ListResponse<Student> listStudentByNumber(@RequestParam String number) {
        return this.managerService.listStudentByExamNumber(number);
    }

    @PostMapping("/password")
    public BaseResponse password(@RequestBody PasswordRequest request) {
        return this.managerService.password(request);
    }
}