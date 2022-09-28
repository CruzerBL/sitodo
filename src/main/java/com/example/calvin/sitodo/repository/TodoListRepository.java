package com.example.calvin.sitodo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.calvin.sitodo.model.TodoItem;

public interface TodoListRepository extends CrudRepository<TodoItem, Long>{

}
