package com.luv2code.springboot.todos.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ExceptionResponses {
    private int statusCode;
    private String message;
    private LocalDateTime timestamp;
}
