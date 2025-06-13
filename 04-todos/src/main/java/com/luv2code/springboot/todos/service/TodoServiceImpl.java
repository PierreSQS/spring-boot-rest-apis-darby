package com.luv2code.springboot.todos.service;

import com.luv2code.springboot.todos.entity.SecurityUser;
import com.luv2code.springboot.todos.entity.Todo;
import com.luv2code.springboot.todos.repository.TodoRepository;
import com.luv2code.springboot.todos.request.TodoRequest;
import com.luv2code.springboot.todos.response.TodoResponse;
import com.luv2code.springboot.todos.util.FindAuthenticatedUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepo;

    private final FindAuthenticatedUser findAuthenticatedUser;


    @Transactional
    @Override
    public TodoResponse createTodo(TodoRequest todoRequest) {

        SecurityUser securityUser = findAuthenticatedUser.getAuthenticatedUser();

        Todo todo = Todo.builder()
                .title(todoRequest.getTitle())
                .description(todoRequest.getDescription())
                .owner(securityUser.getUser())
                .complete(false)
                .build();

        Todo savedTodo = todoRepo.save(todo);
        log.info("Saved new todo for user: {}", securityUser.getEmail());

        return TodoResponse.builder()
                .id(savedTodo.getId())
                .title(savedTodo.getTitle())
                .description(savedTodo.getDescription())
                .priority(savedTodo.getPriority())
                .complete(savedTodo.isComplete())
                .build();
    }
}
