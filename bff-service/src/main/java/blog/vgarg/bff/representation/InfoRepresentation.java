package blog.vgarg.bff.representation;

import java.io.Serializable;
import java.util.List;

public class InfoRepresentation implements Serializable {
    private static final long serialVersionUID = -4164834254117131288L;
    private final List<String> todos;
    private final List<String> tweets;

    public InfoRepresentation(TodoList todos, TweetList tweets) {
        this.todos = todos.getTodos();
        this.tweets = tweets.getTweets();
    }

    public List<String> getTodos() {
        return todos;
    }

    public List<String> getTweets() {
        return tweets;
    }
}
