package com.example.calvin.sitodo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.calvin.sitodo.model.TodoItem;
import com.example.calvin.sitodo.model.TodoList;

public interface TodoListRepository extends CrudRepository<TodoList, Long>{

}
