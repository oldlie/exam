package com.oldlie.exam.http.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaperRequest {
    private long id;
    private String number;
    private String content;
}