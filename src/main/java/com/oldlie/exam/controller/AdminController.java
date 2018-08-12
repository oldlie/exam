package com.oldlie.exam.controller;

import com.oldlie.exam.entity.Exam;
import com.oldlie.exam.entity.Paper;
import com.oldlie.exam.entity.Student;
import com.oldlie.exam.http.reponse.BaseResponse;
import com.oldlie.exam.http.reponse.ListResponse;
import com.oldlie.exam.http.reponse.PageResponse;
import com.oldlie.exam.http.request.ExamRequest;
import com.oldlie.exam.http.request.IdRequest;
import com.oldlie.exam.http.request.PageRequest;
import com.oldlie.exam.http.request.PaperRequest;
import com.oldlie.exam.http.request.StudentRequest;
import com.oldlie.exam.service.ManagerService;

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
    public void setManagerService(ManagerService managerService) {
        this.managerService = managerService;
    }

    @PostMapping("/paper")
    public BaseResponse storePaper(@RequestBody PaperRequest request) {
        return this.managerService.storePaper(request.getId(), request.getNumber(), request.getContent());
    }

    @DeleteMapping("/paper")
    public BaseResponse deletePaper(@RequestBody IdRequest request) {
        return this.managerService.deletePaper(request);
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

    @PostMapping("/exam")
    public BaseResponse storeExam(@RequestBody ExamRequest request) {
        return this.managerService.storeExam(request.getId(), request.getNumber(), request.getStart(),
                request.getMinus());
    }

    @DeleteMapping("/exam")
    public BaseResponse deleteExam(@RequestBody IdRequest request) {
        return this.managerService.deleteExam(request.getId());
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
    public BaseResponse storeStudent(@RequestBody StudentRequest request) {
        return this.managerService.addStudent(request.getId(), request.getName(), request.getNumber(), request.getFlag());
    }

    @DeleteMapping("/student")
    public BaseResponse deleteStudent(@RequestBody IdRequest request) {
        return this.managerService.deleteStudent(request.getId());
    }

    @GetMapping("/student/name")
    public ListResponse<Student> listStudentByNumber(@RequestParam String number) {
        return this.managerService.listStudentByExamNumber(number);
    }
}