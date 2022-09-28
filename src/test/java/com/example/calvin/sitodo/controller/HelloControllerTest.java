package com.example.calvin.sitodo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.example.calvin.sitodo.HelloController;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;


//New annotation

@WebMvcTest(HelloController.class)
class HelloControllerTest {

 // New instance variable
 @Autowired
 private MockMvc mockMvc;

 @Test
 void showHello_ok() {
     // Omitted for brevity
	 HelloController helloController = new HelloController();

     // [Exercise]
     String result = helloController.showHello();

     // [Verify]
     assertEquals("hello", result);

     // [Teardown]
     // Do nothing
 } 

 @Test
 void showHello_okResponse() throws Exception {
     mockMvc.perform(get("/hello")).andExpectAll(
         status().isOk(),
         content().contentTypeCompatibleWith(TEXT_HTML),
         content().encoding(UTF_8),
         view().name("hello")
     );
 }
}