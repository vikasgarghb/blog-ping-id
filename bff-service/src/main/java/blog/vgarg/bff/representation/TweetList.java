package blog.vgarg.bff.representation;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class TweetList implements Serializable {
    private static final long serialVersionUID = -7775912146887799705L;
    private final List<String> tweets;

    public TweetList() {
        tweets = Collections.emptyList();
    }

    public TweetList(List<String> tweets) {
        this.tweets = tweets;
    }

    public List<String> getTweets() {
        return tweets;
    }
}
