package com.example.calvin.sitodo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.calvin.sitodo.model.TodoItem;
import com.example.calvin.sitodo.model.TodoList;
import com.example.calvin.sitodo.repository.TodoListRepository;

@SpringBootTest
class TodoListServiceTest {

	@Autowired
    private TodoListService todoListService;

    @MockBean
    private TodoListRepository todoListRepository;

    @Test
    @DisplayName("Given an existing ID, getTodoListById should return an existing list")
    void getTodoListById_ok() {
        TodoList todoList = createTodoList("Buy milk");
        when(todoListRepository.findById(anyLong())).thenReturn(Optional.of(todoList));

        TodoList savedList = todoListService.getTodoListById(1L);

        assertFalse(savedList.getItems().isEmpty());
    }

    @Test
    @DisplayName("Suppose the list does not exist, getTodoListById should throw an exception")
    void getTodoListById_exception() {
        when(todoListRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> todoListService.getTodoListById(1L));
    }

    @Test
    @DisplayName("Given a new todo item, addTodoItem should save the item into a new list")
    void addTodoItem_ok() {
        TodoItem todoItem = new TodoItem("Buy milk");
        when(todoListRepository.save(any(TodoList.class))).thenReturn(new TodoList(List.of(todoItem)));

        TodoList savedList = todoListService.addTodoItem(todoItem);
        TodoItem savedTodoItem = savedList.getItems().get(0);

        assertFalse(savedList.getItems().isEmpty());
        assertEquals("Buy milk", savedTodoItem.getTitle());
    }

    @Test
    @DisplayName("Given a todo item, addTodoItem should save the item into an existing list")
    void addTodoItem_existingList_ok() {
        TodoList list = createTodoList("Buy milk");
        when(todoListRepository.findById(anyLong())).thenReturn(Optional.of(list));

        todoListService.addTodoItem(1L, new TodoItem("Touch grass"));

        assertEquals(2, list.getItems().size(), "The numbers of items in the list: " + list.getItems().size());
    }

    @Test
    @DisplayName("Suppose the list does not exist, addTodoItem should throw an exception")
    void addTodoItem_existingList_exception() {
        when(todoListRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> todoListService.addTodoItem(1L, new TodoItem("Buy milk")));
    }

    @Test
    @DisplayName("Given an existing list with an item, updateTodoItem should update the status of an item")
    void updateTodoItem_ok() {
        // TODO: Implement me!
    	TodoItem item = new TodoItem("Buy milk");
    	item.setId(1L);
    	TodoList mockList = new TodoList(List.of(item));
    	when(todoListRepository.findById(anyLong())).thenReturn(Optional.of(mockList));
    	mockList.getItems().get(0).setFinished(true);
		assertTrue(item.getFinished());
    }

    @Test
    @DisplayName("Suppose the list does not exist, updateTodoItem should throw an exception")
    void updateTodoItem_exception() {
        when(todoListRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> todoListService.updateTodoItem(1L, 2L, true));
    }

    // TODO: Create tests for deleteTodoItem
    @Test
    @DisplayName("Given an existing list with an item, deleteTodoItem should delete the item")
    void deleteTodoItem_success() {
    	TodoItem item = new TodoItem("Buy milk");
    	item.setId(1L);
    	TodoList mockList = new TodoList(List.of(item));
    	when(todoListRepository.findById(anyLong())).thenReturn(Optional.of(mockList));
    	todoListService.deleteTodoItem(1L,1L);
    	assertTrue(item==null);
    }
    
    private TodoList createTodoList(String... items) {
        TodoList list = new TodoList(new ArrayList<>());

        Arrays.stream(items)
            .map(TodoItem::new)
            .forEach(list::addTodoItem);

        return list;
    }
}
