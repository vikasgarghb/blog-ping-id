package blog.vgarg.tweets.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TweetService {
    private static List<String> tweets = new ArrayList<>();

    public List<String> getTweets() {
        return tweets;
    }

    public String getTweet(int idx) {
        if (tweets.size() == 0) {
            throw new RuntimeException("No tweets posted.");
        }
        if (idx >= tweets.size()) {
            throw new RuntimeException("Requested tweet do not exist.");
        }
        return tweets.get(idx);
    }

    public void postTweet(String tweet) {
        tweets.add(tweet);
    }
}
