package com.oldlie.exam.http.reponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleResponse<T> extends BaseResponse {
    
    private T value;
}