package com.example.calvin.sitodo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.calvin.sitodo.model.TodoItem;
import com.example.calvin.sitodo.service.TodoListService;

@Controller
public class TodoListController {

    private TodoListService todoListService;

    @Autowired
    public void setTodoListService(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @GetMapping("/list")
    public String showList(Model model) {
        List<TodoItem> todoItems = todoListService.getTodoItems();

        model.addAttribute("todoList", todoItems);

        return "list";
    }
    @PostMapping("/list")
    public String addItemToList(@ModelAttribute TodoItem todoItem, Model model) {
    	todoListService.addTodoItem(todoItem);
    	
    	List<TodoItem> todoItems = todoListService.getTodoItems();

        model.addAttribute("todoList", todoItems);

        return "list";
    }
}
