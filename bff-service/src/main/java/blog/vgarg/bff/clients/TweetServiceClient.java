package blog.vgarg.bff.clients;

import blog.vgarg.bff.representation.TweetList;
import blog.vgarg.bff.util.TokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TweetServiceClient extends BaseClient {
    private static final String SERVICE_ID = "tweet";

    @Value("${tweet.serviceUrl}")
    private String serviceUrl;

    @Value("${tweet.audience}")
    private String audience;

    @Value("${tweet.clientId}")
    private String clientId;

    @Value("${tweet.clientSecret}")
    private String clientSecret;

    public TweetServiceClient(TokenUtil tokenUtil, RestTemplate restTemplate) {
        super(tokenUtil, restTemplate, SERVICE_ID);
    }

    public TweetList getTweets() {
        ResponseEntity<TweetList> response = getRestTemplate().exchange(serviceUrl + "/tweets", HttpMethod.GET,
                getRequest(null, audience, clientId, clientSecret), TweetList.class);
        return response.getBody();
    }

    public void addTweet(String tweet) {
        getRestTemplate().exchange(serviceUrl + "/tweets", HttpMethod.POST,
                getRequest(tweet, audience, clientId, clientSecret), String.class);
    }


}
