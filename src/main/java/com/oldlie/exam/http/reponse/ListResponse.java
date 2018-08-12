package com.oldlie.exam.http.reponse;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListResponse<T> extends BaseResponse {
    private final static long serialVersionUID = 153407041057L;
    private List<T> list;
}