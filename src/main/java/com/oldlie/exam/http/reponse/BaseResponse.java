package com.oldlie.exam.http.reponse;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class BaseResponse implements Serializable {

    private final static long serialVersionUID = 153404790911L;

    @Getter
    @Setter
    private int status = 0;
    @Getter
    @Setter
    private String message = "success";
}