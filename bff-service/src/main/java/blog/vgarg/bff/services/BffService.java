package blog.vgarg.bff.services;

import blog.vgarg.bff.clients.TodoServiceClient;
import blog.vgarg.bff.clients.TweetServiceClient;
import blog.vgarg.bff.representation.InfoRepresentation;
import blog.vgarg.bff.representation.TodoList;
import blog.vgarg.bff.representation.TweetList;
import org.springframework.stereotype.Service;

@Service
public class BffService {
    private final TodoServiceClient todoServiceClient;
    private final TweetServiceClient tweetServiceClient;

    public BffService(TodoServiceClient todoServiceClient, TweetServiceClient tweetServiceClient) {
        this.todoServiceClient = todoServiceClient;
        this.tweetServiceClient = tweetServiceClient;
    }

    public InfoRepresentation getInfo() {
        TodoList todos = todoServiceClient.getTodos();
        TweetList tweets = tweetServiceClient.getTweets();

        return new InfoRepresentation(todos, tweets);
    }

    public void addTodo(String todo) {
        todoServiceClient.addTodo(todo);
    }

    public void addTweet(String tweet) {
        tweetServiceClient.addTweet(tweet);
    }
}
