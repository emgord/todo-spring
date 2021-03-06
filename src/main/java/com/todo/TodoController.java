package com.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping(value="/todos/{todoId}/toggle", method=RequestMethod.POST)
    public String toggleTodo(@PathVariable("todoId") Long todoId) {
        Todo todo = todoRepository.findOne(todoId);
        todo.toggleCompleted();
        todoRepository.save(todo);
        return "redirect:/";
    }

    @RequestMapping(value="todos/{todoId}/delete", method=RequestMethod.POST)
    public String deleteTodo(@PathVariable("todoId") Long todoId) {
        todoRepository.delete(todoId);
        return "redirect:/";
    }

    @RequestMapping(value="todos/{todoId}", method=RequestMethod.POST)
    public String editTodo(@PathVariable("todoId") Long todoId, @RequestParam("action") String action){
        Todo todo = todoRepository.findOne(todoId);
        todo.setAction(action);
        todoRepository.save(todo);
        return "redirect:/";
    }

    @RequestMapping(value="todos/{todoId}/edit", method=RequestMethod.GET)
    public String editTodoForm(@PathVariable("todoId") Long todoId, Model model) {
        Todo todo = todoRepository.findOne(todoId);
        if (todo == null) {
            return "redirect:/";
        } else {
            model.addAttribute("todo", todo);
            return "editTodo";
        }
    }
}
