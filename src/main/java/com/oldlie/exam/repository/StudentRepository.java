package com.oldlie.exam.repository;

import java.util.List;

import com.oldlie.exam.entity.Student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAllByExamNumber(String examNumber);

    Student findOneByNameAndNumberAndExamNumber(String name, String number, String examNumber);
}
