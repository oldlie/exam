package com.oldlie.exam.http.reponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageResponse<T> extends ListResponse<T> {
    private final static long serialVersionUID = 153406908485L;

    private Long totle;
}