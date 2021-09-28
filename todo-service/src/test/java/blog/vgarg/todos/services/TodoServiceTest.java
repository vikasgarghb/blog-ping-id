package blog.vgarg.todos.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TodoServiceTest {
    private TodoService todoService;

    @BeforeEach
    public void setup() {
        todoService = new TodoService();
    }

    @Test
    public void testTodos() {
        assertThat(todoService.getTodos()).hasSize(0);
    }
}
