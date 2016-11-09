package com.todo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.
        MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.
        MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        classes = TodoApplication.class)
@WebAppConfiguration

public class TodoControllerTests {

    @Autowired
    private WebApplicationContext webContext;

    @Autowired
    private TodoRepository todoRepository;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webContext)
                .build();
        this.todoRepository.deleteAllInBatch();
    }

    @Test
    public void emptyList() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("todoList"))
                .andExpect(model().attributeExists("todos"))
                .andExpect(model().attribute("todos", is(empty())));
    }

    @Test
    public void createTodo() throws Exception {
        mockMvc.perform(post("/todos")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("action", "Important Stuff")
        .param("description", "Extremely Important Stuff"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/"));
        Todo expectedTodo = new Todo("Important Stuff", "Extremely Important Stuff");
        expectedTodo.setId(3L);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("todoList"))
                .andExpect(model().attributeExists("todos"))
                .andExpect(model().attribute("todos", hasSize(1)))
                .andExpect(model().attribute("todos", contains(samePropertyValuesAs(expectedTodo))));
    }
}
