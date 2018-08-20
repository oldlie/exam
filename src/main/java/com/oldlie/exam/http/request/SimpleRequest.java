package com.oldlie.exam.http.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleRequest<T> {
    T value;
}