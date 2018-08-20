package com.oldlie.exam.repository;

import java.util.List;

import com.oldlie.exam.entity.Exam;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    Exam findByNumber(String number);

    List<Exam> findAllByStatus(int status);
}