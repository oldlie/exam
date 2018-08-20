package com.oldlie.exam.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.oldlie.exam.entity.Exam;
import com.oldlie.exam.entity.Paper;
import com.oldlie.exam.entity.Role;
import com.oldlie.exam.entity.Student;
import com.oldlie.exam.entity.User;
import com.oldlie.exam.http.reponse.BaseResponse;
import com.oldlie.exam.http.reponse.IdResponse;
import com.oldlie.exam.http.reponse.ListResponse;
import com.oldlie.exam.http.reponse.PageResponse;
import com.oldlie.exam.http.request.ExamRequest;
import com.oldlie.exam.http.request.IdRequest;
import com.oldlie.exam.http.request.PageRequest;
import com.oldlie.exam.http.request.PaperRequest;
import com.oldlie.exam.http.request.PasswordRequest;
import com.oldlie.exam.http.request.SimpleRequest;
import com.oldlie.exam.http.request.StudentRequest;
import com.oldlie.exam.repository.ExamRepository;
import com.oldlie.exam.repository.PaperRepository;
import com.oldlie.exam.repository.RoleRepository;
import com.oldlie.exam.repository.StudentRepository;
import com.oldlie.exam.repository.UserRepository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ExamRepository examRepository;
    private PaperRepository paperRepository;
    private RoleRepository roleRepository;
    private StudentRepository studentRepository;
    private UserRepository userRepository;

    public ManagerService(BCryptPasswordEncoder bCryptPasswordEncoder, ExamRepository examRepository, PaperRepository paperRepository, RoleRepository roleRepository,
            StudentRepository studentRepository, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
                this.examRepository = examRepository;
        this.paperRepository = paperRepository;
        this.roleRepository = roleRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    public BaseResponse init(String version) {
        BaseResponse response = new BaseResponse();
        Role role = this.roleRepository.findOneByRole("ADMIN");
        if (role == null) {
            role = new Role();
            role.setRole("ADMIN");
            this.roleRepository.save(role);
        }

        User admin = this.userRepository.findByUsername("admin");
        if (admin == null) {
            List<Role> roles = new ArrayList<>();
            roles.add(role);
            admin = new User();
            admin.setUsername("admin");
            admin.setPassword(this.bCryptPasswordEncoder.encode("123456"));
            admin.setAuthorities(roles);
            this.userRepository.save(admin);
        }

        response.setStatus(0);
        response.setMessage(version);
        return response;
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

    public BaseResponse storePaper(PaperRequest request) {
        BaseResponse response = new BaseResponse();
        Optional<Paper> optional = this.paperRepository.findById(request.getId());
        Paper paper;
        if (request.getId() > 0 && optional.isPresent()) {
            paper = optional.get();
        } else {
            paper = new Paper();
        }
        paper.setNumber(request.getNumber());
        paper.setContent(request.getContent());
        paper.setStatus(request.getStatus());
        try {
            this.paperRepository.save(paper);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return response;
    }

    public BaseResponse deletePaper(final long id) {
        BaseResponse response = new BaseResponse();
        this.paperRepository.deleteById(id);
        return response;
    }

    public PageResponse<Paper> listPaper(PageRequest request) {
        PageResponse<Paper> response = new PageResponse<>();
        Page<Paper> page = this.paperRepository.findAll(request.getPageable());
        response.setTotle(page.getTotalElements());
        response.setList(page.getContent());
        return response;
    }

    public IdResponse storeExam(ExamRequest request) {
        IdResponse response = new IdResponse();
        Exam entity = null;
        Optional<Exam> optional;
        if (request.getId() > 0) {
            optional = this.examRepository.findById(request.getId());
            if (optional.isPresent()) {
                entity = optional.get();
            }
        } else {
            entity = this.examRepository.findByNumber(request.getNumber());
            if (entity != null) {
                response.setStatus(1);
                response.setId(entity.getId());
                response.setMessage("考试编号已经存在了");
                return response;
            }
        }
        if (entity == null) {
            entity = new Exam();
        }
        entity.setNumber(request.getNumber());
        entity.setStart(request.getStart());
        entity.setMinute(request.getMinute());
        entity.setCheckPaperTime(request.getCheckPaperTime());
        entity.setStatus(request.getStatus());
        entity = this.examRepository.save(entity);
        response.setId(entity.getId());
        return response;
    }

    public BaseResponse deleteExam(Long id) {
        BaseResponse response = new BaseResponse();
        Optional<Exam> optional = this.examRepository.findById(id);
        if (optional.isPresent()) {
            Exam exam = optional.get();
            List<Student> students = this.studentRepository.findAllByExamNumber(exam.getNumber());
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
        Page<Exam> page = this.examRepository.findAll(request.getPageable());
        response.setList(page.getContent());
        response.setTotle(page.getTotalElements());
        return response;
    }

    public ListResponse<Paper> activePaperList() {
        ListResponse<Paper> response = new ListResponse<>();
        List<Paper> list = this.paperRepository.findAllByStatus(1);
        response.setList(list);
        return response;
    }

    public IdResponse addStudent(StudentRequest request) {
        IdResponse response = new IdResponse();
        Student entity = new Student();
        if (request.getId() > 0) {
            Optional<Student> optional = this.studentRepository.findById(request.getId());
            if (optional.isPresent()) {
                entity = optional.get();
            }
        }
        entity.setName(request.getName());
        entity.setNumber(request.getNumber());
        entity.setExamNumber(request.getExamNumber());
        entity.setFlag(request.getFlag());
        entity.setPaperId(request.getPaperId());
        entity.setIsFinished(request.getIsFinished());
        try {
            entity = this.studentRepository.save(entity);
            response.setId(entity.getId());
        } catch (Exception e) {
            response.setStatus(1);
            response.setMessage(e.getMessage());
            response.setId(0L);
        }
        return response;
    }

    public BaseResponse deleteStudent(Long id) {
        BaseResponse response = new BaseResponse();
        this.studentRepository.deleteById(id);
        return response;
    }

    public ListResponse<Student> listStudentByExamNumber(String number) {
        ListResponse<Student> response = new ListResponse<>();
        List<Student> list = this.studentRepository.findAllByExamNumber(number);
        response.setList(list);
        return response;
    }

    public BaseResponse password(PasswordRequest request) {
        BaseResponse response = new BaseResponse();
        User user = this.userRepository.findByUsername(request.getAccount());
        if (user != null) {
            user.setPassword(this.bCryptPasswordEncoder.encode(request.getPassword()));
            this.userRepository.save(user);
        } else {
            response.setStatus(1);
            response.setMessage("账户已注销");
        }
        return response;
    }
}