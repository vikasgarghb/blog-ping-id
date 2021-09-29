package blog.vgarg.todos;

import blog.vgarg.todos.config.TrustStoreConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class BlogTodosSpringApplication {

    @Autowired
    TrustStoreConfiguration trustStoreConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(BlogTodosSpringApplication.class, args);
    }

    @PostConstruct
    private void setUpTrustStoreForApplication() {
        System.setProperty("javax.net.ssl.trustStore", trustStoreConfiguration.getPath());
        System.setProperty("javax.net.ssl.trustStorePassword", trustStoreConfiguration.getPassword());
    }
}
