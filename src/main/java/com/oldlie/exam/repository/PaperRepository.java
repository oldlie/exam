package com.oldlie.exam.repository;

import java.util.List;

import com.oldlie.exam.entity.Paper;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperRepository extends JpaRepository<Paper, Long> {
    
    Paper findByNumber(String number);

    List<Paper> findAllByStatus(int status);
}