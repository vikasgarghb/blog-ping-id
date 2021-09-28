package blog.vgarg.todos.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TodoService {
    private static List<String> todos = new ArrayList<>();

    public List<String> getTodos() {
        return todos;
    }

    public String getTodo(int idx) {
        if (todos.size() == 0) {
            throw new RuntimeException("No todos defined");
        }
        if (idx >= todos.size()) {
            throw new RuntimeException("Requested todo do not exist.");
        }
        return todos.get(idx);
    }

    public void addTodo(String todo) {
        todos.add(todo);
    }
}
