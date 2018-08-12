package com.oldlie.exam.repository;

import com.oldlie.exam.entity.Exam;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {}