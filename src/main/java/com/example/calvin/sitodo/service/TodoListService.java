package com.example.calvin.sitodo.service;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.calvin.sitodo.model.TodoItem;
import com.example.calvin.sitodo.model.TodoList;
import com.example.calvin.sitodo.repository.TodoListRepository;

@Service
public class TodoListService {

    private static final Logger LOG = LoggerFactory.getLogger(TodoListService.class);
    private static final String TODO_LIST_DOES_NOT_EXIST_FMT = "TodoList(id=%d) does not exist";

    private TodoListRepository todoListRepository;

    @Autowired
    public void setTodoListRepository(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    public TodoList getTodoListById(Long id) throws NoSuchElementException {
        Optional<TodoList> result = todoListRepository.findById(id);

        return result.get();
    }

    public TodoList addTodoItem(TodoItem todoItem) {
        TodoList list = new TodoList(List.of(todoItem));

        return todoListRepository.save(list);
    }

    public TodoList addTodoItem(Long id, TodoItem todoItem) throws NoSuchElementException {
        Optional<TodoList> result = todoListRepository.findById(id);

        TodoList foundList = result.get();
        foundList.addTodoItem(todoItem);

        return todoListRepository.save(foundList);
    }

    public TodoList updateTodoItem(Long listId, Long itemId, Boolean status) throws NoSuchElementException {
        Optional<TodoList> result = todoListRepository.findById(listId);
        
        // TODO: Implement me!
        TodoList list;
        list = result.get();
        if(list!=null) {
        	for (TodoItem item : list.getItems()) {
				if(item.getId().equals(itemId)) {
					item.setFinished(status);
					break;
				}
			}
        }
        return todoListRepository.save(list);
    }

    public Boolean deleteTodoItem(Long listId, Long itemId) throws NoSuchElementException {
        Optional<TodoList> result = todoListRepository.findById(listId);
        
        // TODO: Implement me!
        if(result.isEmpty()) {
        	return Boolean.FALSE;
        }
        TodoList todoList = result.get();
        List<TodoItem> listOfItem = todoList.getItems();
        
        int index = -1;
        for (int i=0;i<listOfItem.size();i++) {
        	if(listOfItem.get(i).getId().equals(itemId)) {
        		index = i;
        	}
        }
        if(index!=-1) {
        	listOfItem.remove(index);
        }
		
        todoListRepository.save(todoList);
        return Boolean.TRUE;
    }
}