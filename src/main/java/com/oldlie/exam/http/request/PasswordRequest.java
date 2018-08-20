package com.oldlie.exam.http.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordRequest {
    private String account;
    private String password;
}