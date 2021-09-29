package blog.vgarg.todos.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("trust-store")
public class TrustStoreConfiguration {
    private String path;
    private String password;

    public TrustStoreConfiguration() {
        path = null;
        password = null;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
