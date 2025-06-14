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

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepo;

    private final FindAuthenticatedUser findAuthenticatedUser;


    @Override
    public List<TodoResponse> getAllTodos() {
        SecurityUser securityUser = findAuthenticatedUser.getAuthenticatedUser();

        List<Todo> todos = todoRepo.findByOwner(securityUser.getUser());

        return todos.stream()
                .map(this::buildTodoResponse)
                .toList();
    }

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

        return buildTodoResponse(savedTodo);
    }

    private TodoResponse buildTodoResponse(Todo todo) {
        return TodoResponse.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .priority(todo.getPriority())
                .complete(todo.isComplete())
                .build();
    }
}
