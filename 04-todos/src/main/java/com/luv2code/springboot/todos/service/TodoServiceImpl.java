package com.luv2code.springboot.todos.service;

import com.luv2code.springboot.todos.entity.SecurityUser;
import com.luv2code.springboot.todos.entity.Todo;
import com.luv2code.springboot.todos.repository.TodoRepository;
import com.luv2code.springboot.todos.request.TodoRequest;
import com.luv2code.springboot.todos.response.TodoResponse;
import com.luv2code.springboot.todos.util.FindAuthenticatedUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
        log.info("Saved new todo with id: {} for user: {}", savedTodo.getId(), securityUser.getEmail());

        return buildTodoResponse(savedTodo);
    }

    @Transactional
    @Override
    public TodoResponse toggleTodoCompletion(long id) {
        SecurityUser securityUser = findAuthenticatedUser.getAuthenticatedUser();

        Todo todo = todoRepo.findByIdAndOwner(id, securityUser.getUser())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Todo with id: " + id+ " not found for user: "+ securityUser.getEmail()));

        todo.setComplete(!todo.isComplete());
        Todo updatedTodo = todoRepo.save(todo);
        log.info("Toggled completion for todo with id: {} for user: {}", id, securityUser.getEmail());

        return buildTodoResponse(updatedTodo);
    }

    @Transactional
    @Override
    public void deleteTodo(long id) {
        SecurityUser securityUser = findAuthenticatedUser.getAuthenticatedUser();

        Todo todo = todoRepo.findByIdAndOwner(id, securityUser.getUser())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Todo with id: " + id + " not found for user: "+ securityUser.getEmail()));

        todoRepo.delete(todo);

        log.info("Deleted todo with id: {} for user: {}", id, securityUser.getEmail());
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
