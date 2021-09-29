package blog.vgarg.tweets;

import blog.vgarg.tweets.config.TrustStoreConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class BlogTweetsSpringApplication {

    @Autowired
    TrustStoreConfiguration trustStoreConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(BlogTweetsSpringApplication.class, args);
    }

    @PostConstruct
    private void setUpTrustStoreForApplication() {
        System.setProperty("javax.net.ssl.trustStore", trustStoreConfiguration.getPath());
        System.setProperty("javax.net.ssl.trustStorePassword", trustStoreConfiguration.getPassword());
    }
}
