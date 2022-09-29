package com.example.calvin.sitodo.controller;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.calvin.sitodo.model.TodoItem;
import com.example.calvin.sitodo.model.TodoList;
import com.example.calvin.sitodo.service.TodoListService;

@Controller
public class TodoListController {

    private static final Logger LOG = LoggerFactory.getLogger(TodoListController.class);

    private TodoListService todoListService;

    @Autowired
    public void setTodoListService(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @GetMapping("/list")
    public String showList(TodoList todoList, Model model) {
        model.addAttribute("todoList", todoList);

        return "list";
    }

    @GetMapping("/list/{id}")
    public String showList(@PathVariable("id") Long id, Model model) {
        TodoList foundTodoList = todoListService.getTodoListById(id);

        model.addAttribute("todoList", foundTodoList);

        return "list";
    }

    @PostMapping("/list")
    public String newItem(@RequestParam("item_text") String item) {
        TodoList saved = todoListService.addTodoItem(new TodoItem(item));

        return redirectToList(saved.getId());
    }

    @PostMapping("/list/{id}")
    public String newItem(@PathVariable("id") Long id,
                          @RequestParam("item_text") String item) {
        TodoList saved = todoListService.addTodoItem(id, new TodoItem(item));

        return redirectToList(saved.getId());
    }

    @GetMapping("/list/{list_id}/update/{item_id}")
    public String updateItem(@PathVariable("list_id") Long listId,
                             @PathVariable("item_id") Long itemId,
                             @RequestParam("finished") Boolean finished) {
    	todoListService.updateTodoItem(listId, itemId, finished);
        return String.format("redirect:/list/%d", listId); // TODO: Implement me!
    }

    // TODO: Create a method named deleteItem() that will remove a todo item from a todo list.
    //       The arguments can be similar to the updateItem() above.
    @GetMapping("/list/{list_id}/delete/{item_id}")
    public String deleteItem(@PathVariable("list_id") Long listId,
                             @PathVariable("item_id") Long itemId) {
    	todoListService.deleteTodoItem(listId, itemId);
        return String.format("redirect:/list/%d", listId);
    }
    
    @ExceptionHandler
    public String handleException(NoSuchElementException exception) {
        return "404";
    }

    private String redirectToList(Long id) {
        return String.format("redirect:/list/%d", id);
    }
}
