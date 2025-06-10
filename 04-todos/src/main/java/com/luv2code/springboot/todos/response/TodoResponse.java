package com.luv2code.springboot.todos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TodoResponse {
    private long id;
    private String title;
    private String description;
    private int priority;
    private boolean complete;

}
