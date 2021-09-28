package blog.vgarg.bff.representation;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class TodoList implements Serializable {

    private static final long serialVersionUID = -6159024856296037465L;
    private final List<String> todos;

    public TodoList() {
        todos = Collections.emptyList();
    }

    public TodoList(List<String> todos) {
        this.todos = todos;
    }

    public List<String> getTodos() {
        return todos;
    }
}
