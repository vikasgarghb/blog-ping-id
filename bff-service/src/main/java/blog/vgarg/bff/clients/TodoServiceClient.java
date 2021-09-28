package blog.vgarg.bff.clients;

import blog.vgarg.bff.representation.TodoList;
import blog.vgarg.bff.util.TokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TodoServiceClient extends BaseClient {

    private static final String SERVICE_ID = "todo";

    @Value("${todo.serviceUrl}")
    private String serviceUrl;

    @Value("${todo.audience}")
    private String audience;

    @Value("${todo.clientId}")
    private String clientId;

    @Value("${todo.clientSecret}")
    private String clientSecret;

    public TodoServiceClient(TokenUtil tokenUtil, RestTemplate restTemplate) {
        super(tokenUtil, restTemplate, SERVICE_ID);
    }

    public TodoList getTodos() {
        ResponseEntity<TodoList> response = getRestTemplate().exchange(serviceUrl + "/todos", HttpMethod.GET,
                getRequest(null, audience, clientId, clientSecret), TodoList.class);
        return response.getBody();
    }

    public void addTodo(String todo) {
        getRestTemplate().exchange(serviceUrl + "/todos", HttpMethod.POST,
                getRequest(todo, audience, clientId, clientSecret), String.class);
    }
}
