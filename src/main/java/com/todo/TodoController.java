package com.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/")
public class TodoController {

    private final TodoRepository todoRepository;

    @Autowired
    TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String todoList (Model model) {
        List<Todo> todos = todoRepository.findAll();
        if (todos != null) {
            model.addAttribute("todos", todos);
        }
        return "todoList";
    }

    @RequestMapping(value="/todos", method=RequestMethod.POST)
    public String createTodo(Todo todo) {
        todoRepository.save(todo);
        return "redirect:/";
    }
}
