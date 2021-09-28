package blog.vgarg.todos.representation;

import java.io.Serializable;
import java.util.List;

public class TodoList implements Serializable {
    private static final long serialVersionUID = -2200762727557964664L;

    private final List<String> todos;

    public TodoList(List<String> todos) {
        this.todos = todos;
    }

    public List<String> getTodos() {
        return todos;
    }
}
