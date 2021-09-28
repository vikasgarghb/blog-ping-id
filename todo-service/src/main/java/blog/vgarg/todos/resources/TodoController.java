package blog.vgarg.todos.resources;

import blog.vgarg.todos.representation.TodoList;
import blog.vgarg.todos.services.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/todos")
@CrossOrigin(origins = "*")
public class TodoController {

    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping()
    public ResponseEntity<TodoList> getTodos() {
        return new ResponseEntity<>(new TodoList(todoService.getTodos()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getTodo(@PathVariable("id") int id) {
        try {
            return new ResponseEntity<>(todoService.getTodo(id), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<String> addTodo(@RequestBody String todo) {
        todoService.addTodo(todo);
        return new ResponseEntity<>(todo + " added successfully.", HttpStatus.OK);
    }
}
