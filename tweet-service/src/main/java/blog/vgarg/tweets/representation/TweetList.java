package blog.vgarg.tweets.representation;

import java.io.Serializable;
import java.util.List;

public class TweetList implements Serializable {
    private static final long serialVersionUID = 1897224280697795089L;

    private final List<String> tweets;

    public TweetList(List<String> tweets) {
        this.tweets = tweets;
    }

    public List<String> getTweets() {
        return tweets;
    }
}
