package com.luv2code.springboot.todos.repository;

import com.luv2code.springboot.todos.entity.Todo;
import com.luv2code.springboot.todos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByOwner(User owner);
}
