package com.example.calvin.sitodo.controller;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.calvin.sitodo.model.TodoItem;
import com.example.calvin.sitodo.model.TodoList;
import com.example.calvin.sitodo.service.TodoListService;
@WebMvcTest(TodoListController.class)
class TodoListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoListService todoListService;

    @Test
    @DisplayName("HTTP GET '/list' retrieves list view")
    void showList_resolvesToIndex() throws Exception {
        mockMvc.perform(get("/list")).andExpectAll(
            status().isOk(),
            content().contentTypeCompatibleWith(TEXT_HTML),
            content().encoding(UTF_8),
            view().name("list")
        );
    }

    @Test
    @DisplayName("HTTP GET '/list' returns an HTML page")
    void showList_returnsHtml() throws Exception {
        mockMvc.perform(get("/list")).andExpectAll(
            status().isOk(),
            content().contentTypeCompatibleWith(TEXT_HTML),
            content().encoding(UTF_8),
            content().string(containsString("</html>"))
        );
    }

    @Test
    @DisplayName("HTTP GET '/list/{id}' returns an HTML page with non-empty list")
    void showList_byId_returnsHtml() throws Exception {
        TodoItem mockTodoItem = createMockTodoItem(1L, "Buy milk");
        TodoList mockList = mock(TodoList.class);
        when(mockList.getId()).thenReturn(1L);
        when(mockList.getItems()).thenReturn(List.of(mockTodoItem));
        when(todoListService.getTodoListById(anyLong())).thenReturn(mockList);

        mockMvc.perform(get("/list/1")).andExpectAll(
            status().isOk(),
            content().contentTypeCompatibleWith(TEXT_HTML),
            content().encoding(UTF_8),
            content().string(containsString("<table")),
            content().string(containsString("<tr")),
            content().string(containsString("Buy milk")),
            content().string(containsString("</html>"))
        );
    }

    @Test
    @DisplayName("Suppose the given ID does not exist, HTTP GET '/list/{id}' returns an error page")
    void showList_byId_notFound() throws Exception {
        when(todoListService.getTodoListById(anyLong())).thenThrow(NoSuchElementException.class);

        mockMvc.perform(get("/list/1")).andExpectAll(
            content().string(containsString("Not Found"))
        );
    }

    @Test
    @DisplayName("HTTP GET '/list/{id}/update/{item_id}' successfully updated status of an item")
    void updateItem_ok() throws Exception {
        // TODO: Implement me!
    	 String text = "Buy milk";
         
         mockMvc.perform(post("/list").param("item_text",text)).andExpectAll(
             status().is3xxRedirection()  
         );
         verify(todoListService, times(1)).updateTodoItem(anyLong(),anyLong(),anyBoolean());
    }

    // TODO: Create the tests for ensuring the correctness of deleteItem() method from the controller.

    private TodoList createMockTodoList(Long id, TodoItem ... items) {
        TodoList mockTodoList = mock(TodoList.class);

        when(mockTodoList.getId()).thenReturn(id);
        when(mockTodoList.getItems()).thenReturn(List.of(items));

        return mockTodoList;
    }

    private TodoItem createMockTodoItem(Long id, String title) {
        TodoItem mockTodoItem = mock(TodoItem.class);

        when(mockTodoItem.getId()).thenReturn(id);
        when(mockTodoItem.getTitle()).thenReturn(title);
        when(mockTodoItem.getFinished()).thenCallRealMethod();

        return mockTodoItem;
    }
}