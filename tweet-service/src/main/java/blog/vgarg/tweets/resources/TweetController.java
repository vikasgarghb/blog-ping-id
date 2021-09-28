package blog.vgarg.tweets.resources;

import blog.vgarg.tweets.representation.TweetList;
import blog.vgarg.tweets.services.TweetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tweets")
@CrossOrigin(origins = "*")
public class TweetController {

    private TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping()
    public ResponseEntity<TweetList> getTweets() {
        return new ResponseEntity<>(new TweetList(tweetService.getTweets()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getTweet(@PathVariable("id") int id) {
        try {
            return new ResponseEntity<>(tweetService.getTweet(id), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<String> postTweet(@RequestBody String tweet) {
        tweetService.postTweet(tweet);
        return new ResponseEntity<>(tweet + " added successfully.", HttpStatus.OK);
    }
}
