package com.oldlie.exam.http.request;

import java.io.Serializable;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.Getter;
import lombok.Setter;

public class PageRequest implements Serializable {
    private final static long serialVersionUID = 153407095365L;

    @Getter
    @Setter
    private int page;

    @Getter
    @Setter
    private int size;

    @Getter
    @Setter
    private String orderBy;

    @Getter
    @Setter
    private int order;

    public Pageable getPageable() {
        return org.springframework.data.domain.PageRequest.of(this.page, this.size,
                Sort.by(order > 0 ? Sort.Direction.DESC : Sort.Direction.ASC, this.orderBy));
    }
}