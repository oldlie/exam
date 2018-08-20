package com.oldlie.exam.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.oldlie.exam.entity.Exam;
import com.oldlie.exam.entity.Paper;
import com.oldlie.exam.entity.Student;
import com.oldlie.exam.http.reponse.BaseResponse;
import com.oldlie.exam.http.reponse.ExamResponse;
import com.oldlie.exam.http.reponse.IdResponse;
import com.oldlie.exam.http.reponse.SimpleResponse;
import com.oldlie.exam.http.request.IdRequest;
import com.oldlie.exam.http.request.VerifyStudentRequest;
import com.oldlie.exam.repository.ExamRepository;
import com.oldlie.exam.repository.PaperRepository;
import com.oldlie.exam.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private ExamRepository examRepository;

    @Autowired
    public void setExamRepository(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    private PaperRepository paperRepository;
    private StudentRepository studentRepository;

    public StudentService(PaperRepository paperRepository, StudentRepository studentRepository) {
        this.paperRepository = paperRepository;
        this.studentRepository = studentRepository;
    }

    public SimpleResponse<Exam> examInfo() {
        SimpleResponse<Exam> response = new SimpleResponse<>();
        try {
            List<Exam> list = this.examRepository.findAllByStatus(1);
            if (list == null || list.size() <= 0) {
                response.setStatus(1);
                response.setMessage("没有开考信息，请稍等");
            } else {
                response.setValue(list.get(0));
            }
        } catch (Exception e) {
            response.setStatus(1);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public SimpleResponse<Student> verifyStudent(VerifyStudentRequest request) {
        SimpleResponse<Student> response = new SimpleResponse<>();
        Student student = this.studentRepository.findOneByNameAndNumberAndExamNumber(request.getName(),
                request.getNumber(), request.getExamNumber());
        if (student == null) {
            response.setStatus(1);
            response.setMessage("没有参与本次考试");
        } else {
            response.setValue(student);
        }
        return response;
    }

    public SimpleResponse<Paper> paper(VerifyStudentRequest request) {
        SimpleResponse<Paper> response = new SimpleResponse<>();
        Student student = this.studentRepository.findOneByNameAndNumberAndExamNumber(request.getName(),
                request.getNumber(), request.getExamNumber());
        if (student == null) {
            response.setStatus(1);
            response.setMessage("没有参与本次考试");
        } else {
            Paper paper = null;
            if (student.getFlag() == 0) {
                // 随机
                List<Paper> papers = this.paperRepository.findAllByStatus(1);
                Random random = new Random(new Date().getTime());
                int index = random.nextInt(papers.size());
                for (int i = 0; i < papers.size(); i++) {
                    if (i == index) {
                        paper = papers.get(index);
                        break;
                    }
                }
                if (paper == null) {
                    paper = papers.get(0);
                }
            } else {
                // 固定
                Optional<Paper> optional = this.paperRepository.findById(student.getPaperId());
                if (optional.isPresent()) {
                    paper = optional.get();
                } else {
                    response.setStatus(1);
                    response.setMessage("没有考试信息");
                    return response;
                }
            }
            student.setPaperNumber(paper.getNumber());
            this.studentRepository.save(student);
            response.setValue(paper);
        }
        return response;
    }

    public BaseResponse finish(IdRequest request) {
        BaseResponse response = new BaseResponse();
        Optional<Student> optional = this.studentRepository.findById(request.getId());
        if (optional.isPresent()) {
            Student student = optional.get();
            student.setIsFinished(1);
            this.studentRepository.save(student);
        } else {
            response.setStatus(1);
            response.setMessage("考生信息不存在");
        }
        return response;
    }
}