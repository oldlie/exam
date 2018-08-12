package com.oldlie.exam.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.oldlie.exam.entity.Exam;
import com.oldlie.exam.entity.Paper;
import com.oldlie.exam.entity.Student;
import com.oldlie.exam.http.reponse.BaseResponse;
import com.oldlie.exam.http.reponse.ListResponse;
import com.oldlie.exam.http.reponse.PageResponse;
import com.oldlie.exam.http.request.IdRequest;
import com.oldlie.exam.http.request.PageRequest;
import com.oldlie.exam.repository.ExamRepository;
import com.oldlie.exam.repository.PaperRepository;
import com.oldlie.exam.repository.StudentRepository;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {

    private ExamRepository examRepository;
    private PaperRepository paperRepository;
    private StudentRepository studentRepository;

    public ManagerService(ExamRepository examRepository, PaperRepository paperRepository,
            StudentRepository studentRepository) {
        this.examRepository = examRepository;
        this.paperRepository = paperRepository;
        this.studentRepository = studentRepository;
    }

    public BaseResponse storePaper(String number, String content) {
        BaseResponse response = new BaseResponse();
        Paper paper = this.paperRepository.findByNumber(number);
        if (paper != null) {
            response.setStatus(1);
            response.setMessage("这个编号的试卷已经录入了。");
            return response;
        }
        paper = new Paper();
        paper.setNumber(number);
        paper.setContent(content);
        this.paperRepository.save(paper);
        return response;
    }

    public BaseResponse storePaper(Long id, String number, String content) {
        BaseResponse response = new BaseResponse();
        Optional<Paper> optional = this.paperRepository.findById(id);
        Paper paper;
        if (optional.isPresent()) {
            paper = optional.get();
        } else {
            paper = new Paper();
        }
        paper.setNumber(number);
        paper.setContent(content);
        this.paperRepository.save(paper);
        return response;
    }

    public BaseResponse deletePaper(IdRequest request) {
        BaseResponse response = new BaseResponse();
        this.paperRepository.deleteById(request.getId());
        return response;
    }

    public PageResponse<Paper> listPaper(PageRequest request) {
        PageResponse<Paper> response = new PageResponse<>();
        Page<Paper> page = this.paperRepository.findAll(request.getPageable());
        response.setTotle(page.getTotalElements());
        response.setList(page.getContent());
        return response;
    }

    public BaseResponse storeExam(Long id, String number, Date start, int minus) {
        BaseResponse response = new BaseResponse();
        Exam entity;
        entity = new Exam();
        if (id != null && id > 0) {
            Optional<Exam> optional = this.examRepository.findById(id);
            if (optional.isPresent()) {
                entity = optional.get();
            }
        }
        entity.setNumber(number);
        entity.setStart(start);
        entity.setMinus(minus);
        this.examRepository.save(entity);
        return response;
    }

    public BaseResponse deleteExam(Long id) {
        BaseResponse response = new BaseResponse();
        Optional<Exam> optional = this.examRepository.findById(id);
        if (optional.isPresent()) {
            Exam exam = optional.get();
            List<Student> students = this.studentRepository.findAllByNumber(exam.getNumber());
            if (students.size() > 0) {
                response.setStatus(1);
                response.setMessage("本次考试已经指定了考生，请先将考生信息删除，再删除本次考试。");
                return response;
            }
            this.examRepository.delete(exam);
        }
        return response;
    }

    public PageResponse<Exam> listExam(PageRequest request) {
        PageResponse<Exam> response = new PageResponse<>();
        this.examRepository.findAll(request.getPageable());
        return response;
    }

    public BaseResponse addStudent(Long id, String name, String number, int flag) {
        BaseResponse response = new BaseResponse();
        Student entity = new Student();
        if (id != null && id > 0) {
            Optional<Student> optional = this.studentRepository.findById(id);
            if (optional.isPresent()) {
                entity = optional.get();
            }
        }
        entity.setName(name);
        entity.setNumber(number);
        entity.setFlag(flag);
        return response;
    }

    public BaseResponse deleteStudent(Long id) {
        BaseResponse response = new BaseResponse();
        this.studentRepository.deleteById(id);
        return response;
    }

    public ListResponse<Student> listStudentByExamNumber(String number) {
        ListResponse<Student> response = new ListResponse<>();
        List<Student> list = this.studentRepository.findAllByNumber(number);
        response.setList(list);
        return response;
    }
}